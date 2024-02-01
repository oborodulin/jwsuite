package com.oborodulin.jwsuite.data_congregation.local.csv.mappers.group

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_congregation.local.db.entities.GroupEntity
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.GroupCsv

class GroupCsvToGroupEntityMapper : Mapper<GroupCsv, GroupEntity> {
    override fun map(input: GroupCsv) = GroupEntity(
        groupId = input.groupId,
        groupNum = input.groupNum,
        gCongregationsId = input.gCongregationsId
    )
}