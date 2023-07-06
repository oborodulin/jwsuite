package com.oborodulin.jwsuite.data.local.db.mappers.group

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data.local.db.views.GroupView
import com.oborodulin.jwsuite.domain.model.Group

class GroupViewListToGroupsListMapper(mapper: GroupViewToGroupMapper) :
    ListMapperImpl<GroupView, Group>(mapper)