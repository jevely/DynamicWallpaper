package com.free.good.dynamicwallpaper.activity

import android.app.WallpaperManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.flash.light.free.good.fashioncallflash.tool.ScreenTool
import com.flash.light.free.good.fashioncallflash.util.SharedPreTool
import com.flash.light.free.good.fashioncallflash.util.SharedPreTool.Companion.getInstance
import com.flash.light.free.good.fashioncallflash.util.getScreen
import com.free.good.dynamicwallpaper.DynamicApplication
import com.free.good.dynamicwallpaper.R
import com.free.good.dynamicwallpaper.db.DataBaseTool
import com.free.good.dynamicwallpaper.db.ThemeContent
import com.free.good.dynamicwallpaper.net.DownloadCallBack
import com.free.good.dynamicwallpaper.net.NetTool
import com.free.good.dynamicwallpaper.service.VideoLiveWallpaper
import com.free.good.dynamicwallpaper.service.VideoLiveWallpaper2
import com.free.good.dynamicwallpaper.tool.DataTool
import com.free.good.dynamicwallpaper.util.Logger
import com.free.good.dynamicwallpaper.view.CallThemeBackView


class ShowActivity : AppCompatActivity() {

    private lateinit var calltheme: CallThemeBackView
    private lateinit var show_iv: ImageView
    private lateinit var call_bt: Button
    private lateinit var download_bt: Button
    private var SET_REQUEST = 1
    private lateinit var call_load: RelativeLayout
    private lateinit var downloadThread: Thread

    private lateinit var themeContent: ThemeContent

    private var isRequestWindowPermission = false

    private var currentService = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_show)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val lp = window.attributes
            lp.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER
            window.attributes = lp
        }

        init()
    }

    private fun init() {
        val intent = intent
        val position = intent.getIntExtra("position", 0)

        themeContent = DataTool.getInstance().themeList[position]

        call_load = findViewById(R.id.call_load)
        calltheme = findViewById(R.id.calltheme)
        show_iv = findViewById(R.id.show_iv)
        call_bt = findViewById(R.id.call_bt)
        download_bt = findViewById(R.id.download_bt)

        Glide
            .with(this)
            .load(themeContent.image_url)
            .placeholder(R.mipmap.defult_img)
            .override(
                ScreenTool.getInstance().getAllScreen().x,
                ScreenTool.getInstance().getAllScreen().y
            )
            .centerCrop()
            .into(show_iv)

        val url = SharedPreTool.getInstance().getString(SharedPreTool.SELECT_THEME)
        if (TextUtils.equals(url, themeContent.video_url)) {
            call_bt.text = resources.getString(R.string.theme_select)
        }

        startDownload()

        download_bt.setOnClickListener {
            startDownload()
        }

        call_bt.setOnClickListener {
            SharedPreTool.getInstance().putString(SharedPreTool.SELECT_THEME,themeContent.video_url!!)
            if (getCurrentService() == MainActivity.SERCIVE_2) {
                VideoLiveWallpaper.setToWallPaper(this)
                currentService = MainActivity.SERCIVE_1
            } else {
                VideoLiveWallpaper2.setToWallPaper(this)
                currentService = MainActivity.SERCIVE_2
            }
        }
    }

    private fun getCurrentService(): String {
        return SharedPreTool.getInstance().getString(SharedPreTool.CURRENT_SERVICE)
    }

    val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 0) {
                call_bt.visibility = View.VISIBLE
                calltheme.show(msg.obj as String?, getScreen())
            } else if (msg.what == 1) {
                Toast.makeText(
                    DynamicApplication.getContext(),
                    "Download error",
                    Toast.LENGTH_SHORT
                ).show()
                download_bt.visibility = View.VISIBLE
            }
            call_load.visibility = View.GONE
        }
    }

    private fun startDownload() {
        download_bt.visibility = View.GONE
        call_load.visibility = View.VISIBLE
        downloadThread = Thread(Runnable {
            try {
                NetTool.downloadWallPaper(
                    themeContent.video_url,
                    object : DownloadCallBack {
                        override fun downloadSuccess(path: String) {
                            Logger.d("下载主题完成")
                            val msg = handler.obtainMessage()
                            msg.what = 0
                            msg.obj = path
                            handler.sendMessage(msg)
                        }

                        override fun downloadError() {
                            Logger.d("下载主题出错")
                            handler.sendEmptyMessage(1)
                        }
                    })
            } catch (e: Exception) {
                e.printStackTrace()
                Logger.d("现在线程终止")
                handler.sendEmptyMessage(1)
            }
        })
        downloadThread.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(null)

        try {
            if (downloadThread != null) {
                downloadThread.interrupt()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MainActivity.REQUEST_LIVE_PAPER) {
            if (isLiveWallpaperChanged()) { // 记录到数据库,更新当前的Service
                Logger.d("修改壁纸成功")
                SharedPreTool.getInstance().putString(SharedPreTool.CURRENT_SERVICE, currentService)
            }
        }
    }

    /**
     * 判断是否点击了更换了动态壁纸
     *
     * @return ture 关闭应用 false 只是返回到了选择界面
     */
    private fun isLiveWallpaperChanged(): Boolean {
        val wallpaperManager =
            WallpaperManager.getInstance(this@ShowActivity) // 得到壁纸管理器
        val wallpaperInfo =
            wallpaperManager.wallpaperInfo // 如果系统使用的壁纸是动态壁纸话则返回该动态壁纸的信息,否则会返回null
        if (wallpaperInfo != null) { // 如果是动态壁纸,则得到该动态壁纸的包名,并与想知道的动态壁纸包名做比较
            val currentLiveWallpaperPackageName = wallpaperInfo.packageName
            val currentSerciceName = wallpaperInfo.serviceName
            if (currentLiveWallpaperPackageName == packageName
                && currentService == currentSerciceName
            ) {
                return true
            }
        }
        return false
    }

}
