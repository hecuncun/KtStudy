package com.example.hcc.kotlinmvpstudy.mvp.base

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import com.example.hcc.kotlinmvpstudy.R
import com.example.hcc.kotlinmvpstudy.common.closeKeyBoard
import com.example.hcc.kotlinmvpstudy.rx.transformer.EmptyTransformer
import com.example.hcc.kotlinmvpstudy.rx.transformer.ProgressTransformer
import com.example.hcc.kotlinmvpstudy.utils.CleanLeakUtils
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.zhy.m.permission.MPermissions
import io.reactivex.ObservableTransformer

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
        if (permissionFlag) {//是否需要重新检查权限标记
            permissionFlag = false
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
    open fun addListener() {}

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

    fun showToast(resId: Int) {
        showToast(getString(resId))
    }

    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    /**
     * 跳转页面
     */
    fun forward(cls: Class<*>) {
        closeKeyBoard()
        forward(Intent(this, cls))
    }

    fun forward(cls: Intent) {
        closeKeyBoard()
        startActivity(cls)

    }

    /**
     * 跳转并关闭当前页
     */
    fun forwardAndFinish(cls: Class<*>) {
        forward(cls)
        finish()
    }

    fun backWard() {
        closeKeyBoard()
        finish()
    }

    /**
     * MPermissions 请求权限回调处理
     */

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    /**
     * 显示权限申请说明
     * @param permission 说明文本
     */
    fun showPermissionDialog(permissions: String) {
        AlertDialog.Builder(this)
                .setCancelable(false)
                .setMessage(permissions)
                .setTitle(getString(R.string.string_help_text, permissions))
                .setPositiveButton("设置") { _, _ ->
                    permissionFlag = true
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    intent.data = Uri.parse("package:$packageName")
                    forward(intent)
                }
                .create()
                .show()
    }

    /**
     * 获取异步进度加载事务，子类复写
     * 默认返回 默认进度框事务
     */
    open fun <VT> getPrgressTransformer(): ObservableTransformer<VT, VT> = ProgressTransformer(this)

    /**
     * 获取异步进度下拉或上拉加载事务，子类复写
     * 默认返回 空事务
     */
    open fun <VT> getSmartRefreshTransformer(): ObservableTransformer<VT, VT> = EmptyTransformer()

    /**
     * 获取进度、错误、内容切换View事务，子类复写
     */
    open fun <VT> getMultipleStatusViewTransformer(): ObservableTransformer<VT, VT> = EmptyTransformer()

    override fun onDestroy() {
        //修复华为手机内存泄漏Bug
        CleanLeakUtils.fixInputMethodManagerLeak(this)
        super.onDestroy()
    }
}