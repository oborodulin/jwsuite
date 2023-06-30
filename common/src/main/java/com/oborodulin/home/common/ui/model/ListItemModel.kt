package com.oborodulin.home.common.ui.model

import android.content.Context
import android.os.Parcelable
import com.oborodulin.home.common.R
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal
import java.util.UUID

@Parcelize
open class ListItemModel(
    val itemId: UUID? = null,
    val headline: String = "",
    val supportingText: String? = null,
    val value: BigDecimal? = null
) : Parcelable {
    companion object {
        fun defaultListItemModel(ctx: Context) = ListItemModel(
            itemId = UUID.randomUUID(),
            headline = ctx.resources.getString(R.string.preview_blank_title),
            supportingText = ctx.resources.getString(R.string.preview_blank_descr),
            value = BigDecimal("123456.54")
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ListItemModel
        if (itemId != other.itemId) return false

        return true
    }

    override fun hashCode(): Int {
        return itemId.hashCode()
    }
}