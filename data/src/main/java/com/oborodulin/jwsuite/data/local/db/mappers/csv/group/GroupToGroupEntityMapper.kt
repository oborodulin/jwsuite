package com.oborodulin.jwsuite.data.local.db.mappers.csv.group

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_congregation.local.db.entities.GroupEntity
import java.util.UUID

class GroupToGroupEntityMapper : Mapper<com.oborodulin.jwsuite.domain.services.csv.model.congregation.GroupCsv, GroupEntity> {
    override fun map(input: com.oborodulin.jwsuite.domain.services.csv.model.congregation.GroupCsv) = GroupEntity(
        groupId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
        groupNum = input.groupNum,
        gCongregationsId = input.congregation.id!!
    )
}