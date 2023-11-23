package com.kauproject.placepick.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.kauproject.placepick.R
import com.kauproject.placepick.databinding.FragmentHomeBinding
import com.kauproject.placepick.model.DataStore
import com.kauproject.placepick.model.response.GetHotPlaceInfoResponse
import com.kauproject.placepick.model.RetrofitInstance
import com.kauproject.placepick.model.service.GetHotPlaceInfoService
import com.kauproject.placepick.ui.chat.ChatFragment
import com.kauproject.placepick.ui.setting.SettingHotPlaceActivity
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var selectedButton: TextView? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            val btnChoice1 = binding.btnChoice1
            val btnChoice2 = binding.btnChoice2
            val btnChoice3 = binding.btnChoice3
            val btn_setting = binding.btnSetting
            val btn_realtime_input = binding.btnRealtimeInput



            val userData = DataStore(requireContext()).getUserData()

            onButtonClicked(btnChoice1, userData.place1 ?: "")

            btnChoice1.text = userData.place1 ?: ""
            btnChoice2.text = userData.place2 ?: ""
            btnChoice3.text = userData.place3 ?: ""

            btnChoice1.setOnClickListener {
                launchHandleButtonClick(btnChoice1.text.toString(), userData.place1 ?: "")
                onButtonClicked(btnChoice1, userData.place1 ?: "")

            }

            btnChoice2.setOnClickListener {
                launchHandleButtonClick(btnChoice2.text.toString(), userData.place2 ?: "")
                onButtonClicked(btnChoice2, userData.place2 ?: "")
            }

            btnChoice3.setOnClickListener {
                launchHandleButtonClick(btnChoice3.text.toString(), userData.place3 ?: "")
                onButtonClicked(btnChoice3, userData.place3 ?: "")
            }

            btn_setting.setOnClickListener {
                val intent = Intent(requireContext(), SettingHotPlaceActivity::class.java)
                startActivity(intent)
            }
            btn_realtime_input.setOnClickListener {
                val selectedPlace: String = btn_realtime_input.text.toString()

                if (selectedPlace.isNotEmpty()) {
                    launchHandleButtonClickChild(selectedPlace, selectedPlace)
                }
            }

        }
    }
    private fun launchHandleButtonClick(selectedPlace: String, placeData: String) {
        lifecycleScope.launch {
            handleButtonClickChild(selectedPlace, placeData)

        }
    }

    private fun launchHandleButtonClickChild(selectedPlace: String, placeData: String) {
        lifecycleScope.launch {
            val chatFragment = ChatFragment.newInstance(selectedPlace, selectedPlace)
            parentFragmentManager.beginTransaction()
                .replace(R.id.fl_main, chatFragment)
                .commit()
        }

        val response = RetrofitInstance.retrofit.create(GetHotPlaceInfoService::class.java)
    }

    private suspend fun handleButtonClickChild(selectedPlace: String, placeData: String) {
        // Retrofit을 이용하여 API 호출
        val response = RetrofitInstance.retrofit.create(GetHotPlaceInfoService::class.java)
            .getHotPlaceInfo(selectedPlace)

        val ppltnRate0 = response.body()?.seoulRtdCitydataPpltn?.firstOrNull()?.rate0?.toDoubleOrNull() ?: 0
        val ppltnRate10 = response.body()?.seoulRtdCitydataPpltn?.firstOrNull()?.rate10?.toDoubleOrNull() ?: 0
        val ppltnRate20 = response.body()?.seoulRtdCitydataPpltn?.firstOrNull()?.rate20?.toDoubleOrNull() ?: 0
        val ppltnRate30 = response.body()?.seoulRtdCitydataPpltn?.firstOrNull()?.rate30?.toDoubleOrNull() ?: 0
        val ppltnRate40 = response.body()?.seoulRtdCitydataPpltn?.firstOrNull()?.rate40?.toDoubleOrNull() ?: 0
        val ppltnRate50 = response.body()?.seoulRtdCitydataPpltn?.firstOrNull()?.rate50?.toDoubleOrNull() ?: 0
        val ppltnRate60 = response.body()?.seoulRtdCitydataPpltn?.firstOrNull()?.rate60?.toDoubleOrNull() ?: 0
        val ppltnRate70 = response.body()?.seoulRtdCitydataPpltn?.firstOrNull()?.rate70?.toDoubleOrNull() ?: 0

        // peoplePrediction 리스트에서 각 FCSTPPLTN 객체를 가져와서 placePredictionTime을 출력
        val peoplePredictions = response.body()?.seoulRtdCitydataPpltn?.firstOrNull()?.peoplePrediction
        peoplePredictions?.let {
            for ((index, prediction) in it.withIndex()) {
                // 예측값이 null이 아니고, TextView 리스트에 대응하는 인덱스가 있다면 값을 설정
                if (prediction != null && index < 9) {
                    when (index) {
                        0 -> binding.apidata1.text =
                            prediction.placePredictionTime ?: "데이터를 불러오지 못했습니다."

                        1 -> binding.apidata2.text =
                            prediction.placeCognitionPrediction ?: "데이터를 불러오지 못했습니다."

                        2 -> binding.apidata3.text =
                            prediction.placePredictionTime ?: "데이터를 불러오지 못했습니다."

                        3 -> binding.apidata4.text =
                            prediction.placeCognitionPrediction ?: "데이터를 불러오지 못했습니다."

                        4 -> binding.apidata5.text =
                            prediction.placePredictionTime ?: "데이터를 불러오지 못했습니다."

                        6 -> binding.apidata6.text =
                            prediction.placeCognitionPrediction ?: "데이터를 불러오지 못했습니다."

                        7 -> binding.apidata7.text =
                            prediction.placePredictionTime ?: "데이터를 불러오지 못했습니다."

                        8 -> binding.apidata8.text =
                            prediction.placeCognitionPrediction ?: "데이터를 불러오지 못했습니다."


                    }
                }
            }
        }


        //최댓값 구하는 메서드
        val maxPpltnRate = maxOf(
            ppltnRate0.toDouble(), ppltnRate10.toDouble(), ppltnRate20.toDouble(), ppltnRate30.toDouble(),
            ppltnRate40.toDouble(), ppltnRate50.toDouble(), ppltnRate60.toDouble(), ppltnRate70.toDouble()
        )
        // 최댓값에 해당하는 연령대를 찾아 한글 메시지 설정
        val maxPpltnRateMessage = when {
            maxPpltnRate.equals(ppltnRate0) -> "어린이가 가장 많습니다."
            maxPpltnRate.equals(ppltnRate10) -> "10대가 가장 많습니다."
            maxPpltnRate.equals(ppltnRate20) -> "20대가 가장 많습니다."
            maxPpltnRate.equals(ppltnRate30) -> "30대가 가장 많습니다."
            maxPpltnRate.equals(ppltnRate40) -> "40대가 가장 많습니다."
            maxPpltnRate.equals(ppltnRate50) -> "50대가 가장 많습니다."
            maxPpltnRate.equals(ppltnRate60) -> "60대가 가장 많습니다."
            maxPpltnRate.equals(ppltnRate70) -> "70대 이상이 가장 많습니다."
            else -> "데이터를 불러오지 못했습니다."
        }

        val areaCongestMsg = response.body()?.seoulRtdCitydataPpltn?.firstOrNull()?.areaMessage
        //"."있을시 개행
        val formattedAreaCongestMsg = areaCongestMsg?.replace(".", ".\n") ?: "데이터를 불러오지 못했습니다."

        val areaCongestlvl = response.body()?.seoulRtdCitydataPpltn?.firstOrNull()?.areaLevel

        val smileImageResource = R.drawable.ic_smile
        val frownImageResource = R.drawable.ic_frown
        val angerImageResource = R.drawable.ic_angle
        val crowdedImageResource = R.drawable.ic_cry

        val imageView = binding.imageView



        binding.textView1.text = maxPpltnRateMessage
        binding.textView4.text = formattedAreaCongestMsg

        // areaCongestlvl 값에 따라 이미지 변경
        when (areaCongestlvl) {
            "여유" -> imageView.setImageResource(smileImageResource)
            "약간 붐빔" -> imageView.setImageResource(frownImageResource)
            "보통" -> imageView.setImageResource(angerImageResource)
            else -> imageView.setImageResource(crowdedImageResource)
        }



    }
    private fun onButtonClicked(button: TextView, placeData: String) {
        if (selectedButton != button) {
            // 선택된 버튼이 변경되었을 때만 업데이트
            selectedButton?.let { updateButtonBackground(it, null) }
            selectedButton = button
            updateButtonBackground(button, placeData)
            binding.btnRealtimeInput.text = placeData

            launchHandleButtonClick(button.text.toString(), placeData)

        }
    }
    private fun updateButtonBackground(textView: TextView, place: String?) {
        if (place.isNullOrEmpty()) {
            // 누르지 않은 상태
            textView.setBackgroundResource(R.drawable.bg_unselected_color)
        } else {
            // 누른 상태
            textView.setBackgroundResource(R.drawable.bg_app_color)
        }
    }



    private suspend fun getHotPlaceInfoFromApi(hotPlace: String): GetHotPlaceInfoResponse? {
        return try {
            // Retrofit을 이용하여 API 호출
            val response = RetrofitInstance.retrofit.create(GetHotPlaceInfoService::class.java)
                .getHotPlaceInfo(hotPlace)

            // API 응답 반환
            response.body()
        } catch (e: Exception) {
            null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
