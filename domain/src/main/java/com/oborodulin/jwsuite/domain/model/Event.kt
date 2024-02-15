package com.oborodulin.jwsuite.domain.model

import android.content.Context
import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.jwsuite.domain.R
import com.oborodulin.jwsuite.domain.types.EventType
import java.time.OffsetDateTime

data class Event(
    val ctx: Context? = null,
    val eventType: EventType,
    val eventTime: OffsetDateTime = OffsetDateTime.now(),
    val isManual: Boolean,
    val isSuccess: Boolean
) : DomainModel() {
    val eventName =
        when (eventType) {
            EventType.BACKUP -> ctx?.resources?.getString(R.string.backup_event_name).orEmpty()

            EventType.RESTORE -> ctx?.resources?.getString(R.string.restore_event_name)
                .orEmpty()

            EventType.SYNC -> ctx?.resources?.getString(R.string.sync_data_event_name)
                .orEmpty()

            EventType.SEND -> ctx?.resources?.getString(R.string.send_data_event_name)
                .orEmpty()

            EventType.RECEIVE -> ctx?.resources?.getString(R.string.receive_data_event_name)
                .orEmpty()
        }
}