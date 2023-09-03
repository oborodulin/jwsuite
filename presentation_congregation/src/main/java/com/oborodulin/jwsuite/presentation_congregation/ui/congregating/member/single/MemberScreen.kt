package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.single

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.MemberInput
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_congregation.ui.FavoriteCongregationViewModel
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationsListItem
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

private const val TAG = "Congregating.MemberScreen"

@Composable
fun MemberScreen(
    appState: AppState,
    sharedViewModel: FavoriteCongregationViewModel<CongregationsListItem?>,
    viewModel: MemberViewModelImpl = hiltViewModel(),
    memberInput: MemberInput? = null
) {
    Timber.tag(TAG).d("MemberScreen(...) called: groupInput = %s", memberInput)
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(memberInput?.memberId) {
        Timber.tag(TAG).d("MemberScreen: LaunchedEffect() BEFORE collect ui state flow")
        viewModel.submitAction(MemberUiAction.Load(memberInput?.memberId))
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
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        MemberView(sharedViewModel)
                        Spacer(Modifier.height(8.dp))
                        SaveButtonComponent(
                            enabled = areInputsValid,
                            onClick = {
                                viewModel.onContinueClick {
                                    Timber.tag(TAG).d("MemberScreen(...): Save Button onClick...")
                                    // checks all errors
                                    viewModel.onContinueClick {
                                        // if success, backToBottomBarScreen
                                        // https://stackoverflow.com/questions/72987545/how-to-navigate-to-another-screen-after-call-a-viemodelscope-method-in-viewmodel
                                        coroutineScope.launch {
                                            viewModel.actionsJobFlow.collectLatest { job ->
                                                Timber.tag(TAG).d(
                                                    "MemberScreen(...): Start actionsJobFlow.collect [job = %s]",
                                                    job?.toString()
                                                )
                                                job?.join()
                                                appState.backToBottomBarScreen()
                                            }
                                        }
                                        // save
                                        viewModel.submitAction(MemberUiAction.Save)
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