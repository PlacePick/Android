package com.kauproject.placepick.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.kauproject.placepick.R
import com.kauproject.placepick.databinding.FragmentHomeBinding
import com.kauproject.placepick.model.DataStore
import com.kauproject.placepick.ui.chat.ChatFragment
import kotlinx.coroutines.launch

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

        lifecycleScope.launch {
            val btnChoice1 = binding.btnChoice1
            val btnChoice2 = binding.btnChoice2
            val btnChoice3 = binding.btnChoice3

            val userData = DataStore(requireContext()).getUserData()

            btnChoice1.text = userData.place1 ?: ""
            btnChoice2.text = userData.place2 ?: ""
            btnChoice3.text = userData.place3 ?: ""

            btnChoice1.setOnClickListener {
                launchHandleButtonClick((it as Button).text.toString(), userData.place1 ?: "")
            }

            btnChoice2.setOnClickListener {
                launchHandleButtonClick((it as Button).text.toString(), userData.place2 ?: "")
            }

            btnChoice3.setOnClickListener {
                launchHandleButtonClick((it as Button).text.toString(), userData.place3 ?: "")
            }
        }
    }

    private fun launchHandleButtonClick(selectedPlace: String, placeData: String) {
        lifecycleScope.launch {
            handleButtonClick(selectedPlace, placeData)
        }
    }

    private suspend fun handleButtonClick(selectedPlace: String, placeData: String) {
        val chatFragment = ChatFragment.newInstance(selectedPlace, placeData)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fl_main, chatFragment)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
