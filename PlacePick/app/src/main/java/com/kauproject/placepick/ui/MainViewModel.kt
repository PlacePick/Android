package com.kauproject.placepick.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kauproject.placepick.model.data.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel: ViewModel() {
    private val _detailList = MutableLiveData<List<Post>>()
    val detailList: LiveData<List<Post>>
        get() = _detailList

    fun setDetailList(detailList: List<Post>){
        _detailList.value = detailList
    }
}