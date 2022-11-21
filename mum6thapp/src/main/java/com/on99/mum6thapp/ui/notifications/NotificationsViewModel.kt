package com.on99.mum6thapp.ui.notifications

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okio.IOException

class NotificationsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment temp wait for do okhttp"
    }
    val text: LiveData<String> = _text

    val client :OkHttpClient = OkHttpClient()

//    fun run():String{
//        val request = Request.Builder()
//            .url("http://localhost:8080/users/")
//            .build()
//        client.newCall(request).execute().use {  response ->
//            if(!response.isSuccessful) throw IOException("Unexpected code $response")
//            return response.body!!.string()
//        }
//    }
    suspend fun fetchHttp(){
        withContext(Dispatchers.Main){
            _text.value = "Updating!!..."
//            run()
            _text.value = run()
            _text.value = run()
        }
    }

    private var str:String ="null"

    suspend fun run():String = withContext(Dispatchers.IO){
        delay(3000)
//        var str:String? = null
        val request = Request.Builder()
            .url("http://192.168.31.233:8080/users/")
            .build()
        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: java.io.IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")
                    str = response.body!!.string()
                    Log.e("Notifications", str)

                }
            }
        })
        str
    }
}