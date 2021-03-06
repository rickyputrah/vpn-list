package com.rickyputrah.express.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rickyputrah.express.data.repository.ILocationVpnRepository
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

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
}