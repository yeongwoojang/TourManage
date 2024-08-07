package com.example.tourmanage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tourmanage.UiState
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.data.server.item.FestivalItem
import com.example.tourmanage.common.data.server.item.StayItem
import com.example.tourmanage.usecase.domain.area.CacheAreaUseCase
import com.example.tourmanage.usecase.domain.area.GetAreaUseCase
import com.example.tourmanage.usecase.domain.area.GetCacheAreaUseCase
import com.example.tourmanage.usecase.domain.area.RemoveCacheAreaUseCase
import com.example.tourmanage.usecase.domain.festival.GetFestivalUseCase
import com.example.tourmanage.usecase.domain.stay.GetStayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAreaUseCase: GetAreaUseCase,
    private val cacheAreaUseCase: CacheAreaUseCase,
    private val getCacheAreaUseCase: GetCacheAreaUseCase,
    private val removeCacheAreaUseCase: RemoveCacheAreaUseCase,
    private val getFestivalUseCase: GetFestivalUseCase,
    private val getStayUseCase: GetStayUseCase

): ViewModel() {
     private var getSubAreaJob: Job? = null //_ 버튼을 연타하여 연속적인 조회를 막고 이전 조회는 취소하기 위한 Job

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e("exceptionHandler:: | throwable: $throwable")
    }

    private val curMainArea = MutableSharedFlow<AreaItem?>()
    private val curSubArea = MutableSharedFlow<AreaItem?>()

    val areaCodeFlow: SharedFlow<Pair<AreaItem?, AreaItem?>> = curMainArea.combine(curSubArea) { areaCode, sigunguCode ->
        Pair(areaCode, sigunguCode)
    }.shareIn(viewModelScope + exceptionHandler, SharingStarted.Eagerly)

    private val _festivalList = MutableStateFlow<UiState<ArrayList<FestivalItem>>>(UiState.Ready())
    val festivalList = _festivalList.asStateFlow()

    private val _stayList = MutableStateFlow<UiState<List<StayItem>>>(UiState.Ready())
    val stayList = _stayList.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val subAreaList: StateFlow<UiState<ArrayList<AreaItem>>> = curMainArea.flatMapLatest {
        flow {
            emit(UiState.Loading())
            getAreaUseCase(it!!.code).getOrThrow()
                .collect {
                    emit(UiState.Success(it))
                }
        }
    }.stateIn(viewModelScope + exceptionHandler, SharingStarted.Eagerly, UiState.Ready())

    fun cacheArea(areaItem: AreaItem?, isSub: Boolean = false) {
        getSubAreaJob?.cancel() //_ job을 취소하면 해당 코루틴 내에서 동작중인 비동기 작업도 취소됨. 따라서 서버 조회가 취소됨.
        getSubAreaJob = viewModelScope.launch(exceptionHandler) {
            if (areaItem == null) {
                return@launch
            }
            val result = cacheAreaUseCase(areaItem, isSub).getOrThrow()
            if (result) {
                if (isSub) {
                    curSubArea.emit(areaItem)
                } else {
                    curMainArea.emit(areaItem)
                    val isRemoved = removeCacheAreaUseCase(true).getOrThrow()
                    if (isRemoved) {
                        curSubArea.emit(null)
                    }
                }
            }
        }
    }

    fun getCachedArea() {
        viewModelScope.launch(exceptionHandler) {
            repeat(2) { count -> //_ 부모, 자식 지역 코드 GET 하기위해 2번 조회
                val isSub = count == 1
                val result = getCacheAreaUseCase.invoke(isSub)
                result.getOrThrow().collect {
                    if (isSub) {
                        curSubArea.emit(it)
                    } else {
                        curMainArea.emit(it)
                    }
                }
            }
        }
    }

    fun requestFestivalInfo(areaCode: String = "", eventStartDate: String = "") {
        viewModelScope.launch {
            getFestivalUseCase(areaCode, eventStartDate).getOrThrow()
                .onStart {
                    _festivalList.value = UiState.Loading()
                }
                .collect {
                    _festivalList.value = UiState.Success(it)
                }
        }
    }

    fun requestStayInfo(areaCode: String?, sigunguCode: String?) {
        Timber.i("requestStayInfo() | areaCode: $areaCode | sigunguCode: $sigunguCode")
        viewModelScope.launch(exceptionHandler) {
            getStayUseCase(areaCode, sigunguCode).getOrThrow()
                .collect {
                    _stayList.value = UiState.Success(it)
                }
        }
    }
}