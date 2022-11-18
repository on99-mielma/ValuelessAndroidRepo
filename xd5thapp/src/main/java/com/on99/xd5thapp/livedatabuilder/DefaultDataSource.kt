package com.on99.xd5thapp.livedatabuilder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket

class DefaultDataSource (private val ioDispatcher: CoroutineDispatcher):DataSource{

    override fun getCurrentTime(): LiveData<Long> =
        liveData {
            while (true){
                emit(System.currentTimeMillis())
                delay(1000)
            }
        }

    private val weatherConditions = listOf("Sunny", "Cloudy", "Rainy", "Stormy", "Snowy")

    override fun fetchWeather(): LiveData<String> = liveData {
        var counter = 0
        while (true){
            counter++
            delay(2000)
            emit(weatherConditions[counter%weatherConditions.size])
        }
    }

    private val _cachedData = MutableLiveData("This is old data")
    private val _socketData = MutableLiveData("This is old and init text data")
    override val cachedData: LiveData<String> = _cachedData
    override val socketText: LiveData<String> = _socketData

    override suspend fun fetchNewData() {
        withContext(Dispatchers.Main){
            _cachedData.value = "捕获新数据"
            _cachedData.value = simulateNetworkDataFetch()
        }
    }

    private var counter2 = 0
    private val host:String = "192.168.31.233"
    private val port= 55545

    private suspend fun simulateNetworkDataFetch():String = withContext(ioDispatcher){
        delay(3000)
        counter2++
        "新数据来自请求 : #$counter2"
    }

    override suspend fun fetchSocket(/*word: String*/) {
        withContext(Dispatchers.IO){
            _socketData.value = "尝试获取"
            _socketData.value = simulateSocketFetch(/*word*/)
        }
    }

    private var strSockI:String = "空"
    private suspend fun simulateSocketFetch(/*word: String*/):String = withContext(Dispatchers.IO){
        delay(3000)
        try {
            val socket = Socket(host,port)
//            val outputstream: OutputStream = socket.getOutputStream();
//            socket.getOutputStream().write(word.toByteArray(Charsets.UTF_8))
//            socket.shutdownOutput()
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


}
interface DataSource {
    fun getCurrentTime(): LiveData<Long>
    fun fetchWeather(): LiveData<String>
    val cachedData: LiveData<String>
    val socketText: LiveData<String>
    suspend fun fetchNewData()
    suspend fun fetchSocket(/*word:String*/)
}