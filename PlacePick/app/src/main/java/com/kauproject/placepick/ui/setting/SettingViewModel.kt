package com.kauproject.placepick.ui.setting

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kauproject.placepick.model.DataStore
import com.kauproject.placepick.model.FirebaseInstance
import com.kauproject.placepick.model.data.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingViewModel(
    private val dataStore: DataStore
): ViewModel() {
    companion object{
        const val TAG = "SettingViewModel"
    }

    fun setUserNum(userNum: String){
        viewModelScope.launch {
            dataStore.setUserData("num", userNum)
            Log.d(TAG, "${dataStore.getUserData()}")
        }
    }

    fun setUserNickName(nick: String){
        viewModelScope.launch {
            dataStore.setUserData("nick", nick)
            Log.d(TAG, "${dataStore.getUserData()}")
        }
    }

    fun setHotPlace(hotPlace: List<String?>){
        viewModelScope.launch {
            dataStore.setUserData("first", hotPlace[0])
            dataStore.setUserData("second", hotPlace[1])
            dataStore.setUserData("third", hotPlace[2])
        }
    }

    fun complete(){
        viewModelScope.launch {
            FirebaseInstance.signUp(dataStore.getUserData())
            Log.d(TAG, "${dataStore.getUserData()}")
        }
    }
}