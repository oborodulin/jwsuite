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
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.components.SignupButtonComponent
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation.ui.session.SessionInputEvent
import com.oborodulin.jwsuite.presentation.ui.session.SessionUiAction
import com.oborodulin.jwsuite.presentation.ui.session.SessionViewModel
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import kotlinx.coroutines.launch
import timber.log.Timber

private const val TAG = "Presentation.SignupScreen"

@Composable
fun SignupScreen(viewModel: SessionViewModel) {//Impl = hiltViewModel()) {
    Timber.tag(TAG).d("SignupScreen(...) called")
    val appState = LocalAppState.current
    val coroutineScope = rememberCoroutineScope()
    /*if (session == null) {
        LaunchedEffect(Unit) {
            Timber.tag(TAG).d("SignupScreen: LaunchedEffect() BEFORE collect ui state flow")
            viewModel.submitAction(SessionUiAction.Load)
        }
    }*/
    viewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        val dialogTitleResId by viewModel.dialogTitleResId.collectAsStateWithLifecycle()
        JWSuiteTheme { //(darkTheme = true)
            ScaffoldComponent(
                topBarTitle = appState.appName,
                topBarSubtitle = dialogTitleResId?.let { stringResource(it) }) { innerPadding ->
                CommonScreen(paddingValues = innerPadding, state = state) { session ->
                    val areInputsValid by viewModel.areInputsValid.collectAsStateWithLifecycle()
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        SignupView(viewModel)
                        Spacer(Modifier.height(8.dp))
                        SignupButtonComponent(
                            enabled = areInputsValid,
                            onClick = {
                                viewModel.onContinueClick {
                                    Timber.tag(TAG)
                                        .d("SignupScreen(...): Start coroutineScope.launch")
                                    coroutineScope.launch {
                                        viewModel.actionsJobFlow.collect {
                                            Timber.tag(TAG).d(
                                                "SignupScreen(...): Start actionsJobFlow.collect [job = %s]",
                                                it?.toString()
                                            )
                                            it?.join()
                                            appState.rootNavController.navigate(session.authStartDestination)
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
}