package com.on99.jet4thapp

import android.Manifest
import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.on99.jet4thapp.ui.theme.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

private const val LOG_TAG = "AudioRecordTest"
private const val REQUEST_RECORD_AUDIO_PERMISSION = 200
private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"

class MainActivity : ComponentActivity() {
    private var fileName: String = ""
    private var permissionToRecordAccepted = false
    private var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var timeString = SimpleDateFormat(FILENAME_FORMAT,Locale.CHINA).format(System.currentTimeMillis()) as String
        fileName = "${externalCacheDir?.absolutePath}/"+timeString+".3gp"
        ActivityCompat.requestPermissions(this,permissions, REQUEST_RECORD_AUDIO_PERMISSION)
        setContent {
            My1stAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        MediaRecorderButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp),
                            fileName = fileName
                        )
                        Greeting(name = "666")
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    My1stAppTheme {
        Greeting("Android")
    }
}

@Composable
fun MediaRecorderButton(
    modifier: Modifier,
    color1: Color = MaterialTheme.colorScheme.error,
    color2: Color = MaterialTheme.colorScheme.primary,
    fileName:String
) {
    var likeornot by remember { mutableStateOf(true) }
    var fakefadeV by rememberSaveable {
        mutableStateOf(0.0f)
    }
    var buttonColorV by rememberSaveable {
        mutableStateOf(1.0f)
    }
    val fadeV by animateFloatAsState(
        targetValue = fakefadeV,
        animationSpec = tween(durationMillis = 1000)
    )
    val fadebuttonColorV by animateFloatAsState(
        targetValue = buttonColorV,
        animationSpec = tween(durationMillis = 1000)
    )
    var rectTargetValue by rememberSaveable {
        mutableStateOf(0f)
    }
    val fadeRectAni by animateFloatAsState(
        targetValue = rectTargetValue,
        animationSpec = tween(durationMillis = 5000)
    )
    val textStateList = listOf<String>("Dislike", "Like")
    var textOfButton by remember {
        mutableStateOf("Like")
    }
    //录音基本组件
    var recorder: MediaRecorder? = null
    //录音相关函数
    fun startRecording() {
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setOutputFile(fileName)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            try {
                prepare()
                println(fileName)
            } catch (e: IOException) {
                Log.e(LOG_TAG, "prepare() failed")
            }
            Log.e(LOG_TAG,"开始！")
            start()
        }
    }

    fun stopRecording() {
        recorder?.apply {
            stop()
            release()
            Log.e(LOG_TAG,"停止！")
        }
        recorder = null
    }

    fun onRecord(pd: Boolean) = if (pd) {
        startRecording()
    } else {
        stopRecording()
    }
    Column(
        modifier = modifier.drawBehind {
            likeTheSun(
                color = color2,
                fadeV = fadeV
            )
            val loserSize = size / 1.25f
            likeTheLoser(
                componentSize = loserSize,
                myColor = color1,
                theWidth = 80f,
                fadeV = fadeRectAni
            )
        },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally//6
    ) {
        Button(
            modifier = Modifier
                .padding(vertical = 50.dp)
                .background(Color(0x00000000))
                .height(100.dp)
                .width(143.dp),
            onClick = {
                println(fileName)
                onRecord(likeornot)
                likeornot = !likeornot
                if (likeornot) {
                    buttonColorV = 1.0f
                    rectTargetValue = 0.0f
                    fakefadeV = 0.0f
                    textOfButton = textStateList[1]
                } else {
                    buttonColorV = 0.3f
                    rectTargetValue = 1.0f
                    fakefadeV = 1.0f
                    textOfButton = textStateList[0]
                }
            },
            contentPadding = ButtonDefaults.TextButtonContentPadding,
            enabled = true,
            colors = ButtonDefaults.buttonColors(
                containerColor = Pinkon99.copy(alpha = fadebuttonColorV),
                contentColor = Color.Black.copy(alpha = fadebuttonColorV)
            )
        ) {
            Icon(
                Icons.Filled.Favorite,
                contentDescription = "Localized description",
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(
                text = if (likeornot) textStateList[1] else textStateList[0],
                color = BlackOn99.copy(alpha = fadebuttonColorV)
            )
        }
    }
}

fun DrawScope.likeTheSun(
    color: Color,
    fadeV: Float
) {
    drawPath(
        path = Path().apply {
            val totalWidth = size.width
            val totalHeight = size.height
            moveTo(size.width / 2f, size.height / 2f)
            relativeLineTo(350f / 1.5f, 0f)
            moveTo(size.width / 2f, size.height / 2f)
            relativeLineTo(-350f / 1.5f, 0f)
            moveTo(size.width / 2f, size.height / 2f)
            relativeLineTo(0f, 350f / 1.5f)
            moveTo(size.width / 2f, size.height / 2f)
            relativeLineTo(0f, -350f / 1.5f)
            moveTo(size.width / 2f, size.height / 2f)
            addArc(
                Rect(
                    totalWidth * 0.28f,
                    totalHeight * 0.23f,
                    totalWidth * 0.72f,
                    totalHeight * 0.77f
                ), 0f, 360f
            )
            addArc(
                Rect(
                    totalWidth * 0.24f,
                    totalHeight * 0.18f,
                    totalWidth * 0.76f,
                    totalHeight * 0.82f
                ), 0f, 90f
            )
            addArc(
                Rect(
                    totalWidth * 0.24f,
                    totalHeight * 0.18f,
                    totalWidth * 0.76f,
                    totalHeight * 0.82f
                ), 180f, 90f
            )

//            println(size)
//            println(size.width)
//            println(size.height)
//            println(size.minDimension)
//            println(size.maxDimension)
        },
        color = color,
        alpha = fadeV,
        style = Stroke(
            width = 30f,
            cap = StrokeCap.Round
        )
    )
}

fun DrawScope.likeTheLoser(
    componentSize: Size,
    myColor: Color,
    theWidth: Float,
    fadeV: Float
) {
    drawRect(
        color = myColor,
        size = componentSize,
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) / 2f
        ),
        style = Stroke(
            width = theWidth,
            cap = StrokeCap.Round
        ),
        alpha = fadeV
    )
    drawRect(
        color = myColor,
        size = Size(componentSize.height, componentSize.width),
        topLeft = Offset(
//            x = (size.width - componentSize.width) / 2f,
//            y = (size.height - componentSize.height) / 2f
            x = (size.width - componentSize.height) / 2.0f,
            y = (size.height - componentSize.width) / 2.0f
        ),
        style = Stroke(
            width = theWidth,
            cap = StrokeCap.Round
        ),
        alpha = fadeV
    )
}


//fun startRecording(fileName:String) {
//    recorder = MediaRecorder().apply {
//        setAudioSource(MediaRecorder.AudioSource.MIC)
//        setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
//        setOutputFile(fileName)
//        setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
//
//        try {
//            prepare()
//        } catch (e: IOException) {
//            Log.e(LOG_TAG, "prepare() failed")
//        }
//
//        start()
//    }
//}
