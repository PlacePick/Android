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
        holder.setPlace(place)

        holder.binding.llBoard.setOnClickListener {
            Log.d(TAG, "Click!!$place")
            onBoardClick(place)
        }

    }

    override fun getItemCount() = hotPlace.size

    inner class BoardHolder(val binding: ItemBoardBinding): RecyclerView.ViewHolder(binding.root){
        fun setPlace(place: String){
            binding.tvPlace.text = place
        }
    }
}

