package com.oborodulin.jwsuite.presentation_dashboard.ui.database.send

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.oborodulin.home.common.ui.components.field.util.InputFocusRequester
import com.oborodulin.home.common.ui.components.field.util.inputProcess
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_INPUT
import com.oborodulin.home.common.util.OnImeKeyAction
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.single.MemberComboBox
import com.oborodulin.jwsuite.presentation_dashboard.ui.database.DatabaseFields
import com.oborodulin.jwsuite.presentation_dashboard.ui.database.DatabaseInputEvent
import com.oborodulin.jwsuite.presentation_dashboard.ui.database.DatabaseViewModelImpl
import timber.log.Timber
import java.util.EnumMap

private const val TAG = "Presentation_Dashboard.DatabaseSendView"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DatabaseSendView(
    viewModel: DatabaseViewModelImpl = hiltViewModel(),
    handleSaveAction: OnImeKeyAction
) {
    Timber.tag(TAG).d("DatabaseSendView(...) called")
    val appState = LocalAppState.current
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(viewModel.events, lifecycleOwner) {
        viewModel.events.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }

    Timber.tag(TAG).d("DatabaseSend: CollectAsStateWithLifecycle for all fields")
    val receiverMember by viewModel.receiverMember.collectAsStateWithLifecycle()

    Timber.tag(TAG).d("DatabaseSend: Init Focus Requesters for all fields")
    val focusRequesters =
        EnumMap<DatabaseFields, InputFocusRequester>(DatabaseFields::class.java)
    enumValues<DatabaseFields>().forEach {
        focusRequesters[it] = InputFocusRequester(it, remember { FocusRequester() })
    }
    val currentCongregation =
        appState.congregationSharedViewModel.value?.sharedFlow?.collectAsStateWithLifecycle()?.value
    Timber.tag(TAG).d("currentCongregation = %s", currentCongregation)
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("DatabaseSendView -> LaunchedEffect()")
        events.collect { event ->
            if (LOG_FLOW_INPUT) Timber.tag(TAG)
                .d("IF# Collect input events flow: %s", event.javaClass.name)
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
        MemberComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[DatabaseFields.RECEIVER_MEMBER]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = DatabaseFields.RECEIVER_MEMBER,
                        isFocused = focusState.isFocused
                    )
                },
            sharedViewModel = appState.congregationSharedViewModel.value,
            inputWrapper = receiverMember,
            onValueChange = { viewModel.onTextFieldEntered(DatabaseInputEvent.ReceiverMember(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewGroupView() {
    //val ctx = LocalContext.current
    JWSuiteTheme {
        Surface {
            DatabaseSendView() {}//sharedViewModel = FavoriteCongregationViewModelImpl.previewModel)
        }
    }
}
