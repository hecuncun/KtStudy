package com.example.hcc.kotlinmvpstudy.common

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.example.hcc.kotlinmvpstudy.mvp.base.MvcActivity

/**
 * Created by hecuncun on 2019/6/4
 */

/**
 * 打开软键盘
 */
fun MvcActivity.openKeyBoard(mEditText :EditText){
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(mEditText,InputMethodManager.RESULT_SHOWN)
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY)
}

/**
 * 关闭软键盘
 */
fun MvcActivity.closeKeyBoard(mEditText: EditText){
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(mEditText.windowToken,0)
}
/**
 * 关闭软键盘
 */
fun MvcActivity.closeKeyBoard(){
    val view : View? = window.peekDecorView()
    view?.apply {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}