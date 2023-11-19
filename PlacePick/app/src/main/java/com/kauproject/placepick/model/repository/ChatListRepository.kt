package com.kauproject.placepick.model.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.kauproject.placepick.model.FireBaseInstance
import com.kauproject.placepick.model.data.Message
import com.kauproject.placepick.util.HotPlace
import kotlinx.coroutines.tasks.await

class ChatListRepository {
    private val database = FireBaseInstance.database
    private val chatListRef = database.getReference("chatList")
//    private val userRef = database.getReference("users")

    // hotplace 리스트를 받아와서 Firebase에 추가하는 함수
    fun addHotPlacesToChatList(hotPlaces: List<String>) {
        for (hotPlace in hotPlaces) {
            // hotPlace를 키로 하는 "chatList"의 하위 레퍼런스 생성
            val chatListDetailRef = chatListRef.child(hotPlace)

            // hotPlace에 대한 message 객체 생성 (원하는 속성으로 변경 가능)
            val messages = Message("샘플유저", "샘플메시지", 131231312, hotPlace,true)

            // hotPlace에 해당하는 데이터를 Firebase에 추가
            chatListDetailRef.setValue(messages)
        }
    }


    // 새로운 함수를 추가하여 특정 채팅방의 메시지를 가져오는 로직을 작성
    suspend fun getMessagesForChatRoom(selectedHotPlace: String, nickName: String): List<Message> {
        val chatListDetailRef = chatListRef.child(selectedHotPlace).child(nickName)

        // ValueEventListener를 사용하여 데이터 읽어오기
        return try {
            val dataSnapshot = chatListDetailRef.get().await()

            // dataSnapshot이 null이 아니고 해당 경로에 데이터가 있는 경우에만 처리
            dataSnapshot.children.mapNotNull { childSnapshot ->
                childSnapshot.getValue(Message::class.java)
            }
        } catch (e: Exception) {
            // 예외 발생 시에는 빈 리스트 반환 또는 에러 로깅 등을 수행할 수 있습니다.
            emptyList()
        }
    }

    suspend fun addMessageToChatList(message: Message, nickName: String, selectedHotPlace: String) {
        // 닉네임으로 메시지를 Firebase에 추가

        val chatListDetailRef = chatListRef.child(selectedHotPlace).child(nickName)

        // timestamp를 고유키로 사용
        val timestampKey = message.timestamp.toString()

        // 해당 timestamp 키에 데이터가 존재하는지 확인
        val existingDataSnapshot = chatListDetailRef.child(timestampKey).get().await()

        if (!existingDataSnapshot.exists()) {
            // timestamp 키에 데이터가 없을 때만 해당 키에 데이터 추가
            chatListDetailRef.child(timestampKey).setValue(message)
        }
    }



}
