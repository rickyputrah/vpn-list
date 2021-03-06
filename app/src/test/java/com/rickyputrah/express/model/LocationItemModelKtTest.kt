package com.rickyputrah.express.model

import com.rickyputrah.express.data.model.*
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class LocationItemModelKtTest {

    @Test
    fun `should set button text when LocationVpnResponse call toLocationListModel`() {
        val response = LocationVpnResponse(
            buttonText = "buttonText"
        )

        val result = response.toLocationListModel()

        assertEquals(response.buttonText, result.buttonText)
    }

    @Test
    fun `should set location list when LocationVpnResponse call toLocationListModel`() {
        val expectedLocation = LocationItemModel(
            name = "name", ipList = listOf("asda", "asdad"), image = "asdasdasddazs"
        )
        val expectedResult = listOf(expectedLocation)
        val response = LocationVpnResponse(
            buttonText = "buttonText",
            locations = LocationList(
                locationList = mutableListOf(
                    Location(
                        name = expectedLocation.name,
                        order = "order",
                        iconId = "id1",
                        server = mutableListOf(
                            Server(ipAddress = expectedLocation.ipList.get(0)),
                            Server(ipAddress = expectedLocation.ipList.get(1))
                        )
                    )
                )
            ), icons = XmlIcons(
                icon = hashMapOf(
                    "id1" to expectedLocation.image,
                    "id2" to "sadadsada",
                    "id3" to "sadadsada"
                )
            )
        )

        val result = response.toLocationListModel()

        assertEquals(expectedResult, result.listOfLocation)
    }
}