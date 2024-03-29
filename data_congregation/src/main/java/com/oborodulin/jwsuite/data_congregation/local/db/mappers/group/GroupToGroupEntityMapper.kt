package com.oborodulin.jwsuite.data_congregation.local.db.mappers.group

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_congregation.local.db.entities.GroupEntity
import com.oborodulin.jwsuite.domain.model.congregation.Group
import java.util.UUID

class GroupToGroupEntityMapper : Mapper<Group, GroupEntity> {
    override fun map(input: Group) = GroupEntity(
        groupId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
        groupNum = input.groupNum,
        gCongregationsId = input.congregation.id!!
    )
}