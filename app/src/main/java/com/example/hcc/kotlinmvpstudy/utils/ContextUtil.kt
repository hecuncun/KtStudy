package com.example.hcc.kotlinmvpstudy.utils

import android.annotation.SuppressLint
import android.content.Context

/**
 * Created by hecuncun on 2019/6/5
 */
class ContextUtil private constructor(){
    companion object {
        @SuppressLint("StaticFieldLeak")
        private var context :Context ?=null

        fun setContext(ctx :Context){
            context=ctx.applicationContext
        }

        fun getContext():Context{
            return context!!
        }
    }

}