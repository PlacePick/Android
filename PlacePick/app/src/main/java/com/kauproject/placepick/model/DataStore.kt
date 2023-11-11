package com.kauproject.placepick.model

import android.content.Context
import android.service.autofill.UserData
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.kauproject.placepick.model.data.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.dataStore by preferencesDataStore(name = "user_data")
class DataStore(
    private val context: Context
) {
    private val _userData = MutableStateFlow(User())

    // Key 값 정의
    companion object{
        private val NUM_KEY = stringPreferencesKey("num")
        private val NICK_KEY = stringPreferencesKey("nick")
        private val PLACE_FIRST_KEY = stringPreferencesKey("first")
        private val PLACE_SECOND_KEY = stringPreferencesKey("second")
        private val PLACE_THIRD_KEY = stringPreferencesKey("third")
    }

    // 유저데이터 조회
    suspend fun getUserData(): User{
        val userData = context.dataStore.data
            .catch { e->
                if(e is IOException){
                    emit(emptyPreferences()) // 예외 발생 경우 emptyPreference 방출
                }else{
                    throw e // IOException 이외에 다른 예외가 발생한 경우
                }
            }.map { preferences->
                mapperToUserData(preferences) // map을 사용해 User 객체로 반환
            }.first() // Flow에서 발생한 첫번째 데이터 반환(소비)
        return userData
    }

    // User 데이터 세팅
    suspend fun setUserData(key: String, value: String?){
        context.dataStore.edit { preferences->
            val preferencesKey = when(key){
                "num" -> {
                    _userData.value.userNum = value ?: ""
                    NUM_KEY
                }
                "nick" -> {
                    _userData.value.nickName = value
                    NICK_KEY
                }
                "first" -> {
                    _userData.value.place1 = value
                    PLACE_FIRST_KEY
                }
                "second" -> {
                    _userData.value.place2 = value
                    PLACE_SECOND_KEY
                }
                "third" -> {
                    _userData.value.place3 = value
                    PLACE_THIRD_KEY
                }
                else -> throw IllegalArgumentException("Unknown key:$key")
            }
            value?.let {
                preferences[preferencesKey] = it
            }
        }
    }

    // User 객체로 반환하기 위한 함수
    private fun mapperToUserData(preferences: Preferences): User{
        val userNum = preferences[NUM_KEY] ?: ""
        val nick = preferences[NICK_KEY]
        val first = preferences[PLACE_FIRST_KEY]
        val second = preferences[PLACE_SECOND_KEY]
        val third = preferences[PLACE_THIRD_KEY]

        return User(userNum, nick, first, second, third)
    }

}