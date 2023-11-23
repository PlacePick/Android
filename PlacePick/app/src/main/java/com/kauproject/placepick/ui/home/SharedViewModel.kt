package com.kauproject.placepick.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _selectedPlace = MutableLiveData<List<String?>>()
    val selectedPlace: LiveData<List<String?>>
        get() = _selectedPlace

    fun setSelectedPlace(place: List<String?>) {
        _selectedPlace.value = place
    }
}