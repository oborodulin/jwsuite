package com.oborodulin.jwsuite.presentation.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.oborodulin.jwsuite.domain.util.RoadType
import com.oborodulin.jwsuite.presentation.R

@Composable
fun ServiceIcon(roadType: RoadType?) =
    Image(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp)),
        painter = painterResource(serviceIconId(roadType)),
        contentDescription = ""
    )

fun serviceIconId(roadType: RoadType?) =
    when (roadType) {
        RoadType.RENT -> R.drawable.ic_cottage_36
        RoadType.ELECTRICITY -> R.drawable.ic_electric_bolt_36
        RoadType.GAS -> R.drawable.ic_fireplace_36
        RoadType.COLD_WATER -> R.drawable.outline_water_drop_black_36
        RoadType.WASTE -> R.drawable.ic_sewage_36
        RoadType.HEATING -> R.drawable.ic_radiator_36
        RoadType.HOT_WATER -> R.drawable.outline_opacity_black_36
        RoadType.GARBAGE -> R.drawable.ic_delete_forever_36
        RoadType.PHONE -> R.drawable.outline_phone_black_36
        RoadType.DOORPHONE -> R.drawable.ic_doorbell_36
        RoadType.USGO -> R.drawable.outline_security_black_36
        RoadType.INTERNET -> R.drawable.outline_network_check_black_36
        else -> -1
    }
