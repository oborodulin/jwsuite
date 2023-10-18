package com.oborodulin.jwsuite.presentation_territory.ui.territoring.room.territory

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
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryRoomInput
import com.oborodulin.jwsuite.presentation.ui.AppState
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single.TerritoryViewModel
import timber.log.Timber

private const val TAG = "Territoring.TerritoryRoomScreen"

@Composable
fun TerritoryRoomScreen(
    appState: AppState,
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    territoryViewModel: TerritoryViewModel,
    territoryRoomViewModel: TerritoryRoomViewModelImpl = hiltViewModel(),
    territoryRoomInput: TerritoryRoomInput? = null
) {
    Timber.tag(TAG)
        .d("TerritoryRoomScreen(...) called: territoryRoomInput = %s", territoryRoomInput)

    val onSaveButtonClick = {
        // checks all errors
        territoryRoomViewModel.onContinueClick {
            Timber.tag(TAG).d("TerritoryRoomScreen(...): Save Button onClick...")
            // if success, save then backToBottomBarScreen
            territoryRoomViewModel.handleActionJob(
                { territoryRoomViewModel.submitAction(TerritoryRoomUiAction.Save) },
                { appState.commonNavigateUp() })
        }
    }
    LaunchedEffect(territoryRoomInput?.territoryId) {
        Timber.tag(TAG).d("TerritoryRoomScreen: LaunchedEffect() BEFORE collect ui state flow")
        territoryRoomInput?.let {
            territoryRoomViewModel.submitAction(TerritoryRoomUiAction.Load(it.territoryId))
        }
    }
    territoryRoomViewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        territoryRoomViewModel.dialogTitleResId.collectAsStateWithLifecycle().value?.let {
            appState.actionBarSubtitle.value = stringResource(it)
        }
        val areInputsValid by territoryRoomViewModel.areInputsValid.collectAsStateWithLifecycle()
        JWSuiteTheme { //(darkTheme = true)
            ScaffoldComponent(
                appState = appState,
                topBarNavImageVector = Icons.Outlined.ArrowBack,
                topBarNavOnClick = { appState.commonNavigateUp() },
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
                        TerritoryRoomView(
                            territoryRoomsUiModel = it,
                            sharedViewModel = appState.sharedViewModel.value,
                            territoryViewModel = territoryViewModel,
                            territoryRoomViewModel = territoryRoomViewModel
                        )
                        Spacer(Modifier.height(8.dp))
                        SaveButtonComponent(enabled = areInputsValid, onClick = onSaveButtonClick)
                    }
                }
            }
        }
    }
}