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
            else -> throw IllegalArgumentException("유효하지 않은 뷰 타입입니다.")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]

        when (holder) {
            is ChatMineViewHolder -> holder.bindMine(message)
            is ChatOthersViewHolder -> holder.bindOthers(message)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages[position]
        return if (message.currentUser) {
            VIEW_TYPE_MINE
        } else {
            VIEW_TYPE_OTHERS
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    class ChatMineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindMine(message: Message) {
            itemView.findViewById<TextView>(R.id.mine_txt_message).text = message.content
            itemView.findViewById<TextView>(R.id.mine_txt_date).text =
                convertTimestampToDate(message.timestamp)

            // 다른 속성 및 UI 요소를 필요에 따라 설정
        }

        private fun convertTimestampToDate(timestamp: Long): String {
            // 예시: timestamp를 날짜 문자열로 변환
            val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
            val date = Date(timestamp)
            return sdf.format(date)
        }
    }


    class ChatOthersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindOthers(message: Message) {
            itemView.findViewById<TextView>(R.id.others_name).text = message.senderId
            itemView.findViewById<TextView>(R.id.others_txt_message).text = message.content
            // Timestamp를 변환 (예: SimpleDateFormat 사용)
            itemView.findViewById<TextView>(R.id.others_txt_date).text = convertTimestampToDate(message.timestamp)



            // 다른 속성 및 UI 요소를 필요에 따라 설정
        }
        private fun convertTimestampToDate(timestamp: Long): String {
            // 예시: timestamp를 날짜 문자열로 변환
            val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
            val date = Date(timestamp)
            return sdf.format(date)
        }

    }
}

