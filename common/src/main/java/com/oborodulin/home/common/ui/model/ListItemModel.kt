package com.oborodulin.home.common.ui.model

import android.content.Context
import android.os.Parcelable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.oborodulin.home.common.R
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal
import java.util.UUID

// https://medium.com/@saqib.tech/compose-single-selection-with-data-binding-37a12cf51bc8
@Parcelize
open class ListItemModel(
    val itemId: UUID? = null,
    val headline: String = "",
    val supportingText: String? = null,
    val value: BigDecimal? = null,
    private var initChecked: Boolean = false,
    private var initSelected: Boolean = false
) : Parcelable, Searchable, Editable {
    @IgnoredOnParcel
    var checked by mutableStateOf(initChecked)

    @IgnoredOnParcel
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
            "$headline${supportingText.orEmpty()}",
            "$headline ${supportingText.orEmpty()}".trim()
        )
        return matchingCombinations.any { it.contains(query, ignoreCase = true) }
    }

    override fun isEditable() = true

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ListItemModel
        return itemId == other.itemId
    }

    override fun hashCode(): Int {
        return itemId.hashCode()
    }

    override fun toString(): String {
        val str = StringBuffer()
        str.append("ListItemModel(headline = ").append(headline)
            .append("; supportingText = ").append(supportingText)
            .append("; [checked = ").append(checked)
            .append("; selected = ").append(selected)
            .append("']; itemId = ").append(itemId).append(")")
        return str.toString()
    }
}

fun ListItemModel?.isEmptyOrNull() = this == null || this.itemId == null || this.headline.isBlank()