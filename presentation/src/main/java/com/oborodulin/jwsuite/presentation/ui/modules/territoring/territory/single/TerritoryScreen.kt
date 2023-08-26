package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.single

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.components.buttons.NextButtonComponent
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.jwsuite.presentation.AppState
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryInput
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import kotlinx.coroutines.launch
import timber.log.Timber

private const val TAG = "Territoring.TerritoryScreen"

@Composable
fun TerritoryScreen(
    appState: AppState,
    viewModel: TerritoryViewModel,
    territoryInput: TerritoryInput? = null
) {
    Timber.tag(TAG).d("TerritoryScreen(...) called: territoryInput = %s", territoryInput)
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(territoryInput?.territoryId) {
        Timber.tag(TAG).d("TerritoryScreen: LaunchedEffect() BEFORE collect ui state flow")
        viewModel.submitAction(TerritoryUiAction.Load(territoryInput?.territoryId))
    }
    viewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        viewModel.dialogTitleResId.collectAsStateWithLifecycle().value?.let {
            appState.actionBarSubtitle.value = stringResource(it)
        }
        JWSuiteTheme { //(darkTheme = true)
            ScaffoldComponent(
                appState = appState,
                topBarNavigationIcon = {
                    IconButton(onClick = { appState.backToBottomBarScreen() }) {
                        Icon(Icons.Outlined.ArrowBack, null)
                    }
                }
            ) { paddingValues ->
                CommonScreen(paddingValues = paddingValues, state = state) {
                    val areInputsValid by viewModel.areInputsValid.collectAsStateWithLifecycle()
                    TerritoryView(appState.sharedViewModel.value)
                    Spacer(Modifier.height(8.dp))
                    NextButtonComponent(
                        enabled = areInputsValid,
                        onClick = {
                            viewModel.onContinueClick {
                                Timber.tag(TAG)
                                    .d("TerritoryScreen(...): Start viewModelScope.launch")
                                // checks all errors
                                viewModel.onContinueClick {
                                    // if success, then save and backToBottomBarScreen
                                    // https://stackoverflow.com/questions/72987545/how-to-navigate-to-another-screen-after-call-a-viemodelscope-method-in-viewmodel
                                    coroutineScope.launch {
                                        viewModel.submitAction(TerritoryUiAction.Save)
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