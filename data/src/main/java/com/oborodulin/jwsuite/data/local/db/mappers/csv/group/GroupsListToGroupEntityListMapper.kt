package com.oborodulin.jwsuite.data.local.db.mappers.csv.group

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_congregation.local.db.entities.GroupEntity

class GroupsListToGroupEntityListMapper(mapper: GroupToGroupEntityMapper) :
    ListMapperImpl<com.oborodulin.jwsuite.domain.services.csv.model.congregation.GroupCsv, GroupEntity>(mapper)