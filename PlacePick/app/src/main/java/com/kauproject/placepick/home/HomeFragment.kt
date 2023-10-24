package com.kauproject.placepick.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kauproject.placepick.databinding.FragmentHomeBinding

class HomeFragment: Fragment() {

    /*
    Fragment의 생명주기를 고려하자 LiveData를 사용시 LifeCycleOwner 설정 고려!!
    onDestroyView() 함수에서 직접 binding 객체를 null로 만들어 GC(가비지 컬렉터)에
    수집해야 메모리 누수를 방지할 수 있다.
    만약 이것을 고려하지 않고 binding 설정을 한다면 onDestroyView()가 수행되도 View는 살아있기 때문에
    메모리 누수가 발생한다
    */
    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}