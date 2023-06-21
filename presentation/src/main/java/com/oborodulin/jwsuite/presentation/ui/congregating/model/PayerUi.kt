package com.oborodulin.home.accounting.ui.model

import com.oborodulin.home.data.util.Constants
import java.math.BigDecimal
import java.util.*

data class PayerUi(
    val id: UUID? = null,
    val ercCode: String = "",
    val fullName: String = "",
    val address: String = "",
    val totalArea: BigDecimal? = null,
    val livingSpace: BigDecimal? = null,
    val heatedVolume: BigDecimal? = null,
    val paymentDay: Int = Constants.DEF_PAYMENT_DAY,
    val personsNum: Int = Constants.DEF_PERSON_NUM,
    val isAlignByPaymentDay: Boolean = false,
    val isFavorite: Boolean = false
)