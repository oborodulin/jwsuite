package com.oborodulin.jwsuite.presentation.ui.session.signup

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import com.oborodulin.jwsuite.presentation.ui.AppState
import com.oborodulin.jwsuite.presentation.ui.model.SessionUi
import com.oborodulin.jwsuite.presentation.ui.session.SessionUiAction
import com.oborodulin.jwsuite.presentation.ui.session.SessionViewModelImpl
import timber.log.Timber

private const val TAG = "Presentation.SignupScreen"

@Composable
fun SignupScreen(
    appState: AppState,
    session: SessionUi? = null,
    viewModel: SessionViewModelImpl = hiltViewModel(),
    paddingValues: PaddingValues,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavClickChange: (() -> Unit) -> Unit
) {
    Timber.tag(TAG).d("SignupScreen(...) called: session = %s", session)
    val backNavigation = { appState.backToBottomBarScreen() }
    val handleSignupButtonClick = {
        Timber.tag(TAG).d("SignupScreen(...): Signup Button onClick...")
        // checks all errors
        viewModel.onContinueClick {
            // if success, save then backToBottomBarScreen
            viewModel.handleActionJob(
                { viewModel.submitAction(SessionUiAction.Signup) },
                afterAction = backNavigation
            )
        }
    }
    if (session == null) {
        LaunchedEffect(Unit) {
            Timber.tag(TAG).d("SignupScreen: LaunchedEffect() BEFORE collect ui state flow")
            viewModel.submitAction(SessionUiAction.Load)
        }
    }
    viewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        viewModel.dialogTitleResId.collectAsStateWithLifecycle().value?.let {
            onActionBarSubtitleChange(stringResource(it))
        }
        // Scaffold Hoisting:
        onTopBarNavClickChange { backNavigation() }
        CommonScreen(paddingValues = paddingValues, state = state) {
            val areInputsValid by viewModel.areSignupInputsValid.collectAsStateWithLifecycle()
            SignupView(viewModel)
            Spacer(Modifier.height(8.dp))
            SaveButtonComponent(enabled = areInputsValid, onClick = handleSignupButtonClick)
        }
    }
}