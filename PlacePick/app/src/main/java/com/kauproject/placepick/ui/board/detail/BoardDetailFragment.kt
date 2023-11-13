package com.kauproject.placepick.ui.board.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kauproject.placepick.databinding.FragmentBoardDetailBinding
import com.kauproject.placepick.model.data.Post
import com.kauproject.placepick.ui.MainViewModel
import com.kauproject.placepick.util.BaseFragment

class BoardDetailFragment(): BaseFragment<FragmentBoardDetailBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel: MainViewModel by activityViewModels()
        var detailList: List<Post> = emptyList()

        viewModel.detailList.observe(viewLifecycleOwner, Observer {
            detailList = it
        })

        binding.rvBoardDetail.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvBoardDetail.adapter = BoardDetailAdapter(detailList.toMutableList())
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBoardDetailBinding {
        return FragmentBoardDetailBinding.inflate(inflater, container, false)
    }

}