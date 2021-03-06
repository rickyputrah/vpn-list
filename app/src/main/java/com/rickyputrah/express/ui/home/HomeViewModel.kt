package com.rickyputrah.express.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rickyputrah.express.data.repository.ILocationVpnRepository
import com.rickyputrah.express.data.repository.ResultRepository.Error
import com.rickyputrah.express.data.repository.ResultRepository.Success
import com.rickyputrah.express.model.LocationListModel
import com.rickyputrah.express.model.toLocationListModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val locationVpnRepository: ILocationVpnRepository
) : ViewModel() {

    fun requestLocationList() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                when (val result = locationVpnRepository.getLocationVpnList()) {
                    is Success -> {
                        State.SuccessGetLocationList(result.data.toLocationListModel())
                    }
                    is Error -> {
                        handleError(result)
                    }
                }
            }
            state.postValue(result)
        }
    }

    private fun handleError(result: Error): State {
        return when {
            (result is Error.ConnectionTimeout) -> State.ErrorConnectionTimeout
            (result is Error.HttpException && result.errorCode == 403) -> State.RequestForbidden
            else -> State.UnknownError
        }
    }

    val state: MutableLiveData<State> = MutableLiveData()

    sealed class State {
        data class SuccessGetLocationList(val data: LocationListModel) : State()
        object ErrorConnectionTimeout : State()
        object UnknownError : State()
        object RequestForbidden : State()
    }
}