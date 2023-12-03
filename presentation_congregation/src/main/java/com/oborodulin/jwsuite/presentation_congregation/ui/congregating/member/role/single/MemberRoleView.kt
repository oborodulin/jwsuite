package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.role.single

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
import com.oborodulin.home.common.ui.components.field.DatePickerComponent
import com.oborodulin.home.common.ui.components.field.util.InputFocusRequester
import com.oborodulin.home.common.ui.components.field.util.inputProcess
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.SharedViewModeled
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_congregation.R
import com.oborodulin.jwsuite.presentation_congregation.ui.FavoriteCongregationViewModelImpl
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.single.MemberComboBox
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.role.single.RoleComboBox
import com.oborodulin.jwsuite.presentation_congregation.ui.model.toCongregationsListItem
import timber.log.Timber
import java.util.EnumMap

private const val TAG = "Congregating.MemberRoleView"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MemberRoleView(
    sharedViewModel: SharedViewModeled<ListItemModel?>?,
    viewModel: MemberRoleViewModelImpl = hiltViewModel()
) {
    Timber.tag(TAG).d("MemberRoleView(...) called")
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(viewModel.events, lifecycleOwner) {
        viewModel.events.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }

    Timber.tag(TAG).d("MemberRole: CollectAsStateWithLifecycle for all fields")
    //val congregation by viewModel.congregation.collectAsStateWithLifecycle()
    val member by viewModel.member.collectAsStateWithLifecycle()
    val role by viewModel.role.collectAsStateWithLifecycle()
    val roleExpiredDate by viewModel.roleExpiredDate.collectAsStateWithLifecycle()

    //val currentCongregation = sharedViewModel?.sharedFlow?.collectAsStateWithLifecycle()?.value
    //Timber.tag(TAG).d("currentCongregation = %s", currentCongregation)

    Timber.tag(TAG).d("MemberRole: Init Focus Requesters for all fields")
    val focusRequesters =
        EnumMap<MemberRoleFields, InputFocusRequester>(MemberRoleFields::class.java)
    enumValues<MemberRoleFields>().forEach {
        focusRequesters[it] = InputFocusRequester(it, remember { FocusRequester() })
    }

    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("MemberRoleView -> LaunchedEffect()")
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
        //currentCongregation?.let { viewModel.onTextFieldEntered(MemberRoleInputEvent.Congregation(it.toCongregationsListItem())) }
        member.item?.let { viewModel.onTextFieldEntered(MemberRoleInputEvent.Member(it)) }
        MemberComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[MemberRoleFields.MEMBER_ROLE_MEMBER]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = MemberRoleFields.MEMBER_ROLE_MEMBER,
                        isFocused = focusState.isFocused
                    )
                },
            enabled = false,
            sharedViewModel = sharedViewModel,
            inputWrapper = member,
            onValueChange = { viewModel.onTextFieldEntered(MemberRoleInputEvent.Member(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        RoleComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[MemberRoleFields.MEMBER_ROLE_ROLE]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = MemberRoleFields.MEMBER_ROLE_ROLE,
                        isFocused = focusState.isFocused
                    )
                },
            memberId = member.item?.itemId!!,
            inputWrapper = role,
            onValueChange = { viewModel.onTextFieldEntered(MemberRoleInputEvent.Role(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        DatePickerComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[MemberRoleFields.MEMBER_ROLE_EXPIRED_DATE]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = MemberRoleFields.MEMBER_ROLE_EXPIRED_DATE,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.member_role_expired_date_hint,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done)
            },
            inputWrapper = roleExpiredDate,
            onValueChange = { viewModel.onTextFieldEntered(MemberRoleInputEvent.RoleExpiredDate(it)) },
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
            MemberRoleView(sharedViewModel = FavoriteCongregationViewModelImpl.previewModel)
        }
    }
}
