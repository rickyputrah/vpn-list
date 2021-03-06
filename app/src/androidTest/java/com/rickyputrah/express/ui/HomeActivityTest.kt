package com.rickyputrah.express.ui

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.agoda.kakao.screen.Screen.Companion.idle
import com.rickyputrah.express.R
import com.rickyputrah.express.base.ErrorDispatcher
import com.rickyputrah.express.base.ErrorType
import com.rickyputrah.express.base.SuccessDispatcher
import com.rickyputrah.express.ui.home.HomeActivity
import com.rickyputrah.express.ui.screen.HomeActivityScreen
import com.squareup.okhttp.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class HomeActivityTest {

    @get:Rule
    val activityTestRule = ActivityTestRule(HomeActivity::class.java, false, false)

    private val mockWebServer = MockWebServer()
    private val homeScreen = HomeActivityScreen()

    @Before
    fun setUp() {
        mockWebServer.play(8080)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun shouldLoadLocationList() {
        mockWebServer.setDispatcher(SuccessDispatcher())

        activityTestRule.launchActivity(null)

        homeScreen {
            idle()
            assertButtonRefresh("refresh button")
            assertButtonShowBest()
            verifySearchResultItemCountAndScroll(5)
        }
    }

    @Test
    fun shouldLoadLocationListWhenClickRefreshButton() {
        mockWebServer.setDispatcher(SuccessDispatcher())

        activityTestRule.launchActivity(null)

        homeScreen {
            idle()
            verifySearchResultItemCountAndScroll(5)
            clickButtonRefresh()
            verifySearchResultItemCountAndScroll(5)
        }
    }

    @Test
    fun shouldShowErrorDialogScreen403() {
        mockWebServer.setDispatcher(ErrorDispatcher(ErrorType.HTTP_EXCEPTION_403))

        activityTestRule.launchActivity(null)

        homeScreen {
            showErrorDialog(R.string.text_request_forbidden_error)
        }
    }

    @Test
    fun shouldShowErrorDialogScreenUnknownError() {
        mockWebServer.setDispatcher(ErrorDispatcher(ErrorType.HTTP_EXCEPTION_404))

        activityTestRule.launchActivity(null)

        homeScreen {
            showErrorDialog(R.string.text_unknown_error)
        }
    }
}