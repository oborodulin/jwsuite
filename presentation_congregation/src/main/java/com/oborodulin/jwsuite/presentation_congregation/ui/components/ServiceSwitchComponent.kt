package com.oborodulin.jwsuite.presentation_congregation.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.oborodulin.home.common.ui.components.field.SwitchComponent
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_congregation.R
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.CongregatingInputEvent
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.CongregatingViewModel
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.CongregatingViewModelImpl
import timber.log.Timber

private const val TAG = "Congregating.ServiceSwitchComponent"

@Composable
fun ServiceSwitchComponent(viewModel: CongregatingViewModel, inputWrapper: InputWrapper) {
    Timber.tag(TAG).d("ServiceSwitchComponent(...) called")
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        SwitchComponent(
            //componentModifier = Modifier.padding(end = 36.dp)
            /*.focusRequester(focusRequesters[TerritoringFields.TERRITORING_IS_PRIVATE_SECTOR.name]!!.focusRequester)
        .onFocusChanged { focusState ->
            territoringViewModel.onTextFieldFocusChanged(
                focusedField = TerritoringFields.TERRITORING_IS_PRIVATE_SECTOR,
                isFocused = focusState.isFocused
            )
        }*/
            horizontalArrangement = Arrangement.Start,
            labelResId = R.string.congregating_is_service_hint,
            lableStyle = MaterialTheme.typography.bodyMedium,
            inputWrapper = inputWrapper,
            onCheckedChange = { viewModel.onTextFieldEntered(CongregatingInputEvent.IsService(it)) }
        )
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewServiceSwitchComponent() {
    val ctx = LocalContext.current
    JWSuiteTheme {
        Surface {
            ServiceSwitchComponent(
                viewModel = CongregatingViewModelImpl.previewModel(ctx),
                inputWrapper = InputWrapper()
            )
        }
    }
}
