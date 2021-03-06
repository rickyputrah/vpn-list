package com.rickyputrah.express.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.rickyputrah.express.CoroutineTestRule
import com.rickyputrah.express.data.model.LocationVpnResponse
import com.rickyputrah.express.data.repository.ILocationVpnRepository
import com.rickyputrah.express.data.repository.ResultRepository
import com.rickyputrah.express.model.toLocationListModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    private val locationRepository: ILocationVpnRepository = mockk()

    private val viewModel = HomeViewModel(locationRepository)

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
    fun `should return loading first when call requestLocationFirst`() = runBlockingTest {
        //GIVEN
        val data = LocationVpnResponse()
        coEvery { locationRepository.getLocationVpnList() } returns ResultRepository.Success(data)

        //Capture Live Data
        val observer = mockk<Observer<HomeViewModel.State>>()
        val slot = slot<HomeViewModel.State>()
        val stateList = arrayListOf<HomeViewModel.State>()
        every { observer.onChanged(capture(slot)) } answers {
            //store captured value
            stateList.add(slot.captured)
        }
        viewModel.state.observeForever(observer)

        //WHEN
        viewModel.requestLocationList()

        //THEN
        assertEquals(
            HomeViewModel.State.Loading,
            stateList[0]
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should return SuccessGetLocationList state with locationListData when call requestLocationList success`() =
        runBlockingTest {
            //GIVEN
            val data = LocationVpnResponse()
            coEvery { locationRepository.getLocationVpnList() } returns ResultRepository.Success(
                data
            )

            //Capture Live Data
            val observer = mockk<Observer<HomeViewModel.State>>()
            val slot = slot<HomeViewModel.State>()
            val stateList = arrayListOf<HomeViewModel.State>()
            every { observer.onChanged(capture(slot)) } answers {
                //store captured value
                stateList.add(slot.captured)
            }
            viewModel.state.observeForever(observer)

            //WHEN
            viewModel.requestLocationList()

            //THEN
            assertEquals(
                HomeViewModel.State.SuccessGetLocationList(data.toLocationListModel()),
                stateList[1]
            )
        }

    @ExperimentalCoroutinesApi
    @Test
    fun `should return ErrorConnectionTimeout when call requestLocationList ConnectionTimeout`() =
        runBlockingTest {
            //GIVEN
            coEvery { locationRepository.getLocationVpnList() } returns ResultRepository.Error.ConnectionTimeout

            //Capture Live Data
            val observer = mockk<Observer<HomeViewModel.State>>()
            val slot = slot<HomeViewModel.State>()
            val stateList = arrayListOf<HomeViewModel.State>()
            every { observer.onChanged(capture(slot)) } answers {
                //store captured value
                stateList.add(slot.captured)
            }
            viewModel.state.observeForever(observer)

            //WHEN
            viewModel.requestLocationList()

            //THEN
            assertEquals(
                HomeViewModel.State.ErrorConnectionTimeout,
                stateList[1]
            )
        }

    @ExperimentalCoroutinesApi
    @Test
    fun `should return UnknownError when call requestLocationList UnknownError`() =
        runBlockingTest {
            //GIVEN
            coEvery { locationRepository.getLocationVpnList() } returns ResultRepository.Error.UnknownError

            //Capture Live Data
            val observer = mockk<Observer<HomeViewModel.State>>()
            val slot = slot<HomeViewModel.State>()
            val stateList = arrayListOf<HomeViewModel.State>()
            every { observer.onChanged(capture(slot)) } answers {
                //store captured value
                stateList.add(slot.captured)
            }
            viewModel.state.observeForever(observer)

            //WHEN
            viewModel.requestLocationList()

            //THEN
            assertEquals(
                HomeViewModel.State.UnknownError,
                stateList[1]
            )
        }

    @ExperimentalCoroutinesApi
    @Test
    fun `should return RequestForbidden when call requestLocationList HttpException and requestCode REQUEST_CODE_FORBIDDEN`() =
        runBlockingTest {
            //GIVEN
            coEvery { locationRepository.getLocationVpnList() } returns ResultRepository.Error.HttpException(
                "",
                HomeViewModel.REQUEST_CODE_FORBIDDEN
            )

            //Capture Live Data
            val observer = mockk<Observer<HomeViewModel.State>>()
            val slot = slot<HomeViewModel.State>()
            val stateList = arrayListOf<HomeViewModel.State>()
            every { observer.onChanged(capture(slot)) } answers {
                //store captured value
                stateList.add(slot.captured)
            }
            viewModel.state.observeForever(observer)

            //WHEN
            viewModel.requestLocationList()

            //THEN
            assertEquals(
                HomeViewModel.State.RequestForbidden,
                stateList[1]
            )
        }

    @ExperimentalCoroutinesApi
    @Test
    fun `should return UnknownError when call requestLocationList HttpException and requestCode not REQUEST_CODE_FORBIDDEN`() =
        runBlockingTest {
            //GIVEN
            coEvery { locationRepository.getLocationVpnList() } returns ResultRepository.Error.HttpException(
                "",
                12345
            )

            //Capture Live Data
            val observer = mockk<Observer<HomeViewModel.State>>()
            val slot = slot<HomeViewModel.State>()
            val stateList = arrayListOf<HomeViewModel.State>()
            every { observer.onChanged(capture(slot)) } answers {
                //store captured value
                stateList.add(slot.captured)
            }
            viewModel.state.observeForever(observer)

            //WHEN
            viewModel.requestLocationList()

            //THEN
            assertEquals(
                HomeViewModel.State.UnknownError,
                stateList[1]
            )
        }
}