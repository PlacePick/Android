package com.kauproject.placepick.ui.board.detail

import BoardDetailAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kauproject.placepick.databinding.FragmentBoardDetailBinding
import com.kauproject.placepick.ui.MainViewModel
import com.kauproject.placepick.util.BaseFragment
import com.kauproject.placepick.util.FragmentUtil

class BoardDetailFragment(): BaseFragment<FragmentBoardDetailBinding>() {
    companion object{
        const val TAG = "BoardDetailFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel: MainViewModel by activityViewModels()
        val boardDetailAdapter = BoardDetailAdapter(
            onPostClicked = {
                viewModel.postContent(it)
                FragmentUtil.showFragment(requireActivity().supportFragmentManager, BoardPostFragment(), BoardPostFragment.TAG)
            }
        )

        viewModel.getDetailList() // 게시판 내용 가져오기

        viewModel.detailList.observe(viewLifecycleOwner, Observer {
            boardDetailAdapter.updateList(it) // 게시판 내용 업데이트 감지
        })
        viewModel.board.observe(viewLifecycleOwner, Observer {
            binding.tvBoardTitle.text = it // 게시판 이름 설정
        })

        binding.tvBtnWrite.setOnClickListener {
            FragmentUtil.showFragment(requireActivity().supportFragmentManager, BoardDetailPostFragment(), BoardDetailPostFragment.TAG)
        }

        binding.ivBtnBack.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.popBackStack()
        }

        binding.rvBoardDetail.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvBoardDetail.adapter = boardDetailAdapter
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBoardDetailBinding {
        return FragmentBoardDetailBinding.inflate(inflater, container, false)
    }

}