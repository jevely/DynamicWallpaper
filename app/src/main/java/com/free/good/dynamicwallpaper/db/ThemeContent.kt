package com.free.good.dynamicwallpaper.db

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.facebook.ads.NativeAd

@Entity(tableName = "calltheme")
class ThemeContent {
    @PrimaryKey
    var id: Int? = null

    var image_url: Int? = null

    var video_url: Int? = null

    var video_name: String = ""

    var isOpen: Boolean = false

    var type: Int = 1

    @Ignore
    var fbNativeAd : NativeAd?= null
}