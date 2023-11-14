package com.kauproject.placepick.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kauproject.placepick.R
import com.kauproject.placepick.databinding.FragmentChatListBinding
import com.kauproject.placepick.model.repository.ChatListRepository
import com.kauproject.placepick.util.BaseFragment

class ChatListFragment : BaseFragment<FragmentChatListBinding>() {

    private val chatListRepository = ChatListRepository()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 실제로 사용하는 채팅방 ID를 설정합니다.
        val chatId = "서울역" // 또는 채팅방을 선택하거나 동적으로 설정하는 로직 추가

        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_messages)
        val chatRooms = chatListRepository.getChatRooms()

        // ChatAdapter의 인스턴스를 생성합니다.
        val adapter = ChatAdapter(mutableListOf())

        // getChatMessages 함수에 ChatAdapter 인스턴스를 전달합니다.
        chatListRepository.getChatMessages(chatId, adapter)

        // RecyclerView에 어댑터를 설정합니다.
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentChatListBinding {
        return FragmentChatListBinding.inflate(inflater, container, false)
    }
}

