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
    override fun requestStayInfo(areaCode: String?, sigunguCode: String?): Flow<UiState<ArrayList<StayItem>>> {
        return callbackFlow {
            try {
                val stayInfo = client.requestSearchStay(areaCode = areaCode, sigunguCode = sigunguCode)
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

    override fun requestAreaCode(areaCode: String?, isInit: Boolean): Flow<UiState<ArrayList<AreaItem>>> {
        return callbackFlow {
            try {
                val areaInfo = client.requestAreaList(areaCode = areaCode)
                val code = areaInfo.response?.header?.resultCode
                val msg = areaInfo.response?.header?.resultMsg
                val areaItemList = areaInfo.toAreaInfoList()
                if ("0000" == code && areaItemList.isNotEmpty()) {
                    if (isInit) {
                        ServerGlobal.setMainAreaList(areaItemList)
                    }
                    trySend(UiState.Success(areaItemList, requestKey = areaCode!!))
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


    override fun requestStayDetailInfo(contentId: String, contentType: String): Flow<UiState<DetailCommonItem>> {
        return callbackFlow {
            try {
                val stayDetailInfo = client.requestStayDetailInfo(contentId = contentId, contentTypeId = contentType)
                val code = stayDetailInfo.response?.header?.resultCode
                val msg = stayDetailInfo.response?.header?.resultMsg
                val stayDetailItem = stayDetailInfo.toDetailCommonItem()
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

    override fun requestFestivalInfo(areaCode: String?, eventStartDate: String?): Flow<UiState<ArrayList<FestivalItem>>> {
        return callbackFlow {
            try {
                var date = eventStartDate
                if (date.isNullOrEmpty()) {
                    val format = SimpleDateFormat("yyyyMMdd", Locale.KOREA)
                    date = format.format(Date().time)
                }

                val festivalInfo = client.requestFestivalInfo(areaCode = areaCode, eventStartDate = date!!)
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

    override fun requestAreaBasedList(
        areaCode: String?,
        sigunguCode: String?,
        contentTypeId: Config.CONTENT_TYPE_ID?
    ): Flow<UiState<ArrayList<AreaBasedItem>>> {
        return callbackFlow {
            try {
                val tourInfo = client.requestAreaBasedList(areaCode = areaCode, sigunguCode = sigunguCode, contentType = contentTypeId?.value)
                val code = tourInfo.response?.header?.resultCode
                val msg = tourInfo.response?.header?.resultMsg
                val tourItemList =  tourInfo.toAreaBasedInfoItems()
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

    override fun requestLocationBasedList(
        contentTypeId: Config.CONTENT_TYPE_ID?,
        mapX: String?,
        mapY: String?,
        radius: String?,
        arrange: Config.ARRANGE_TYPE
    ): Flow<UiState<ArrayList<LocationBasedItem>>> {
        return callbackFlow {
            try {
                val locationBasedInfo = client.requestLocationBasedList(contentTypeId = contentTypeId!!.value, mapX = mapX!!, mapY = mapY!!, radius = radius, arrange = arrange.value)
                val code = locationBasedInfo.response?.header?.resultCode
                val msg = locationBasedInfo.response?.header?.resultMsg
                val locationBasedItemList =  locationBasedInfo.toLocationBasedList()
                if ("0000" == code && locationBasedItemList.isNotEmpty()) {
                    trySend(UiState.Success(locationBasedItemList))
                } else {
                    trySend(UiState.Error(msg ?: "requestLocationBasedList() Error."))
                }
                awaitClose()
            } catch (e: Exception) {
                trySend(UiState.Error(e.message ?: "requestLocationBasedList() Error."))

            }finally {
                close()
            }
        }
    }
}




















