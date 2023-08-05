package com.oborodulin.home.common.ui.model

import android.content.Context
import android.os.Parcelable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.oborodulin.home.common.R
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal
import java.util.UUID

@Parcelize
open class ListItemModel(
    val itemId: UUID? = null,
    val headline: String = "",
    val supportingText: String? = null,
    val value: BigDecimal? = null,
    var initChecked: Boolean = false,
    var initSelected: Boolean = false
) : Parcelable, Searchable {
    var checked by mutableStateOf(initChecked)
    var selected by mutableStateOf(initSelected)

    companion object {
        fun defaultListItemModel(ctx: Context) = ListItemModel(
            itemId = UUID.randomUUID(),
            headline = ctx.resources.getString(R.string.preview_blank_title),
            supportingText = ctx.resources.getString(R.string.preview_blank_descr),
            value = BigDecimal("123456.54")
        )
    }

    // https://www.youtube.com/watch?v=CfL6Dl2_dAE
    override fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            "$headline$supportingText",
            "$headline $supportingText"
        )
        return matchingCombinations.any { it.contains(query, ignoreCase = true) }
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