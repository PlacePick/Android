package com.kauproject.placepick.model

import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.kauproject.placepick.model.data.User
import kotlinx.coroutines.tasks.await
import java.lang.Exception

object FirebaseInstance {
    private val database = Firebase.database("https://kau-android-default-rtdb.asia-southeast1.firebasedatabase.app/")
    private val userRef = database.getReference("users")

    // Firebase에서 해당유저인지 확인 (Firebase 접근을 비동기적으로 수행)
    suspend fun isMember(userNum: String): Boolean {
        return try {
            val snapShot = userRef.child(userNum).get().await() // 기존 유저의 확인 await()
            snapShot.exists() && snapShot.value != null // 값이 존재
        }catch (e: Exception){
            Log.d("TEST", "${e.message}") // 예외 Log
            false
        }
    }

    // 회원가입
    fun signUp(userData: User){
        userRef.child(userData.userNum).setValue(userData)
    }
}