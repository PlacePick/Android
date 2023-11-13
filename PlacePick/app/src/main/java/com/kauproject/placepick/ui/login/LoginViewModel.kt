package com.kauproject.placepick.ui.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kauproject.placepick.model.repository.LoginRepository
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import kotlinx.coroutines.launch

class LoginViewModel(
    val context: Context,
    private val loginRepository: LoginRepository
): ViewModel() {
    companion object{
        const val TAG = "LoginViewModel"
        const val NAVER_CLIENT_ID = "kLOuqtFqCgm7GAYYDQvb"
        const val NAVER_SECRET_KEY = "zVr8P_1j35"
    }

    private val _userNum = MutableLiveData<String?>()
    val userNum: LiveData<String?>
        get() = _userNum
    private val _isMember = MutableLiveData<Boolean?>()
    val isMember: LiveData<Boolean?>
        get() = _isMember

    private lateinit var oAuthLoginCallback: OAuthLoginCallback

    fun naverSetOAuthLoginCallback(updateToken: (String) -> Unit){
        oAuthLoginCallback = object : OAuthLoginCallback{
            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }

            override fun onFailure(httpStatus: Int, message: String) {
                Log.d(TAG, message)
            }

            override fun onSuccess() {
                updateToken(NaverIdLoginSDK.getAccessToken() ?: "")
            }
        }
    }

    fun initNaverSDK(updateToken: (String) -> Unit){
        naverSetOAuthLoginCallback { it->
            updateToken(it)
        }

        NaverIdLoginSDK.initialize(
            context,
            NAVER_CLIENT_ID,
            NAVER_SECRET_KEY,
            "PlacePick"
        )
        NaverIdLoginSDK.authenticate(context, oAuthLoginCallback)
    }

    fun startLogin(){
        viewModelScope.launch {
            initNaverSDK {
                NidOAuthLogin().callProfileApi(object : NidProfileCallback<NidProfileResponse>{
                    override fun onError(errorCode: Int, message: String) {
                        onFailure(errorCode, message)
                    }

                    override fun onFailure(httpStatus: Int, message: String) {
                        Log.d(TAG, "ERROR: $message")
                    }

                    override fun onSuccess(result: NidProfileResponse) {
                        checkMember(result.profile?.id)
                        _userNum.value = result.profile?.id
                    }

                })
            }
        }
    }

    fun checkMember(userNum: String?){
        viewModelScope.launch{
            userNum?.let {
                _isMember.value = loginRepository.isMember(userNum)
            }
        }
    }


}