package com.on99.xd5thapp

import com.on99.xd5thapp.Order

object Order {
    private var onChangeListener // 声明interface接口
            : OnChangeListener? = null

    fun setOnChangeListener(onChange: OnChangeListener?) {    // 创建setListener方法
        onChangeListener = onChange
    }

    var flage: String? = null
        set(flage) {
            field = flage
            onChangeListener!!.onChange()
        }

    interface OnChangeListener {
        // 创建interface类
        fun onChange() // 值改变
    }
}