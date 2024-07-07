package com.example.tourmanage.common.data.server.item

import com.squareup.moshi.Json

data class DetailItem(
    @Json(name = "contentid") val contentId: String?,
    @Json(name = "contenttypeid") val contentTypeId: String?,
    @Json(name = "roomcode") val roomCode: String?,
    @Json(name = "roomtitle") val roomTitle: String?,
    @Json(name = "roomsize1") val roomSize: String?,
    @Json(name = "roomcount") val roomCount: String?,
    @Json(name = "roombasecount") val roomBaseCount: String?,
    @Json(name = "roommaxcount") val roomMaxCount: String?,
    @Json(name = "roomoffseasonminfee1") val offWeekDayFee: String?,
    @Json(name = "roomoffseasonminfee2") val offWeekendDayFee: String?,
    @Json(name = "roompeakseasonminfee1") val peakWeekDayFee: String?,
    @Json(name = "roompeakseasonminfee2") val peakWeekendFee: String?,
    @Json(name = "roomintro") val introMsg: String?,
    @Json(name = "roombathfacility") val bathFacilityYn: String?,
    @Json(name = "roombath") val roomBathYn: String?,
    @Json(name = "roomhometheater") val hometheaterYn: String?,
    @Json(name = "roomaircondition") val airConditionYn: String?,
    @Json(name = "roomtv") val roomTvYn: String?,
    @Json(name = "roompc") val roomPcYn: String?,
    @Json(name = "roomcable") val roomCableYn: String?,
    @Json(name = "roominternet") val roomInternetYn: String?,
    @Json(name = "roomrefrigerator") val roomRefrigeratorYn: String?,
    @Json(name = "roomtoiletries") val roomToiletriesYn: String?,
    @Json(name = "roomsofa") val roomSofaYn: String?,
    @Json(name = "roomcook") val roomCookYn: String?,
    @Json(name = "roomtable") val roomTableYn: String?,
    @Json(name = "roomhairdryer") val roomHairDryerYn: String?,
    @Json(name = "roomsize2") val roomSize2: String?,
    @Json(name = "roomimg1") val roomImg1: String?,
    @Json(name = "roomingalt") val roomImgDesc1: String?,
    @Json(name = "cpyrhtDivCd1") val cpyrhtDivCd1: String?,
    @Json(name = "roomimg2") val roomImg2: String?,
    @Json(name = "roomimg2alt") val roomImgDesc2: String?,
    @Json(name = "cpyrhtDivCd2") val cpyrhtDivCd2: String?,
    @Json(name = "roomimg3") val roomIng3: String?,
    @Json(name = "rooming3alt") val roomImgDesc3: String?,
    @Json(name = "cpyrhtDivCd3") val cpyrhtDivCd3: String?,
    @Json(name = "roomimg4") val roomIng4: String?,
    @Json(name = "rooming4alt") val roomImgDesc4: String?,
    @Json(name = "cpyrhtDivCd4") val cpyrhtDivCd4: String?,
    @Json(name = "roomimg5") val roomIng5: String?,
    @Json(name = "rooming5alt") val roomImgDesc5: String?,
    @Json(name = "cpyrhtDivCd5") val cpyrhtDivCd5: String?,
    @Json(name = "infoname") val infoName: String?,
    @Json(name = "infotext") var infoText: String?,

): CommonBodyItem()
