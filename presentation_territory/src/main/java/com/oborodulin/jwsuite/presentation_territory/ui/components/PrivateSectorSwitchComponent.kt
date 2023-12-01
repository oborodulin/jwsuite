package com.oborodulin.jwsuite.presentation_territory.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oborodulin.home.common.ui.components.field.SwitchComponent
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_territory.R
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.TerritoringInputEvent
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.TerritoringViewModel
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.TerritoringViewModelImpl
import timber.log.Timber

private const val TAG = "Territoring.PrivateSectorSwitchComponent"

@Composable
fun PrivateSectorSwitchComponent(viewModel: TerritoringViewModel, inputWrapper: InputWrapper) {
    Timber.tag(TAG).d("PrivateSectorSwitchComponent(...) called")
    SwitchComponent(
        componentModifier = Modifier.padding(end = 36.dp)
        /*.focusRequester(focusRequesters[TerritoringFields.TERRITORING_IS_PRIVATE_SECTOR.name]!!.focusRequester)
        .onFocusChanged { focusState ->
            territoringViewModel.onTextFieldFocusChanged(
                focusedField = TerritoringFields.TERRITORING_IS_PRIVATE_SECTOR,
                isFocused = focusState.isFocused
            )
        }*/,
        labelResId = R.string.territoring_is_private_sector_hint,
        inputWrapper = inputWrapper,
        onCheckedChange = {
            viewModel.onTextFieldEntered(TerritoringInputEvent.IsPrivateSector(it))
        }
    )
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewPrivateSectorSwitchComponent() {
    val ctx = LocalContext.current
    JWSuiteTheme {
        Surface {
            PrivateSectorSwitchComponent(
                viewModel = TerritoringViewModelImpl.previewModel(ctx),
                inputWrapper = InputWrapper()
            )
        }
    }
}
