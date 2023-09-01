package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single

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
import com.oborodulin.jwsuite.presentation_congregation.ui.FavoriteCongregationViewModel
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationsListItem
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.UUID

private const val TAG = "Territoring.TerritoryScreen"

@Composable
fun TerritoryScreen(
    appState: AppState,
    sharedViewModel: FavoriteCongregationViewModel<CongregationsListItem?>,
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
                    val territoryId by viewModel.id.collectAsStateWithLifecycle()
                    val areInputsValid by viewModel.areInputsValid.collectAsStateWithLifecycle()
                    TerritoryView(sharedViewModel)
                    Spacer(Modifier.height(8.dp))
                    NextButtonComponent(
                        enabled = areInputsValid,
                        onClick = {
                            viewModel.onContinueClick {
                                Timber.tag(TAG)
                                    .d("TerritoryScreen(...): Start viewModelScope.launch")
                                // checks all errors
                                viewModel.onContinueClick {
                                    // if success,
                                    coroutineScope.launch {
                                        // wait wile actionsJob executed
                                        viewModel.actionsJobFlow.collectLatest { job ->
                                            Timber.tag(TAG).d(
                                                "TerritoryScreen(...): Start actionsJobFlow.collect [job = %s]",
                                                job?.toString()
                                            )
                                            job?.join()
                                            // navigate to TerritoryDetailsScreen
                                            viewModel.submitAction(
                                                TerritoryUiAction.EditTerritoryDetails(
                                                    UUID.fromString(territoryId.value)
                                                )
                                            )
                                        }
                                    }
                                    // execute viewModel.Save()
                                    viewModel.submitAction(TerritoryUiAction.Save)
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}