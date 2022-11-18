package com.on99.mum6thapp.ui.dashboard

import androidx.lifecycle.*

import kotlinx.coroutines.delay

import java.util.*

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }

    val text: LiveData<String> = _text


    val cT: LiveData<Long> = liveData {
        while (true){
            emit(System.currentTimeMillis())
            delay(1000)
        }
    }

    val cTafterTrans = cT.switchMap {
        liveData { emit(timeStampToTime(it)) }
    }



    private suspend fun timeStampToTime(timestamp: Long): String {
        delay(500)  // Simulate long operation
        val date = Date(timestamp)
        return date.toString()
    }
}