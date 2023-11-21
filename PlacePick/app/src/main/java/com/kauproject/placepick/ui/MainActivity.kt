package com.kauproject.placepick.ui

import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.kauproject.placepick.R
import com.kauproject.placepick.databinding.ActivityMainBinding
import com.kauproject.placepick.model.repository.BoardRepository
import com.kauproject.placepick.ui.board.BoardFragment
import com.kauproject.placepick.ui.home.HomeFragment
import com.kauproject.placepick.ui.map.MapFragment
import com.kauproject.placepick.ui.mypage.MypageFragment

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val viewModel: MainViewModel by viewModels()
        initFragment()

        binding.bottomMain.setOnItemSelectedListener { it->
            when(it.itemId){
                R.id.nav_home -> {
                    HomeFragment().changeFragment()
                }
                R.id.nav_board -> {
                    BoardFragment().changeFragment()
                }
                R.id.nav_map -> {
                    MapFragment().changeFragment()
                }
                R.id.nav_mypage -> {
                    MypageFragment().changeFragment()
                }
            }
            // 이벤트 처리 종료 처리 return (@기호: 레이블 지정, 람다식에서의 반환)
            return@setOnItemSelectedListener true
        }

    }

    // 처음 프래그먼트 지정 (초기화)
    private fun initFragment(){
        val transaction = supportFragmentManager.beginTransaction()
            .add(R.id.fl_main, HomeFragment())
        transaction.commit()
    }

    // Fragment 확장함수
    private fun Fragment.changeFragment(){
        supportFragmentManager.beginTransaction().replace(R.id.fl_main, this).commit()

    }

    // 앱 상단바, 하단바 보이기 및 숨기기
    fun isShowView(show: Boolean){
        if(show){
            binding.llMain.visibility = View.VISIBLE
            binding.bottomMain.visibility = View.VISIBLE
        }else{
            binding.llMain.visibility = View.GONE
            binding.bottomMain.visibility = View.GONE
        }
    }

}