package com.kauproject.placepick.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.kauproject.placepick.R
import com.kauproject.placepick.databinding.FragmentHomeBinding
import com.kauproject.placepick.model.repository.ChatListRepository
import com.kauproject.placepick.ui.chat.ChatFragment

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnRealtimeInput = binding.btnRealtimeInput
        val btnChoice1 = binding.btnChoice1
        val btnChoice2 = binding.btnChoice2
        val btnChoice3 = binding.btnChoice3

        btnChoice1.setOnClickListener {
            navigateToChatFragment("이태원역")
        }

        btnChoice2.setOnClickListener {
            navigateToChatFragment("서울역")
        }

        btnChoice3.setOnClickListener {
            navigateToChatFragment("강남역")
        }

        btnRealtimeInput.setOnClickListener {
            navigateToChatFragment("서울역")
        }

    }

    private fun navigateToChatFragment(chatId: String) {
        val chatFragment = ChatFragment()
        val bundle = Bundle()
        bundle.putString("chatId", chatId)
        chatFragment.arguments = bundle

        parentFragmentManager.beginTransaction()
            .replace(R.id.fl_main, chatFragment)
            .addToBackStack(null)
            .commit()

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}