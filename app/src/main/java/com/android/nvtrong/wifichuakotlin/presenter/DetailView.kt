package com.android.nvtrong.wifichuakotlin.presenter

/**
 * Created by nvtrong on 4/17/2018.
 */
interface DetailView {
    fun showProgess()
    fun hidenProgess()
    fun display(s: String)
    fun displayFailed()
}