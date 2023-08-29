package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territorystreet.single

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.components.buttons.SaveButtonComponent
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.jwsuite.presentation.AppState
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryStreetInput
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.territorystreet.list.TerritoryStreetsListUiAction
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.territorystreet.list.TerritoryStreetsListViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import kotlinx.coroutines.launch
import timber.log.Timber

private const val TAG = "Territoring.TerritoryStreetScreen"

@Composable
fun TerritoryStreetScreen(
    appState: AppState,
    territoryStreetsListViewModel: TerritoryStreetsListViewModelImpl = hiltViewModel(),
    territoryStreetViewModel: TerritoryStreetViewModelImpl = hiltViewModel(),
    territoryStreetInput: TerritoryStreetInput? = null
) {
    Timber.tag(TAG)
        .d("TerritoryStreetScreen(...) called: territoryStreetInput = %s", territoryStreetInput)
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(territoryStreetInput?.territoryStreetId) {
        Timber.tag(TAG).d("TerritoryStreetScreen: LaunchedEffect() BEFORE collect ui state flow")
        territoryStreetViewModel.submitAction(
            TerritoryStreetUiAction.Load(
                territoryStreetInput?.territoryId, territoryStreetInput?.territoryStreetId
            )
        )
        territoryStreetInput?.territoryId?.let {
            territoryStreetsListViewModel.submitAction(TerritoryStreetsListUiAction.Load(it))
        }
    }
    territoryStreetViewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        territoryStreetViewModel.dialogTitleResId.collectAsStateWithLifecycle().value?.let {
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
                    val areInputsValid by territoryStreetViewModel.areInputsValid.collectAsStateWithLifecycle()
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TerritoryStreetView()
                        Spacer(Modifier.height(8.dp))
                        SaveButtonComponent(
                            enabled = areInputsValid,
                            onClick = {
                                Timber.tag(TAG)
                                    .d("TerritoryStreetScreen(...): Save Button onClick...")
                                // checks all errors
                                territoryStreetViewModel.onContinueClick {
                                    // if success, then save and backToBottomBarScreen
                                    // https://stackoverflow.com/questions/72987545/how-to-navigate-to-another-screen-after-call-a-viemodelscope-method-in-viewmodel
                                    coroutineScope.launch {
                                        territoryStreetViewModel.submitAction(
                                            TerritoryStreetUiAction.Save
                                        )
                                            .join()
                                        appState.backToBottomBarScreen()
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}