package com.kauproject.placepick.ui.chat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kauproject.placepick.R
import com.kauproject.placepick.databinding.FragmentChatBinding
import com.kauproject.placepick.model.DataStore
import com.kauproject.placepick.model.repository.ChatListRepository
import com.kauproject.placepick.model.data.Message
import com.kauproject.placepick.model.data.User
import com.kauproject.placepick.ui.home.HomeFragment
import com.kauproject.placepick.util.BaseFragment
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ChatFragment : BaseFragment<FragmentChatBinding>() {
    private lateinit var chatAdapter: ChatAdapter
    private val messages = mutableListOf<Message>()


    companion object {
        private const val ARG_TITLE = "title"
        private const val ARG_PLACE_DATA = "place_data"

        fun newInstance(title: String, placeData: String): ChatFragment {
            val fragment = ChatFragment()
            val args = Bundle()
            args.putString(ARG_TITLE, title)
            args.putString(ARG_PLACE_DATA, placeData)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.recyclerMessages
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val title = arguments?.getString(ARG_TITLE)
        binding.txtTitle.text = title ?: ""
        binding.imgbtnQuit.setOnClickListener {
            val homeFragment = HomeFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fl_main, homeFragment)
                .commit()
        }
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("MM/dd", Locale.getDefault())
        val formattedDate = dateFormat.format(calendar.time)
        binding.textView2.text = formattedDate

        lifecycleScope.launch {
            val userData = getUserData()
            val currentUserNickname = userData.nickName ?: ""

            chatAdapter = ChatAdapter(messages, currentUserNickname)
            recyclerView.adapter = chatAdapter

            // 채팅방의 메시지를 가져오고 어댑터에 설정
            val placeData = arguments?.getString(ARG_PLACE_DATA) ?: ""
            val board = placeData
            val nickName = userData.nickName ?: ""
            val selectedHotPlace = binding.txtTitle.text.toString()

            val chatListRepository = ChatListRepository()
            try {
                val chatMessages = chatListRepository.getMessagesForChatRoom(selectedHotPlace)
                messages.clear()
                messages.addAll(chatMessages.sortedBy { it.timestamp })
                chatAdapter = ChatAdapter(messages, currentUserNickname)
                recyclerView.adapter = chatAdapter

            } catch (e: Exception) {
                Log.e("ChatFragment", "Error getting messages: ${e.message}")
            }

            binding.btnSubmit.setOnClickListener {
                val messageContent = binding.edtMessage.text.toString().trim()

                if (messageContent.isNotEmpty()) {
                    val timestamp = System.currentTimeMillis()

                    lifecycleScope.launch {
                        val newMessage = Message(nickName, messageContent, timestamp, board )
                        messages.add(newMessage)
                        chatAdapter.notifyItemInserted(messages.size - 1)

                        // Firebase에 메시지 추가
                        try {
                            chatListRepository.addMessageToChatList(newMessage, nickName, selectedHotPlace)
                        } catch (e: Exception) {
                            Log.e("ChatFragment", "Error adding message to Firebase: ${e.message}")
                        }

                        binding.edtMessage.text.clear()
                        recyclerView.smoothScrollToPosition(messages.size - 1)

                    }
                }
            }
        }
    }






    private suspend fun getUserData(): User {
        return DataStore(requireContext()).getUserData()
    }



    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentChatBinding {
        return FragmentChatBinding.inflate(inflater, container, false)
    }



}
