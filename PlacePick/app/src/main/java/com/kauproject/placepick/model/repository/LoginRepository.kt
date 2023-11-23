package com.kauproject.placepick.model.repository

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.kauproject.placepick.model.FireBaseInstance
import com.kauproject.placepick.model.data.User
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class LoginRepository {
    private val database = FireBaseInstance.database
    private val userRef = database.getReference("users")

    suspend fun isMember(userNum: String): Boolean {
        return try {
            val snapShot = userRef.child(userNum).get().await() // 기존 유저의 확인 await()
            snapShot.exists() && snapShot.value != null // 값이 존재
        }catch (e: Exception){
            Log.d("ERROR:", "${e.message}") // 예외 Log
            false
        }
    }

    // 회원가입
    fun signUp(userData: User){
        userRef.child(userData.userNum).setValue(userData)
    }
    suspend fun updateUserHotPlaces(userNum: String, place1: String?, place2: String?, place3: String?) {
        try {
            val userRef = database.getReference("users").child(userNum)
            userRef.child("place1").setValue(place1)
            userRef.child("place2").setValue(place2)
            userRef.child("place3").setValue(place3)
        } catch (e: Exception) {
            Log.d("ERROR:", "${e.message}")
        }
    }

}