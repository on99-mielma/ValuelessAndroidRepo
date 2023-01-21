package com.on99.mum6thapp.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.on99.stb8thapp.data.textborad
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HomeViewModel : ViewModel() {

    val address = "http://192.168.31.17:8999/tb/"
    val addressN = "http://192.168.31.233:8080/users/"
    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    private var str:String ="null"
//    val text2Tran = "00000test${date2Tran}+${author2Tran}....."
    val text: LiveData<String> = _text
    val client : OkHttpClient = OkHttpClient()
    suspend fun postHttp(text:String,author:String){
        withContext(Dispatchers.Main){
            _text.value = "POSTING..."
            _text.value = posttheThing(text,author)
        }
    }
    private fun parserJson(jsonstr:String?):String{
        val obj = JSONObject(jsonstr)
        val result = "code=${obj.getLong("code")}\n"+
                "msg=${obj.getString("msg")}\n"+
                "data=${obj.getString("data")}"
        return result
    }
    suspend fun posttheThing(text:String,author:String) :String = withContext(Dispatchers.IO){
        val dt =LocalDateTime.now()
        val myDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val date2Tran = myDateTimeFormatter.format(dt)
        val author2Tran = author
        val tb = textborad(text,date2Tran,author2Tran)
        val theTransJSON = Gson().toJson(tb)
        val request = Request.Builder()
            .url(address)
            .header("User-Agent","Okhttp Hearders.java")
            .addHeader("Content-Type","application/json;charset=UTF-8")
            .addHeader("Access-Control-Allow-Origin","*")
            .post(theTransJSON.toRequestBody())
            .build()
        client.newCall(request).execute().use {
            if(!it.isSuccessful) throw  IOException("Unexpected code $it")
            str = it.body!!.string()
            Log.e("POST RETURN",str)
        }
        delay(1000)
        str = parserJson(str)
        str
    }
}

//'Content-Type': 'application/json;charset=UTF-8',
//'Access-Control-Allow-Origin':'*'