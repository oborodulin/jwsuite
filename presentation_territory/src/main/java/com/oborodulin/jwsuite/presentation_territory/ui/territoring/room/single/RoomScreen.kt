package com.oborodulin.jwsuite.presentation_territory.ui.territoring.room.single

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
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.RoomInput
import com.oborodulin.jwsuite.presentation.ui.AppState
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import timber.log.Timber

private const val TAG = "Territoring.RoomScreen"

@Composable
fun RoomScreen(
    appState: AppState,
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    //territoryViewModel: TerritoryViewModelImpl = hiltViewModel(),
    viewModel: RoomViewModelImpl = hiltViewModel(),
    roomInput: RoomInput? = null
) {
    Timber.tag(TAG).d("RoomScreen(...) called: houseInput = %s", roomInput)
    val coroutineScope = rememberCoroutineScope()
    val onSaveButtonClick = {
        viewModel.onContinueClick {
            Timber.tag(TAG).d("RoomScreen(...): Save Button onClick...")
            // checks all errors
            viewModel.onContinueClick {
                // if success, save then backToBottomBarScreen
                viewModel.handleActionJob(
                    { viewModel.submitAction(RoomUiAction.Save) },
                    { appState.backToBottomBarScreen() })
            }
        }
    }
    LaunchedEffect(roomInput?.roomId) {
        Timber.tag(TAG).d("RoomScreen: LaunchedEffect() BEFORE collect ui state flow")
        viewModel.submitAction(RoomUiAction.Load(roomInput?.roomId))
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
                topBarNavImageVector = Icons.Outlined.ArrowBack,
                topBarNavOnClick = { appState.backToBottomBarScreen() },
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
                        RoomView(sharedViewModel = appState.sharedViewModel.value)
                        Spacer(Modifier.height(8.dp))
                        SaveButtonComponent(enabled = areInputsValid, onClick = onSaveButtonClick)
                    }
                }
            }
        }
    }
}