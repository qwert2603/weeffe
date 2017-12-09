package gc

data class GeoResponse(val response: Response)

data class Response(val GeoObjectCollection: GeoObjectCollection)

data class GeoObjectCollection(val featureMember: List<FeatureMember>)

data class FeatureMember(val GeoObject: GeoObject)

data class GeoObject(
        val Point: Point,
        val metaDataProperty: MetaDataProperty
)

data class Point(val pos: String)

data class MetaDataProperty(val GeocoderMetaData: GeocoderMetaData)

data class GeocoderMetaData(val Address: Address)

data class Address(val Components: List<Component>)

data class Component(
        val kind: String,
        val name: String
)