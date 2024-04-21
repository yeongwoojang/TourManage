package com.example.tourmanage.model

import com.example.tourmanage.*
import com.example.tourmanage.common.ServerGlobal
import com.example.tourmanage.common.data.server.item.*
import com.example.tourmanage.common.repository.ServiceAPI
import com.example.tourmanage.common.value.Config
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class ServerDataImpl @Inject constructor(
    private val client: ServiceAPI
): ServerDataRepository {
    override fun requestStayInfo(areaCode: String?): Flow<UiState<ArrayList<StayItem>>> {
        return callbackFlow {
            try {
                val stayInfo = client.requestSearchStay(areaCode = areaCode)
                val code = stayInfo.response?.header?.resultCode
                val msg = stayInfo.response?.header?.resultMsg
                val stayItemList =  stayInfo.toStayInfoList()
                if ("0000" == code && stayItemList.isNotEmpty()) {
                    trySend(UiState.Success(stayItemList))
                } else {
                    trySend(UiState.Error(msg ?: "requestStayInfo() Error."))
                }
            awaitClose()
            } catch (e: Exception) {
                trySend(UiState.Error(e.message ?: "requestStayInfo() Error."))
            } finally {
                close()
            }
        }
    }

    override fun requestAreaCode(): Flow<UiState<ArrayList<AreaItem>>> {
        return callbackFlow {
            try {
                val areaInfo = client.requestAreaList()
                val code = areaInfo.response?.header?.resultCode
                val msg = areaInfo.response?.header?.resultMsg
                val areaItemList = areaInfo.toAreaInfoList()
                if ("0000" == code && areaItemList.isNotEmpty()) {
                    ServerGlobal.setAreaCodeMap(areaItemList)
                    trySend(UiState.Success(areaItemList))
                } else {
                    trySend(UiState.Error(msg ?: "requestAreaList() Error."))
                }

                awaitClose()
            } catch (e: Exception) {
                trySend(UiState.Error(e.message ?: "requestAreaList() Error."))
            } finally {
                close()
            }
        }
    }


    override fun requestStayDetailInfo(contentId: String, contentType: String): Flow<UiState<StayDetailItem>> {
        return callbackFlow {
            try {
                val stayDetailInfo = client.requestStayDetailInfo(contentId = contentId, contentTypeId = contentType)
                val code = stayDetailInfo.response?.header?.resultCode
                val msg = stayDetailInfo.response?.header?.resultMsg
                val stayDetailItem = stayDetailInfo.toStayDetail()
                if ("0000" == code && stayDetailItem != null) {
                    trySend(UiState.Success(stayDetailItem))
                } else {
                    trySend(UiState.Error(msg ?: "requestStayDetailInfo() Error."))
                }
                awaitClose()
            } catch (e: Exception) {
                trySend(UiState.Error(e.message ?: "requestDetailInfo() Error."))
            } finally {
                close()
            }
        }

    }

    override fun requestOptionInfo(contentId: String, contentType: String): Flow<UiState<ArrayList<DetailItem>>> {
        return callbackFlow {
            try {
                val detailInfo = client.requestOptionInfo(contentId = contentId, contentTypeId = contentType)
                val code = detailInfo.response?.header?.resultCode
                val msg = detailInfo.response?.header?.resultMsg
                val optionItem = detailInfo.toDetailItems()
                if ("0000" == code && optionItem.isNotEmpty()) {
                    trySend(UiState.Success(optionItem))
                } else if (optionItem.isEmpty()){
                    trySend(UiState.Error(msg ?: "requestDetailInfo() | Empty()"))
                } else {
                    trySend(UiState.Error(msg ?: "requestDetailInfo() Error."))
                }
                awaitClose()
            } catch (e: Exception) {
                trySend(UiState.Error(e.message ?: "requestDetailInfo() Error."))
            } finally {
                close()
            }
        }
    }

    override fun requestFestivalInfo(areaCode: String?, eventStartDate: String?, arrange: Config.ARRANGE_TYPE): Flow<UiState<ArrayList<FestivalItem>>> {
        return callbackFlow {
            try {
                var date = eventStartDate
                if (date.isNullOrEmpty()) {
                    val format = SimpleDateFormat("yyyyMMdd", Locale.KOREA)
                    date = format.format(Date().time)
                }

                val festivalInfo = client.requestFestivalInfo(areaCode = areaCode, eventStartDate = date!!, arrange = arrange.name)
                val code = festivalInfo.response?.header?.resultCode
                val msg = festivalInfo.response?.header?.resultMsg
                val festivalItems = festivalInfo.toFestivalItems()
                if ("0000" == code && festivalItems.isNotEmpty()) {
                    trySend(UiState.Success(festivalItems))
                } else {
                    trySend(UiState.Error(msg ?: "requestFestivalInfo() Error."))

                }
                awaitClose()
            } catch (e: java.lang.Exception) {
                trySend(UiState.Error(e.message ?: "requestFestivalInfo() Error."))
            } finally {
                close()
            }
        }
    }

    override fun requestTourInfo(areaCode: String?): Flow<UiState<ArrayList<TourItem>>> {
        return callbackFlow {
            try {
                val tourInfo = client.requestTourInfo(areaCode = areaCode)
                val code = tourInfo.response?.header?.resultCode
                val msg = tourInfo.response?.header?.resultMsg
                val tourItemList =  tourInfo.toTourInfoList()
                if ("0000" == code && tourItemList.isNotEmpty()) {
                    trySend(UiState.Success(tourItemList))
                } else {
                    trySend(UiState.Error(msg ?: "requestTourInfo() Error."))
                }
                awaitClose()
            } catch (e: Exception) {
                trySend(UiState.Error(e.message ?: "requestTourInfo() Error."))
            } finally {
                close()
            }
        }
    }
}




















