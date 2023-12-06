package com.kauproject.placepick.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kauproject.placepick.databinding.ItemChatMineBinding
import com.kauproject.placepick.databinding.ItemChatOthersBinding
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
    override fun getItemViewType(position: Int): Int {
        val message = messages[position]
        return if (message.senderId == currentUserNickname) {
            VIEW_TYPE_MINE
        } else {
            VIEW_TYPE_OTHERS
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_MINE -> {
                val binding = ItemChatMineBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ChatMineViewHolder(binding)
            }

            VIEW_TYPE_OTHERS -> {
                val binding = ItemChatOthersBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ChatOthersViewHolder(binding)
            }


            else -> throw IllegalArgumentException("정하지 않은 뷰 타입")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]

        when (holder) {
            is ChatMineViewHolder -> {
                holder.bindMine(message)
            }

            is ChatOthersViewHolder -> {
                holder.bindOthers(message)

            }
        }
    }



    override fun getItemCount(): Int = messages.size


    class ChatMineViewHolder(private val binding: ItemChatMineBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindMine(message: Message) {
            // 메시지 내용 설정
            binding.mineTxtMessage.text = message.content

            // 메시지의 타임스탬프를 형식화하여 시간을 설정
            val dateFormat = SimpleDateFormat("a hh:mm", Locale.getDefault())
            val formattedTime = dateFormat.format(Date(message.timestamp))
            binding.mineTxtDate.text = formattedTime
        }
    }

    class ChatOthersViewHolder(private val binding: ItemChatOthersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindOthers(message: Message) {

            // 발신자의 이름 설정
            binding.othersName.text = message.senderId

            // 메시지 내용 설정
            binding.othersTxtMessage.text = message.content

            // 메시지의 타임스탬프를 형식화하여 시간을 설정
            val dateFormat = SimpleDateFormat("a hh:mm", Locale.getDefault())
            val formattedTime = dateFormat.format(Date(message.timestamp))
            binding.othersTxtDate.text = formattedTime
        }
    }
}

