package com.example.hcc.kotlinmvpstudy.mvp.ui.activity

import android.Manifest
import android.graphics.Typeface
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.example.hcc.kotlinmvpstudy.R
import com.example.hcc.kotlinmvpstudy.mvp.base.MvcActivity
import com.example.hcc.kotlinmvpstudy.utils.AppUtils
import com.example.hcc.kotlinmvpstudy.utils.ContextUtil
import com.zhy.m.permission.MPermissions
import com.zhy.m.permission.PermissionDenied
import com.zhy.m.permission.PermissionGrant
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * Created by hecuncun on 2019/6/5
 */
class SplashActivity : MvcActivity() {
    private var textTypeface: Typeface? = null
    private var descTypeFace: Typeface? = null
    private var alphaAnimation: AlphaAnimation? = null

    companion object {
        const val PERMISSION_CODE = 101//权限Code
    }

    init {
        textTypeface = Typeface.createFromAsset(ContextUtil.getContext().assets, "fonts/Lobster-1.4.otf")
        descTypeFace = Typeface.createFromAsset(ContextUtil.getContext().assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
    }

    override fun initView(savedInstanceState: Bundle?) {
        tv_app_name.typeface = textTypeface
        tv_splash_desc.typeface = descTypeFace
        tv_version_name.text = "v${AppUtils.getVerName(applicationContext)}"

        //渐变启动屏
        alphaAnimation = AlphaAnimation(0.3f, 1.0f)
        alphaAnimation?.duration = 2000
        alphaAnimation?.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {

            }


            override fun onAnimationEnd(p0: Animation?) {
                forwardAndFinish(MainActivity::class.java)
            }

            override fun onAnimationStart(p0: Animation?) {
            }

        })

        reCheckPermissions()

    }

    override fun getLayoutId(): Int = R.layout.activity_splash

    override fun reCheckPermissions() {
        super.reCheckPermissions()
        MPermissions.requestPermissions(this, PERMISSION_CODE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE)

    }

    /**
     * 权限获取成功
     */
    @PermissionGrant(PERMISSION_CODE)
    fun requestWriteExternalStorageForUploadSuccess() {
        alphaAnimation?.let {
            iv_web_icon.startAnimation(it)
        }
    }

    /**
     * 权限获取失败
     */
    @PermissionDenied(PERMISSION_CODE)
    fun requestWriteExternalStorageForUploadFailed() {
        showPermissionDialog(resources.getString(R.string.p_read_external_storage))
    }

    override fun onDestroy() {
        super.onDestroy()
        alphaAnimation?.cancel()
        alphaAnimation = null
    }

}

