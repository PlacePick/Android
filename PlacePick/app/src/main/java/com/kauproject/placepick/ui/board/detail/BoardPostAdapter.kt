package com.kauproject.placepick.ui.board.detail

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kauproject.placepick.databinding.ItemBoardDetailPostBinding
import com.kauproject.placepick.databinding.ItemBoardDetailPostCommentBinding
import com.kauproject.placepick.model.data.Comment
import com.kauproject.placepick.model.data.Post

class BoardPostAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE_CONTENT = 1
    private val TYPE_COMMEND = 2

    var content: Post? = null
    private var commentList: MutableList<Comment> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            TYPE_CONTENT -> {
                val binding = ItemBoardDetailPostBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                PostContentHolder(binding)
            }
            TYPE_COMMEND -> {
                val binding = ItemBoardDetailPostCommentBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                CommentHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int {
        return if(content != null){
            commentList.size + 1
        }else{
            0
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)){
            TYPE_CONTENT -> {
                content?.let { (holder as PostContentHolder).setContent(it) }
            }
            TYPE_COMMEND -> {
                // 항상 position이 1부터 수행하므로 1을 빼줘야함
                if(commentList.isNotEmpty()) { (holder as CommentHolder).setComment(commentList[position-1]) }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == 0){
            TYPE_CONTENT
        }else{
            TYPE_COMMEND
        }
    }

    inner class PostContentHolder(val binding: ItemBoardDetailPostBinding): RecyclerView.ViewHolder(binding.root){
        fun setContent(content: Post){
            binding.tvPostTitle.text = content.title
            binding.tvPostNickname.text = "작성자: ${content.nick}"
            binding.tvPostContent.text = content.content
            binding.tvPostDuration.text = content.date
        }
    }

    inner class CommentHolder(val binding: ItemBoardDetailPostCommentBinding): RecyclerView.ViewHolder(binding.root){
        fun setComment(comment: Comment){
            binding.tvPostCommentNick.text = comment.nick
            binding.tvPostCommentContent.text = comment.content
            binding.tvPostCommentDuration.text = comment.duration
        }
    }
    fun contentUpdate(updateContent: Post){
        content = updateContent
        notifyDataSetChanged()
    }

    fun commentListUpdate(updateList: List<Comment>){
        if(updateList.isEmpty()){
            commentList.clear()
        }

        if(commentList.isNotEmpty()){
            Log.d("TESTSIZE", "list:${updateList}")
            Log.d("TESTSIZE", "size:${updateList.size}")
            commentList.add(updateList[updateList.lastIndex])
        }else{
            commentList.addAll(updateList)
        }

        notifyDataSetChanged()
    }

}