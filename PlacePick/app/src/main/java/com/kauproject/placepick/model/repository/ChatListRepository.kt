package com.kauproject.placepick.model.repository

import com.kauproject.placepick.model.FireBaseInstance
import com.kauproject.placepick.model.data.Message
import kotlinx.coroutines.tasks.await

class ChatListRepository {
    private val database = FireBaseInstance.database
    private val chatListRef = database.getReference("chatList")



    // 새로운 함수를 추가하여 특정 채팅방의 메시지를 가져오는 로직을 작성
    suspend fun getMessagesForChatRoom(selectedHotPlace: String): List<Message> {
        val chatListDetailRef = chatListRef.child(selectedHotPlace)

        return try {
            val dataSnapshot = chatListDetailRef.get().await()

            val messages = mutableListOf<Message>()

            dataSnapshot.children.mapNotNull { nickNameSnapshot ->

                    // nickNameSnapshot은 nickName을 키로 갖는 객체
                    val nickName = nickNameSnapshot.key
                    if (nickName != null) {
                        // 각 nickName에 해당하는 메시지들을 가져옴
                        val messagesForNickName = nickNameSnapshot.children.mapNotNull { timestampSnapshot ->
                                val message = timestampSnapshot.getValue(Message::class.java)
                                if (message != null) {
                                    messages.add(message)
                                } else {

                            }
                        }
                    } else {
                    }

            }

            messages
        } catch (e: Exception) {
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
