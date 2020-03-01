package com.free.good.dynamicwallpaper.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.flash.light.free.good.fashioncallflash.tool.ScreenTool
import com.flash.light.free.good.fashioncallflash.util.SharedPreTool
import com.flash.light.free.good.fashioncallflash.util.setWhite
import com.free.good.dynamicwallpaper.R
import com.free.good.dynamicwallpaper.adapter.MainAdapter
import com.free.good.dynamicwallpaper.view.NewGridManager

class MainActivity : AppCompatActivity(),View.OnClickListener {

    companion object{
        const val REQUEST_LIVE_PAPER = 777
        val REQUEST_UPDATE_DURATION = 888
        val REQUEST_UPDATE_SORT = 999
        val SERCIVE_1 = "com.free.good.dynamicwallpaper.service.VideoLiveWallpaper"
        val SERCIVE_2 = "com.free.good.dynamicwallpaper.service.VideoLiveWallpaper2"
    }

    private lateinit var main_setting: ImageView
    private lateinit var main_recyclerview: RecyclerView
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //获取屏幕宽度，展示来电秀的时候用，这样可以使它全屏展示
        ScreenTool.getInstance().setScreen(this)
        init()

        val count = SharedPreTool.getInstance().getInt(SharedPreTool.START_COUNT)
        if (count >= 1) {
            setWhite(this)
        }
        SharedPreTool.getInstance().putInt(SharedPreTool.START_COUNT, count + 1)
    }

    private fun init() {
        main_setting = findViewById(R.id.main_setting)
        main_recyclerview = findViewById(R.id.main_recyclerview)

        main_setting.setOnClickListener(this)

        main_recyclerview.layoutManager = NewGridManager(this, 2)
        adapter = MainAdapter()
        main_recyclerview.adapter = adapter
    }

    override fun onClick(view: View) {
        when (view.id) {
//            R.id.main_setting ->
        }
    }
}
