package com.rickyputrah.express.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rickyputrah.express.data.model.LocationVpnResponse
import io.mockk.coEvery
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

    private val repository = LocationVpnRepository(locationApi)

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
}