package com.rickyputrah.express.data.repository

import com.rickyputrah.express.util.PING_TEST_TIMEOUT_MILLIS
import java.net.InetAddress
import javax.inject.Inject

class BestLocationUseCase @Inject constructor() {

    fun checkDestinationConnection(destination: String): Pair<Boolean, Long> {
        val beforeTime = System.currentTimeMillis()
        val reachable = InetAddress.getByName(destination).isReachable(PING_TEST_TIMEOUT_MILLIS)
        val afterTime = System.currentTimeMillis()

        return Pair(reachable, afterTime - beforeTime)
    }

}