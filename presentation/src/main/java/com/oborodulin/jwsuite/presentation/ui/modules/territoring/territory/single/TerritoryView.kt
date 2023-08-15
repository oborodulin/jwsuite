package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.single

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.oborodulin.home.common.ui.components.field.DatePickerComponent
import com.oborodulin.home.common.ui.components.field.ExposedDropdownMenuBoxComponent
import com.oborodulin.home.common.ui.components.field.TextFieldComponent
import com.oborodulin.home.common.ui.components.field.util.InputFocusRequester
import com.oborodulin.home.common.ui.components.field.util.inputProcess
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.ui.modules.FavoriteCongregationViewModel
import com.oborodulin.jwsuite.presentation.ui.modules.FavoriteCongregationViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.congregation.single.CongregationComboBox
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.group.single.GroupComboBox
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.CongregationsListItem
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import timber.log.Timber

private const val TAG = "Congregating.MemberView"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MemberView(
    sharedViewModel: FavoriteCongregationViewModel<CongregationsListItem?>?,
    memberViewModel: TerritoryViewModelImpl = hiltViewModel()
) {
    Timber.tag(TAG).d("MemberView(...) called")
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(memberViewModel.events, lifecycleOwner) {
        memberViewModel.events.flowWithLifecycle(
            lifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
    }

    Timber.tag(TAG).d("CollectAsStateWithLifecycle for all member fields")
    val congregation by memberViewModel.congregation.collectAsStateWithLifecycle()
    val group by memberViewModel.group.collectAsStateWithLifecycle()
    val memberNum by memberViewModel.memberNum.collectAsStateWithLifecycle()
    val memberName by memberViewModel.memberName.collectAsStateWithLifecycle()
    val surname by memberViewModel.surname.collectAsStateWithLifecycle()
    val patronymic by memberViewModel.patronymic.collectAsStateWithLifecycle()
    val pseudonym by memberViewModel.pseudonym.collectAsStateWithLifecycle()
    val phoneNumber by memberViewModel.phoneNumber.collectAsStateWithLifecycle()
    val memberType by memberViewModel.memberType.collectAsStateWithLifecycle()
    val dateOfBirth by memberViewModel.dateOfBirth.collectAsStateWithLifecycle()
    val dateOfBaptism by memberViewModel.dateOfBaptism.collectAsStateWithLifecycle()
    val inactiveDate by memberViewModel.inactiveDate.collectAsStateWithLifecycle()

    val memberTypes by memberViewModel.memberTypes.collectAsStateWithLifecycle()

    Timber.tag(TAG).d("Init Focus Requesters for all region fields")
    val focusRequesters: MutableMap<String, InputFocusRequester> = HashMap()
    enumValues<TerritoryFields>().forEach {
        focusRequesters[it.name] = InputFocusRequester(it, remember { FocusRequester() })
    }

    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("MemberView(...): LaunchedEffect()")
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
                .focusRequester(focusRequesters[TerritoryFields.MEMBER_CONGREGATION.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    memberViewModel.onTextFieldFocusChanged(
                        focusedField = TerritoryFields.MEMBER_CONGREGATION,
                        isFocused = focusState.isFocused
                    )
                },
            enabled = false,
            sharedViewModel = sharedViewModel,
            inputWrapper = congregation,
            onValueChange = { memberViewModel.onTextFieldEntered(TerritoryInputEvent.Congregation(it)) },
            onImeKeyAction = memberViewModel::moveFocusImeAction
        )
        GroupComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[TerritoryFields.MEMBER_GROUP.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    memberViewModel.onTextFieldFocusChanged(
                        focusedField = TerritoryFields.MEMBER_GROUP,
                        isFocused = focusState.isFocused
                    )
                },
            inputWrapper = group,
            sharedViewModel = sharedViewModel,
            onValueChange = { memberViewModel.onTextFieldEntered(TerritoryInputEvent.Group(it)) },
            onImeKeyAction = memberViewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[TerritoryFields.MEMBER_NUM.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    memberViewModel.onTextFieldFocusChanged(
                        focusedField = TerritoryFields.MEMBER_NUM,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.member_num_hint,
            leadingIcon = {
                Icon(painterResource(com.oborodulin.home.common.R.drawable.ic_123_36), null)
            },
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
            },
            inputWrapper = memberNum,
            onValueChange = { memberViewModel.onTextFieldEntered(TerritoryInputEvent.TerritoryNum(it)) },
            onImeKeyAction = memberViewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[TerritoryFields.MEMBER_SURNAME.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    memberViewModel.onTextFieldFocusChanged(
                        focusedField = TerritoryFields.MEMBER_SURNAME,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.member_surname_hint,
            leadingIcon = { Icon(painterResource(R.drawable.ic_person_36), null) },
            keyboardOptions = remember {
                KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            },
            inputWrapper = surname,
            onValueChange = { memberViewModel.onTextFieldEntered(TerritoryInputEvent.Surname(it)) },
            onImeKeyAction = memberViewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[TerritoryFields.MEMBER_NAME.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    memberViewModel.onTextFieldFocusChanged(
                        focusedField = TerritoryFields.MEMBER_NAME,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.member_name_hint,
            keyboardOptions = remember {
                KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            },
            inputWrapper = memberName,
            onValueChange = { memberViewModel.onTextFieldEntered(TerritoryInputEvent.TerritoryName(it)) },
            onImeKeyAction = memberViewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[TerritoryFields.MEMBER_PATRONYMIC.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    memberViewModel.onTextFieldFocusChanged(
                        focusedField = TerritoryFields.MEMBER_PATRONYMIC,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.member_patronymic_hint,
            keyboardOptions = remember {
                KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            },
            inputWrapper = patronymic,
            onValueChange = { memberViewModel.onTextFieldEntered(TerritoryInputEvent.Patronymic(it)) },
            onImeKeyAction = memberViewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[TerritoryFields.MEMBER_PSEUDONYM.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    memberViewModel.onTextFieldFocusChanged(
                        focusedField = TerritoryFields.MEMBER_PSEUDONYM,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.member_pseudonym_hint,
            keyboardOptions = remember {
                KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            },
            inputWrapper = pseudonym,
            onValueChange = { memberViewModel.onTextFieldEntered(TerritoryInputEvent.Pseudonym(it)) },
            onImeKeyAction = memberViewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[TerritoryFields.MEMBER_PHONE_NUMBER.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    memberViewModel.onTextFieldFocusChanged(
                        focusedField = TerritoryFields.MEMBER_PHONE_NUMBER,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.member_phone_number_hint,
            leadingIcon = { Icon(painterResource(R.drawable.ic_phone_36), null) },
            keyboardOptions = remember {
                KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            },
            inputWrapper = phoneNumber,
            onValueChange = { memberViewModel.onTextFieldEntered(TerritoryInputEvent.PhoneNumber(it)) },
            onImeKeyAction = memberViewModel::moveFocusImeAction
        )
        ExposedDropdownMenuBoxComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[TerritoryFields.MEMBER_PHONE_NUMBER.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    memberViewModel.onTextFieldFocusChanged(
                        focusedField = TerritoryFields.MEMBER_PHONE_NUMBER,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.member_type_hint,
            leadingIcon = { Icon(painterResource(R.drawable.ic_badge_36), null) },
            keyboardOptions = remember {
                KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            },
            inputWrapper = memberType,
            values = memberTypes.values.toList(), // resolve Enums to Resource
            keys = memberTypes.keys.map { it.name }, // Enums
            onValueChange = { memberViewModel.onTextFieldEntered(TerritoryInputEvent.TerritoryType(it)) },
            onImeKeyAction = memberViewModel::moveFocusImeAction
        )
        DatePickerComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[TerritoryFields.MEMBER_DATE_OF_BIRTH.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    memberViewModel.onTextFieldFocusChanged(
                        focusedField = TerritoryFields.MEMBER_DATE_OF_BIRTH,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.member_date_of_birth_hint,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
            },
            inputWrapper = dateOfBirth,
            onValueChange = { memberViewModel.onTextFieldEntered(TerritoryInputEvent.DateOfBirth(it)) },
            onImeKeyAction = memberViewModel::moveFocusImeAction
        )
        DatePickerComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[TerritoryFields.MEMBER_DATE_OF_BAPTISM.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    memberViewModel.onTextFieldFocusChanged(
                        focusedField = TerritoryFields.MEMBER_DATE_OF_BAPTISM,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.member_date_of_baptism_hint,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
            },
            inputWrapper = dateOfBaptism,
            onValueChange = { memberViewModel.onTextFieldEntered(TerritoryInputEvent.DateOfBaptism(it)) },
            onImeKeyAction = memberViewModel::moveFocusImeAction
        )
        DatePickerComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[TerritoryFields.MEMBER_INACTIVE_DATE.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    memberViewModel.onTextFieldFocusChanged(
                        focusedField = TerritoryFields.MEMBER_INACTIVE_DATE,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.member_inactive_date_hint,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done)
            },
            inputWrapper = inactiveDate,
            onValueChange = { memberViewModel.onTextFieldEntered(TerritoryInputEvent.InactiveDate(it)) },
            onImeKeyAction = memberViewModel::moveFocusImeAction
        )
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewGroupView() {
    val ctx = LocalContext.current
    JWSuiteTheme {
        Surface {
            MemberView(sharedViewModel = FavoriteCongregationViewModelImpl.previewModel)
        }
    }
}
