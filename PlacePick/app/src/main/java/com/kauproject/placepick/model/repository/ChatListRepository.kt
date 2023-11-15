package com.kauproject.placepick.model.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.kauproject.placepick.model.FireBaseInstance
import com.kauproject.placepick.model.data.Message
class ChatListRepository {
    private val database = FireBaseInstance.database
    private val chatListRef = database.getReference("chatList")

    // hotplace 리스트를 받아와서 Firebase에 추가하는 함수
    fun addHotPlacesToChatList(hotPlaces: List<String>) {
        for (hotPlace in hotPlaces) {
            // hotPlace를 키로 하는 "chatList"의 하위 레퍼런스 생성
            val chatListDetailRef = chatListRef.child(hotPlace)

            // hotPlace에 대한 message 객체 생성 (원하는 속성으로 변경 가능)
            val messages = Message("샘플유저", "샘플메시지", 131231312, hotPlace)

            // hotPlace에 해당하는 데이터를 Firebase에 추가
            chatListDetailRef.setValue(messages)
        }
    }

    fun addChatList(
        board: String,
        titleCallback: (String) -> Unit,
        detail: (List<Message>) -> Unit
    ) {
        val chatListDetailRef = chatListRef.child(board)
        val detailList = mutableListOf<Message>()

        // 추가: 채팅 목록의 제목을 콜백으로 전달
        titleCallback(board)

        chatListDetailRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (item in snapshot.children) {
                    item.getValue(Message::class.java)?.let { message ->
                        // Message 객체에 board 식별자 추가
                        message.board = board
                        detailList.add(message)
                    }
                }
                detail(detailList)
            }

            override fun onCancelled(error: DatabaseError) {
                detail(emptyList())
            }
        })
    }
}