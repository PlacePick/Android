package com.kauproject.placepick.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kauproject.placepick.databinding.FragmentChatBinding
import com.kauproject.placepick.model.repository.ChatListRepository
import com.kauproject.placepick.model.data.Message
import com.kauproject.placepick.util.BaseFragment

class ChatFragment : BaseFragment<FragmentChatBinding>() {
    companion object {
        private const val ARG_TITLE = "title"

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

        val title = arguments?.getString(ARG_TITLE)
        binding.txtTItle.text = title ?: ""
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentChatBinding {
        return FragmentChatBinding.inflate(inflater, container, false)
    }

}