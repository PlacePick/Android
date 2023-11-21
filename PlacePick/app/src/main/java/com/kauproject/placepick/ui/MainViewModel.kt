package com.kauproject.placepick.ui

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kauproject.placepick.ApplicationClass
import com.kauproject.placepick.model.DataStore
import com.kauproject.placepick.model.RetrofitInstance
import com.kauproject.placepick.model.State
import com.kauproject.placepick.model.data.Post
import com.kauproject.placepick.model.data.User
import com.kauproject.placepick.model.repository.BoardRepository
import com.kauproject.placepick.model.service.GetHotPlaceInfoService
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
class MainViewModel(
    application: Application
): AndroidViewModel(application) {
    private val dataStore = DataStore(application)
    private val retrofit = RetrofitInstance.retrofit
    companion object{
        const val TAG = "MainViewModel"
    }

    private val boardRepository = BoardRepository()

    private val _detailList = MutableLiveData<List<Post>>()
    val detailList: LiveData<List<Post>>
        get() = _detailList


    private val _board = MutableLiveData<String>()
    val board: LiveData<String>
        get() = _board

    private val _userData = MutableLiveData<UserData>()
    val userData: LiveData<UserData>
        get() = _userData

    private val _placeState = MutableLiveData<List<String?>?>()
    val placeState: LiveData<List<String?>?>
        get() = _placeState

    fun getDetailList(){
        board.value?.let {
            boardRepository.getBoardDetail(it){ list->
                _detailList.value = list
            }
        }
    }

    fun setBoard(board: String){
        _board.value = board
    }

    fun getUserData(){
        viewModelScope.launch {
            _userData.value = UserData(
                userNum = dataStore.getUserData().userNum,
                nick = dataStore.getUserData().nickName
            )
        }
    }
    fun postWrite(post: Post){
        board.value?.let {
            boardRepository.postWrite(post, it)
        }
    }

    fun getPlaceStatus(){
        viewModelScope.launch {
            boardRepository.getPlaceStatus().collect{ state->
                when(state){
                    is State.Loading -> { _placeState.value = emptyList()}
                    is State.Success -> { _placeState.value = state.data }
                    is State.ServerError -> {
                        _placeState.value = null
                        Log.d(TAG, "SERVERERROR${state.code}")
                    }
                    is State.Error -> {
                        _placeState.value = null
                        Log.d(TAG, "ERROR:${state.exception}")
                    }
                }
            }
        }
    }
}

data class UserData(
    val userNum: String = "",
    val nick: String? = null
)