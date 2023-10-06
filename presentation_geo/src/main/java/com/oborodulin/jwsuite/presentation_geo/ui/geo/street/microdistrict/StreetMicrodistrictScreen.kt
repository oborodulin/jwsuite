package com.oborodulin.jwsuite.presentation_geo.ui.geo.street.microdistrict

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

private const val TAG = "Territoring.StreetMicrodistrictScreen"

@Composable
fun StreetMicrodistrictScreen(
    appState: AppState,
    viewModel: StreetMicrodistrictViewModelImpl = hiltViewModel(),
    streetMicrodistrictInput: NavigationInput.StreetMicrodistrictInput? = null
) {
    Timber.tag(TAG)
        .d(
            "StreetMicrodistrictScreen(...) called: streetMicrodistrictInput = %s",
            streetMicrodistrictInput
        )
    val onSaveButtonClick = {
        // checks all errors
        viewModel.onContinueClick {
            Timber.tag(TAG).d("StreetMicrodistrictScreen(...): Save Button onClick...")
            // if success, save then commonNavigateUp
            viewModel.handleActionJob(
                { viewModel.submitAction(StreetMicrodistrictUiAction.Save) },
                { appState.commonNavigateUp() })
        }
    }
    LaunchedEffect(streetMicrodistrictInput?.streetId) {
        Timber.tag(TAG)
            .d("StreetMicrodistrictScreen: LaunchedEffect() BEFORE collect ui state flow")
        streetMicrodistrictInput?.let {
            viewModel.submitAction(StreetMicrodistrictUiAction.Load(it.streetId))
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
                    IconButton(enabled = areInputsValid, onClick = onSaveButtonClick) {
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
                        StreetMicrodistrictView(
                            streetMicrodistrictsUiModel = it,
                            streetMicrodistrictViewModel = viewModel
                        )
                        Spacer(Modifier.height(8.dp))
                        SaveButtonComponent(enabled = areInputsValid, onClick = onSaveButtonClick)
                    }
                }
            }
        }
    }
}