package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid.handout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.ui.AppState
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_territory.components.HandOutButtonComponent
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid.TerritoriesGridUiAction
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid.TerritoriesGridViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

private const val TAG = "Territoring.HandOutTerritoriesConfirmationScreen"

@Composable
fun HandOutTerritoriesConfirmationScreen(
    appState: AppState,
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    viewModel: TerritoriesGridViewModel//Impl = hiltViewModel()
) {
    Timber.tag(TAG).d("HandOutTerritoriesConfirmationScreen(...) called")
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        Timber.tag(TAG)
            .d("HandOutTerritoriesConfirmationScreen: LaunchedEffect() BEFORE collect ui state flow")
        viewModel.submitAction(TerritoriesGridUiAction.HandOutConfirmation)
    }
    viewModel.dialogTitleResId.collectAsStateWithLifecycle().value?.let { dialogTitleResId ->
        Timber.tag(TAG).d("Collect ui state flow")
        appState.actionBarSubtitle.value = stringResource(dialogTitleResId)
        JWSuiteTheme { //(darkTheme = true)
            ScaffoldComponent(
                appState = appState,
                topBarNavigationIcon = {
                    IconButton(onClick = { appState.backToBottomBarScreen() }) {
                        Icon(Icons.Outlined.ArrowBack, null)
                    }
                }
            ) { paddingValues ->
                val areInputsValid by viewModel.areHandOutInputsValid.collectAsStateWithLifecycle()
                Column(
                    modifier = Modifier.padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    HandOutTerritoriesConfirmationView(
                        sharedViewModel = appState.sharedViewModel.value,
                        viewModel = viewModel
                    )
                    Spacer(Modifier.height(8.dp))
                    HandOutButtonComponent(
                        enabled = areInputsValid,
                        onClick = {
                            viewModel.onContinueClick {
                                Timber.tag(TAG)
                                    .d("HandOutTerritoriesConfirmationScreen(...): Hand Out Territory Button onClick...")
                                // checks all errors
                                viewModel.onContinueClick {
                                    // if success, then HandOut and backToBottomBarScreen
                                    // https://stackoverflow.com/questions/72987545/how-to-navigate-to-another-screen-after-call-a-viemodelscope-method-in-viewmodel
                                    coroutineScope.launch {
                                        viewModel.submitAction(TerritoriesGridUiAction.HandOut)
                                            ?.join()
                                        appState.backToBottomBarScreen()
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}