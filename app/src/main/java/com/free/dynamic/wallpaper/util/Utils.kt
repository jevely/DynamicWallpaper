package com.flash.light.free.good.fashioncallflash.util

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import android.view.KeyCharacterMap
import android.view.KeyEvent
import android.view.ViewConfiguration
import com.free.dynamic.wallpaper.DynamicApplication
import com.free.dynamic.wallpaper.view.WhiteDialog
import java.io.File


fun getScreen(): Point {
    val dm = DynamicApplication.getContext().resources.displayMetrics
    val point = Point(dm.widthPixels, dm.heightPixels)
    return point
}

//val saveFloadAddress =
//    Environment.getExternalStorageDirectory().absolutePath + File.separator + "wallpaper" + File.separator

val saveFloadAddress =
    DynamicApplication.getContext().filesDir.absolutePath + File.separator + "wallpaper" + File.separator

//本地读取图片
fun getWallpaperFromLocal(path: String): Bitmap {
    val bitmap = BitmapFactory.decodeFile(path)
    return bitmap
}

/**
 * 删除文件
 */
fun deleteFile(file: File): Boolean {
    if (file.isFile) {
        file.delete()
    } else if (file.isDirectory) {
        val files = file.listFiles()
        for (file1 in files) {
            deleteFile(file1)
        }
    }
    return file.exists()
}

/**
 * 白名单判断加设置
 */
fun setWhite(context: Context) {
    if (!checkIsInWhite()) {
        val whiteDiolog = WhiteDialog(context)
        whiteDiolog.show()
    }
}

/**
 * 判断是否在白名单
 */
private fun checkIsInWhite(): Boolean {
    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var isIgnoring = false
            val powerManager =
                DynamicApplication.getContext().getSystemService(Context.POWER_SERVICE) as PowerManager
            if (powerManager != null) {
                isIgnoring =
                    powerManager.isIgnoringBatteryOptimizations(DynamicApplication.getContext().getPackageName())
            }
            return isIgnoring
        }
        return true
    } catch (e: Exception) {
        e.printStackTrace()
        return true
    }
}

/**
 * 前往设置白名单
 */
fun goToWhite() {
    try {
        val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
        intent.setData(Uri.parse("package:" + DynamicApplication.getContext().getPackageName()))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        DynamicApplication.getContext().startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace();
    }
}

/**
 * 手机后台管理设置
 */
fun backManagerSet() {
    if (Build.BRAND != null && Build.BRAND.toLowerCase().equals("xiaomi")) {
        showActivity(
            "com.miui.securitycenter",
            "com.miui.permcenter.autostart.AutoStartManagementActivity"
        )
    }

    if (Build.BRAND != null) {
        if (Build.BRAND.toLowerCase().equals("huawei") || Build.BRAND.toLowerCase().equals("honor")) {
            try {
                showActivity(
                    "com.huawei.systemmanager",
                    "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity"
                )
            } catch (e: Exception) {
                e.printStackTrace()
                showActivity(
                    "com.huawei.systemmanager",
                    "com.huawei.systemmanager.optimize.bootstart.BootStartActivity"
                )
            }
        }
    }

    if (Build.BRAND != null && Build.BRAND.toLowerCase().equals("samsung")) {
        try {
            showActivity("com.samsung.android.sm_cn")
        } catch (e: Exception) {
            showActivity("com.samsung.android.sm")
        }
    }
}

/**
 * 跳转到指定应用的首页
 */
private fun showActivity(packageName: String) {
    val intent =
        DynamicApplication.getContext().packageManager.getLaunchIntentForPackage(packageName)
    DynamicApplication.getContext().startActivity(intent)
}

/**
 * 跳转到指定应用的指定页面
 */
private fun showActivity(packageName: String, activityDir: String) {
    val intent = Intent()
    intent.component = ComponentName(packageName, activityDir)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    DynamicApplication.getContext().startActivity(intent)
}


fun isNavigationBarShow(activity: Activity): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        val display = activity.getWindowManager().getDefaultDisplay()
        val size = Point()
        val realSize = Point()
        display.getSize(size)
        display.getRealSize(realSize)
        return realSize.y != size.y
    } else {
        val menu = ViewConfiguration.get(activity).hasPermanentMenuKey()
        val back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)
        return if (menu || back) {
            false
        } else {
            true
        }
    }
}

private fun getNavigationBarHeight(activity: Activity): Int {
    if (!isNavigationBarShow(activity)) {
        return 0
    }
    val resources = activity.resources
    val resourceId = resources.getIdentifier(
        "navigation_bar_height",
        "dimen", "android"
    )
    //获取NavigationBar的高度
    return resources.getDimensionPixelSize(resourceId)
}

/**
 * 获取状态栏高度
 * @param context
 * @return
 */
fun getStatusBarHeight(context: Context): Int {
    val resources: Resources = context.resources
    val resourceId: Int = resources.getIdentifier("status_bar_height", "dimen", "android")
    return resources.getDimensionPixelSize(resourceId)
}


fun getSceenHeight(activity: Activity): Int {
    return activity.windowManager.defaultDisplay.height + getNavigationBarHeight(activity) + getStatusBarHeight(
        activity
    )
}

