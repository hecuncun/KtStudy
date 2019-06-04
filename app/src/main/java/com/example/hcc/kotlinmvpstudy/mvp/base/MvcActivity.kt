package com.example.hcc.kotlinmvpstudy.mvp.base

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.hcc.kotlinmvpstudy.common.closeKeyBoard
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

/**
 * Created by hecuncun on 2019/6/4
 */
abstract class MvcActivity : RxAppCompatActivity() {
    var permissionFlag = false //权限设置  跳转记录标志

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initView(savedInstanceState)
        addListener()
        processLogic(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        if(permissionFlag){//是否需要重新检查权限标记
            permissionFlag=false
            reCheckPermissions()
        }
    }

    /**
     * 重新检查权限  子类复写
     *
     * 当跳转至设置页面后，回到当前页面时会调用此方法，子类可重新检查权限是否设置
     */
    open fun reCheckPermissions() {
    }


    /**
     * 处理逻辑
     */
    open fun processLogic(savedInstanceState: Bundle?) {
    }

    /**
     * 设置监听器
     */
    open fun addListener(){}

    /**
     * 初始化View
     */
    abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 初始化布局
     */
    protected abstract fun getLayoutId(): Int


    /**
     * 显示Toast
     */

    fun showToast(resId:Int){
        showToast(getString(resId))
    }

    fun showToast(msg :String){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }

    /**
     * 跳转页面
     */
    fun forward(cls :Class<*>){
        closeKeyBoard()
        forward(Intent(this,cls))
    }

     fun forward(cls: Intent) {
         closeKeyBoard()
        startActivity(cls)

    }
}