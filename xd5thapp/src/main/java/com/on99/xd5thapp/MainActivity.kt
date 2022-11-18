package com.on99.xd5thapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.textfield.TextInputEditText
import com.on99.xd5thapp.livedatabuilder.LiveDataVMFactory
import com.on99.xd5thapp.livedatabuilder.LiveDataViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket

class MainActivity : AppCompatActivity() {
    private val viewmodel:LiveDataViewModel by viewModels{LiveDataVMFactory}
    public var str:String = "Initwt11"
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView0: TextView = findViewById(R.id.textview0)
        var textViewinput: TextInputEditText = findViewById(R.id.textInpu00)
//        var socket = Socket(host, port)
        val openButton: Button = findViewById(R.id.button0)
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        openButton.setOnClickListener {

            var word: String = textViewinput.text.toString();
            println("word length = " + word.length)
            if (word.length > 0) {
                threadSocket(word)
                textView0.setText(str)
//                textView0.setText(viewmodel.socketValue.value)
            } else {
//                println("检查输入")
                Log.e(TAG, "检查输入")
            }
        }

    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun doSocket( word: String){
        var socket = Socket(host, port)
        var outputstream: OutputStream = socket.getOutputStream();
        socket.getOutputStream().write(word.toByteArray(Charsets.UTF_8))
        socket.shutdownOutput()

        var inputstream: InputStream = socket.getInputStream()
        var bytes: ByteArray = ByteArray(1024)
        var len: Int
        var sb: StringBuilder = StringBuilder()

        len = inputstream.read(bytes)
        while (len != -1) {
            sb.append(String(bytes, 0, len, Charsets.UTF_8))
            len = inputstream.read(bytes)
        }
        Log.e(TAG, "从服务端拿到信息")
        println("信息是 " + sb)

        inputstream.close()
        outputstream.close()
        socket.close()


    }

    private fun threadSocket(word: String){
        val thread1 = Thread(Runnable {
            try {
                var socket = Socket(host, port)
                var outputstream: OutputStream = socket.getOutputStream();
                socket.getOutputStream().write(word.toByteArray(Charsets.UTF_8))
                socket.shutdownOutput()

                var inputstream: InputStream = socket.getInputStream()
                var bytes: ByteArray = ByteArray(1024)
                var len: Int
                var sb: StringBuilder = StringBuilder()

                len = inputstream.read(bytes)
                while (len != -1) {
                    sb.append(String(bytes, 0, len, Charsets.UTF_8))
                    len = inputstream.read(bytes)
                }
                Log.e(TAG, "从服务端拿到信息")
                println("信息是 " + sb)
                str = sb.toString()
//                tV.setText(sb.toString())
            }catch (e:Exception){
                e.printStackTrace()
            }
        })
        thread1.start()
    }
//    private val _socketData = MutableLiveData("This is old and init text data")
//    val socketText: LiveData<String> = _socketData




    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.INTERNET)
        private const val REQUEST_CODE_PERMISSIONS = 15
        var host: String = "192.168.31.233"
        val port = 55545
        val TAG = "Check!"
    }
}