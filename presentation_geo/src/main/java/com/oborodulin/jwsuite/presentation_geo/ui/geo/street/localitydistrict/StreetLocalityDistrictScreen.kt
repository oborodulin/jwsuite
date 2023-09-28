package com.oborodulin.jwsuite.presentation_geo.ui.geo.street.localitydistrict

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.components.buttons.SaveButtonComponent
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput
import com.oborodulin.jwsuite.presentation.ui.AppState
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import timber.log.Timber

private const val TAG = "Territoring.StreetLocalityDistrictScreen"

@Composable
fun StreetLocalityDistrictScreen(
    appState: AppState,
    viewModel: StreetLocalityDistrictViewModelImpl = hiltViewModel(),
    streetLocalityDistrictInput: NavigationInput.StreetLocalityDistrictInput? = null
) {
    Timber.tag(TAG)
        .d(
            "StreetLocalityDistrictScreen(...) called: streetLocalityDistrictInput = %s",
            streetLocalityDistrictInput
        )
    val saveButtonOnClick = {
        // checks all errors
        viewModel.onContinueClick {
            Timber.tag(TAG).d("StreetLocalityDistrictScreen(...): Save Button onClick...")
            // if success, save then commonNavigateUp
            viewModel.handleActionJob(
                { viewModel.submitAction(StreetLocalityDistrictUiAction.Save) },
                { appState.commonNavigateUp() })
        }
    }
    LaunchedEffect(streetLocalityDistrictInput?.streetId) {
        Timber.tag(TAG)
            .d("StreetLocalityDistrictScreen: LaunchedEffect() BEFORE collect ui state flow")
        streetLocalityDistrictInput?.let {
            viewModel.submitAction(StreetLocalityDistrictUiAction.Load(it.streetId))
        }
    }
    viewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        viewModel.dialogTitleResId.collectAsStateWithLifecycle().value?.let {
            appState.actionBarSubtitle.value = stringResource(it)
        }
        val areInputsValid by viewModel.areInputsValid.collectAsStateWithLifecycle()
        JWSuiteTheme { //(darkTheme = true)
            ScaffoldComponent(
                appState = appState,
                topBarNavigationIcon = {
                    IconButton(onClick = { appState.commonNavigateUp() }) {
                        Icon(Icons.Outlined.ArrowBack, null)
                    }
                },
                topBarActions = {
                    IconButton(enabled = areInputsValid, onClick = saveButtonOnClick) {
                        Icon(Icons.Outlined.Done, null)
                    }
                }
            ) { paddingValues ->
                CommonScreen(paddingValues = paddingValues, state = state) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        StreetLocalityDistrictView(
                            streetLocalityDistrictsUiModel = it,
                            streetLocalityDistrictViewModel = viewModel
                        )
                        Spacer(Modifier.height(8.dp))
                        SaveButtonComponent(enabled = areInputsValid, onClick = saveButtonOnClick)
                    }
                }
            }
        }
    }
}