package com.kauproject.placepick.ui.board

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.kauproject.placepick.R
import com.kauproject.placepick.databinding.FragmentBoardBinding
import com.kauproject.placepick.model.repository.BoardRepository
import com.kauproject.placepick.ui.MainActivity
import com.kauproject.placepick.ui.MainViewModel
import com.kauproject.placepick.ui.board.detail.BoardDetailFragment
import com.kauproject.placepick.util.BaseFragment
import com.kauproject.placepick.util.FragmentUtil

class BoardFragment: BaseFragment<FragmentBoardBinding>() {

    companion object{
        const val TAG = "BoardFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel: MainViewModel by activityViewModels()
        val mainActivity = activity as MainActivity
        val boardAdapter = BoardAdapter(onBoardClick = {
            viewModel.setBoard(it)
            FragmentUtil.showFragment(requireActivity().supportFragmentManager, BoardDetailFragment(), BoardDetailFragment.TAG)
            mainActivity.isShowView(false)
        })

        mainActivity.isShowView(true)
        viewModel.getPlaceStatus()

        viewModel.placeState.observe(viewLifecycleOwner, Observer {
            boardAdapter.updateState(it)
        })

        viewModel.serverError.observe(viewLifecycleOwner, Observer {
            Snackbar.make(requireView(), "서버와의 연결이 원활하지 않습니다: Error code: $it", Snackbar.LENGTH_SHORT).show()
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            Snackbar.make(requireView(), "Error: $it", Snackbar.LENGTH_SHORT).show()
        })

        binding.rvBoard.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvBoard.adapter = boardAdapter
    }
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBoardBinding {
        return FragmentBoardBinding.inflate(inflater, container, false)
    }
}