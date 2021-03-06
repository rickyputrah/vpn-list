package com.rickyputrah.express.data.model

import org.simpleframework.xml.*

@Root(name = "expressvpn", strict = false)
class LocationVpnResponse(
    @field: Element(name = "button_text")
    var buttonText: String = "",
    @field: Element(name = "locations")
    var locations: LocationList = LocationList(),
    @field: Element(name = "icons")
    var icons: XmlIcons = XmlIcons()
)

@Root(name = "locations", strict = false)
class LocationList(
    @field:ElementList(name = "location", inline = true)
    var locationList: MutableList<Location> = mutableListOf()
)

@Root(name = "location")
class Location(
    @field:Attribute(name = "name")
    var name: String = "",
    @field: Attribute(name = "sort_order")
    var order: String = "",
    @field: Attribute(name = "icon_id")
    var iconId: String = "",
    @field:ElementList(name = "server", inline = true)
    var server: MutableList<Server> = mutableListOf()
)

@Root(name = "server")
class Server {
    @field:Attribute(name = "ip")
    var ipAddress: String = ""
}

@Root(name = "icons", strict = false)
class XmlIcons(
    @field:ElementMap(
        key = "id",
        attribute = true,
        entry = "icon",
        inline = true
    )
    var icon: HashMap<String, String> = hashMapOf()
)
