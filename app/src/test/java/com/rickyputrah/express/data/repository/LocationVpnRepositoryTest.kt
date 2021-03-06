package com.rickyputrah.express.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rickyputrah.express.data.model.LocationVpnResponse
import com.rickyputrah.express.model.BestLocationItemModel
import com.rickyputrah.express.model.LocationItemModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException


@RunWith(JUnit4::class)
class LocationVpnRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val locationApi: LocationVpnApi = mockk()
    private val bestLocationUseCase: BestLocationUseCase = mockk()

    private val repository = LocationVpnRepository(locationApi, bestLocationUseCase)

    @ExperimentalCoroutinesApi
    @Before
    fun setupReposViewModel() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should return success with data when call api and return success`() = runBlocking {
        val expectedResponse = LocationVpnResponse()
        coEvery { locationApi.getLocations() } returns expectedResponse

        val result = repository.getLocationVpnList()

        assertEquals(ResultRepository.Success(expectedResponse), result)
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `should return ConnectionTimeout when call api and throw SocketTimeoutException`() =
        runBlocking {
            coEvery { locationApi.getLocations() } throws SocketTimeoutException()

            val result = repository.getLocationVpnList()

            assertEquals(ResultRepository.Error.ConnectionTimeout, result)
        }

    @ExperimentalCoroutinesApi
    @Test
    fun `should return HttpException when call api and throw HttpException`() = runBlocking {
        val httpException = HttpException(Response.error<String>(403, mockk(relaxed = true)))
        coEvery { locationApi.getLocations() } throws httpException
        val expectedReturn = ResultRepository.Error.HttpException(
            httpException.message(),
            httpException.code()
        )

        val result = repository.getLocationVpnList()

        assertEquals(expectedReturn, result)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should return UnknownError when call api and throw IOException`() = runBlocking {
        coEvery { locationApi.getLocations() } throws IOException()

        val result = repository.getLocationVpnList()

        assertEquals(ResultRepository.Error.UnknownError, result)
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `should return the best location when call getBestLocation`() = runBlocking {
        val firstLocation =
            LocationItemModel(name = "syalala", ipList = listOf("12"), image = "asd")
        val firstPingResult = Pair(true, 1000L)

        val secondLocation =
            LocationItemModel(name = "syalala2323", ipList = listOf("465"), image = "sdadasd")
        val secondPingResult = Pair(true, 2000L)

        val list = listOf(firstLocation, secondLocation)
        every { bestLocationUseCase.checkDestinationConnection(firstLocation.ipList[0]) } returns firstPingResult
        every { bestLocationUseCase.checkDestinationConnection(secondLocation.ipList[0]) } returns secondPingResult

        val result = repository.getBestLocation(list)

        val expectedResult = ResultRepository.Success(
            BestLocationItemModel(
                firstLocation.name,
                firstLocation.ipList[0],
                firstLocation.image
            )
        )
        assertEquals(expectedResult, result)
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `should return the best location and switch from the first item when call getBestLocation`() =
        runBlocking {
            val firstLocation =
                LocationItemModel(name = "syalala", ipList = listOf("12"), image = "asd")
            val firstPingResult = Pair(true, 2000L)

            val secondLocation =
                LocationItemModel(name = "syalala2323", ipList = listOf("465"), image = "sdadasd")
            val secondPingResult = Pair(true, 1000L)

            val list = listOf(firstLocation, secondLocation)
            every { bestLocationUseCase.checkDestinationConnection(firstLocation.ipList[0]) } returns firstPingResult
            every { bestLocationUseCase.checkDestinationConnection(secondLocation.ipList[0]) } returns secondPingResult

            val result = repository.getBestLocation(list)

            val expectedResult = ResultRepository.Success(
                BestLocationItemModel(
                    secondLocation.name,
                    secondLocation.ipList[0],
                    secondLocation.image
                )
            )
            assertEquals(expectedResult, result)
        }

    @ExperimentalCoroutinesApi
    @Test
    fun `should return UnknownError when don't have available location when call getBestLocation`() =
        runBlocking {
            val firstLocation =
                LocationItemModel(name = "syalala", ipList = listOf("12"), image = "asd")
            val firstPingResult = Pair(false, 2000L)

            val secondLocation =
                LocationItemModel(name = "syalala2323", ipList = listOf("465"), image = "sdadasd")
            val secondPingResult = Pair(false, 1000L)

            val list = listOf(firstLocation, secondLocation)
            every { bestLocationUseCase.checkDestinationConnection(firstLocation.ipList[0]) } returns firstPingResult
            every { bestLocationUseCase.checkDestinationConnection(secondLocation.ipList[0]) } returns secondPingResult

            val result = repository.getBestLocation(list)

            val expectedResult = ResultRepository.Error.UnknownError
            assertEquals(expectedResult, result)
        }
}