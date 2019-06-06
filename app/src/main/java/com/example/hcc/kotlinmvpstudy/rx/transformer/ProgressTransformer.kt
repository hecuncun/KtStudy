package com.example.hcc.kotlinmvpstudy.rx.transformer

import android.app.Activity
import android.app.ProgressDialog
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

/**
 * Created by hecuncun on 2019/6/6
 */
class ProgressTransformer<T>(avtivity :Activity):ObservableTransformer<T,T> {
    private val subject :Subject<Boolean> =PublishSubject.create()
    private var progressDialog: ProgressDialog?=null
    init {
        progressDialog = ProgressDialog(avtivity)
        progressDialog?.setOnCancelListener {
            subject.onNext(true) //取消订阅
        }
    }
    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        return upstream.takeUntil(subject.filter { it }
                .take(1)
                .observeOn(AndroidSchedulers.mainThread()))
                .doOnComplete { dismiss()  }
                .doOnError { dismiss() }
    }


    /**
     * 显示进度框
     */
    private fun show() {
        progressDialog?.show()
    }

    /**
     * 隐藏进度框
     */
    private fun dismiss() {
        progressDialog?.dismiss()
        progressDialog = null
    }
}