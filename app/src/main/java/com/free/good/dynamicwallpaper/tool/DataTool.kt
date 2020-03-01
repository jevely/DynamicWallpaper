package com.free.good.dynamicwallpaper.tool

import com.free.good.dynamicwallpaper.db.DataBaseTool
import com.free.good.dynamicwallpaper.db.ThemeContent
import com.free.good.dynamicwallpaper.net.NetUrl
import java.util.*

class DataTool {
    companion object {

        private var dataTool: DataTool? = null

        fun getInstance(): DataTool {
            if (dataTool == null) {
                @Synchronized
                if (dataTool == null) {
                    dataTool =
                        DataTool()
                }
            }
            return dataTool as DataTool
        }

        fun destroy() {
            dataTool = null
        }
    }


    val themeList = mutableListOf<ThemeContent>()
    fun initData() {
        themeList.clear()

        val list = mutableListOf<ThemeContent>()
        for (i in 0 until 81) {
            val theme = ThemeContent()
            theme.image_url = "${NetUrl.THEME}dynamic_${i + 1}.png"
            theme.video_url = "${NetUrl.THEME}dynamic_${i + 1}.mp4"
            DataBaseTool.getInstance().insertWords(theme)
//            themeList.add(theme)
            list.add(theme)
        }

//        for (i in 0 until 20) {
//            val theme = ThemeContent()
//            theme.image_url =
//                "https://s3.amazonaws.com/download.filterisq.com/jar/newcall/scenery/scenery_${i + 1}.webp"
//            theme.video_url =
//                "https://s3.amazonaws.com/download.filterisq.com/jar/newcall/scenery/scenery_${i + 1}.mp4"
//            DataBaseTool.getInstance().insertWords(theme)
//            list.add(theme)
//        }
//
//        for (i in 0 until 7) {
//            val theme = ThemeContent()
//            theme.image_url =
//                "https://s3.amazonaws.com/download.filterisq.com/jar/newcall/animal/animal_${i + 1}.webp"
//            theme.video_url =
//                "https://s3.amazonaws.com/download.filterisq.com/jar/newcall/animal/animal_${i + 1}.mp4"
//            DataBaseTool.getInstance().insertWords(theme)
//            list.add(theme)
//        }
//
//        for (i in 23 until 27) {
//            val theme = ThemeContent()
//            theme.image_url =
//                "https://s3.amazonaws.com/download.filterisq.com/jar/newcall/dance/dance_${i + 1}.png"
//            theme.video_url =
//                "https://s3.amazonaws.com/download.filterisq.com/jar/newcall/dance/dance_${i + 1}.mp4"
//            DataBaseTool.getInstance().insertWords(theme)
//            list.add(theme)
//        }
//
//        for (i in 0 until 16) {
//            val theme = ThemeContent()
//            theme.image_url =
//                "https://s3.amazonaws.com/download.filterisq.com/jar/newcall/electric/electric_${i + 1}.webp"
//            theme.video_url =
//                "https://s3.amazonaws.com/download.filterisq.com/jar/newcall/electric/electric_${i + 1}.mp4"
//            DataBaseTool.getInstance().insertWords(theme)
//            list.add(theme)
//        }
//
//        for (i in 0 until 19) {
//            val theme = ThemeContent()
//            theme.image_url =
//                "https://s3.amazonaws.com/download.filterisq.com/jar/newcall/heart/heart_${i + 1}.webp"
//            theme.video_url =
//                "https://s3.amazonaws.com/download.filterisq.com/jar/newcall/heart/heart_${i + 1}.mp4"
//            DataBaseTool.getInstance().insertWords(theme)
//            list.add(theme)
//        }
//
//        for (i in 0 until 25) {
//            if (i < 22) {
//                val theme = ThemeContent()
//                theme.image_url =
//                    "https://s3.amazonaws.com/download.filterisq.com/jar/newcall/other/other_${i + 1}.webp"
//                theme.video_url =
//                    "https://s3.amazonaws.com/download.filterisq.com/jar/newcall/other/other_${i + 1}.mp4"
//                DataBaseTool.getInstance().insertWords(theme)
//                list.add(theme)
//            } else {
//                val theme = ThemeContent()
//                theme.image_url =
//                    "https://s3.amazonaws.com/download.filterisq.com/jar/newcall/other/other_${i + 1}.png"
//                theme.video_url =
//                    "https://s3.amazonaws.com/download.filterisq.com/jar/newcall/other/other_${i + 1}.mp4"
//                DataBaseTool.getInstance().insertWords(theme)
//                list.add(theme)
//            }
//        }
//
//        for (i in 0 until 13) {
//            val theme = ThemeContent()
//            theme.image_url =
//                "https://s3.amazonaws.com/download.filterisq.com/jar/newcall/sport/sport_${i + 1}.webp"
//            theme.video_url =
//                "https://s3.amazonaws.com/download.filterisq.com/jar/newcall/sport/sport_${i + 1}.mp4"
//            DataBaseTool.getInstance().insertWords(theme)
//            list.add(theme)
//        }

        while (list.isNotEmpty()) {
            val index = Random().nextInt(list.size)
            val content = list[index]
            list.remove(content)
            themeList.add(content)
        }

    }

}