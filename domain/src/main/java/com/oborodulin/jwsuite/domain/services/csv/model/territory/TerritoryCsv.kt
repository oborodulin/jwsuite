package com.oborodulin.jwsuite.domain.services.csv.model.territory

import com.oborodulin.jwsuite.domain.services.Exportable
import com.oborodulin.jwsuite.domain.services.Importable
import com.opencsv.bean.CsvBindByName
import java.util.UUID

data class TerritoryCsv(
    @CsvBindByName val territoryId: UUID,
    @CsvBindByName val territoryNum: Int,
    @CsvBindByName val isActive: Boolean = true,
    @CsvBindByName val isBusinessTerritory: Boolean = false,
    @CsvBindByName val isGroupMinistry: Boolean = false,
    @CsvBindByName val isProcessed: Boolean = true,
    @CsvBindByName val territoryDesc: String? = null,
    @CsvBindByName val tMicrodistrictsId: UUID? = null,
    @CsvBindByName val tLocalityDistrictsId: UUID? = null,
    @CsvBindByName val tLocalitiesId: UUID,
    @CsvBindByName val tTerritoryCategoriesId: UUID,
    @CsvBindByName val tCongregationsId: UUID
) : Exportable, Importable