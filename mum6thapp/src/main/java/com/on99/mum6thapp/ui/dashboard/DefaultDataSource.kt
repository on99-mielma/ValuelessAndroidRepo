package com.on99.mum6thapp.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay

@Deprecated("no use any more")
class DefaultDataSource (private val ioDispatcher: CoroutineDispatcher) : DataSource{

    override fun getCurrentTime(): LiveData<Long> =
        liveData {
            while (true){
                emit(System.currentTimeMillis())
                delay(1000)
            }
        }



}



@Deprecated("useless")
interface DataSource{
    fun getCurrentTime():LiveData<Long>
}