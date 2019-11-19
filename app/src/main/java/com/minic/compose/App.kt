package com.minic.compose

import android.app.Application


/**
 * 描述: Application
 * 作者: ChenYy
 * 日期: 2019/11/19 15:25
 */

class App : Application() {
    override fun onCreate() {
        INSTANCE = this
        super.onCreate()
    }

    companion object {
        lateinit var INSTANCE: App
    }
}