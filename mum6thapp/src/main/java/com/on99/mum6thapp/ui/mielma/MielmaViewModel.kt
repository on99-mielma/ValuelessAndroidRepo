package com.on99.mum6thapp.ui.mielma

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket

class MielmaViewModel :ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is make by on99 the Fragment but write in ViewModel"
    }

    val text: LiveData<String> = _text

    private val _socketData = MutableLiveData("This is old and init text data")

    val socketData: LiveData<String> = _socketData

    private val host:String = "192.168.31.233"
    private val port= 55545

    suspend fun fetchSocket(word: String?){
        withContext(Dispatchers.Main){
            _socketData.value = "Now is trying to catch socket data"
            _socketData.value = simulataSocketFetch(word!!)
        }
    }

    private var strSockI:String = "now is null temporary"
    private suspend fun simulataSocketFetch(word: String?): String = withContext(Dispatchers.IO) {
        delay(500)
        try {
            val socket = Socket(host,port)
//            val word:String = "go fuck yourself"
            val outputstream: OutputStream = socket.getOutputStream();
            socket.getOutputStream().write(word!!.toByteArray(Charsets.UTF_8))
            socket.shutdownOutput()

            val inputstream: InputStream = socket.getInputStream()
            val bytes: ByteArray = ByteArray(1024)
            var len: Int
            val sb: StringBuilder = StringBuilder()

            len = inputstream.read(bytes)
            while (len != -1) {
                sb.append(String(bytes, 0, len, Charsets.UTF_8))
                len = inputstream.read(bytes)
            }
            strSockI = sb.toString()
        }catch (e:Exception){
            e.printStackTrace()
        }
        strSockI
    }

    @Deprecated("False use")
    suspend fun sendSocket(word: String?){
        if ("".equals(word)){
            return
        }
        withContext(Dispatchers.Main){
            simulateSocketSend(word!!)
        }
    }

    @Deprecated("False use")
    private suspend fun simulateSocketSend(word:String?): String = withContext(Dispatchers.IO){
        delay(3000)
        try {
            val socket = Socket(host,port)
//            val word:String = "go fuck yourself"
            val outputstream: OutputStream = socket.getOutputStream();
            socket.getOutputStream().write(word!!.toByteArray(Charsets.UTF_8))
            socket.shutdownOutput()
        }catch (e:Exception){
            e.printStackTrace()
        }
        word!!
    }

}