package com.oborodulin.jwsuite.presentation.ui.session.login

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.oborodulin.home.common.ui.components.field.OtpTextFieldComponent
import com.oborodulin.home.common.ui.components.field.util.InputFocusRequester
import com.oborodulin.home.common.ui.components.field.util.inputProcess
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation.ui.model.LocalSession
import com.oborodulin.jwsuite.presentation.ui.session.SessionFields
import com.oborodulin.jwsuite.presentation.ui.session.SessionInputEvent
import com.oborodulin.jwsuite.presentation.ui.session.SessionUiAction
import com.oborodulin.jwsuite.presentation.ui.session.SessionViewModel
import com.oborodulin.jwsuite.presentation.ui.session.SessionViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation.util.Constants.PASS_MIN_LENGTH
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.EnumMap

private const val TAG = "Presentation.LoginView"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginView(viewModel: SessionViewModel) {
    Timber.tag(TAG).d("LoginView(...) called")
    val appState = LocalAppState.current
    val session = LocalSession.current
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(viewModel.events, lifecycleOwner) {
        viewModel.events.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }

    Timber.tag(TAG).d("Session: CollectAsStateWithLifecycle for all fields")
    val pin by viewModel.pin.collectAsStateWithLifecycle()

    Timber.tag(TAG).d("Session: Init Focus Requesters for all fields")
    val focusRequesters = EnumMap<SessionFields, InputFocusRequester>(SessionFields::class.java)
    enumValues<SessionFields>().forEach {
        focusRequesters[it] = InputFocusRequester(it, remember { FocusRequester() })
    }

    val handleLogin = {
        viewModel.onContinueClick {
            Timber.tag(TAG).d("LoginView(...): Start coroutineScope.launch")
            coroutineScope.launch {
                viewModel.actionsJobFlow.collect {
                    Timber.tag(TAG).d(
                        "LoginView(...): Start actionsJobFlow.collect [job = %s]", it?.toString()
                    )
                    it?.join()
                    appState.commonNavController.navigate(session.route)
                }
            }
            viewModel.submitAction(SessionUiAction.Login)
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("LoginView(...): LaunchedEffect()")
        events.collect { event ->
            Timber.tag(TAG).d("Collect input events flow: %s", event.javaClass.name)
            inputProcess(context, focusManager, keyboardController, event, focusRequesters)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(
                2.dp, MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(16.dp)
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OtpTextFieldComponent(inputWrapper = pin,
            otpCount = PASS_MIN_LENGTH,
            onOtpTextChange = { value, otpInputFilled ->
                viewModel.onTextFieldEntered(SessionInputEvent.Pin(value))
                if (otpInputFilled) handleLogin()
            })
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewLoginView() {
    val ctx = LocalContext.current
    JWSuiteTheme { Surface { LoginView(viewModel = SessionViewModelImpl.previewModel(ctx)) } }
}
