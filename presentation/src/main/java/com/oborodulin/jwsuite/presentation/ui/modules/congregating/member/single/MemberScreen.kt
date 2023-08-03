package com.oborodulin.jwsuite.presentation.ui.modules.congregating.member.single

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.jwsuite.presentation.AppState
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.MemberInput
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import kotlinx.coroutines.launch
import timber.log.Timber

private const val TAG = "Congregating.MemberScreen"

@Composable
fun MemberScreen(
    appState: AppState,
    memberViewModel: MemberViewModelImpl = hiltViewModel(),
    memberInput: MemberInput? = null
) {
    Timber.tag(TAG).d("MemberScreen(...) called: groupInput = %s", memberInput)
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(memberInput?.memberId) {
        Timber.tag(TAG).d("MemberScreen: LaunchedEffect() BEFORE collect ui state flow")
        memberViewModel.submitAction(MemberUiAction.Load(memberInput?.memberId))
    }
    memberViewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        memberViewModel.dialogTitleResId.collectAsStateWithLifecycle().value?.let {
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
                    val areInputsValid by memberViewModel.areInputsValid.collectAsStateWithLifecycle()
                    MemberView(appState.sharedViewModel.value)
                    Spacer(Modifier.height(8.dp))
                    Button(
                        onClick = {
                            memberViewModel.onContinueClick {
                                Timber.tag(TAG).d("MemberScreen(...): Start viewModelScope.launch")
                                // checks all errors
                                memberViewModel.onContinueClick {
                                    // if success, then save and backToBottomBarScreen
                                    // https://stackoverflow.com/questions/72987545/how-to-navigate-to-another-screen-after-call-a-viemodelscope-method-in-viewmodel
                                    coroutineScope.launch {
                                        memberViewModel.submitAction(MemberUiAction.Save)
                                            .join()
                                        appState.backToBottomBarScreen()
                                    }
                                }
                            }
                        },
                        enabled = areInputsValid
                    ) { Text(text = stringResource(com.oborodulin.home.common.R.string.btn_save_lbl)) }
                }
            }
        }
    }
}