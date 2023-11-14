package com.kauproject.placepick.ui.chat


data class ChatMessage(
    val sender: String = "",
    val text: String = "",
    val timestamp: Long = 0
)
