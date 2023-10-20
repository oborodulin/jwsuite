package com.oborodulin.jwsuite.presentation.ui.session.signup

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.components.buttons.SaveButtonComponent
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.ui.AppState
import com.oborodulin.jwsuite.presentation.ui.model.SessionUi
import com.oborodulin.jwsuite.presentation.ui.session.SessionUiAction
import com.oborodulin.jwsuite.presentation.ui.session.SessionViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import kotlinx.coroutines.launch
import timber.log.Timber

private const val TAG = "Presentation.SignupScreen"

@Composable
fun SignupScreen(
    appState: AppState,
    session: SessionUi? = null,
    viewModel: SessionViewModelImpl = hiltViewModel()
) {
    Timber.tag(TAG).d("SignupScreen(...) called: session = %s", session)
    val coroutineScope = rememberCoroutineScope()
    if (session == null) {
        LaunchedEffect(Unit) {
            Timber.tag(TAG).d("SignupScreen: LaunchedEffect() BEFORE collect ui state flow")
            viewModel.submitAction(SessionUiAction.Load)
        }
    }
    viewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        val dialogTitleResId by viewModel.dialogTitleResId.collectAsStateWithLifecycle()
        JWSuiteTheme { //(darkTheme = true)
            ScaffoldComponent(
                appState = appState,
                topBarSubtitle = dialogTitleResId?.let { stringResource(it) },
                topBarNavImageVector = Icons.Outlined.ArrowBack,
                onTopBarNavClick = { appState.backToBottomBarScreen() }
            ) { it ->
                CommonScreen(paddingValues = it, state = state) {
                    val areInputsValid by viewModel.areSignupInputsValid.collectAsStateWithLifecycle()
                    SignupView(viewModel)
                    Spacer(Modifier.height(8.dp))
                    SaveButtonComponent(
                        enabled = areInputsValid,
                        onClick = {
                            viewModel.onContinueClick {
                                Timber.tag(TAG).d("SignupScreen(...): Start viewModelScope.launch")
                                coroutineScope.launch {
                                    viewModel.actionsJobFlow.collect {
                                        Timber.tag(TAG).d(
                                            "SignupScreen(...): Start actionsJobFlow.collect [job = %s]",
                                            it?.toString()
                                        )
                                        it?.join()
                                        appState.backToBottomBarScreen()
                                    }
                                }
                                viewModel.submitAction(SessionUiAction.Signup)
                                Timber.tag(TAG).d("SignupScreen(...): onSubmit() executed")
                            }
                        }
                    )
                }
            }
        }
    }
}