package com.kauproject.placepick.ui.board

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kauproject.placepick.databinding.FragmentBoardBinding
import com.kauproject.placepick.ui.MainViewModel
import com.kauproject.placepick.util.BaseFragment

class BoardFragment: BaseFragment<FragmentBoardBinding>() {

    companion object{
        fun newInstance(): BoardFragment = BoardFragment()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel: MainViewModel by activityViewModels()

        binding.rvBoard.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvBoard.adapter = BoardAdapter(
            postDetail = { viewModel.setDetailList(it) }
        )

    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBoardBinding {
        return FragmentBoardBinding.inflate(inflater, container, false)
    }
}