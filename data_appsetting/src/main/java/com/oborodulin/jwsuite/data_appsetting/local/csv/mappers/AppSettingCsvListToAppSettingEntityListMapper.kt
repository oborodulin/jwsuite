package com.oborodulin.jwsuite.data_appsetting.local.csv.mappers

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_appsetting.local.db.entities.AppSettingEntity
import com.oborodulin.jwsuite.domain.services.csv.model.appsetting.AppSettingCsv

class AppSettingCsvListToAppSettingEntityListMapper(mapper: AppSettingCsvToAppSettingEntityMapper) :
    ListMapperImpl<AppSettingCsv, AppSettingEntity>(mapper)