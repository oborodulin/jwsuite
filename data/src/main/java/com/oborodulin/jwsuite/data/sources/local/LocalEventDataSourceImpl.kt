package com.oborodulin.jwsuite.data.sources.local

import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data.local.db.dao.EventDao
import com.oborodulin.jwsuite.data.local.db.entities.EventEntity
import com.oborodulin.jwsuite.data.local.db.repositories.sources.LocalEventDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

/**
 * Created by o.borodulin on 08.August.2022
 */
@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class LocalEventDataSourceImpl @Inject constructor(
    private val eventDao: EventDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : LocalEventDataSource {
    override fun getEvents() = eventDao.findDistinctAll()
    override fun getEvent(eventId: UUID) = eventDao.findDistinctById(eventId)

    override suspend fun insertEvent(event: EventEntity) = withContext(dispatcher) {
        eventDao.insert(event)
    }

    override suspend fun updateEvent(event: EventEntity) = withContext(dispatcher) {
        eventDao.update(event)
    }

    override suspend fun deleteEvent(event: EventEntity) = withContext(dispatcher) {
        eventDao.delete(event)
    }

    override suspend fun deleteEventById(eventId: UUID) = withContext(dispatcher) {
        eventDao.deleteById(eventId)
    }

    override suspend fun deleteEvents(events: List<EventEntity>) =
        withContext(dispatcher) {
            eventDao.delete(events)
        }

    override suspend fun deleteEvents() = withContext(dispatcher) {
        eventDao.deleteAll()
    }

    // -------------------------------------- CSV Transfer --------------------------------------
    override fun getEventEntities() = eventDao.findAllEntities()
    override suspend fun loadEventEntities(events: List<EventEntity>) =
        withContext(dispatcher) {
            eventDao.insert(events)
        }
}
