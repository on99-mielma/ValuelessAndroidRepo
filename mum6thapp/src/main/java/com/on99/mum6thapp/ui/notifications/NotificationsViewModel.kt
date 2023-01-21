package com.on99.mum6thapp.ui.notifications

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.*
import okio.IOException
import org.json.JSONObject

class NotificationsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment temp wait for do okhttp"
    }
    val text: LiveData<String> = _text

    private val _al = MutableLiveData<ArrayList<List<String>>>().apply {
        value = arrayListOf()
    }
    val al:LiveData<ArrayList<List<String>>> = _al


    val address = "http://192.168.31.17:8999/tb/"
    val addressN = "http://192.168.31.233:8080/users/"
    var arrayList = ArrayList<List<String>>()
    val client :OkHttpClient = OkHttpClient()

    private fun parserJson(jsonstr:String?):String{
        arrayList.clear()
        val obj = JSONObject(jsonstr)
        val result = "code=${obj.getLong("code")}\n"+
                "msg=${obj.getString("msg")}\n"
        val listArray = obj.getJSONArray("data")
        for (i in 0 until listArray.length()){
            val item = listArray.getJSONObject(i)
            Log.e("item ${i}",item.toString())
//            println(oneJ2mulS(item))
            arrayList.add(oneJ2mulS(item))
        }
        println(arrayList)
        return result
    }
    private fun oneJ2mulS(jsonobj:JSONObject?): List<String> {
        val id = "${jsonobj?.getString("id")}"
        val text = "${jsonobj?.getString("text")}"
        val date = "${jsonobj?.getString("date")}"
        val author = "${jsonobj?.getString("author")}"
        val textlist:List<String> = listOf(id,text,date,author)
        return textlist
    }



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
            _text.value = run_sync()
            delay(1000)
            _al.value = arrayList
            delay(500)
        }
    }

    private var str:String ="null"

    suspend fun run_async():String = withContext(Dispatchers.IO){
        delay(3000)
//        var str:String? = null
        val request = Request.Builder()
            .url(address)
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
    suspend fun run_sync():String = withContext(Dispatchers.IO){
        delay(1500)
        val request = Request.Builder()
            .url(address)
            .build()
        client.newCall(request).execute().use {
            if(!it.isSuccessful) throw IOException("Unexpected code $it")
            str = it.body!!.string()
            Log.e("Notifications", str)
        }
        str = parserJson(str)
        str
    }
}