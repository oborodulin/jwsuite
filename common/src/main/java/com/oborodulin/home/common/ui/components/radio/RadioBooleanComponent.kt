package com.oborodulin.home.common.ui.components.radio

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.List
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.oborodulin.home.common.R
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.theme.HomeComposableTheme

private const val TAG = "Common.ui.RadioBooleanComponent"

@Composable
fun RadioBooleanComponent(
    modifier: Modifier = Modifier,
    imageVector: ImageVector? = null,
    @DrawableRes painterResId: Int? = null,
    @StringRes labelResId: Int? = null,
    @StringRes contentDescriptionResId: Int? = null,
    inputWrapper: InputWrapper,
    onValueChange: (Boolean?) -> Unit
) {
    val radioBooleanOptions = mutableMapOf<String, RadioBooleanType>()
    val radioBooleanTypes =
        LocalContext.current.resources.getStringArray(R.array.radio_boolean_types)
    for (type in RadioBooleanType.values()) radioBooleanOptions[radioBooleanTypes[type.ordinal]] =
        type

    var selectedItem by remember {
        mutableStateOf(
            if (!listOf("true", "false").contains(inputWrapper.value)) RadioBooleanType.UNDEF
            else when (inputWrapper.value.toBoolean()) {
                true -> RadioBooleanType.YES
                false -> RadioBooleanType.NO
            }
        )
    }
    RadioGroupComponent(
        modifier = modifier,
        imageVector = imageVector,
        painterResId = painterResId,
        labelResId = labelResId,
        contentDescriptionResId = contentDescriptionResId,
        radioOptions = radioBooleanOptions,
        selectedItem = selectedItem,
        onClick = {
            selectedItem = it
            onValueChange(
                when (it) {
                    RadioBooleanType.YES -> true
                    RadioBooleanType.NO -> false
                    RadioBooleanType.UNDEF -> null
                }
            )
        }
    )
}

@Preview(name = "Night Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewRadioBooleanComponent() {
    var inputWrapper by remember { mutableStateOf(InputWrapper("true")) }
    HomeComposableTheme {
        Surface {
            RadioBooleanComponent(
                imageVector = Icons.Rounded.List,
                labelResId = R.string.preview_blank_radio_boolean_text,
                inputWrapper = inputWrapper,
                onValueChange = { inputWrapper = InputWrapper(it.toString()) }
            )
        }
    }
}