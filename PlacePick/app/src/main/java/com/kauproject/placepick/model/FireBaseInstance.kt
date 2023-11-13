package com.kauproject.placepick.model

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

object FireBaseInstance {
    val database = Firebase.database("https://kau-android-default-rtdb.asia-southeast1.firebasedatabase.app/")
}