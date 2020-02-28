package com.free.good.dynamicwallpaper.net

interface DownloadCallBack{
    fun downloadSuccess(path : String)
    fun downloadError()
}