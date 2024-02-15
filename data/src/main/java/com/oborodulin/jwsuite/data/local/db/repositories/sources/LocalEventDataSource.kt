package com.oborodulin.jwsuite.data.local.db.repositories.sources

import com.oborodulin.jwsuite.data.local.db.entities.EventEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface LocalEventDataSource {
    fun getEvents(): Flow<List<EventEntity>>
    fun getEvent(eventId: UUID): Flow<EventEntity>
    suspend fun insertEvent(event: EventEntity)
    suspend fun updateEvent(event: EventEntity)
    suspend fun deleteEvent(event: EventEntity)
    suspend fun deleteEventById(eventId: UUID)
    suspend fun deleteEvents(events: List<EventEntity>)
    suspend fun deleteEvents()

    // -------------------------------------- CSV Transfer --------------------------------------
    fun getEventEntities(): Flow<List<EventEntity>>
    suspend fun loadEventEntities(events: List<EventEntity>)
}