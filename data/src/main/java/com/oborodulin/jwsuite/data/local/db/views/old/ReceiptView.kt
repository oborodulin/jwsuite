package com.oborodulin.jwsuite.data.local.db.views.old

import androidx.room.DatabaseView
import androidx.room.Embedded
import androidx.room.TypeConverters
import com.oborodulin.jwsuite.data.local.db.converters.DateTypeConverter
import com.oborodulin.jwsuite.data.local.db.entities.old.ReceiptEntity
import com.oborodulin.jwsuite.data.local.db.entities.old.ReceiptLineEntity
import com.oborodulin.jwsuite.data.util.Constants
import java.time.OffsetDateTime

@DatabaseView(
    viewName = ReceiptView.VIEW_NAME,
    value = """
SELECT r.*, rl.*, 
    strftime(${Constants.DB_FRACT_SEC_TIME}, printf('%d-%02d-01T00:00:00.000', r.receiptYear, r.receiptMonth)) AS receiptDate 
FROM ${ReceiptEntity.TABLE_NAME} r LEFT JOIN ${ReceiptLineEntity.TABLE_NAME} rl ON rl.receiptsId = r.receiptId
"""
)
class ReceiptView(
    @Embedded
    val receipt: ReceiptEntity,
    @Embedded
    val line: ReceiptLineEntity? = null,
    @field:TypeConverters(DateTypeConverter::class)
    val receiptDate: OffsetDateTime?
    ) {
    companion object {
        const val VIEW_NAME = "receipts_view"
    }
}