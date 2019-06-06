package com.example.hcc.kotlinmvpstudy

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.example.hcc.kotlinmvpstudy.utils.ContextUtil

/**
 * Created by hecuncun on 2019/6/4
 */
class App : Application(){
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
       MultiDex.install(this)//分包处理，解决64k问题
    }

    override fun onCreate() {
        super.onCreate()
        ContextUtil.setContext(this)
    }

}