package com.example.hcc.kotlinmvpstudy.mvp.base

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by hecuncun on 2019/6/6
 */
class BaseFragmentAdapter : FragmentPagerAdapter {
    private var fragmentList: List<Fragment>? = null
    private var mTitles: List<String>? = null

    constructor(fm: FragmentManager, fragmentList: List<Fragment>) : super(fm) {
        this.fragmentList = fragmentList
    }

    private fun setFragment(fm: FragmentManager, fragmentList: List<Fragment>, mTitles: List<String>) {
     this.mTitles = mTitles
        if(this.fragmentList != null){
            val ft = fm.beginTransaction()
            fragmentList?.forEach {
                ft.remove(it)
            }
            ft?.commitAllowingStateLoss()
            fm.executePendingTransactions()
        }
        this.fragmentList=fragmentList
        notifyDataSetChanged()

    }

    constructor(fm: FragmentManager, fragmentList: List<Fragment>, mTitles: List<String>) : super(fm) {
        this.mTitles = mTitles
        setFragment(fm, fragmentList, mTitles)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if(null!=mTitles)mTitles!![position] else ""
    }
    override fun getItem(position: Int): Fragment {
        return fragmentList!![position]
    }

    override fun getCount(): Int {
       return fragmentList!!.size
    }
}