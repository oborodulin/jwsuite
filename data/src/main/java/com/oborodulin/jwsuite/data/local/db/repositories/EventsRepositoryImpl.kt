package com.oborodulin.jwsuite.data.local.db.repositories

import com.oborodulin.jwsuite.data.local.csv.mappers.EventCsvMappers
import com.oborodulin.jwsuite.data.local.db.entities.EventEntity
import com.oborodulin.jwsuite.data.local.db.mappers.EventMappers
import com.oborodulin.jwsuite.data.local.db.repositories.sources.LocalEventDataSource
import com.oborodulin.jwsuite.domain.model.Event
import com.oborodulin.jwsuite.domain.repositories.EventsRepository
import com.oborodulin.jwsuite.domain.services.csv.CsvExtract
import com.oborodulin.jwsuite.domain.services.csv.CsvLoad
import com.oborodulin.jwsuite.domain.services.csv.model.EventCsv
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class EventsRepositoryImpl @Inject constructor(
    private val localEventDataSource: LocalEventDataSource,
    private val domainMappers: EventMappers,
    private val csvMappers: EventCsvMappers
) : EventsRepository {
    override fun getAll() = localEventDataSource.getEvents()
        .map(domainMappers.eventEntityListToEventsListMapper::map)

    override fun get(eventId: UUID) = localEventDataSource.getEvent(eventId)
        .map(domainMappers.eventEntityToEventMapper::map)

    override fun save(event: Event) = flow {
        if (event.id == null) {
            localEventDataSource.insertEvent(domainMappers.eventToEventEntityMapper.map(event))
        } else {
            localEventDataSource.updateEvent(domainMappers.eventToEventEntityMapper.map(event))
        }
        emit(event)
    }

    override fun delete(event: Event) = flow {
        localEventDataSource.deleteEvent(domainMappers.eventToEventEntityMapper.map(event))
        this.emit(event)
    }

    override fun delete(eventId: UUID) = flow {
        localEventDataSource.deleteEventById(eventId)
        this.emit(eventId)
    }

    override fun delete(events: List<Event>) = flow {
        localEventDataSource.deleteEvents(events.map {
            domainMappers.eventToEventEntityMapper.map(it)
        })
        this.emit(events)
    }

    override suspend fun deleteAll() = localEventDataSource.deleteEvents()

    // -------------------------------------- CSV Transfer --------------------------------------
    @CsvExtract(fileNamePrefix = EventEntity.TABLE_NAME)
    override fun extractEvents() = localEventDataSource.getEventEntities()
        .map(csvMappers.eventEntityListToEventCsvListMapper::map)

    @CsvLoad<EventCsv>(
        fileNamePrefix = EventEntity.TABLE_NAME,
        contentType = EventCsv::class
    )
    override fun loadEvents(events: List<EventCsv>) = flow {
        localEventDataSource.loadEventEntities(
            csvMappers.eventCsvListToEventEntityListMapper.map(events)
        )
        emit(events.size)
    }
}