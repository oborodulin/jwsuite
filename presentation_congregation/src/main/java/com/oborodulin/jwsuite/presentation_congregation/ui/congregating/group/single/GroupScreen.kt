package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.group.single

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
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.GroupInput
import com.oborodulin.jwsuite.presentation.ui.AppState
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

private const val TAG = "Congregating.GroupScreen"

@Composable
fun GroupScreen(
    appState: AppState,
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    viewModel: GroupViewModelImpl = hiltViewModel(),
    groupInput: GroupInput? = null
) {
    Timber.tag(TAG).d("GroupScreen(...) called: groupInput = %s", groupInput)
    val coroutineScope = rememberCoroutineScope()
    val onSaveButtonClick = {
        Timber.tag(TAG).d("GroupScreen(...): Save Button onClick...")
        // checks all errors
        viewModel.onContinueClick {
            // if success, backToBottomBarScreen
            // https://stackoverflow.com/questions/72987545/how-to-navigate-to-another-screen-after-call-a-viemodelscope-method-in-viewmodel
            coroutineScope.launch {
                viewModel.actionsJobFlow.collectLatest { job ->
                    Timber.tag(TAG).d(
                        "GroupScreen(...): Start actionsJobFlow.collect [job = %s] for backToBottomBarScreen()",
                        job?.toString()
                    )
                    job?.join()
                    appState.backToBottomBarScreen()
                }
            }
            // save
            viewModel.submitAction(GroupUiAction.Save)
        }
    }
    val currentCongregation =
        appState.sharedViewModel.value?.sharedFlow?.collectAsStateWithLifecycle()?.value
    LaunchedEffect(groupInput?.groupId) {
        Timber.tag(TAG).d("GroupScreen: LaunchedEffect() BEFORE collect ui state flow")
        viewModel.submitAction(GroupUiAction.Load(groupInput?.groupId))
        currentCongregation?.itemId?.let { congregationId ->
            viewModel.onInsert {
                viewModel.submitAction(GroupUiAction.GetNextGroupNum(congregationId))
            }
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
                        GroupView(appState.sharedViewModel.value)
                        Spacer(Modifier.height(8.dp))
                        SaveButtonComponent(enabled = areInputsValid, onClick = onSaveButtonClick)
                    }
                }
            }
        }
    }
}