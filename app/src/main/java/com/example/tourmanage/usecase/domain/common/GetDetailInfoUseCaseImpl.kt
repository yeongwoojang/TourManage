package com.example.tourmanage.usecase.domain.common

import com.example.tourmanage.common.data.server.item.DetailItem
import com.example.tourmanage.common.repository.ServiceAPI
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.error.area.TourMangeException
import com.example.tourmanage.toDetailItems
import com.example.tourmanage.usecase.data.common.GetDetailInfoUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetDetailInfoUseCaseImpl @Inject constructor(
    private val serviceAPI: ServiceAPI
) : GetDetailInfoUseCase {
    override suspend fun invoke(
        contentId: String,
        contentTypeId: Config.CONTENT_TYPE_ID
    ): Result<Flow<ArrayList<DetailItem>>> = runCatching {
        flow {
            val response = serviceAPI.requestDetailInfo(contentId = contentId, contentTypeId = contentTypeId.value)
            val detailItems = response.toDetailItems()
            if (detailItems.isEmpty()) {
                throw TourMangeException.DetailInfoNullException("공통 정보 조회를 실패했습니다.")
            } else {
                emit(detailItems)
            }
        }
    }.onFailure {
        throw TourMangeException.DetailInfoNullException(it.message.orEmpty())
    }
}