package com.oborodulin.jwsuite.presentation_territory.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.oborodulin.home.common.ui.components.bar.BarListItemExposedDropdownMenuBoxComponent
import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryLocationsListItem
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.TerritoringInputEvent
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.TerritoringViewModel
import timber.log.Timber

private const val TAG = "Territoring.LocationDropdownMenuBoxComponent"

@Composable
fun LocationDropdownMenuBoxComponent(
    viewModel: TerritoringViewModel,
    inputWrapper: InputListItemWrapper<TerritoryLocationsListItem>,
    items: List<TerritoryLocationsListItem>
) {
    Timber.tag(TAG).d("LocationDropdownMenuBoxComponent(...) called")
    BarListItemExposedDropdownMenuBoxComponent(
        /*modifier = Modifier
            .focusRequester(focusRequesters[TerritoringFields.TERRITORY_LOCATION.name]!!.focusRequester)
            .onFocusChanged { focusState ->
                territoringViewModel.onTextFieldFocusChanged(
                    focusedField = TerritoringFields.TERRITORY_LOCATION,
                    isFocused = focusState.isFocused
                )
            },*/
        leadingImageVector = Icons.Outlined.Place,
        keyboardOptions = remember {
            KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
        },
        inputWrapper = inputWrapper,
        items = items,
        onValueChange = { viewModel.onTextFieldEntered(TerritoringInputEvent.Location(it)) },
        onImeKeyAction = viewModel::moveFocusImeAction,
    )
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewLocationDropdownMenuBoxComponent() {
    val ctx = LocalContext.current
    JWSuiteTheme {
        Surface {
            /*LocationDropdownMenuBoxComponent(
                territoringViewModel = TerritoringViewModelImpl.previewModel(ctx),
                inputWrapper = InputWrapper()
            )*/
        }
    }
}
