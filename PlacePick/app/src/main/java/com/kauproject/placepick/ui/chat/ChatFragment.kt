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
import com.kauproject.placepick.ui.home.HomeFragment
import com.kauproject.placepick.util.BaseFragment
import kotlinx.coroutines.launch

class ChatFragment : BaseFragment<FragmentChatBinding>() {
    private lateinit var chatAdapter: ChatAdapter
    private val messages = mutableListOf<Message>()


    // ChatFragment의 newInstance 메서드에 placeData 추가
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

        // ChatAdapter 생성 및 RecyclerView에 연결
        chatAdapter = ChatAdapter(messages)
        recyclerView.adapter = chatAdapter

        // btn_submit 클릭 시 호출되는 함수
        binding.btnSubmit.setOnClickListener {
            val messageContent = binding.edtMessage.text.toString().trim()

            if (messageContent.isNotEmpty()) {
//                val senderId = "사용자 고유 식별자" // 사용자 고유 식별자를 지정해야 합니다.
                val timestamp = System.currentTimeMillis() // 현재 시간을 가져와서 사용합니다.
                lifecycleScope.launch { // lifecycleScope를 사용하여 코루틴 블록 시작

                    // DataStore에서 사용자 데이터 가져오기
                    val placeData = arguments?.getString(ARG_PLACE_DATA) ?: ""
                    val board = placeData
                    val userData = DataStore(requireContext()).getUserData()
                    val senderId  = userData.nickName ?: ""
                    val nickName = userData.nickName ?: ""
                    val selectedHotPlace = binding.txtTitle.text.toString()


                    // ChatListRepository를 이용하여 특정 채팅방의 메시지를 가져오기
                    val chatListRepository = ChatListRepository()
                    val chatMessages = chatListRepository.getMessagesForChatRoom(selectedHotPlace, nickName)
                    messages.addAll(chatMessages)
                    chatAdapter.notifyDataSetChanged()


                    val newMessage = Message(senderId, messageContent, timestamp, board, true)

                    messages.add(newMessage)
                    chatAdapter.notifyItemInserted(messages.size - 1)

                    // Firebase에 메시지 추가
                    addMessageToFirebase(newMessage, nickName, selectedHotPlace)

                    // 메시지를 입력한 후 EditText 초기화
                    binding.edtMessage.text.clear()
                }
            }
        }
        val title = arguments?.getString(ARG_TITLE)
        binding.txtTitle.text = title ?: ""
        binding.imgbtnQuit.setOnClickListener {
            // HomeFragment의 인스턴스 생성
            val homeFragment = HomeFragment()

            // 현재 프래그먼트를 HomeFragment로 교체하기 위한 트랜잭션 시작
            parentFragmentManager.beginTransaction()
                .replace(R.id.fl_main, homeFragment)
                .commit()
        }

    }

    // Firebase에 메시지를 추가하는 함수
    private suspend fun addMessageToFirebase(message: Message, nickName: String, selectedHotPlace: String) {
        val chatListRepository = ChatListRepository()
        chatListRepository.addMessageToChatList(message, nickName, selectedHotPlace)
    }
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentChatBinding {
        return FragmentChatBinding.inflate(inflater, container, false)
    }


}
