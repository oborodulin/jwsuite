package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.group.single

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.oborodulin.home.common.ui.components.field.TextFieldComponent
import com.oborodulin.home.common.ui.components.field.util.InputFocusRequester
import com.oborodulin.home.common.ui.components.field.util.inputProcess
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_congregation.ui.FavoriteCongregationViewModel
import com.oborodulin.jwsuite.presentation_congregation.ui.FavoriteCongregationViewModelImpl
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.congregation.single.CongregationComboBox
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationsListItem
import timber.log.Timber

private const val TAG = "Congregating.GroupView"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GroupView(
    sharedViewModel: FavoriteCongregationViewModel<CongregationsListItem?>,
    groupViewModel: GroupViewModelImpl = hiltViewModel()
) {
    Timber.tag(TAG).d("GroupView(...) called")
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(groupViewModel.events, lifecycleOwner) {
        groupViewModel.events.flowWithLifecycle(
            lifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
    }

    Timber.tag(TAG).d("CollectAsStateWithLifecycle for all region fields")
    val congregation by groupViewModel.congregation.collectAsStateWithLifecycle()
    val groupNum by groupViewModel.groupNum.collectAsStateWithLifecycle()

    Timber.tag(TAG).d("Init Focus Requesters for all region fields")
    val focusRequesters: MutableMap<String, InputFocusRequester> = HashMap()
    enumValues<GroupFields>().forEach {
        focusRequesters[it.name] = InputFocusRequester(it, remember { FocusRequester() })
    }

    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("GroupView(...): LaunchedEffect()")
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
        CongregationComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[GroupFields.GROUP_CONGREGATION.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    groupViewModel.onTextFieldFocusChanged(
                        focusedField = GroupFields.GROUP_CONGREGATION,
                        isFocused = focusState.isFocused
                    )
                },
            enabled = false,
            sharedViewModel = sharedViewModel,
            inputWrapper = congregation,
            onValueChange = { groupViewModel.onTextFieldEntered(GroupInputEvent.Congregation(it)) },
            onImeKeyAction = groupViewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[GroupFields.GROUP_NUM.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    groupViewModel.onTextFieldFocusChanged(
                        focusedField = GroupFields.GROUP_NUM,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.code_hint,
            leadingPainterResId = com.oborodulin.home.common.R.drawable.ic_123_36,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
            },
            inputWrapper = groupNum,
            onValueChange = { groupViewModel.onTextFieldEntered(GroupInputEvent.GroupNum(it)) },
            onImeKeyAction = groupViewModel::moveFocusImeAction
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
            GroupView(sharedViewModel = FavoriteCongregationViewModelImpl.previewModel)
        }
    }
}