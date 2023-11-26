package com.kauproject.placepick.ui.board

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.kauproject.placepick.R
import com.kauproject.placepick.databinding.ItemBoardBinding
import com.kauproject.placepick.util.HotPlace.hotPlace

class BoardAdapter(
    val onBoardClick: (String) -> Unit
) : RecyclerView.Adapter<BoardAdapter.BoardHolder>() {
    companion object{
        const val TAG = "BoardAdapter"
    }
    private val placeList = hotPlace
    private var stateList = emptyList<String?>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardHolder {
        val binding = ItemBoardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BoardHolder(binding)
    }

    override fun onBindViewHolder(holder: BoardHolder, position: Int) {
        val place = placeList[position]
        var state: String? = "불러오는 중"
        if(stateList.isNotEmpty()){
            state = stateList[position]
        }
        holder.setPlace(place, state)

        holder.binding.llBoard.setOnClickListener {
            onBoardClick(place)
        }

    }

    override fun getItemCount() = hotPlace.size

    inner class BoardHolder(val binding: ItemBoardBinding): RecyclerView.ViewHolder(binding.root){
        fun setPlace(place: String, state: String?){
            binding.tvPlace.text = place
            when(state){
                "불러오는 중" -> {
                    binding.tvState.text = state
                    binding.tvState.setBackgroundResource(R.drawable.bg_state_default)
                }
                "여유" -> {
                    binding.tvState.text = state
                    binding.tvState.setBackgroundResource(R.drawable.bg_state_good)
                }
                "보통" -> {
                    binding.tvState.text = state
                    binding.tvState.setBackgroundResource(R.drawable.bg_state_normal)
                }
                "약간 붐빔" -> {
                    binding.tvState.text = state
                    binding.tvState.setBackgroundResource(R.drawable.bg_state_bad)
                }
                "혼잡" -> {
                    binding.tvState.text = state
                    binding.tvState.setBackgroundResource(R.drawable.bg_state_bad)
                }
                else -> {
                    binding.tvState.text = "통신 실패"
                    binding.tvState.setBackgroundResource(R.drawable.bg_state_default)
                }
            }
        }
    }

    fun updateState(state: List<String?>?){
        Log.d(TAG, "update:$state")
        state?.let {
            stateList = it
            notifyDataSetChanged()
        }
    }
}

