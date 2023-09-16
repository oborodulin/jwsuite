package com.oborodulin.jwsuite.presentation_territory.ui.territoring.house.territory

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.components.buttons.SaveButtonComponent
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryHouseInput
import com.oborodulin.jwsuite.presentation.ui.AppState
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single.TerritoryViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

private const val TAG = "Territoring.TerritoryHouseScreen"

@Composable
fun TerritoryHouseScreen(
    appState: AppState,
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    territoryViewModel: TerritoryViewModel,
    territoryHouseViewModel: TerritoryHouseViewModelImpl = hiltViewModel(),
    territoryHouseInput: TerritoryHouseInput? = null
) {
    Timber.tag(TAG)
        .d("TerritoryHouseScreen(...) called: territoryHouseInput = %s", territoryHouseInput)
    val coroutineScope = rememberCoroutineScope()
    val saveButtonOnClick = {
        territoryHouseViewModel.onContinueClick {
            Timber.tag(TAG).d("TerritoryHouseScreen(...): Save Button onClick...")
            // checks all errors
            territoryHouseViewModel.onContinueClick {
                // if success, backToBottomBarScreen
                // https://stackoverflow.com/questions/72987545/how-to-navigate-to-another-screen-after-call-a-viemodelscope-method-in-viewmodel
                coroutineScope.launch {
                    territoryHouseViewModel.actionsJobFlow.collectLatest { job ->
                        Timber.tag(TAG).d(
                            "TerritoryHouseScreen(...): Start actionsJobFlow.collect [job = %s]",
                            job?.toString()
                        )
                        job?.join()
                        appState.backToBottomBarScreen()
                    }
                }
                // save
                territoryHouseViewModel.submitAction(TerritoryHouseUiAction.Save)
            }
        }
    }
    LaunchedEffect(territoryHouseInput?.territoryId) {
        Timber.tag(TAG).d("TerritoryHouseScreen: LaunchedEffect() BEFORE collect ui state flow")
        territoryHouseInput?.let {
            territoryHouseViewModel.submitAction(TerritoryHouseUiAction.Load(it.territoryId))
        }
    }
    territoryHouseViewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        territoryHouseViewModel.dialogTitleResId.collectAsStateWithLifecycle().value?.let {
            appState.actionBarSubtitle.value = stringResource(it)
        }
        val areInputsValid by territoryHouseViewModel.areInputsValid.collectAsStateWithLifecycle()
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
                        TerritoryHouseView(
                            territoryUi = it,
                            sharedViewModel = appState.sharedViewModel.value,
                            territoryViewModel = territoryViewModel
                        )
                        Spacer(Modifier.height(8.dp))
                        SaveButtonComponent(enabled = areInputsValid, onClick = saveButtonOnClick)
                    }
                }
            }
        }
    }
}