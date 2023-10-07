package com.oborodulin.jwsuite.data_congregation.local.db.mappers.group

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_congregation.local.db.views.GroupView
import com.oborodulin.jwsuite.domain.model.congregation.Group

class GroupViewListToGroupsListMapper(mapper: GroupViewToGroupMapper) :
    ListMapperImpl<GroupView, Group>(mapper)