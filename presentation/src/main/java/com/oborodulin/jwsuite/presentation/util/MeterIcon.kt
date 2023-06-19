package com.oborodulin.jwsuite.presentation.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.oborodulin.jwsuite.domain.util.MemberType
import com.oborodulin.jwsuite.presentation.R

@Composable
fun MeterIcon(memberType: MemberType?) =
    Image(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp)),
        painter = painterResource(meterIconId(memberType)),
        contentDescription = ""
    )

fun meterIconId(memberType: MemberType?) =
    when (memberType) {
        MemberType.ELECTRICITY -> R.drawable.ic_electric_meter_36
        MemberType.GAS -> R.drawable.ic_gas_meter_36
        MemberType.COLD_WATER -> R.drawable.ic_water_meter_36
        MemberType.HEATING -> R.drawable.ic_thermometer_36
        MemberType.HOT_WATER -> R.drawable.ic_hot_water_meter_36
        else -> -1
    }
