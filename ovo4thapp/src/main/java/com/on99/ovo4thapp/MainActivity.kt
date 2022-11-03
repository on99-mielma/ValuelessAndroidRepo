package com.on99.ovo4thapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.view.marginLeft
import androidx.core.view.marginTop
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

private const val LOG_TAG = "AudioRecordTest"
private const val REQUEST_RECORD_AUDIO_PERMISSION = 200
private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"

class MainActivity : AppCompatActivity(){
    private var fileName: String = ""

    private var recordButton: RecordButton? = null
    private var recorder: MediaRecorder? = null

    private var playButton: PlayButton? = null
    private var player: MediaPlayer? = null

//    private lateinit var outputDirectory:File

    private var text1:TextView? = null

    private var permissionToRecordAccepted = false
    private var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)
    private var mStartRecording1 = true
    private var mStartPlaying1 = true
    @SuppressLint("ResourceAsColor", "UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var timeString = SimpleDateFormat(FILENAME_FORMAT, Locale.CHINA).format(System.currentTimeMillis()) as String
        fileName = "${externalCacheDir?.absolutePath}/"+timeString+".3gp"
        println(fileName)
//        outputDirectory = getOutputDirectory()

        ActivityCompat.requestPermissions(this,permissions, REQUEST_RECORD_AUDIO_PERMISSION)

        recordButton = RecordButton(this)
        playButton = PlayButton(this)
        text1 = TextView(this)
        text1!!.setText(R.string.app_name)
        text1!!.setTextColor(R.color.purple_700)
//        val ll = LinearLayout(this).apply {
//            addView(recordButton,
//            LinearLayout.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT,
//                /*ViewGroup.LayoutParams.WRAP_CONTENT*/0/*ViewGroup.LayoutParams.MATCH_PARENT*/,
//                16f
//            )).also {
////                setVerticalGravity(right)
//                gravity = Gravity.END
//            }
//            addView(text1,
//                LinearLayout.LayoutParams(
//                    ViewGroup.LayoutParams.WRAP_CONTENT/*100*/,
//                    ViewGroup.LayoutParams.WRAP_CONTENT/*200*/,
//                    0f
//                )
//            ).also {
//                gravity=Gravity.CENTER
//            }
//            addView(playButton,
//                LinearLayout.LayoutParams(
//                    ViewGroup.LayoutParams.WRAP_CONTENT/*100*/,
//                    ViewGroup.LayoutParams.WRAP_CONTENT/*200*/,
//                    1f
//                ))
//        }
//
//        ll.orientation = LinearLayout.VERTICAL
//
//        setContentView(ll)

        setContentView(R.layout.activity_main)
        val cl:ConstraintLayout = findViewById(R.id.am1)
        var recordButton:Button = findViewById(R.id.recordButton)
        recordButton.setOnClickListener{

            onRecord(mStartRecording1)
            var text = when (mStartRecording1) {
                true -> "Stop recording"
                false -> "Start recording"
            }
            recordButton.setText(text)
            if(mStartRecording1){
                println("应该是录制中")//下放录制中应该展示的东西
                recordButton.setBackgroundResource(R.color.thistle)
                cl.setBackgroundResource(R.color.black)
            }
            else{
                println("应该no是录制中")
                recordButton.setBackgroundResource(R.color.violetred)
                cl.setBackgroundResource(R.color.white)
            }
            mStartRecording1 = !mStartRecording1
        }
        var FplayButton:Button = findViewById(R.id.PlayButton)
        FplayButton.setOnClickListener {
            onPlay(mStartPlaying1)
            var text = when (mStartPlaying1) {
                true -> "Stop playing"
                false -> "Start playing"
            }
            mStartPlaying1 = !mStartPlaying1
            FplayButton.setText(text)
        }
        FplayButton.visibility = View.INVISIBLE
        val ShowPlayButton:Switch = findViewById(R.id.PlaySwitch)
        ShowPlayButton.setOnCheckedChangeListener { _, b ->
            if (b){
                FplayButton.visibility = View.VISIBLE
            }else{
                FplayButton.visibility = View.INVISIBLE
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionToRecordAccepted = if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        } else {
            false
        }
        if (!permissionToRecordAccepted) finish()
    }

    internal inner class RecordButton(ctx: Context) : androidx.appcompat.widget.AppCompatButton(ctx){
        var mStartRecording = true

        var clicker: OnClickListener = OnClickListener {
            onRecord(mStartRecording)
            text = when (mStartRecording) {
                true -> "Stop recording"
                false -> "Start recording"
            }
            mStartRecording = !mStartRecording
        }

        init {
            text = "Start recording"
            setOnClickListener(clicker)
        }
    }

    private fun onRecord(start: Boolean) = if (start) {
        startRecording()
    } else{
        stopRecording()
    }

    private fun startPlaying(){
        player = MediaPlayer().apply {
            try {
                setDataSource(fileName)
                prepare()
                start()
            }catch (e: IOException){
                Log.e(LOG_TAG,"prepare() failed")
            }
        }
    }

    private fun stopPlaying(){
        player?.release()
        player = null
    }

    private fun startRecording(){
//        val recordFile = File(
//            outputDirectory,
//            SimpleDateFormat(FILENAME_FORMAT, Locale.CHINA).format(System.currentTimeMillis())+".3gp"
//        )


        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setOutputFile(fileName)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

            try {
                prepare()
            }catch (e: IOException){
                Log.e(LOG_TAG,"prepare() failed")
            }

            start()
        }
    }

    private fun stopRecording(){
        recorder?.apply {
            stop()
            release()
        }
        recorder = null
    }

    internal inner class PlayButton(ctx: Context) : androidx.appcompat.widget.AppCompatButton(ctx) {
        var mStartPlaying = true
        var clicker: OnClickListener = OnClickListener {
            onPlay(mStartPlaying)
            text = when (mStartPlaying) {
                true -> "Stop playing"
                false -> "Start playing"
            }
            mStartPlaying = !mStartPlaying
        }

        init {
            text = "Start playing"
            setOnClickListener(clicker)
        }
    }

    private fun onPlay(start: Boolean) = if (start) {
        startPlaying()
    } else {
        stopPlaying()
    }

    override fun onStop() {
        super.onStop()
        recorder?.release()
        recorder = null
        player?.release()
        player = null
    }
//    fun getOutputDirectory():File{
//        val mediaDir = externalMediaDirs.firstOrNull()?.let {
//            File(it,resources.getString(R.string.app_name)).apply { mkdirs() }
//        }
//        return  if (mediaDir != null && mediaDir.exists())
//            mediaDir else filesDir
//    }
}