package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.model.congregation.Group
import com.oborodulin.jwsuite.domain.services.csv.CsvTransferableRepo
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.GroupCsv
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface GroupsRepository : CsvTransferableRepo {
    fun getAllByCongregation(congregationId: UUID): Flow<List<Group>>
    fun getAllByFavoriteCongregation(): Flow<List<Group>>
    fun getNextGroupNum(congregationId: UUID): Flow<Int>
    fun get(groupId: UUID): Flow<Group>
    fun save(group: Group): Flow<Group>
    fun delete(group: Group): Flow<Group>
    fun delete(groupId: UUID): Flow<UUID>
    suspend fun deleteAll()

    // -------------------------------------- CSV Transfer --------------------------------------
    fun extractGroups(username: String? = null, byFavorite: Boolean = false): Flow<List<GroupCsv>>
    fun loadGroups(groups: List<GroupCsv>): Flow<Int>
}