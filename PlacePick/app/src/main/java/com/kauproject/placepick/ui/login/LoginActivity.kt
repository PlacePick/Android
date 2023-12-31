package com.kauproject.placepick.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.kauproject.placepick.databinding.ActivityLoginBinding
import com.kauproject.placepick.model.repository.LoginRepository
import com.kauproject.placepick.ui.MainActivity
import com.kauproject.placepick.ui.setting.SettingActivity

class LoginActivity: AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val loginRepository = LoginRepository()

        val viewModel: LoginViewModel = LoginViewModel(this@LoginActivity, loginRepository)

        // 기존유저의 유무에 따른 화면전환 조건문
        viewModel.isMember.observe(this@LoginActivity, Observer {
            it?.let {
                if(it){
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                }else{
                    val intent = Intent(this@LoginActivity, SettingActivity::class.java)
                    intent.putExtra("userNum", viewModel.userNum.value) // 최초회원일 경우 userNum 전송
                    startActivity(intent)
                }
            }
        })

        binding.linearNaverLoginBtn.setOnClickListener {
            viewModel.startLogin()
        }
    }

}