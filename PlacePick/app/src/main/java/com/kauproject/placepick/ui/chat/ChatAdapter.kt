package com.kauproject.placepick.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kauproject.placepick.R
import com.kauproject.placepick.model.data.Message
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ChatAdapter(
    private val messages: List<Message>,
    private val currentUserNickname: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_MINE = 1
        const val VIEW_TYPE_OTHERS = 0

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_MINE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_chat_mine, parent, false)
                ChatMineViewHolder(view)
            }

            VIEW_TYPE_OTHERS -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_chat_others, parent, false)
                ChatOthersViewHolder(view)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]

        when (holder) {
            is ChatMineViewHolder -> {
                holder.bindMine(message, currentUserNickname, this)
            }

            is ChatOthersViewHolder -> {
                holder.bindOthers(message, currentUserNickname, this)

            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages[position]
        return if (message.senderId == currentUserNickname) {
            VIEW_TYPE_MINE
        } else {
            VIEW_TYPE_OTHERS
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    class ChatMineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindMine(message: Message, currentUserNickname: String, adapter: ChatAdapter) {
            itemView.findViewById<TextView>(R.id.mine_txt_message).text = message.content
            val dateFormat = SimpleDateFormat("a hh:mm", Locale.getDefault())
            val formattedTime = dateFormat.format(Date(message.timestamp))
            itemView.findViewById<TextView>(R.id.mine_txt_date).text = formattedTime
        }
    }

    class ChatOthersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindOthers(message: Message, currentUserNickname: String, adapter: ChatAdapter) {
            itemView.findViewById<TextView>(R.id.others_name).text = message.senderId
            itemView.findViewById<TextView>(R.id.others_txt_message).text = message.content
            val dateFormat = SimpleDateFormat("a hh:mm", Locale.getDefault())
            val formattedTime = dateFormat.format(Date(message.timestamp))
            itemView.findViewById<TextView>(R.id.others_txt_date).text = formattedTime
        }
    }
}
