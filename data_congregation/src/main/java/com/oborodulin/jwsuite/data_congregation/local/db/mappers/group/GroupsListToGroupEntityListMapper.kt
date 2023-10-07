package com.oborodulin.jwsuite.data_congregation.local.db.mappers.group

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_congregation.local.db.entities.GroupEntity
import com.oborodulin.jwsuite.domain.model.congregation.Group

class GroupsListToGroupEntityListMapper(mapper: GroupToGroupEntityMapper) :
    ListMapperImpl<Group, GroupEntity>(mapper)