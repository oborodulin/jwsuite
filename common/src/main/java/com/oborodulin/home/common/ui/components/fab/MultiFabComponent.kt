package com.oborodulin.home.common.ui.components.fab

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oborodulin.home.common.R
import com.oborodulin.home.common.ui.components.IconComponent
import com.oborodulin.home.common.ui.theme.HomeComposableTheme

// https://www.youtube.com/watch?v=9SHNfpnzdEU

@Composable
fun MultiFabComponent(
    modifier: Modifier = Modifier,
    enabled: Boolean = false,
    multiFloatingState: MultiFloatingState,
    onMultiFabStateChange: (MultiFloatingState) -> Unit,
    collapsedImageVector: ImageVector? = null,
    @DrawableRes collapsedPainterResId: Int? = null,
    @StringRes collapsedTextResId: Int? = null,
    @StringRes collapsedCtxDescResId: Int? = null,
    expandedImageVector: ImageVector? = null,
    @DrawableRes expandedPainterResId: Int? = null,
    @StringRes expandedTextResId: Int? = null,
    @StringRes expandedCtxDescResId: Int? = null,
    items: List<MinFabItem> = emptyList()
) {
    val transition = updateTransition(targetState = multiFloatingState, label = "transition")
    val rotate by transition.animateFloat(label = "rotate") {
        if (it == MultiFloatingState.Expanded) 315f else 0f
    }
    val fabScale by transition.animateFloat(label = "FabScale") {
        if (it == MultiFloatingState.Expanded) 36f else 0f
    }
    val alpha by transition.animateFloat(
        label = "alpha",
        transitionSpec = { tween(durationMillis = 50) }) {
        if (it == MultiFloatingState.Expanded) 1f else 0f
    }
    val textShadow by transition.animateDp(
        label = "textShadow",
        transitionSpec = { tween(durationMillis = 50) }) {
        if (it == MultiFloatingState.Expanded) 2.dp else 0.dp
    }

    // https://www.appsloveworld.com/kotlin/100/7/jetpack-compose-how-to-disable-floatingaction-button
    val collapsedIcon = @Composable {
        IconComponent(
            modifier = Modifier.padding(end = 4.dp),
            imageVector = collapsedImageVector,
            painterResId = collapsedPainterResId,
            contentDescriptionResId = collapsedCtxDescResId
        )
    }
    val expandedIcon = @Composable {
        IconComponent(
            modifier = Modifier.padding(end = 4.dp),
            imageVector = expandedImageVector,
            painterResId = expandedPainterResId,
            contentDescriptionResId = expandedCtxDescResId
        )
    }
    // https://stackoverflow.com/questions/69780839/how-do-you-animate-a-swap-of-image
    val iconCrossfade = @Composable {
        Crossfade(
            targetState = multiFloatingState,
            animationSpec = tween(1000),
            label = "MultiFabCrossfade"
        ) { targetState ->
            if (targetState == MultiFloatingState.Collapsed) collapsedIcon.invoke() else expandedIcon.invoke()
        }
    }
    val textResId = when (multiFloatingState) {
        MultiFloatingState.Collapsed -> collapsedTextResId
        MultiFloatingState.Expanded -> expandedTextResId
    }
    CompositionLocalProvider(
        LocalRippleTheme provides if (enabled) LocalRippleTheme.current else NoRippleTheme
    ) {
        // https://stackoverflow.com/questions/72574071/unable-to-change-text-emphasis-using-localcontentalpha-in-material-design-3
        // https://stackoverflow.com/questions/70831743/customize-contentalpha-in-jetpack-compose-like-a-theme
        Column(
            horizontalAlignment = Alignment.End
        ) {
            if (multiFloatingState == MultiFloatingState.Expanded) {
                items.forEach {
                    MinFabComponent(
                        item = it,
                        alpha = alpha,
                        textShadow = textShadow,
                        fabScale = fabScale
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                }
            }
            ExtendedFloatingActionButton(
                modifier = Modifier.then(modifier),
                containerColor = if (enabled) MaterialTheme.colorScheme.primary else Gray,
                icon = {
                    if (enabled)
                    // High emphasis
                        CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onPrimary) {
                            iconCrossfade()
                        }
                    else
                    // Disabled emphasis
                        CompositionLocalProvider(
                            LocalContentColor provides MaterialTheme.colorScheme.onSurface.copy(
                                alpha = 0.38f
                            )
                        ) { iconCrossfade() }
                },
                text = { textResId?.let { Text(stringResource(it)) } },
                onClick = { if (enabled) onMultiFabStateChange(multiFloatingState) },
                elevation = FloatingActionButtonDefaults.elevation(8.dp)
            )
        }
    }
}

@Preview(name = "Night Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewMultiFabComponentEnabled() {
    var multiFloatingState by remember { mutableStateOf(MultiFloatingState.Expanded) }
    HomeComposableTheme {
        Surface {
            MultiFabComponent(
                enabled = true,
                multiFloatingState = multiFloatingState,
                onMultiFabStateChange = { state: MultiFloatingState ->
                    // if (transition.currentState == MultiFloatingState.Expanded) {
                    multiFloatingState = if (state == MultiFloatingState.Expanded) {
                        MultiFloatingState.Collapsed
                    } else {
                        MultiFloatingState.Expanded
                    }
                },
                collapsedImageVector = Icons.Default.Add,
                collapsedTextResId = R.string.preview_blank_fab_text,
                expandedImageVector = Icons.Default.Close,
                expandedTextResId = R.string.preview_blank_fab_text,
                items = listOf(
                    MinFabItem(
                        labelResId = R.string.preview_blank_fab_text,
                        imageVector = Icons.Default.Email
                    ),
                    MinFabItem(
                        labelResId = R.string.preview_blank_fab_text,
                        imageVector = Icons.Default.Edit
                    ),
                    MinFabItem(
                        labelResId = R.string.preview_blank_fab_text,
                        imageVector = Icons.Default.Delete
                    )
                )
            )
        }
    }
}

@Preview(name = "Night Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewMultiFabComponentDisabled() {
    var multiFloatingState by remember { mutableStateOf(MultiFloatingState.Expanded) }
    HomeComposableTheme {
        Surface {
            MultiFabComponent(
                enabled = false,
                multiFloatingState = multiFloatingState,
                onMultiFabStateChange = { state: MultiFloatingState ->
                    // if (transition.currentState == MultiFloatingState.Expanded) {
                    multiFloatingState = if (state == MultiFloatingState.Expanded) {
                        MultiFloatingState.Collapsed
                    } else {
                        MultiFloatingState.Expanded
                    }
                },
                collapsedImageVector = Icons.Default.Add,
                collapsedTextResId = R.string.preview_blank_fab_text,
                expandedImageVector = Icons.Default.Close,
                expandedTextResId = R.string.preview_blank_fab_text,
                items = listOf(
                    MinFabItem(
                        labelResId = R.string.preview_blank_fab_text,
                        imageVector = Icons.Default.Email
                    ),
                    MinFabItem(
                        labelResId = R.string.preview_blank_fab_text,
                        imageVector = Icons.Default.Edit
                    ),
                    MinFabItem(
                        labelResId = R.string.preview_blank_fab_text,
                        imageVector = Icons.Default.Delete
                    )
                )
            )
        }
    }
}
