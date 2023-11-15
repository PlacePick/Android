package com.kauproject.placepick.model.response


import com.squareup.moshi.Json

data class GetHotPlaceInfoResponse(
    @Json(name = "SeoulRtd.citydata_ppltn")
    val seoulRtdCitydataPpltn: List<SeoulRtdCitydataPpltn?>?
)

data class SeoulRtdCitydataPpltn(
    @Json(name = "AREA_CD")
    val aREACD: String?,
    @Json(name = "AREA_CONGEST_LVL")
    val aREACONGESTLVL: String?,
    @Json(name = "AREA_CONGEST_MSG")
    val aREACONGESTMSG: String?,
    @Json(name = "AREA_NM")
    val aREANM: String?,
    @Json(name = "AREA_PPLTN_MAX")
    val aREAPPLTNMAX: String?,
    @Json(name = "AREA_PPLTN_MIN")
    val aREAPPLTNMIN: String?,
    @Json(name = "FCST_PPLTN")
    val fCSTPPLTN: List<FCSTPPLTN?>?,
    @Json(name = "FCST_YN")
    val fCSTYN: String?,
    @Json(name = "FEMALE_PPLTN_RATE")
    val fEMALEPPLTNRATE: String?,
    @Json(name = "MALE_PPLTN_RATE")
    val mALEPPLTNRATE: String?,
    @Json(name = "NON_RESNT_PPLTN_RATE")
    val nONRESNTPPLTNRATE: String?,
    @Json(name = "PPLTN_RATE_0")
    val pPLTNRATE0: String?,
    @Json(name = "PPLTN_RATE_10")
    val pPLTNRATE10: String?,
    @Json(name = "PPLTN_RATE_20")
    val pPLTNRATE20: String?,
    @Json(name = "PPLTN_RATE_30")
    val pPLTNRATE30: String?,
    @Json(name = "PPLTN_RATE_40")
    val pPLTNRATE40: String?,
    @Json(name = "PPLTN_RATE_50")
    val pPLTNRATE50: String?,
    @Json(name = "PPLTN_RATE_60")
    val pPLTNRATE60: String?,
    @Json(name = "PPLTN_RATE_70")
    val pPLTNRATE70: String?,
    @Json(name = "PPLTN_TIME")
    val pPLTNTIME: String?,
    @Json(name = "REPLACE_YN")
    val rEPLACEYN: String?,
    @Json(name = "RESNT_PPLTN_RATE")
    val rESNTPPLTNRATE: String?
)

data class FCSTPPLTN(
    @Json(name = "FCST_CONGEST_LVL")
    val fCSTCONGESTLVL: String?,
    @Json(name = "FCST_PPLTN_MAX")
    val fCSTPPLTNMAX: String?,
    @Json(name = "FCST_PPLTN_MIN")
    val fCSTPPLTNMIN: String?,
    @Json(name = "FCST_TIME")
    val fCSTTIME: String?
)