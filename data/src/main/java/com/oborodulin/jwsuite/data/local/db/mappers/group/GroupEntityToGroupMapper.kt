package com.oborodulin.jwsuite.data.local.db.mappers.group

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.entities.GroupEntity
import com.oborodulin.jwsuite.domain.model.Group

class GroupEntityToGroupMapper : Mapper<GroupEntity, Group> {
    override fun map(input: GroupEntity): Group {
        val group = Group(congregationId = input.congregationsId, groupNum = input.groupNum)
        group.id = input.groupId
        return group
    }
}