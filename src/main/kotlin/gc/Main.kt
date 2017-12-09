package gc

import com.google.gson.Gson

fun main(args: Array<String>) {

    Rest.rest
            .getXMLReverseGeoCodeURL(
                    //                    format = "xml",
//                    geocode = "82.9336000,55.0416000"
                    geocode = "Новосибирская область, ff"
            )
            .subscribe { t, e ->
                t?.let {
                    val fromJson = Gson().fromJson(it.string(), GeoResponse::class.java)
                    println(fromJson)
                    val pos = fromJson
                            .response
                            .GeoObjectCollection
                            .featureMember
                            .firstOrNull()
                            ?.GeoObject
                            ?.metaDataProperty
                            ?.GeocoderMetaData
                            ?.Address
                            ?.let { it.Components.associateBy { it.kind }.mapValues { it.value.name } }
                            ?.let {
                                val city = it["locality"]?.let { "г. $it" }
                                val street = it["street"]?.replace("улица ", "")?.let { ", ул. $it" }
                                val house = it["house"]?.let { ", д. $it" }
                                listOf(city, street, house)
                                        .filterNotNull()
                                        .reduce { acc, s -> "$acc$s" }
                            }
                    println(pos)
                }
                e?.let { print(it) }
            }

    Thread.sleep(100000)
}