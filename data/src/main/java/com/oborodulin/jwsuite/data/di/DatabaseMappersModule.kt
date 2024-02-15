package com.oborodulin.jwsuite.data.di

import android.content.Context
import com.oborodulin.jwsuite.data.local.csv.mappers.EventCsvListToEventEntityListMapper
import com.oborodulin.jwsuite.data.local.csv.mappers.EventCsvMappers
import com.oborodulin.jwsuite.data.local.csv.mappers.EventCsvToEventEntityMapper
import com.oborodulin.jwsuite.data.local.csv.mappers.EventEntityListToEventCsvListMapper
import com.oborodulin.jwsuite.data.local.csv.mappers.EventEntityToEventCsvMapper
import com.oborodulin.jwsuite.data.local.db.mappers.EventEntityListToEventsListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.EventEntityToEventMapper
import com.oborodulin.jwsuite.data.local.db.mappers.EventMappers
import com.oborodulin.jwsuite.data.local.db.mappers.EventToEventEntityMapper
import com.oborodulin.jwsuite.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseMappersModule {
    // Events:
    @Singleton
    @Provides
    fun provideEventEntityToEventMapper(@ApplicationContext ctx: Context): EventEntityToEventMapper =
        EventEntityToEventMapper(ctx = ctx)

    @Singleton
    @Provides
    fun provideEventToEventEntityMapper(): EventToEventEntityMapper = EventToEventEntityMapper()

    @Singleton
    @Provides
    fun provideEventEntityListToEventsListMapper(mapper: EventEntityToEventMapper): EventEntityListToEventsListMapper =
        EventEntityListToEventsListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideEventMappers(
        eventEntityListToEventListMapper: EventEntityListToEventsListMapper,
        eventEntityToEventMapper: EventEntityToEventMapper,
        eventToEventEntityMapper: EventToEventEntityMapper
    ): EventMappers = EventMappers(
        eventEntityListToEventListMapper,
        eventEntityToEventMapper,
        eventToEventEntityMapper
    )

    // CSV:
    // Events:
    @Singleton
    @Provides
    fun provideEventEntityToEventCsvMapper(): EventEntityToEventCsvMapper =
        EventEntityToEventCsvMapper()

    @Singleton
    @Provides
    fun provideEventEntityListToEventCsvListMapper(mapper: EventEntityToEventCsvMapper): EventEntityListToEventCsvListMapper =
        EventEntityListToEventCsvListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideEventCsvToEventEntityMapper(): EventCsvToEventEntityMapper =
        EventCsvToEventEntityMapper()

    @Singleton
    @Provides
    fun provideEventCsvListToEventEntityListMapper(mapper: EventCsvToEventEntityMapper): EventCsvListToEventEntityListMapper =
        EventCsvListToEventEntityListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideEventCsvMappers(
        eventEntityListToEventCsvListMapper: EventEntityListToEventCsvListMapper,
        eventCsvListToEventEntityListMapper: EventCsvListToEventEntityListMapper
    ): EventCsvMappers = EventCsvMappers(
        eventEntityListToEventCsvListMapper,
        eventCsvListToEventEntityListMapper
    )
}