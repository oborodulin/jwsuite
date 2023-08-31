package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.group.single

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.components.buttons.SaveButtonComponent
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.jwsuite.presentation.AppState
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.GroupInput
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_congregation.ui.FavoriteCongregationViewModel
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationsListItem
import kotlinx.coroutines.launch
import timber.log.Timber

private const val TAG = "Congregating.GroupScreen"

@Composable
fun GroupScreen(
    appState: AppState,
    sharedViewModel: FavoriteCongregationViewModel<CongregationsListItem?>,
    groupViewModel: GroupViewModelImpl = hiltViewModel(),
    groupInput: GroupInput? = null
) {
    Timber.tag(TAG).d("GroupScreen(...) called: groupInput = %s", groupInput)
    LaunchedEffect(groupInput?.groupId) {
        Timber.tag(TAG).d("GroupScreen: LaunchedEffect() BEFORE collect ui state flow")
        groupViewModel.submitAction(GroupUiAction.Load(groupInput?.groupId))
    }
    groupViewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        groupViewModel.dialogTitleResId.collectAsStateWithLifecycle().value?.let {
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
            ) { it ->
                CommonScreen(paddingValues = it, state = state) {
                    val areInputsValid by groupViewModel.areInputsValid.collectAsStateWithLifecycle()
                    GroupView(sharedViewModel)
                    Spacer(Modifier.height(8.dp))
                    SaveButtonComponent(
                        enabled = areInputsValid,
                        onClick = {
                            groupViewModel.onContinueClick {
                                Timber.tag(TAG).d("GroupScreen(...): Start viewModelScope.launch")
                                groupViewModel.viewModelScope().launch {
                                    groupViewModel.actionsJobFlow.collect {
                                        Timber.tag(TAG).d(
                                            "GroupScreen(...): Start actionsJobFlow.collect [job = %s]",
                                            it?.toString()
                                        )
                                        it?.join()
                                        appState.backToBottomBarScreen()
                                    }
                                }
                                groupViewModel.submitAction(GroupUiAction.Save)
                                Timber.tag(TAG).d("GroupScreen(...): onSubmit() executed")
                            }
                        }
                    )
                }
            }
        }
    }
}