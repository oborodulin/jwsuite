package com.oborodulin.jwsuite.domain.services.csv.model.territory

import com.oborodulin.jwsuite.domain.services.Exportable
import com.oborodulin.jwsuite.domain.types.BuildingType
import com.opencsv.bean.CsvBindByName
import java.util.UUID

data class HouseCsv(
    @CsvBindByName val houseId: UUID,
    @CsvBindByName val zipCode: String? = null,
    @CsvBindByName val houseNum: Int,
    @CsvBindByName val houseLetter: String? = null,
    @CsvBindByName val buildingNum: Int? = null,
    @CsvBindByName val buildingType: BuildingType,
    @CsvBindByName val isBusinessHouse: Boolean = false,
    @CsvBindByName val isSecurityHouse: Boolean = false,
    @CsvBindByName val isIntercomHouse: Boolean? = null,
    @CsvBindByName val isResidentialHouse: Boolean = true,
    @CsvBindByName val houseEntrancesQty: Int? = null,
    @CsvBindByName val floorsByEntrance: Int? = null,
    @CsvBindByName val roomsByHouseFloor: Int? = null,
    @CsvBindByName val estHouseRooms: Int? = null,
    @CsvBindByName val isForeignLangHouse: Boolean = false,
    @CsvBindByName val isHousePrivateSector: Boolean = false,
    @CsvBindByName val houseDesc: String? = null,
    @CsvBindByName val hTerritoriesId: UUID? = null,
    @CsvBindByName val hMicrodistrictsId: UUID? = null,
    @CsvBindByName val hLocalityDistrictsId: UUID? = null,
    @CsvBindByName val hStreetsId: UUID
) : Exportable