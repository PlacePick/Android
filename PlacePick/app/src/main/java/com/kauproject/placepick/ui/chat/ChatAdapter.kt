package com.kauproject.placepick.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.kauproject.placepick.R

class ChatAdapter(private val messages: MutableList<ChatMessage>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_MINE = 1
    private val VIEW_TYPE_OTHERS = 2

    inner class MineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageTextView: TextView = itemView.findViewById(R.id.txt_message)
        val dateTextView: TextView = itemView.findViewById(R.id.txt_date)
        val isShownTextView: TextView = itemView.findViewById(R.id.txt_isShown)
    }

    inner class OthersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageTextView: TextView = itemView.findViewById(R.id.txt_message)
        val dateTextView: TextView = itemView.findViewById(R.id.txt_date)
        val isShownTextView: TextView = itemView.findViewById(R.id.txt_isShown)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_MINE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_talk_item_mine, parent, false)
                MineViewHolder(view)
            }

            VIEW_TYPE_OTHERS -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_talk_item_others, parent, false)
                OthersViewHolder(view)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]

        when (holder) {
            is MineViewHolder -> {
                holder.messageTextView.text = message.text
                // 다른 데이터를 적절하게 설정합니다.
            }

            is OthersViewHolder -> {
                holder.messageTextView.text = message.text
                // 다른 데이터를 적절하게 설정합니다.
            }
        }
    }

    fun addMessage(message: ChatMessage) {
        messages.add(message)
        notifyItemInserted(messages.size - 1)
    }


    override fun getItemCount(): Int {
        return messages.size
    }

    override fun getItemViewType(position: Int): Int {
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
        return if (messages[position].sender == currentUserId) {
            VIEW_TYPE_MINE
        } else {
            VIEW_TYPE_OTHERS
        }
    }

}