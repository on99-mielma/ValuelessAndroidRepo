package com.on99.xd5thapp

import android.Manifest
import android.os.Handler
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket

class ClientSocket :Thread{

    private var handler:Handler ?=null
    private var host:String ?= null
    private var port:Int = 55545
    private var isConnected:Boolean = false
    private var outputstream:OutputStream ?= null
    private var iutputstream: InputStream ?= null
    private var socket:Socket ?= null

    constructor(handler: Handler?, host: String?, port: Int) {
        this.handler = handler
        this.host = host
        this.port = port
    }

    override fun run() {
        println("客户端Socket " + Thread.currentThread().name)
        try {
            socket = Socket(host, port)

            outputstream = socket!!.getOutputStream()
            iutputstream = socket!!.getInputStream()

            isConnected = true



        }catch (e:Exception){
            socket!!.close()
            outputstream?.close()
            iutputstream?.close()
            e.printStackTrace()
            isConnected = false
            return
        }
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.INTERNET)
        private const val REQUEST_CODE_PERMISSIONS = 15
//        var host: String = "192.168.31.233"
//        val port = 55545
        val TAG = "Check!"
    }
}