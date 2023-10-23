package com.oborodulin.jwsuite.presentation.ui.session.login

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation.ui.session.SessionViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import timber.log.Timber

private const val TAG = "Presentation.LoginScreen"

@Composable
fun LoginScreen(viewModel: SessionViewModelImpl = hiltViewModel()) {
    Timber.tag(TAG).d("LoginScreen(...) called")
    val appState = LocalAppState.current
    /*if (session == null) {
        LaunchedEffect(Unit) {
            Timber.tag(TAG).d("LoginScreen: LaunchedEffect() BEFORE collect ui state flow")
            viewModel.submitAction(SessionUiAction.Load)
        }
    }*/
    viewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        val dialogTitleResId by viewModel.dialogTitleResId.collectAsStateWithLifecycle()
        JWSuiteTheme { //(darkTheme = true)
            ScaffoldComponent(
                appState = appState,
                topBarSubtitle = dialogTitleResId?.let { stringResource(it) },
                topBarNavImageVector = Icons.Outlined.ArrowBack,
                onTopBarNavClick = { appState.backToBottomBarScreen() }
            ) { paddingValues ->
                CommonScreen(paddingValues = paddingValues, state = state) {
                    LoginView(viewModel)
                }
            }
        }
    }
}