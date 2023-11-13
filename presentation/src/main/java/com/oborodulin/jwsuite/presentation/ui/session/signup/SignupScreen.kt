package com.oborodulin.jwsuite.presentation.ui.session.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.components.SignupButtonComponent
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation.ui.session.SessionUiAction
import com.oborodulin.jwsuite.presentation.ui.session.SessionViewModel
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import timber.log.Timber

private const val TAG = "Presentation.SignupScreen"

@Composable
fun SignupScreen(viewModel: SessionViewModel) {//Impl = hiltViewModel()) {
    Timber.tag(TAG).d("SignupScreen(...) called")
    val appState = LocalAppState.current
    val coroutineScope = rememberCoroutineScope()
    /*if (session == null) {
        LaunchedEffect(Unit) {
            Timber.tag(TAG).d("SignupScreen -> LaunchedEffect() BEFORE collect ui state flow")
            viewModel.submitAction(SessionUiAction.Load)
        }
    }*/
    //viewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
    //    Timber.tag(TAG).d("Collect ui state flow: %s", state)
    val handleSignup = {
        viewModel.onContinueClick {
            viewModel.handleActionJob(
                { viewModel.submitAction(SessionUiAction.Signup) },
                { viewModel.submitAction(SessionUiAction.StartSession) }
            )
        }
    }
    val dialogTitleResId by viewModel.dialogTitleResId.collectAsStateWithLifecycle()
    JWSuiteTheme { //(darkTheme = true)
        ScaffoldComponent(
            topBarTitle = appState.appName,
            topBarSubtitle = dialogTitleResId?.let { stringResource(it) }) { innerPadding ->
            //            CommonScreen(paddingValues = innerPadding, state = state) { //session ->
            val areInputsValid by viewModel.areSignupInputsValid.collectAsStateWithLifecycle()
            Timber.tag(TAG).d("SignupScreen: areInputsValid = %s", areInputsValid)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SignupView(viewModel)
                Spacer(Modifier.height(8.dp))
                SignupButtonComponent(enabled = areInputsValid, onClick = handleSignup)
            }
            //}
            //}
        }
    }
}