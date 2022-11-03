package com.on99.ovo4thapp.tools

import android.content.Context
import android.media.MediaRecorder
import android.util.Log
import java.io.IOException

private const val LOG_TAG = "AudioRecordTest"
private const val REQUEST_RECORD_AUDIO_PERMISSION = 200
private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"

class RecordButton(ctx: Context,var recorder: MediaRecorder?,var fileName:String) : androidx.appcompat.widget.AppCompatButton(ctx){
    var mStartRecording = true

    var clicker: OnClickListener = OnClickListener {
        onRecord(mStartRecording)
        text = when (mStartRecording) {
            true -> "Stop recording"
            false -> "Start recording"
        }
        mStartRecording = !mStartRecording
    }

    private fun onRecord(start: Boolean) = if (start){
        startRecording()
    } else{
        stopRecording()
    }

    private fun stopRecording() {
        recorder?.apply {
            stop()
            release()
        }
        recorder = null
    }

    private fun startRecording(){
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

    init {
        text = "Start recording"
        setOnClickListener(clicker)
    }

}