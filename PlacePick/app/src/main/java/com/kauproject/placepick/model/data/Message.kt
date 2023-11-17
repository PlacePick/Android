package com.kauproject.placepick.model.data
data class Message(
    val senderId: String, // 사용자 고유 식별자
    val content: String, // 메시지 내용
    val timestamp: Long, // 메시지를 보낸 시간
    var board: String, //  방번호 식별자
    val currentUser: Boolean // 유저가 '나'인지 여부

)
