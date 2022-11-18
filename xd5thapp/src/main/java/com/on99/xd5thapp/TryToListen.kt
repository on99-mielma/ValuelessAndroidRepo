package com.on99.xd5thapp

class TryToListen {
    var str = "?1"

    lateinit var myListener:(String) -> Unit

    fun setListener(listener:(String) -> Unit){
        this.myListener = listener
        this.myListener(str)
    }
}