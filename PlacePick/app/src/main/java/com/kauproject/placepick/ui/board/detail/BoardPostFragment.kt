package com.kauproject.placepick.ui.board.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kauproject.placepick.databinding.FragmentBoardDetailPostBinding
import com.kauproject.placepick.ui.MainViewModel
import com.kauproject.placepick.util.BaseFragment
import com.kauproject.placepick.util.FragmentUtil

class BoardPostFragment: BaseFragment<FragmentBoardDetailPostBinding>() {
    companion object{
        const val TAG = "BoardPostFragment"
    }
    private val boardPostAdapter = BoardPostAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel: MainViewModel by activityViewModels()
        var postId: String? = null // 게시글 id 값

        // 게시글 상세 조회
        viewModel.postContent.value?.let {
            postId = it.postId
            viewModel.getComment(it.postId) // 댓글 조회
            boardPostAdapter.contentUpdate(it)
        }

        // 게시판 이름 가져오기
        viewModel.board.value?.let {
            binding.tvPostTitle.text = it
        }

        viewModel.commentList.observe(viewLifecycleOwner, Observer {
            boardPostAdapter.commentListUpdate(it)
        })

        // 이전버튼
        binding.ivBtnBack.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.popBackStack()
        }

        // 댓글 입력
        binding.ivCommentSend.setOnClickListener {
            if(binding.etPostComment.text.toString() != "" && postId != null){
                viewModel.commendWrite(
                    content = binding.etPostComment.text.toString(),
                    postId = postId!!
                )
                binding.etPostComment.setText("")
            }else{
                Snackbar.make(requireView(), "댓글을 입력하세요", Snackbar.LENGTH_SHORT).show()
            }
        }

        // 어뎁터 설정
        binding.rvPost.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvPost.adapter = boardPostAdapter
    }

    override fun onResume() {
        super.onResume()
        boardPostAdapter.commentListUpdate(emptyList())
        boardPostAdapter.notifyDataSetChanged()
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBoardDetailPostBinding {
        return FragmentBoardDetailPostBinding.inflate(inflater, container, false)
    }
}