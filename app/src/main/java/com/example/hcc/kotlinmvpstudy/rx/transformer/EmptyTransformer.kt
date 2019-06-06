package com.example.hcc.kotlinmvpstudy.rx.transformer

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer

/**
 * Created by hecuncun on 2019/6/6
 */
class EmptyTransformer<T>:ObservableTransformer<T,T> {
    override fun apply(upstream: Observable<T>): ObservableSource<T> {
       return upstream
    }
}