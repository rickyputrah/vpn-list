package com.rickyputrah.express.data.repository

import com.rickyputrah.express.data.model.LocationVpnResponse
import com.rickyputrah.express.model.BestLocationItemModel
import com.rickyputrah.express.model.LocationItemModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocationVpnRepository @Inject constructor(
    private val locationVpnApi: LocationVpnApi,
    private val bestLocationUseCase: BestLocationUseCase
) :
    ILocationVpnRepository {

    override suspend fun getLocationVpnList(): ResultRepository<LocationVpnResponse> =
        handleRequest {
            locationVpnApi.getLocations()
        }

    override suspend fun getBestLocation(list: List<LocationItemModel>): ResultRepository<BestLocationItemModel> {
        var bestLocation: BestLocationItemModel? = null
        var bestIntervalTime = Long.MAX_VALUE
        return withContext(Dispatchers.IO) {
            list.forEach { location ->
                location.ipList.forEach { destination ->
                    runCatching {
                        bestLocationUseCase.checkDestinationConnection(destination)
                    }.onSuccess { (reachable, interval) ->
                        if (reachable && interval < bestIntervalTime) {
                            bestLocation =
                                BestLocationItemModel(location.name, destination, location.image)
                            bestIntervalTime = interval
                        }
                    }
                }
            }
            bestLocation?.let {
                ResultRepository.Success(it)
            } ?: run {
                ResultRepository.Error.UnknownError
            }
        }
    }
}