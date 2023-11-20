package com.kauproject.placepick.model.response


import com.squareup.moshi.Json

data class GetHotPlaceInfoResponse(
    @field:Json(name = "SeoulRtd.citydata_ppltn")
    val seoulRtdCitydataPpltn: List<SeoulRtdCitydataPpltn?>?
)

data class SeoulRtdCitydataPpltn(
    @field:Json(name = "AREA_CD")
    val code: String?, // 핫스팟 코드명
    @field:Json(name = "AREA_CONGEST_LVL")
    val areaLevel: String?, // 장소 혼잡도 지표
    @field:Json(name = "AREA_CONGEST_MSG")
    val areaMessage: String?, // 장소 혼잡도 지표 관련 메세지
    @field:Json(name = "AREA_NM")
    val areaName: String?, // 핫스팟 장소명
    @field:Json(name = "AREA_PPLTN_MAX")
    val areaLiveMax: String?, // 실시간 인구 지표 최대값
    @field:Json(name = "AREA_PPLTN_MIN")
    val areaLiveMin: String?, // 실시간 인구 지표 최소값
    @field:Json(name = "FCST_PPLTN")
    val peoplePrediction: List<FCSTPPLTN?>?, // 인구 예측 객체
    @field:Json(name = "FCST_YN")
    val peoplePredictionYN: String?, // 예측값 제공 여부
    @field:Json(name = "FEMALE_PPLTN_RATE")
    val femaleRate: String?, // 여성 인구 비율
    @field:Json(name = "MALE_PPLTN_RATE")
    val maleRate: String?, // 남성 인구 비율
    @field:Json(name = "NON_RESNT_PPLTN_RATE")
    val noneRate: String?, // 비상주 인구 비율
    @field:Json(name = "PPLTN_RATE_0") // 10~70대 실시간 인구 비율
    val rate0: String?,
    @field:Json(name = "PPLTN_RATE_10")
    val rate10: String?,
    @field:Json(name = "PPLTN_RATE_20")
    val rate20: String?,
    @field:Json(name = "PPLTN_RATE_30")
    val rate30: String?,
    @field:Json(name = "PPLTN_RATE_40")
    val rate40: String?,
    @field:Json(name = "PPLTN_RATE_50")
    val rate50: String?,
    @field:Json(name = "PPLTN_RATE_60")
    val rate60: String?,
    @field:Json(name = "PPLTN_RATE_70")
    val rate70: String?,
    @field:Json(name = "PPLTN_TIME")
    val updateTime: String?, // 실시간 인구 데이터 업데이트 시간
    @field:Json(name = "REPLACE_YN")
    val replaceYN: String?, // 대체 데이터 여부
    @field:Json(name = "RESNT_PPLTN_RATE")
    val planeRate: String? // 상주 인구 비율
)

data class FCSTPPLTN(
    @field:Json(name = "FCST_CONGEST_LVL")
    val placeCognitionPrediction: String?, // 장소 예측 혼잡도 지표
    @field:Json(name = "FCST_PPLTN_MAX")
    val placeCognitionMAX: String?, // 예측 실시간 인구 지표 최대값
    @field:Json(name = "FCST_PPLTN_MIN")
    val placeCognitionMIN: String?, // 예측 실시간 인구 지표 최소값
    @field:Json(name = "FCST_TIME")
    val placePredictionTime: String? // 인구예측 시점
)