package com.free.good.dynamicwallpaper.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.free.good.dynamicwallpaper.db.ThemeContent
import com.free.good.dynamicwallpaper.db.ThemeDao

@Database(entities = [ThemeContent::class], version = 1)
abstract class ThemeDataBase : RoomDatabase() {

    abstract fun themeDao(): ThemeDao

}