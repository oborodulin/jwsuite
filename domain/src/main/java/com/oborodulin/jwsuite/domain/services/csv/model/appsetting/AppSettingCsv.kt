package com.oborodulin.jwsuite.domain.services.csv.model.appsetting

import com.oborodulin.jwsuite.domain.services.Exportable
import com.oborodulin.jwsuite.domain.types.AppSettingParam
import com.opencsv.bean.CsvBindByName
import java.util.UUID

data class AppSettingCsv(
    @CsvBindByName val settingId: UUID,
    @CsvBindByName val paramName: AppSettingParam,
    @CsvBindByName val paramValue: String
) : Exportable
