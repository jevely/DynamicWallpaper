package com.free.dynamic.wallpaper.tool

import com.free.dynamic.wallpaper.R
import com.free.dynamic.wallpaper.db.DataBaseTool
import com.free.dynamic.wallpaper.db.ThemeContent
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

        val theme1 = ThemeContent()
        theme1.image_url = R.mipmap.dynamic_1
        theme1.video_url = R.raw.dynamic_1
        theme1.video_name = "dynamic_1"
        DataBaseTool.getInstance().insertWords(theme1)
        list.add(theme1)

        val theme2 = ThemeContent()
        theme2.image_url = R.mipmap.dynamic_2
        theme2.video_url = R.raw.dynamic_2
        theme2.video_name = "dynamic_2"
        DataBaseTool.getInstance().insertWords(theme2)
        list.add(theme2)

        val theme3 = ThemeContent()
        theme3.image_url = R.mipmap.dynamic_3
        theme3.video_url = R.raw.dynamic_3
        theme3.video_name = "dynamic_3"
        DataBaseTool.getInstance().insertWords(theme3)
        list.add(theme3)

        val theme4 = ThemeContent()
        theme4.image_url = R.mipmap.dynamic_4
        theme4.video_url = R.raw.dynamic_4
        theme4.video_name = "dynamic_4"
        DataBaseTool.getInstance().insertWords(theme4)
        list.add(theme4)

        val theme5 = ThemeContent()
        theme5.image_url = R.mipmap.dynamic_5
        theme5.video_url = R.raw.dynamic_5
        theme5.video_name = "dynamic_5"
        DataBaseTool.getInstance().insertWords(theme5)
        list.add(theme5)

        val theme6 = ThemeContent()
        theme6.image_url = R.mipmap.dynamic_6
        theme6.video_url = R.raw.dynamic_6
        theme6.video_name = "dynamic_6"
        DataBaseTool.getInstance().insertWords(theme6)
        list.add(theme6)

        val theme7 = ThemeContent()
        theme7.image_url = R.mipmap.dynamic_7
        theme7.video_url = R.raw.dynamic_7
        theme7.video_name = "dynamic_7"
        DataBaseTool.getInstance().insertWords(theme7)
        list.add(theme7)

        val theme8 = ThemeContent()
        theme8.image_url = R.mipmap.dynamic_8
        theme8.video_url = R.raw.dynamic_8
        theme8.video_name = "dynamic_8"
        DataBaseTool.getInstance().insertWords(theme8)
        list.add(theme8)

        val theme9 = ThemeContent()
        theme9.image_url = R.mipmap.dynamic_9
        theme9.video_url = R.raw.dynamic_9
        theme9.video_name = "dynamic_9"
        DataBaseTool.getInstance().insertWords(theme9)
        list.add(theme9)

        val theme10 = ThemeContent()
        theme10.image_url = R.mipmap.dynamic_10
        theme10.video_url = R.raw.dynamic_10
        theme10.video_name = "dynamic_10"
        DataBaseTool.getInstance().insertWords(theme10)
        list.add(theme10)

        val theme11 = ThemeContent()
        theme11.image_url = R.mipmap.dynamic_11
        theme11.video_url = R.raw.dynamic_11
        theme11.video_name = "dynamic_11"
        DataBaseTool.getInstance().insertWords(theme11)
        list.add(theme11)

        val theme12 = ThemeContent()
        theme12.image_url = R.mipmap.dynamic_12
        theme12.video_url = R.raw.dynamic_12
        theme12.video_name = "dynamic_12"
        DataBaseTool.getInstance().insertWords(theme12)
        list.add(theme12)

        val theme13 = ThemeContent()
        theme13.image_url = R.mipmap.dynamic_13
        theme13.video_url = R.raw.dynamic_13
        theme13.video_name = "dynamic_13"
        DataBaseTool.getInstance().insertWords(theme13)
        list.add(theme13)

        val theme14 = ThemeContent()
        theme14.image_url = R.mipmap.dynamic_14
        theme14.video_url = R.raw.dynamic_14
        theme14.video_name = "dynamic_14"
        DataBaseTool.getInstance().insertWords(theme14)
        list.add(theme14)

        val theme15 = ThemeContent()
        theme15.image_url = R.mipmap.dynamic_15
        theme15.video_url = R.raw.dynamic_15
        theme15.video_name = "dynamic_15"
        DataBaseTool.getInstance().insertWords(theme15)
        list.add(theme15)

        while (list.isNotEmpty()) {
            val index = Random().nextInt(list.size)
            val content = list[index]
            list.remove(content)
            themeList.add(content)
        }

    }

}