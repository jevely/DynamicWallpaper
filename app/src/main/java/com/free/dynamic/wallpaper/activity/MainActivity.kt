package com.free.dynamic.wallpaper.activity

import android.R.attr
import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.flash.light.free.good.fashioncallflash.tool.ScreenTool
import com.flash.light.free.good.fashioncallflash.util.SharedPreTool
import com.flash.light.free.good.fashioncallflash.util.setWhite
import com.free.dynamic.wallpaper.R
import com.free.dynamic.wallpaper.adapter.MainAdapter

class MainActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val REQUEST_LIVE_PAPER = 777
        val SERCIVE_1 = "com.free.good.dynamicwallpaper.service.VideoLiveWallpaper"
        val SERCIVE_2 = "com.free.good.dynamicwallpaper.service.VideoLiveWallpaper2"
    }

    private lateinit var main_setting: ImageView
    private lateinit var adapter: MainAdapter
    private lateinit var viewpager: ViewPager2
    private lateinit var main_add: ImageView

    private val VIDEO_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //获取屏幕宽度，展示来电秀的时候用，这样可以使它全屏展示
        ScreenTool.getInstance().setScreen(this)
        init()

        val count = SharedPreTool.getInstance().getInt(SharedPreTool.START_COUNT)
        if (count >= 1) {
            setWhite(this)
        } else {
            Thread(Thr()).start()
        }
        SharedPreTool.getInstance().putInt(SharedPreTool.START_COUNT, count + 1)
    }

    private fun init() {
        main_add = findViewById(R.id.main_add)
        main_setting = findViewById(R.id.main_setting)
        viewpager = findViewById(R.id.viewpager)
        viewpager.orientation = ViewPager2.ORIENTATION_VERTICAL

        main_setting.setOnClickListener(this)
        main_add.setOnClickListener(this)

        adapter = MainAdapter()
        viewpager.adapter = adapter
    }

    override fun onClick(view: View) {
        when (view.id) {
//            R.id.main_setting ->
            R.id.main_add -> {
                val i = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                )
                startActivityForResult(i, VIDEO_REQUEST_CODE)
            }
        }
    }

    private inner class Thr : Runnable {
        override fun run() {
            try {
                Thread.sleep(1000)
                handler.sendEmptyMessage(0)
                Thread.sleep(1000)
                handler.sendEmptyMessage(1)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    private val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            if (msg.what == 0) {
                viewpager.setCurrentItem(1, true)
            } else {
                viewpager.setCurrentItem(0, true)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == VIDEO_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val selectedVideo: Uri? = data?.getData()
            val filePathColumn = arrayOf(MediaStore.Video.Media.DATA)
            val cursor: Cursor? = contentResolver.query(
                selectedVideo!!,
                filePathColumn, null, null, null
            )
            if (cursor != null) {
                cursor.moveToFirst()
                val columnIndex: Int = cursor.getColumnIndex(filePathColumn[0])
                val VIDEOPATH = cursor.getString(columnIndex)
                cursor.close()
            }
        }
    }
}
