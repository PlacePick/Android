package com.kauproject.placepick.ui.setting
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.kauproject.placepick.databinding.ActivityHotplaceBinding
import com.kauproject.placepick.model.DataStore
import com.kauproject.placepick.model.repository.LoginRepository

class SettingHotPlaceActivity: AppCompatActivity() {
    private val binding: ActivityHotplaceBinding by lazy {
        ActivityHotplaceBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val dataStore = DataStore(this@SettingHotPlaceActivity)
        val loginRepository = LoginRepository()
        val viewModel = SettingViewModel(dataStore, loginRepository)

        initView()

        // 완료 버튼 동작
        binding.btnComplete.setOnClickListener {
            viewModel.setHotPlace(getHotPlace())
            finish()
        }
    }

    // GridView로 핫플레이스 선택 버튼 구현
    private fun initView(){
        binding.rvPlace.layoutManager = GridLayoutManager(this@SettingHotPlaceActivity, 2)
        binding.rvPlace.adapter = SettingHotPlaceAdapter()
    }
}