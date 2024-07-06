package com.example.tourmanage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tourmanage.UiState
import com.example.tourmanage.common.data.server.item.DetailItem
import com.example.tourmanage.common.data.server.item.DetailCommonItem
import com.example.tourmanage.model.ServerDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class StayDetailViewModel @Inject constructor(
    private val serverRepo: ServerDataRepository
): ViewModel() {
    val _stayDetailInfo = MutableStateFlow<UiState<DetailCommonItem>>(UiState.Ready())
    val stayDetailInfo = _stayDetailInfo

    private val _optionInfo = MutableStateFlow<UiState<ArrayList<DetailItem>>>(UiState.Ready())
    val optionInfo = _optionInfo

    fun requestStayDetailInfo(contentId: String?, contentType: String?) {
        Timber.i("requestStayDetailInfo | contentId: $contentId | contentType: $contentType")
        if (contentId == null || contentType == null) {
            return
        }
        viewModelScope.launch {
            serverRepo.requestStayDetailInfo(contentId, contentType)
                .onStart {}
                .catch { _stayDetailInfo.value = UiState.Error(it.message!!) }
                .collect { _stayDetailInfo.value = it}
        }
    }

    fun requestOptionInfo(contentId: String?, contentType: String?) {
        Timber.i("requestOptionInfo() | contentId: $contentId | contentType: $contentType")
        if (contentId == null || contentType == null) {
            _optionInfo.value = UiState.Error("Invalid Parameter")
            return
        }

        viewModelScope.launch {
            serverRepo.requestOptionInfo(contentId, contentType)
                .onStart { _optionInfo.value = UiState.Loading()}
                .catch { _optionInfo.value = UiState.Error(it.message!!) }
                .collect { _optionInfo.value = it }
        }
    }
}