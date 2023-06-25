package com.oborodulin.jwsuite.presentation.ui.modules.dashboarding.model

import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.data.util.Constants.DEF_PAYMENT_DAY
import com.oborodulin.home.data.util.Constants.DEF_PERSON_NUM
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.UUID

data class PayerListItem(
    val id: UUID,
    val fullName: String = "",
    val address: String = "",
    val totalArea: BigDecimal? = null,
    val livingSpace: BigDecimal? = null,
    val paymentDay: Int = DEF_PAYMENT_DAY,
    val personsNum: Int = DEF_PERSON_NUM,
    val isAlignByPaymentDay: Boolean = false,
    val isFavorite: Boolean = false,
    val fromPaymentDate: OffsetDateTime? = null,
    val toPaymentDate: OffsetDateTime? = null,
    val totalDebt: BigDecimal? = null
) : ListItemModel(
    itemId = id,
    headline = fullName,
    supportingText = address,
    value = totalDebt
)
