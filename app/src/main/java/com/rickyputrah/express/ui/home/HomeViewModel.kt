package com.rickyputrah.express.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rickyputrah.express.data.repository.ILocationVpnRepository
import com.rickyputrah.express.data.repository.ResultRepository.Error
import com.rickyputrah.express.data.repository.ResultRepository.Success
import com.rickyputrah.express.model.BestLocationItemModel
import com.rickyputrah.express.model.LocationItemModel
import com.rickyputrah.express.model.LocationListModel
import com.rickyputrah.express.model.toLocationListModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val locationVpnRepository: ILocationVpnRepository
) : ViewModel() {

    fun requestLocationList() {
        viewModelScope.launch {
            state.postValue(State.Loading)
            val stateResult = when (val result = locationVpnRepository.getLocationVpnList()) {
                is Success -> State.SuccessGetLocationList(result.data.toLocationListModel())
                is Error -> handleError(result)
            }
            state.postValue(stateResult)
        }
    }

    fun startToSearchBestLocation(listOfLocation: List<LocationItemModel>) {
        viewModelScope.launch {
            when (val result = locationVpnRepository.getBestLocation(listOfLocation)) {
                is Success -> state.postValue(State.SuccessBestLocation(result.data))
            }
        }
    }

    private fun handleError(result: Error): State {
        return when {
            (result is Error.ConnectionTimeout) -> State.ErrorConnectionTimeout
            (result is Error.HttpException && result.errorCode == REQUEST_CODE_FORBIDDEN) -> State.RequestForbidden
            else -> State.UnknownError
        }
    }

    val state: MutableLiveData<State> = MutableLiveData()

    sealed class State {
        data class SuccessGetLocationList(val data: LocationListModel) : State()
        data class SuccessBestLocation(val data: BestLocationItemModel) : State()
        object Loading : State()
        object ErrorConnectionTimeout : State()
        object UnknownError : State()
        object RequestForbidden : State()
    }

    companion object {
        const val REQUEST_CODE_FORBIDDEN = 403
    }
}