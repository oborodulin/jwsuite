package com.oborodulin.jwsuite.data_territory.remote.osm.model.house

import androidx.core.text.isDigitsOnly
import com.oborodulin.jwsuite.data_geo.remote.osm.model.Geometry
import com.oborodulin.jwsuite.data_geo.remote.osm.model.Osm3s
import com.oborodulin.jwsuite.domain.model.territory.House.Companion.LETTER_DELIMITERS
import com.oborodulin.jwsuite.domain.model.territory.House.Companion.NUMBER_DELIMITERS
import com.oborodulin.jwsuite.domain.types.BuildingType
import com.oborodulin.jwsuite.domain.util.Constants.OSM_TIMEOUT
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.UUID

data class HouseApiModel(
    @Json(name = "version") val version: String,
    @Json(name = "generator") val generator: String,
    @Json(name = "osm3s") val osm3s: Osm3s,
    @Json(name = "elements") val elements: List<HouseElement>
) {
    companion object {
        // https://wiki.openstreetmap.org/wiki/RU:Key:building#%D0%9A%D0%BE%D0%BC%D0%BC%D0%B5%D1%80%D1%87%D0%B5%D1%81%D0%BA%D0%B8%D0%B5
        // https://taginfo.openstreetmap.org/keys/building#values
        // Donetsk / Budyonny Raion
        // Карагандинська вулиця
        // 230-ї Стрілецької Дивізії вулиця
        fun data(
            streetId: UUID,
            localityDistrictId: UUID? = null,
            microdistrictId: UUID? = null,
            geocodeArea: String,
            streetName: String
        ) = """
    [out:json][timeout:$OSM_TIMEOUT];
    {{geocodeArea:$geocodeArea}}->.searchArea;
    nwr["building"!~"^(warehouse|religious|cathedral|chapel|church|kingdom_hall|monastery|mosque|presbytery|shrine|synagogue|temple|bridge|toilets|barn|conservatory|cowshed|farm_auxiliary)${'$'}"]["addr:housenumber"]["addr:street"="$streetName"](area.searchArea)->.chs;
    foreach.chs(
        convert HouseType 
            osmType = type(), ::id = id(), ::geom = geom(), streetId = "$streetId", localityDistrictId = "${
            localityDistrictId?.toString().orEmpty()
        }", microdistrictId = "${
            microdistrictId?.toString().orEmpty()
        }", building = t["building"], housenumber = t["addr:housenumber"], postcode = t["addr:postcode"], flats  = t["building:flats"], levels = t["building:levels"], max_level = t["max_level"];
        out center;
    );
    """.trimIndent()
    }
}

data class HouseElement(
    @Json(name = "type") val type: String,
    @Json(name = "id") val id: Long,
    @Json(name = "geometry") val geometry: Geometry,
    @Json(name = "tags") val tags: HouseTags
)

// https://wiki.openstreetmap.org/wiki/RU:%D0%90%D0%B4%D1%80%D0%B5%D1%81%D0%B0#%D0%9D%D1%83%D0%BC%D0%B5%D1%80%D0%B0%D1%86%D0%B8%D1%8F_%D0%B4%D0%BE%D0%BC%D0%BE%D0%B2
@JsonClass(generateAdapter = true)
data class HouseTags(
    @Json(name = "osmType") val osmType: String,
    @Json(name = "streetId") val streetId: UUID,
    @Json(name = "localityDistrictId") val localityDistrictId: UUID?,
    @Json(name = "microdistrictId") val microdistrictId: UUID?,
    @Json(name = "building") val building: String,
    @Json(name = "housenumber") val houseNumber: String,
    @Json(name = "postcode") val postcode: String,
    @Json(name = "flats") val flats: String,
    @Json(name = "levels") val levels: String,
    @Json(name = "max_level") val maxLevel: String
) {
    fun buildingType(): BuildingType = when {
        this.building in listOf(
            "bungalow",
            "cabin",
            "detached",
            "farm",
            "ger",
            "houseboat",
            "semidetached_house",
            "static_caravan",
            "stilt_house",
            "terrace",
            "tree_house",
            "trullo",
            "hut"
        ) -> BuildingType.HOUSE

        this.building in listOf("yes", "residential") -> BuildingType.HOUSE
        "commercial" == this.building -> BuildingType.OFFICE
        "kiosk" == this.building -> BuildingType.RETAIL
        this.building in listOf("industrial", "shed") -> BuildingType.INDUSTRIAL

        else -> try {
            BuildingType.valueOf(this.building.trim().uppercase())
        } catch (e: IllegalArgumentException) {
            BuildingType.PUBLIC
        }
    }

    fun houseNum(): Int {
        var number = houseNumber
        NUMBER_DELIMITERS.forEach { delimiter ->
            houseNumber.takeIf { delimiter in it }?.substringBefore(delimiter)?.let {
                number = it
                return@forEach
            }
        }
        return number.filter { it.isDigit() }.toInt()
    }

    fun houseLetter(): String? {
        val number = houseNumber.substringBefore(' ')
        if (number.isDigitsOnly()) return null
        var letter = number.filter { it.isLetter() && it !in LETTER_DELIMITERS }
        if (letter.isNotEmpty()) return letter.uppercase()
        LETTER_DELIMITERS.forEach { delimiter ->
            number.takeIf { delimiter in it }?.substringAfter(delimiter)?.let {
                letter = it
                return@forEach
            }
        }
        return letter.ifBlank { null }
    }

    fun buildingNum() =
        houseNumber.takeIf { ' ' in it }?.substringAfter(' ')?.substringBefore(' ')
            ?.filter { it.isDigit() }?.toIntOrNull()

    fun floors() = levels.ifBlank { maxLevel }.toIntOrNull()
}