package com.oborodulin.jwsuite.presentation.ui.session.login

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
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
import com.oborodulin.home.common.util.LogLevel.LOG_SECURE
import com.oborodulin.jwsuite.presentation.ui.session.SessionFields
import com.oborodulin.jwsuite.presentation.ui.session.SessionInputEvent
import com.oborodulin.jwsuite.presentation.ui.session.SessionModeType
import com.oborodulin.jwsuite.presentation.ui.session.SessionViewModel
import com.oborodulin.jwsuite.presentation.ui.session.SessionViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation.util.Constants.PASS_MIN_LENGTH
import timber.log.Timber
import java.util.EnumMap

private const val TAG = "Presentation.LoginView"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginView(viewModel: SessionViewModel) {
    Timber.tag(TAG).d("LoginView(...) called")
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(viewModel.events, lifecycleOwner) {
        viewModel.events.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }

    Timber.tag(TAG).d("LoginView: CollectAsStateWithLifecycle for pin field")
    val pin by viewModel.pin.collectAsStateWithLifecycle()

    Timber.tag(TAG).d("LoginView: Init Focus Requesters for all fields")
    val focusRequesters = EnumMap<SessionFields, InputFocusRequester>(SessionFields::class.java)
    enumValues<SessionFields>().forEach {
        focusRequesters[it] = InputFocusRequester(it, remember { FocusRequester() })
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("LoginView -> LaunchedEffect(Unit)")
        viewModel.setSessionMode(SessionModeType.LOGIN)
        events.collect { event ->
            Timber.tag(TAG).d("Collect input events flow: %s", event.javaClass.name)
            inputProcess(context, focusManager, keyboardController, event, focusRequesters)
        }
    }
    Column(
        modifier = Modifier
            //.fillMaxSize()
            .fillMaxWidth()
            .height(350.dp)
            .padding(4.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(
                2.dp, MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(16.dp)
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OtpTextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[SessionFields.SESSION_PIN]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = SessionFields.SESSION_PIN,
                        isFocused = focusState.isFocused
                    )
                },
            inputWrapper = pin,
            otpCount = PASS_MIN_LENGTH,
            onOtpTextChange = { value, otpInputFilled ->
                if (LOG_SECURE) Timber.tag(TAG)
                    .d("LoginView: value = %s; otpInputFilled = %s", value, otpInputFilled)
                viewModel.onTextFieldEntered(SessionInputEvent.Pin(value))
                //if (otpInputFilled) handleLogin()
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
