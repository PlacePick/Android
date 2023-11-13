package com.kauproject.placepick.ui.setting

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kauproject.placepick.databinding.ActivitySettingBinding
import com.kauproject.placepick.model.DataStore
import com.kauproject.placepick.model.repository.LoginRepository
import com.kauproject.placepick.ui.MainActivity

class SettingActivity: AppCompatActivity() {
    private val binding: ActivitySettingBinding by lazy {
        ActivitySettingBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val dataStore = DataStore(this@SettingActivity)
        val loginRepository = LoginRepository()

        val intent = intent
        val userNum = intent.getStringExtra("userNum")
        val viewModel = SettingViewModel(dataStore, loginRepository)

        // userNum 세팅
        userNum?.let {
            viewModel.setUserNum(it)
        }

        // 핫플레이스 선택 이동
        binding.tvPlacePick.setOnClickListener {
            val settingIntent = Intent(this@SettingActivity, SettingHotPlaceActivity::class.java)
            startActivity(settingIntent)
        }

        // 완료 버튼 로직
        binding.btnComplete.setOnClickListener {
            val mainIntent = Intent(this@SettingActivity, MainActivity::class.java)
            viewModel.setUserNickName(binding.etNickname.text.toString())
            viewModel.complete()
            startActivity(mainIntent)
            finish()
        }
    }
}