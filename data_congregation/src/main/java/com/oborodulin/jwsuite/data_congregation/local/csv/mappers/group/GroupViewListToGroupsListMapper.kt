package com.oborodulin.jwsuite.data_congregation.local.csv.mappers.group

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_congregation.local.db.views.GroupView

class GroupViewListToGroupsListMapper(mapper: GroupViewToGroupMapper) :
    ListMapperImpl<GroupView, com.oborodulin.jwsuite.domain.services.csv.model.congregation.GroupCsv>(mapper)