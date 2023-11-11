package com.kauproject.placepick.ui.setting

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kauproject.placepick.R
import com.kauproject.placepick.databinding.ItemHotplaceBinding
import com.kauproject.placepick.util.HotPlace.hotPlace

private val placeList = hotPlace
private val isSelectedList = MutableList(placeList.size){false} // 선택된 버튼의 유무 List
private val selectedPlace = mutableListOf<String?>() // 선택된 버튼 List

class SettingHotPlaceAdapter: RecyclerView.Adapter<SettingHotPlaceAdapter.HotPlaceHolder>() {
    companion object{
        const val TAG = "SettingHotPlaceAdapter"
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotPlaceHolder {
        val binding = ItemHotplaceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HotPlaceHolder(binding)
    }

    override fun getItemCount(): Int = hotPlace.size

    override fun onBindViewHolder(holder: HotPlaceHolder, position: Int) {
        val place = placeList[position]
        holder.setPlaceBtn(place)

        val selected = isSelectedList[position]

        // 클릭여부에 따른 버튼 ui 변경
        if(selected){
            holder.binding.tvHotPlace.setBackgroundResource(R.drawable.bg_app_color)
        }else{
            holder.binding.tvHotPlace.setBackgroundResource(R.drawable.bg_unselected_color)
        }

        Log.d(TAG, "TEST:$isSelectedList")

        // 최대 3개까지 선택, 선택된 버튼을 누르면 해제
        holder.binding.root.setOnClickListener {
            if (isSelectedList.count { it } < 3 || isSelectedList[position]) {
                isSelectedList[position] = !isSelectedList[position]
                notifyDataSetChanged() // UI 갱신

                // 클릭 여부에 따라 리스트에 추가 또는 삭제
                if(isSelectedList[position]){
                    selectedPlace.add(place)
                }else{
                    selectedPlace.remove(place)
                }
            }
        }

    }
    inner class HotPlaceHolder(val binding: ItemHotplaceBinding): RecyclerView.ViewHolder(binding.root){
        // 버튼 text 설정
        fun setPlaceBtn(place: String){
            binding.tvHotPlace.text = place
        }
    }
}

// 선택된 핫플레이스 반환
fun getHotPlace(): List<String?>{
    val size = selectedPlace.size
    val result = mutableListOf<String?>()

    for(i in 0 until 3){
        if(i < size){
            result.add(selectedPlace[i])
        }else{
            result.add(null)
        }
    }
    return result
}

