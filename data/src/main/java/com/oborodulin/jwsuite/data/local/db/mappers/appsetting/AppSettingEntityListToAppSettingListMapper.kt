package com.oborodulin.jwsuite.data.local.db.mappers.appsetting

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data.local.db.entities.AppSettingEntity
import com.oborodulin.jwsuite.domain.model.AppSetting

class AppSettingEntityListToAppSettingListMapper(mapper: AppSettingEntityToAppSettingMapper) :
    ListMapperImpl<AppSettingEntity, AppSetting>(mapper)