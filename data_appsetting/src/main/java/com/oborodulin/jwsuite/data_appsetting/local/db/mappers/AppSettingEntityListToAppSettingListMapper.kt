package com.oborodulin.jwsuite.data_appsetting.local.db.mappers

import com.oborodulin.home.common.mapping.NullableInputListMapperImpl
import com.oborodulin.jwsuite.data_appsetting.local.db.entities.AppSettingEntity
import com.oborodulin.jwsuite.domain.model.AppSetting

class AppSettingEntityListToAppSettingListMapper(mapper: AppSettingEntityToAppSettingMapper) :
    NullableInputListMapperImpl<AppSettingEntity, AppSetting>(mapper)