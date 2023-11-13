package com.kauproject.placepick.ui.board.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kauproject.placepick.databinding.ItemBoardDetailBinding
import com.kauproject.placepick.model.data.Post

class BoardDetailAdapter(
    private val detailList: MutableList<Post>
): RecyclerView.Adapter<BoardDetailAdapter.BoardDetailHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardDetailHolder {
        val binding = ItemBoardDetailBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BoardDetailHolder(binding)
    }

    override fun getItemCount(): Int = detailList.size

    override fun onBindViewHolder(holder: BoardDetailHolder, position: Int) {
        val detail = detailList[position]
        holder.setDetail(detail)
    }

    inner class BoardDetailHolder(val binding: ItemBoardDetailBinding): RecyclerView.ViewHolder(binding.root){
        fun setDetail(detail: Post){
            binding.tvTitle.text = detail.title
            binding.tvContent.text = detail.content
            binding.tvDate.text = detail.date
        }

    }
}