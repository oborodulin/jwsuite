package com.oborodulin.home.common.ui.components.text

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.oborodulin.home.common.ui.components.IconComponent

@Composable
fun ErrorTextComponent(
    imageVector: ImageVector? = null,
    @DrawableRes painterResId: Int? = null,
    @StringRes contentDescResId: Int? = null,
    @StringRes textResId: Int
) {
    val imgVector = imageVector ?: when (painterResId) {
        null -> Icons.Outlined.Warning
        else -> null
    }
    Row(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(
                1.dp, MaterialTheme.colorScheme.error, shape = RoundedCornerShape(16.dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconComponent(
            modifier = Modifier.padding(8.dp),
            imageVector = imgVector,
            painterResId = painterResId,
            contentDescriptionResId = contentDescResId,
            tint = MaterialTheme.colorScheme.error
        )
        Text(
            text = stringResource(textResId),
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center
        )
    }
}