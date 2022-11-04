package com.on99.uwu2ndapp


import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.SurfaceView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.util.Date
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Low1Activity : AppCompatActivity() {
    private var preview:Preview? =null
    private var imageCapture: ImageCapture? = null
    private var camera: Camera? = null


    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_format_low1)
        val textViewl10:TextView = findViewById(R.id.textViewl10)
        var strTime:String = timeStamptoTime(System.currentTimeMillis())
        textViewl10.setText(strTime)
        if (allPermissionsGranted()){
            startCamera()
        }
        else{
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }
    }
    private  fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext,it) == PackageManager.PERMISSION_GRANTED
    }
    private fun startCamera(){
        println("开启照相机now....")
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)//?

        cameraProviderFuture.addListener(Runnable {
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            preview = Preview.Builder().build()

            imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build()


            val cameraSelector = CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()

            try {
                cameraProvider.unbindAll()
                val viewFinder: PreviewView = findViewById(R.id.viewFinder)
                preview?.setSurfaceProvider(viewFinder.surfaceProvider)
                camera = cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview,imageCapture
                )
            }catch (e:Exception){
                Log.e(TAG,"绑定失败",e)
            }
        },ContextCompat.getMainExecutor(this))
    }

    private fun timeStamptoTime(timeMillis: Long): String {//时间戳转具体时间尝试另法
        val date = Date(timeMillis)
        return date.toString()
    }
    companion object{
        private val REQUIRED_PERMISSIONS  = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
        private const val TAG = "CameraXBasic"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    }
}