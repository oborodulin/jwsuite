package com.oborodulin.jwsuite.data_appsetting.local.csv.mappers

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_appsetting.local.db.entities.AppSettingEntity
import com.oborodulin.jwsuite.domain.services.csv.model.appsetting.AppSettingCsv

class AppSettingEntityListToAppSettingCsvListMapper(mapper: AppSettingEntityToAppSettingCsvMapper) :
    ListMapperImpl<AppSettingEntity, AppSettingCsv>(mapper)