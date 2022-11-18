package com.on99.xd5thapp

class InterfaceListener {
    interface OnChangeListener{
        fun onChange()
    }
    private var onChangeListener:OnChangeListener ?= null
    fun setOnChangeListener(onChange:OnChangeListener?){
        onChangeListener = onChange
    }
    var str:String? = null
        set(str) {
            field = str
            onChangeListener!!.onChange()
        }
}