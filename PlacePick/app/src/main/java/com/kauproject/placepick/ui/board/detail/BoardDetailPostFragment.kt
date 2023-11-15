package com.kauproject.placepick.ui.board.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kauproject.placepick.databinding.FragmentBoardDetailWriteBinding
import com.kauproject.placepick.model.data.Post
import com.kauproject.placepick.model.data.User
import com.kauproject.placepick.ui.MainViewModel
import com.kauproject.placepick.util.BaseFragment

class BoardDetailPostFragment: BaseFragment<FragmentBoardDetailWriteBinding>() {
    companion object{
        const val TAG = "BoardDetailPostFragment"
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentManager = requireActivity().supportFragmentManager
        val viewModel: MainViewModel by activityViewModels()

        viewModel.getUserData()

        viewModel.userData.observe(viewLifecycleOwner, Observer { data ->
            binding.tvBtnPost.setOnClickListener {
                viewModel.postWrite(
                    Post(
                        title = binding.etTitle.text.toString(),
                        content = binding.etContent.text.toString(),
                        date = "",
                        userNum = data.userNum,
                        nick = data.nick ?: "익명"
                    )
                )
                fragmentManager.popBackStack()
            }
        })

        binding.ibtnClear.setOnClickListener {
            fragmentManager.popBackStack()
        }
    }
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBoardDetailWriteBinding {
        return FragmentBoardDetailWriteBinding.inflate(inflater, container, false)
    }
}