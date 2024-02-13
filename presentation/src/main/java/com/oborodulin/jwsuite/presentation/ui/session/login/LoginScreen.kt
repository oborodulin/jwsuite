package com.oborodulin.jwsuite.presentation.ui.session.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation.ui.session.SessionInputEvent
import com.oborodulin.jwsuite.presentation.ui.session.SessionUiAction
import com.oborodulin.jwsuite.presentation.ui.session.SessionUiSingleEvent
import com.oborodulin.jwsuite.presentation.ui.session.SessionViewModel
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

private const val TAG = "Presentation.LoginScreen"

@Composable
fun LoginScreen(viewModel: SessionViewModel) {//Impl = hiltViewModel()) {
    Timber.tag(TAG).d("LoginScreen(...) called")
    val appState = LocalAppState.current
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("LoginScreen -> LaunchedEffect()")
        viewModel.onTextFieldEntered(SessionInputEvent.Pin(""))
        viewModel.singleEventFlow.collectLatest {
            Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
            when (it) {
                is SessionUiSingleEvent.OpenMainScreen -> {
                    appState.rootNavController.navigate(it.navRoute) {
                        popUpTo(it.navRoute) { inclusive = true }
                        launchSingleTop = true
                    }
                }

                is SessionUiSingleEvent.OpenLoginScreen -> {
                    appState.rootNavController.navigate(it.navRoute) {
                        popUpTo(it.navRoute) { inclusive = true }
                        launchSingleTop = true
                    }
                }

                else -> {}
            }
        }
    }
    val handleCheckPasswordValid = {
        viewModel.onContinueClick {
            viewModel.submitAction(SessionUiAction.CheckPasswordValid)
        }
    }
    //viewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
    //    Timber.tag(TAG).d("Collect ui state flow: %s", state)
    val dialogTitleResId by viewModel.dialogTitleResId.collectAsStateWithLifecycle()
    //JWSuiteTheme { //(darkTheme = true)
    ScaffoldComponent(
        topBarTitle = appState.appName,
        topBarSubtitle = dialogTitleResId?.let { stringResource(it) }) { innerPadding ->
        //CommonScreen(paddingValues = innerPadding, state = state) { //session ->
        //if (!session.isSigned) viewModel.submitAction(SessionUiAction.Registration)
        //if (session.isLogged) viewModel.submitAction(SessionUiAction.StartSession)
        val areInputsValid by viewModel.areSignupInputsValid.collectAsStateWithLifecycle()
        Timber.tag(TAG).d("LoginScreen: areInputsValid = %s", areInputsValid)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginView(viewModel = viewModel, handleCheckPasswordValid = handleCheckPasswordValid)
            /*Spacer(Modifier.height(8.dp))
            LoginButtonComponent(enabled = areInputsValid, onClick = handleLogin)*/
        }
        //}
        //}
    }
    //}
}