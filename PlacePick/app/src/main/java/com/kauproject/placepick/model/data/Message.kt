package com.kauproject.placepick.model.data

data class Message(
    val senderId: String = "", // 사용자 고유 식별자
    val content: String = "", // 메시지 내용
    val timestamp: Long = 0, // 메시지를 보낸 시간
)


