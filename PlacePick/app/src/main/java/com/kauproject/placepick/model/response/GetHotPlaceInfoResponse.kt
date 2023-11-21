package com.kauproject.placepick.model.response


import com.squareup.moshi.Json

data class GetHotPlaceInfoResponse(
    @Json(name = "SeoulRtd.citydata_ppltn")
    val seoulRtdCitydataPpltn: List<SeoulRtdCitydataPpltn?>?
)

data class SeoulRtdCitydataPpltn(
    @Json(name = "AREA_CD")
    val code: String?, // 핫스팟 코드명
    @Json(name = "AREA_CONGEST_LVL")
    val areaLevel: String?, // 장소 혼잡도 지표
    @Json(name = "AREA_CONGEST_MSG")
    val areaMessage: String?, // 장소 혼잡도 지표 관련 메세지
    @Json(name = "AREA_NM")
    val areaName: String?, // 핫스팟 장소명
    @Json(name = "AREA_PPLTN_MAX")
    val areaLiveMax: String?, // 실시간 인구 지표 최대값
    @Json(name = "AREA_PPLTN_MIN")
    val areaLiveMin: String?, // 실시간 인구 지표 최소값
    @Json(name = "FCST_PPLTN")
    val peoplePrediction: List<FCSTPPLTN?>?, // 인구 예측 객체
    @Json(name = "FCST_YN")
    val peoplePredictionYN: String?, // 예측값 제공 여부
    @Json(name = "FEMALE_PPLTN_RATE")
    val femaleRate: String?, // 여성 인구 비율
    @Json(name = "MALE_PPLTN_RATE")
    val maleRate: String?, // 남성 인구 비율
    @Json(name = "NON_RESNT_PPLTN_RATE")
    val noneRate: String?, // 비상주 인구 비율
    @Json(name = "PPLTN_RATE_0") // 10~70대 실시간 인구 비율
    val rate0: String?,
    @Json(name = "PPLTN_RATE_10")
    val rate10: String?,
    @Json(name = "PPLTN_RATE_20")
    val rate20: String?,
    @Json(name = "PPLTN_RATE_30")
    val rate30: String?,
    @Json(name = "PPLTN_RATE_40")
    val rate40: String?,
    @Json(name = "PPLTN_RATE_50")
    val rate50: String?,
    @Json(name = "PPLTN_RATE_60")
    val rate60: String?,
    @Json(name = "PPLTN_RATE_70")
    val rate70: String?,
    @Json(name = "PPLTN_TIME")
    val updateTime: String?, // 실시간 인구 데이터 업데이트 시간
    @Json(name = "REPLACE_YN")
    val replaceYN: String?, // 대체 데이터 여부
    @Json(name = "RESNT_PPLTN_RATE")
    val planeRate: String? // 상주 인구 비율
)

data class FCSTPPLTN(
    @Json(name = "FCST_CONGEST_LVL")
    val placeCognitionPrediction: String?, // 장소 예측 혼잡도 지표
    @Json(name = "FCST_PPLTN_MAX")
    val placeCognitionMAX: String?, // 예측 실시간 인구 지표 최대값
    @Json(name = "FCST_PPLTN_MIN")
    val placeCognitionMIN: String?, // 예측 실시간 인구 지표 최소값
    @Json(name = "FCST_TIME")
    val placePredictionTime: String? // 인구예측 시점
)