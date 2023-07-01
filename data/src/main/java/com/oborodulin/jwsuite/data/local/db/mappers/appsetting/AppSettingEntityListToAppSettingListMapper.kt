package com.oborodulin.jwsuite.data.local.db.mappers.appsetting

import com.oborodulin.home.common.mapping.NullableInputListMapperImpl
import com.oborodulin.jwsuite.data.local.db.entities.AppSettingEntity
import com.oborodulin.jwsuite.domain.model.AppSetting

class AppSettingEntityListToAppSettingListMapper(mapper: AppSettingEntityToAppSettingMapper) :
    NullableInputListMapperImpl<AppSettingEntity, AppSetting>(mapper)