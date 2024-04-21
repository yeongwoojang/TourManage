package com.example.tourmanage

import com.example.tourmanage.common.data.server.info.AreaInfo
import com.example.tourmanage.common.data.server.info.DetailInfo
import com.example.tourmanage.common.data.server.info.FestivalInfo
import com.example.tourmanage.common.data.server.info.StayDetailInfo
import com.example.tourmanage.common.data.server.info.StayInfo
import com.example.tourmanage.common.data.server.info.TourInfo
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.data.server.item.DetailItem
import com.example.tourmanage.common.data.server.item.FestivalItem
import com.example.tourmanage.common.data.server.item.StayDetailItem
import com.example.tourmanage.common.data.server.item.StayItem
import com.example.tourmanage.common.data.server.item.TourItem
import com.example.tourmanage.common.extension.isBooleanYn

fun StayInfo.toStayInfoList(): ArrayList<StayItem> {
    return response?.body?.items?.item?.let {
        it as ArrayList<StayItem>
    } ?: ArrayList(emptyList())
}

fun AreaInfo.toAreaInfoList(): ArrayList<AreaItem> {
    return response?.body?.items?.item?.let {
        it as ArrayList<AreaItem>
    } ?: ArrayList(emptyList())
}

fun StayDetailInfo.toStayDetail(): StayDetailItem? {
    return response?.body?.items?.item?.let {
        it[0]
    }
}

fun DetailInfo.toDetailItems(): ArrayList<DetailItem> {
    return response?.body?.items?.item?.let {
        it as ArrayList<DetailItem>
    } ?: ArrayList(emptyList())
}

fun FestivalInfo.toFestivalItems(): ArrayList<FestivalItem> {
    return response?.body?.items?.item?.let {
        it as ArrayList<FestivalItem>
    } ?: ArrayList(emptyList())
}

fun TourInfo.toTourInfoList(): ArrayList<TourItem> {
    return response?.body?.items?.item?.let {
        it as ArrayList<TourItem>
    } ?: ArrayList(emptyList())
}

fun DetailItem.getOptionString(): String {
    var options = ""
    if (airConditionYn.isBooleanYn()) {
        options += "에어컨 | "
    }
    if (bathFacilityYn.isBooleanYn()) {
        options += "욕조 | "
    }
    if (roomPcYn.isBooleanYn()) {
        options += "PC | "
    }
    if (roomTvYn.isBooleanYn()) {
        options += "TV | "
    }
    if (roomInternetYn.isBooleanYn()) {
        options += "인터넷 | "
    }
    if (roomCookYn.isBooleanYn()) {
        options += "취사 가능 | "
    }
    if (roomSofaYn.isBooleanYn()) {
        options += "소파 | "
    }
    if (roomRefrigeratorYn.isBooleanYn()) {
        options += "냉장고"
    }

    return options
}