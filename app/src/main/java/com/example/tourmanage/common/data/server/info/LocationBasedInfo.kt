package com.example.tourmanage.common.data.server.info

import com.example.tourmanage.common.data.server.common.CommonResponse
import com.example.tourmanage.common.data.server.item.LocationBasedItem
import com.squareup.moshi.Json

data class LocationBasedInfo(
    @Json(name = "response") val response: CommonResponse<LocationBasedItem>?
)
