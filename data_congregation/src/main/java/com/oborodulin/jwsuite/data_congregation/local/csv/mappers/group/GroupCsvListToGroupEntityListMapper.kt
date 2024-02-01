package com.oborodulin.jwsuite.data_congregation.local.csv.mappers.group

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_congregation.local.db.entities.GroupEntity
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.GroupCsv

class GroupCsvListToGroupEntityListMapper(mapper: GroupCsvToGroupEntityMapper) :
    ListMapperImpl<GroupCsv, GroupEntity>(mapper)