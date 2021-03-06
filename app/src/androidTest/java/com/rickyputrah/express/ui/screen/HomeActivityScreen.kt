package com.rickyputrah.express.ui.screen

import android.view.View
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton
import com.agoda.kakao.text.KTextView
import com.rickyputrah.express.R
import org.hamcrest.Matcher


class HomeActivityScreen : Screen<HomeActivityScreen>() {

    fun assertButtonRefresh(text: String) = KButton {
        withId(R.id.button_refresh)
        withText(text)
    }.isDisplayed()

    fun assertButtonShowBest() = KButton {
        withId(R.id.button_show_best)
    }.isDisplayed()

    fun verifySearchResultItemCountAndScroll(itemCount: Int) {
        recyclerViewLocation.hasSize(itemCount)
        recyclerViewLocation.perform { scrollToEnd() }
    }

    fun clickButtonRefresh() = KButton {
        withId(R.id.button_refresh)
    }.perform { click() }

    fun showErrorDialog(resourcesString: Int) = KTextView {
        withId(R.id.text_error_message)
        withText(resourcesString)
    }


    private val recyclerViewLocation = KRecyclerView({
        withId(R.id.recycler_view)
    }, itemTypeBuilder = {
        itemType(::LocationItemList)
    })
}

class LocationItemList(parent: Matcher<View>) : KRecyclerItem<LocationItemList>(parent)
