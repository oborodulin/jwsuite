package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.role.single

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
import androidx.compose.foundation.text.KeyboardOptions
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
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_INPUT
import com.oborodulin.home.common.util.OnImeKeyAction
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_congregation.R
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.list.MembersListViewModelImpl
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.single.MemberComboBox
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.role.single.RoleComboBox
import timber.log.Timber
import java.time.Instant
import java.util.EnumMap

private const val TAG = "Congregating.MemberRoleView"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MemberRoleView(
    memberRoleViewModel: MemberRoleViewModelImpl = hiltViewModel(),
    membersListViewModel: MembersListViewModelImpl = hiltViewModel(),
    handleSaveAction: OnImeKeyAction
) {
    Timber.tag(TAG).d("MemberRoleView(...) called")
    val appState = LocalAppState.current
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(memberRoleViewModel.events, lifecycleOwner) {
        memberRoleViewModel.events.flowWithLifecycle(
            lifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
    }

    Timber.tag(TAG).d("MemberRole: CollectAsStateWithLifecycle for all fields")
    val member by memberRoleViewModel.member.collectAsStateWithLifecycle()
    val role by memberRoleViewModel.role.collectAsStateWithLifecycle()
    val roleExpiredDate by memberRoleViewModel.roleExpiredDate.collectAsStateWithLifecycle()

    Timber.tag(TAG).d("MemberRole: Init Focus Requesters for all fields")
    val focusRequesters =
        EnumMap<MemberRoleFields, InputFocusRequester>(MemberRoleFields::class.java)
    enumValues<MemberRoleFields>().forEach {
        focusRequesters[it] = InputFocusRequester(it, remember { FocusRequester() })
    }
    val currentCongregation =
        appState.congregationSharedViewModel.value?.sharedFlow?.collectAsStateWithLifecycle()?.value
    val selectedMember =
        appState.memberSharedViewModel.value?.sharedFlow?.collectAsStateWithLifecycle()?.value
    var currentMember = member.item
    Timber.tag(TAG)
        .d(
            "currentCongregation = %s; selectedMember = %s; currentMember = %s",
            currentCongregation, selectedMember, currentMember
        )
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("MemberRoleView -> LaunchedEffect()")
        if (currentMember == null) {
            currentMember = selectedMember
            currentCongregation?.let {
                memberRoleViewModel.onTextFieldEntered(MemberRoleInputEvent.Congregation(it))
            }
            currentMember?.let {
                memberRoleViewModel.onTextFieldEntered(MemberRoleInputEvent.Member(it))
            }
        }
        events.collect { event ->
            if (LOG_FLOW_INPUT) Timber.tag(TAG)
                .d("IF# Collect input events flow: %s", event.javaClass.name)
            inputProcess(context, focusManager, keyboardController, event, focusRequesters)
        }
    }
    Timber.tag(TAG).d("currentMember = %s", currentMember)
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
                .focusRequester(focusRequesters[MemberRoleFields.MEMBER_ROLE_MEMBER]!!.focusRequester)
                .onFocusChanged { focusState ->
                    memberRoleViewModel.onTextFieldFocusChanged(
                        focusedField = MemberRoleFields.MEMBER_ROLE_MEMBER,
                        isFocused = focusState.isFocused
                    )
                },
            enabled = member.item?.itemId == null,
            sharedViewModel = appState.congregationSharedViewModel.value,
            inputWrapper = member,
            onValueChange = { memberRoleViewModel.onTextFieldEntered(MemberRoleInputEvent.Member(it)) },
            onImeKeyAction = memberRoleViewModel::moveFocusImeAction
        )
        RoleComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[MemberRoleFields.MEMBER_ROLE_ROLE]!!.focusRequester)
                .onFocusChanged { focusState ->
                    memberRoleViewModel.onTextFieldFocusChanged(
                        focusedField = MemberRoleFields.MEMBER_ROLE_ROLE,
                        isFocused = focusState.isFocused
                    )
                },
            memberId = member.item?.itemId!!,
            inputWrapper = role,
            onValueChange = { memberRoleViewModel.onTextFieldEntered(MemberRoleInputEvent.Role(it)) },
            onImeKeyAction = memberRoleViewModel::moveFocusImeAction
        )
        DatePickerComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[MemberRoleFields.MEMBER_ROLE_EXPIRED_DATE]!!.focusRequester)
                .onFocusChanged { focusState ->
                    memberRoleViewModel.onTextFieldFocusChanged(
                        focusedField = MemberRoleFields.MEMBER_ROLE_EXPIRED_DATE,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.member_role_expired_date_hint,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done)
            },
            inputWrapper = roleExpiredDate,
            onValueChange = {
                memberRoleViewModel.onTextFieldEntered(MemberRoleInputEvent.RoleExpiredDate(it))
            },
            dateValidator = { timestamp -> timestamp > Instant.now().toEpochMilli() },
            onImeKeyAction = memberRoleViewModel::moveFocusImeAction
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
            MemberRoleView() {}//sharedViewModel = FavoriteCongregationViewModelImpl.previewModel)
        }
    }
}
