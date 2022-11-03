package com.on99.uwu2ndapp.tool

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class HttpUtils {
    fun getHttpResult(str:String):String{
        try {
            var url:URL = URL(str)
            var connect:HttpURLConnection = url.openConnection() as HttpURLConnection
            var input:InputStream = connect.inputStream
            var temp_in:BufferedReader = BufferedReader(InputStreamReader(input))
            var line:String ?=null
            println(connect.responseCode)
            var sb = StringBuffer()
            while (true){
                line = temp_in.readLine()
                if(line==null){
                    break
                }
                sb.append(line)
            }
            return sb.toString()
        }catch (e:java.lang.Exception){
            println(e.toString())
            return ""
        }
    }
}