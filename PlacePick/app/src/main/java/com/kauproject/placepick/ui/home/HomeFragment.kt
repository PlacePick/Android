package com.kauproject.placepick.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.kauproject.placepick.R
import com.kauproject.placepick.databinding.FragmentHomeBinding
import com.kauproject.placepick.model.RetrofitInstance
import com.kauproject.placepick.model.service.GetHotPlaceInfoService
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
        btnRealtimeInput.setOnClickListener {
            val chatFragment = ChatFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fl_main, chatFragment)
                .addToBackStack(null) // 백 스택에 추가 (뒤로 가기 버튼으로 홈 화면으로 돌아갈 수 있게 함)
                .commit()
        }

        val response = RetrofitInstance.retrofit.create(GetHotPlaceInfoService::class.java)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
