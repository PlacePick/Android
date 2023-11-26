import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kauproject.placepick.databinding.ItemBoardDetailBinding
import com.kauproject.placepick.model.data.Post

class BoardDetailAdapter(
    val onPostClicked: (Post) -> Unit
): RecyclerView.Adapter<BoardDetailAdapter.BoardDetailHolder>() {
    private var detailList = emptyList<Post>()
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

        // 게시글 클릭
        holder.binding.llBoardDetail.setOnClickListener {
            Log.d("TEST", "Click:${detailList[position]}")
            onPostClicked(detailList[position])
        }
    }

    inner class BoardDetailHolder(val binding: ItemBoardDetailBinding): RecyclerView.ViewHolder(binding.root){
        fun setDetail(detail: Post){
            binding.tvTitle.text = detail.title
            binding.tvContent.text = detail.content
            binding.tvDate.text = detail.date
        }
    }

    fun updateList(list: List<Post>){
        detailList = list.reversed()
        notifyDataSetChanged()
    }
}