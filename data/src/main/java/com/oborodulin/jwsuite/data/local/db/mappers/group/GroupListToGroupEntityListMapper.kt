package com.oborodulin.jwsuite.data.local.db.mappers.group

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data.local.db.entities.GroupEntity
import com.oborodulin.jwsuite.domain.model.Group

class GroupListToGroupEntityListMapper(mapper: GroupToGroupEntityMapper) :
    ListMapperImpl<Group, GroupEntity>(mapper)