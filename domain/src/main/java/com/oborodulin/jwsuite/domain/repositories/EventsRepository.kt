package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.model.Event
import com.oborodulin.jwsuite.domain.services.csv.CsvTransferableRepo
import com.oborodulin.jwsuite.domain.services.csv.model.EventCsv
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface EventsRepository : CsvTransferableRepo {
    fun getAll(): Flow<List<Event>>
    fun get(eventId: UUID): Flow<Event>
    fun save(event: Event): Flow<Event>
    fun delete(event: Event): Flow<Event>
    fun delete(eventId: UUID): Flow<UUID>
    fun delete(events: List<Event>): Flow<List<Event>>
    suspend fun deleteAll()

    // -------------------------------------- CSV Transfer --------------------------------------
    fun extractEvents(): Flow<List<EventCsv>>
    fun loadEvents(events: List<EventCsv>): Flow<Int>
}