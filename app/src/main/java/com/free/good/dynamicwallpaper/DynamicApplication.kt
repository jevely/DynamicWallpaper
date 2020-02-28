package com.free.good.dynamicwallpaper

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Process
import androidx.multidex.MultiDex
import com.flash.light.free.good.fashioncallflash.util.SharedPreTool
import com.free.good.dynamicwallpaper.db.DataBaseTool
import com.free.good.dynamicwallpaper.tool.DataTool

class DynamicApplication : Application() {

    companion object{
        private lateinit var context: Context

        fun getContext() : Context{
            return context
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext

        //只初始化一次 限主进程
        val processName = getProcessName(this, Process.myPid())
        if (null != processName) {
            if (processName != packageName) {
                return
            }
        }

        SharedPreTool.getInstance().init(this)
        DataBaseTool.getInstance().initDataBase(this)
        DataTool.getInstance().initData()
    }

    /**
     * 获取app进程
     */
    private fun getProcessName(context: Context, pid: Int): String? {
        val am =
            context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningApps =
            am.runningAppProcesses ?: return null
        for (procInfo in runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName
            }
        }
        return null
    }

}