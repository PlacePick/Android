package com.kauproject.placepick.ui.setting

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kauproject.placepick.model.DataStore
import com.kauproject.placepick.model.repository.LoginRepository
import kotlinx.coroutines.launch

class SettingViewModel(
    private val dataStore: DataStore,
    private val loginRepository: LoginRepository
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
            loginRepository.signUp(dataStore.getUserData())
            Log.d(TAG, "${dataStore.getUserData()}")
        }
    }
}