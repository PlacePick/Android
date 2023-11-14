package com.kauproject.placepick.ui.chat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kauproject.placepick.databinding.FragmentChatBinding
import com.kauproject.placepick.util.BaseFragment

class ChatFragment : BaseFragment<FragmentChatBinding>() {

    private lateinit var chatId: String
    private lateinit var chatAdapter: ChatAdapter
    private val database = Firebase.database

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chatId = arguments?.getString("chatId", "") ?: ""
        binding.txtTItle.text = chatId

        val messages = mutableListOf<ChatMessage>()

        chatAdapter = ChatAdapter(messages)
        binding.recyclerMessages.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerMessages.adapter = chatAdapter

        // 메시지 전송 버튼 클릭 시 호출
        binding.btnSubmit.setOnClickListener {
            val messageText = binding.edtMessage.text.toString()
            if (messageText.isNotEmpty()) {
                // 메시지를 Firebase에 전송하는 로직을 추가
                sendMessageToFirebase(messageText)
                binding.edtMessage.text.clear()
            } else {
                // 사용자에게 메시지를 입력하라는 메시지 또는 적절한 조치를 취할 수 있습니다.
                Toast.makeText(requireContext(), "메시지를 입력하세요.", Toast.LENGTH_SHORT).show()
            }
        }
        setupFirebaseListener()
    }

    private fun sendMessageToFirebase(messageText: String) {
        try {
            val chatRef = database.getReference("chatList").child(chatId).child("messages")

            val messageId = chatRef.push().key ?: ""
            val timestamp = System.currentTimeMillis()
            val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

            val messageData = mapOf(
                "senderId" to currentUserId,
                "text" to messageText,
                "timestamp" to timestamp
            )

            chatRef.child(messageId).setValue(messageData)
                .addOnSuccessListener {
                    // Firebase에 성공적으로 메시지를 전송한 경우 로그 출력
                    Log.d("ChatFragment", "Message sent successfully")
                }
                .addOnFailureListener { e ->
                    // 전송 실패 시 로그 출력
                    Log.e("ChatFragment", "Error sending message", e)
                }
        } catch (e: Exception) {
            // 예외 발생 시 로그 출력
            Log.e("ChatFragment", "Exception occurred in sendMessageToFirebase", e)
        }
    }


    private fun setupFirebaseListener() {
        try {
            val chatRef = database.getReference("chatList").child(chatId).child("messages")

            chatRef.addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    try {
                        val message = snapshot.getValue(ChatMessage::class.java)
                        message?.let {
                            chatAdapter.addMessage(it)
                            binding.recyclerMessages.smoothScrollToPosition(chatAdapter.itemCount - 1)
                            Log.d("ChatFragment", "Message added to the adapter")
                        }
                    } catch (e: Exception) {
                        // 예외 발생 시 로그 출력
                        Log.e("ChatFragment", "Exception occurred in onChildAdded", e)
                    }
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    // 필요한 경우 처리
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    // 필요한 경우 처리
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    // 필요한 경우 처리
                }

                override fun onCancelled(error: DatabaseError) {
                    // 필요한 경우 처리
                    Log.e("ChatFragment", "Firebase listener cancelled", error.toException())
                }
            })
        } catch (e: Exception) {
            // 예외 발생 시 로그 출력
            Log.e("ChatFragment", "Exception occurred in setupFirebaseListener", e)
        }
    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentChatBinding {
        return FragmentChatBinding.inflate(inflater, container, false)
    }
}