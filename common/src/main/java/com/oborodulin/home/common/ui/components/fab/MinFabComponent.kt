package com.oborodulin.home.common.ui.components.fab

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oborodulin.home.common.R
import com.oborodulin.home.common.ui.theme.HomeComposableTheme

@Composable
fun MinFabComponent(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    item: MinFabItem,
    alpha: Float,
    textShadow: Dp,
    fabScale: Float
) {
    val buttonColor = MaterialTheme.colorScheme.secondary
    val shadow = Black.copy(.5f)

    // https://stackoverflow.com/questions/70122336/how-to-draw-imagevector-on-canvas-jetpack-compose
    val vectorPainter = item.imageVector?.let { rememberVectorPainter(it) }
    // https://stackoverflow.com/questions/66186917/jetpack-compose-draw-on-image-with-painter
    //val imageBitmap = item.painterResId?.let { ImageBitmap.imageResource(it) }
    // https://stackoverflow.com/questions/75932860/drawimage-with-vector-drawable-jetpack-compose-graphics
    val painter = item.painterResId?.let { painterResource(id = it) }
    Row(verticalAlignment = Alignment.CenterVertically) {
        item.labelResId?.let {
            Text(
                stringResource(id = it), fontSize = 12.sp, fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .alpha(
                        animateFloatAsState(
                            targetValue = alpha,
                            animationSpec = tween(50),
                            label = "textMinFabAlpha"
                        ).value
                    )
                    .shadow(textShadow)
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(start = 6.dp, end = 6.dp, top = 4.dp)
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        Canvas(
            modifier = Modifier
                .size(32.dp)
                .clickable(onClick = { item.onClick(item) })
        ) {
            drawCircle(
                color = shadow, radius = fabScale, center = Offset(
                    center.x + 2f,
                    center.y + 2f,
                )
            )
            drawCircle(color = buttonColor, radius = fabScale)
            if (painter != null) {
                translate(
                    left = center.x - (painter.intrinsicSize.width / 2f),
                    top = center.y - (painter.intrinsicSize.width / 2f)
                ) {
                    with(painter) {
                        draw(size = painter.intrinsicSize, alpha = alpha)
                    }
                }
                /*drawImage(
                    image = imageBitmap,
                    topLeft = Offset(
                        center.x - (imageBitmap.width / 2f),
                        center.y - (imageBitmap.width / 2f),
                    ),
                    alpha = alpha
                )*/
            } else {
                vectorPainter?.let {
                    translate(
                        left = center.x - (it.intrinsicSize.width / 2f),
                        top = center.y - (it.intrinsicSize.width / 2f)
                    ) {
                        with(it) {
                            draw(size = it.intrinsicSize, alpha = alpha)
                        }
                    }
                }
            }
        }
    }
}

@Preview(name = "Night Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewMinFabComponentEnabled() {
    HomeComposableTheme {
        Surface {
            MinFabComponent(
                enabled = true,
                item = MinFabItem(
                    labelResId = R.string.preview_blank_fab_text,
                    imageVector = Icons.Rounded.Add
                ),
                alpha = 1f,
                textShadow = 2.dp,
                fabScale = 36f
            )
        }
    }
}

@Preview(name = "Night Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewMinFabComponentDisabled() {
    HomeComposableTheme {
        Surface {
            MinFabComponent(
                enabled = false,
                item = MinFabItem(
                    labelResId = R.string.preview_blank_fab_text,
                    painterResId = R.drawable.outline_photo_24
                ),
                alpha = 1f,
                textShadow = 2.dp,
                fabScale = 36f
            )
        }
    }
}
