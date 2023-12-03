package com.oborodulin.jwsuite.presentation_territory.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oborodulin.home.common.ui.components.fab.ExtFabComponent
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_territory.R
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.TerritoringUiAction
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.TerritoringViewModel
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.TerritoringViewModelImpl
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid.TerritoriesGridViewModel
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid.TerritoriesGridViewModelImpl
import timber.log.Timber

private const val TAG = "Territoring.HandOutFabComponent"

@Composable
fun HandOutFabComponent(
    enabled: Boolean,
    territoriesGridViewModel: TerritoriesGridViewModel,
    territoringViewModel: TerritoringViewModel
) {
    Timber.tag(TAG).d("HandOutFabComponent(...) called")
    ExtFabComponent(
        modifier = Modifier.padding(bottom = 48.dp, end = 4.dp),
        enabled = enabled,
        painterResId = com.oborodulin.jwsuite.presentation.R.drawable.ic_hand_map_24,
        textResId = R.string.fab_territory_hand_out_text
    ) {
        Timber.tag(TAG).d("HandOutFabComponent: FAB onClick...")
        // checks all errors
        territoriesGridViewModel.onContinueClick {
            // if success, then go to Hand Out Confirmation
            Timber.tag(TAG)
                .d("HandOutFabComponent: submitAction TerritoringUiAction.HandOutTerritoriesConfirmation")
            territoringViewModel.submitAction(TerritoringUiAction.HandOutTerritoriesConfirmation)
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewHandOutFabComponent() {
    val ctx = LocalContext.current
    JWSuiteTheme {
        Surface {
            HandOutFabComponent(
                enabled = true,
                territoriesGridViewModel = TerritoriesGridViewModelImpl.previewModel(ctx),
                territoringViewModel = TerritoringViewModelImpl.previewModel(ctx)
            )
        }
    }
}
