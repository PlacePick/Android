package com.kauproject.placepick.model.repository

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kauproject.placepick.model.FireBaseInstance.database
import com.kauproject.placepick.ui.chat.ChatAdapter
import com.kauproject.placepick.ui.chat.ChatMessage
class ChatListRepository {

    // 여러 채팅 아이디에 대해 채팅 방을 생성하는 메서드
    fun createChatRooms(chatIds: List<String>) {
        for (chatId in chatIds) {
            createChatRoom(chatId)
        }
    }

    // 단일 채팅 아이디에 대해 채팅 방을 생성하는 메서드
    private fun createChatRoom(chatId: String) {
        try {
            // Firebase Realtime Database에서 "chatList" 경로에 있는 특정 채팅 아이디의 하위 레퍼런스를 가져옴
            val chatRef = database.getReference("chatList").child(chatId)

            // 생성된 채팅 방의 초기 데이터로 빈 메시지 목록을 가지는 맵을 생성
            val chatData = mapOf(
                "messages" to emptyList<ChatMessage>()
            )

            // 채팅 방 레퍼런스에 초기 데이터를 설정하고, 설정이 완료되면 콜백이 호출됨
            chatRef.setValue(chatData)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("ChatListRepository", "Chat room created successfully")
                    } else {
                        // 채팅 방 생성 중 오류가 발생한 경우 에러 로그를 출력
                        Log.e("ChatListRepository", "Error creating chat room", task.exception)
                    }
                }
        } catch (e: Exception) {
            // 예외가 발생한 경우 에러 로그를 출력
            Log.e("ChatListRepository", "Exception occurred in createChatRoom", e)
        }
    }

    fun getChatMessages(chatId: String, chatAdapter: ChatAdapter) {
        // Firebase에서 해당 chatId의 메시지를 가져오는 로직을 구현합니다.
        // ChatMessage 객체의 목록을 반환합니다.
        val messages = mutableListOf<ChatMessage>()

        // chatId에 해당하는 채팅방의 messages 하위 노드에서 데이터 가져오기
        val chatRef = database.getReference("chatList").child(chatId).child("messages")

        chatRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (messageSnapshot in snapshot.children) {
                    val messageData = messageSnapshot.getValue(ChatMessage::class.java)
                    messageData?.let {
                        messages.add(it)
                    }
                }
                // 데이터 변경이 있을 때마다 어댑터에 알리기
                chatAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // 실패 시 처리
            }
        })
    }


    // 채팅방 목록 가져오기
    fun getChatRooms(): List<String> {
        // Firebase에서 채팅방 목록을 가져오는 로직을 구현합니다.
        // 채팅방의 ID 목록을 반환합니다.
        return listOf("이태원역", "서울역", "강남역")
    }

    fun getDynamicChatRooms(): List<String> {
        return listOf("강남역", "이태원역", "서울역")
    }

}