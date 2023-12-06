package com.kauproject.placepick.ui.home


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kauproject.placepick.model.RetrofitInstance
import com.kauproject.placepick.model.response.GetHotPlaceInfoResponse
import com.kauproject.placepick.model.service.GetHotPlaceInfoService
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _hotPlaceInfo = MutableLiveData<GetHotPlaceInfoResponse>()
    val hotPlaceInfo: LiveData<GetHotPlaceInfoResponse>
        get() = _hotPlaceInfo

    private val _selectedPlace = MutableLiveData<String>()
    val selectedPlace: LiveData<String>
        get() = _selectedPlace


    fun loadHotPlaceInfo(selectedPlace: String) {
        viewModelScope.launch {
            val response = RetrofitInstance.retrofit.create(GetHotPlaceInfoService::class.java).getHotPlaceInfo(selectedPlace)
            _hotPlaceInfo.value = response.body()
        }
    }

    fun setSelectedPlace(place: String) {
        _selectedPlace.value = place
    }
}

