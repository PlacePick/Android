package com.kauproject.placepick.ui.chat

import android.os.Bundle
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
        const val ARG_TITLE = "title"
        fun newInstance(title: String): ChatFragment {
            val fragment = ChatFragment()
            val args = Bundle()
            args.putString(ARG_TITLE, title)
            fragment.arguments = args
            return fragment
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //메시지 전체
        val recyclerView = binding.recyclerMessages
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //채팅방 이름 설정
        val title = arguments?.getString(ARG_TITLE)
        binding.txtTitle.text = title ?: ""

        //뒤로가기 버튼
        binding.imgbtnQuit.setOnClickListener {
            val homeFragment = HomeFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fl_main, homeFragment)
                .commit()
        }


        //당일 날짜 설정
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("MM/dd", Locale.getDefault())
        val formattedDate = dateFormat.format(calendar.time)
        binding.textView2.text = formattedDate

        lifecycleScope.launch {
            val userData = getUserData()
            val currentUserNickname = userData.nickName ?: ""

            // 채팅방의 메시지를 가져오고 어댑터에 설정& 시간순으로 정렬
            val selectedHotPlace = binding.txtTitle.text.toString()

            val chatListRepository = ChatListRepository()

            val chatMessages = chatListRepository.getMessagesForChatRoom(selectedHotPlace)
            messages.clear()
            messages.addAll(chatMessages.sortedBy { it.timestamp })
            chatAdapter = ChatAdapter(messages, currentUserNickname)
            recyclerView.adapter = chatAdapter


            //전송 버튼 시  edtMessage 내용이 해당 firebase에 값 추가
            binding.btnSubmit.setOnClickListener {
                val messageContent = binding.edtMessage.text.toString().trim()

                if (messageContent.isNotEmpty()) {
                    val timestamp = System.currentTimeMillis()

                    lifecycleScope.launch {
                        val newMessage = Message(currentUserNickname, messageContent, timestamp )

                        //화면에 메시지 추가&자동 스크롤
                        messages.add(newMessage)

                        // Firebase에 메시지 추가
                        chatListRepository.addMessageToChatList(newMessage, currentUserNickname, selectedHotPlace)

                        //전송 후 입력창 초기화&최신 메시지 보이도록 스크롤 설정
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

