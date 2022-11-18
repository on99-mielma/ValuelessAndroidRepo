package com.on99.mum6thapp.ui.mielma

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MielmaViewModel :ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is make by on99 the Fragment but write in ViewModel"
    }

    val text: LiveData<String> = _text

}