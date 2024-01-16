package com.oborodulin.jwsuite.data.local.db.mappers.csv.appsetting

import com.oborodulin.home.common.mapping.NullableInputListMapperImpl
import com.oborodulin.jwsuite.data_appsetting.local.db.entities.AppSettingEntity

class AppSettingEntityListToAppSettingCsvListMapper(mapper: AppSettingEntityToAppSettingCsvMapper) :
    NullableInputListMapperImpl<AppSettingEntity, com.oborodulin.jwsuite.domain.services.csv.model.appsetting.AppSettingCsv>(mapper)