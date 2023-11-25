package com.kauproject.placepick.model.data

data class Message(
    val senderId: String = "", // 사용자 고유 식별자
    val content: String = "", // 메시지 내용
    val timestamp: Long = 0, // 메시지를 보낸 시간
    var board: String = "", //  방번호 식별자
) : Comparable<Message> {

    constructor() : this("", "", 0, "")

    override fun compareTo(other: Message): Int {
        return timestamp.compareTo(other.timestamp)
    }
}
