package com.oborodulin.jwsuite.presentation.ui.session

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.oborodulin.home.common.ui.components.field.TextFieldComponent
import com.oborodulin.home.common.ui.components.field.util.InputFocusRequester
import com.oborodulin.home.common.ui.components.field.util.inputProcess
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import timber.log.Timber
import java.util.EnumMap

private const val TAG = "Presentation.SignupView"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignupView(viewModel: SessionViewModel) {
    Timber.tag(TAG).d("SignupView(...) called")
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(viewModel.events, lifecycleOwner) {
        viewModel.events.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }

    Timber.tag(TAG).d("Session: CollectAsStateWithLifecycle for all fields")
    val username by viewModel.username.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()
    val confirmPassword by viewModel.confirmPassword.collectAsStateWithLifecycle()

    Timber.tag(TAG).d("Session: Init Focus Requesters for all fields")
    val focusRequesters = EnumMap<SessionFields, InputFocusRequester>(SessionFields::class.java)
    enumValues<SessionFields>().forEach {
        focusRequesters[it] = InputFocusRequester(it, remember { FocusRequester() })
    }

    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("SignupView(...): LaunchedEffect()")
        events.collect { event ->
            Timber.tag(TAG).d("Collect input events flow: %s", event.javaClass.name)
            inputProcess(context, focusManager, keyboardController, event, focusRequesters)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .height(IntrinsicSize.Min)
            .clip(RoundedCornerShape(16.dp))
            .border(
                2.dp,
                MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(16.dp)
            )
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[SessionFields.SESSION_USERNAME]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = SessionFields.SESSION_USERNAME,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.session_username_hint,
            leadingImageVector = Icons.Outlined.Person,
            keyboardOptions = remember {
                KeyboardOptions(
                    capitalization = KeyboardCapitalization.Characters,
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                )
            },
            inputWrapper = username,
            onValueChange = { viewModel.onTextFieldEntered(SessionInputEvent.Username(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[SessionFields.SESSION_PASSWORD]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = SessionFields.SESSION_PASSWORD,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.session_password_hint,
            leadingImageVector = Icons.Outlined.Lock,
            keyboardOptions = remember {
                KeyboardOptions(
                    keyboardType = KeyboardType.Password, imeAction = ImeAction.Next
                )
            },
            //  visualTransformation = ::creditCardFilter,
            inputWrapper = password,
            onValueChange = { viewModel.onTextFieldEntered(SessionInputEvent.Password(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[SessionFields.SESSION_PASSWORD]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = SessionFields.SESSION_PASSWORD,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.session_confirm_password_hint,
            leadingImageVector = Icons.Outlined.Lock,
            keyboardOptions = remember {
                KeyboardOptions(
                    keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
                )
            },
            //  visualTransformation = ::creditCardFilter,
            inputWrapper = confirmPassword,
            onValueChange = { viewModel.onTextFieldEntered(SessionInputEvent.ConfirmPassword(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewSignupView() {
    JWSuiteTheme {
        Surface {
            SignupView(viewModel = SessionViewModelImpl.previewModel(LocalContext.current))
        }
    }
}
