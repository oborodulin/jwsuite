package com.oborodulin.jwsuite.data_territory.remote.osm.mappers.house

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.remote.osm.mappers.GeometryToGeoCoordinatesMapper
import com.oborodulin.jwsuite.data_territory.remote.osm.model.house.HouseElement
import com.oborodulin.jwsuite.domain.model.geo.GeoLocalityDistrict
import com.oborodulin.jwsuite.domain.model.geo.GeoStreet
import com.oborodulin.jwsuite.domain.model.territory.House
import com.oborodulin.jwsuite.domain.types.BuildingType

class HouseElementToHouseMapper(private val mapper: GeometryToGeoCoordinatesMapper) :
    Mapper<HouseElement, House> {
    override fun map(input: HouseElement): House {
        with(input.tags) {
            val type = this.buildingType()
            return House(
                //val microdistrict: GeoMicrodistrict? = null,
                street = GeoStreet().also { it.id = this.streetId },
                localityDistrict = this.localityDistrictId?.let { ldId ->
                    GeoLocalityDistrict().also { it.id = ldId }
                },
                zipCode = this.postcode.ifBlank { null },
                houseNum = this.houseNum(),
                houseLetter = this.houseLetter(),
                buildingNum = this.buildingNum(),
                buildingType = type,
                isBusiness = listOf(
                    BuildingType.HOTEL,
                    BuildingType.RETAIL,
                    BuildingType.SUPERMARKET,
                    BuildingType.OFFICE
                ).contains(type),
                isResidential = listOf(
                    BuildingType.APARTMENTS,
                    BuildingType.HOUSE,
                    BuildingType.DORMITORY
                ).contains(type),
                floorsByEntrance = this.levels.ifBlank { this.maxLevel }.toIntOrNull(),
                estimatedRooms = this.flats.toIntOrNull(),
                isPrivateSector = type == BuildingType.HOUSE,
                houseOsmId = input.id,
                coordinates = mapper.map(input.geometry)
            )
        }
    }
}