package com.on99.xd5thapp.livedatabuilder

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Date

class LiveDataViewModel(
    private val dataSource: DataSource
):ViewModel(){
    val currentTime = dataSource.getCurrentTime()
    val currentTimeTransformed = currentTime.switchMap {
        liveData { emit(timeStampToTime(it)) }
    }

    val currentWeather: LiveData<String> = liveData {
        emit(LOADING_STRING)
        emitSource(dataSource.fetchWeather())
    }

    val cachedValue = dataSource.cachedData

    val socketValue = dataSource.socketText

    fun onRefresh(){
        viewModelScope.launch {
            dataSource.fetchNewData()
        }
    }

    fun onSocketRefresh(/*word:String*/){
        viewModelScope.launch {
            dataSource.fetchSocket(/*word*/)
        }
    }



    private suspend fun timeStampToTime(timestamp: Long): String {
        delay(500)
        val data = Date(timestamp)
        return data.toString()
    }

    companion object{
        const val LOADING_STRING = "加载中"
    }
}

object  LiveDataVMFactory : ViewModelProvider.Factory{
    private val dataSource = DefaultDataSource(Dispatchers.IO)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return LiveDataViewModel(dataSource) as T
    }
}