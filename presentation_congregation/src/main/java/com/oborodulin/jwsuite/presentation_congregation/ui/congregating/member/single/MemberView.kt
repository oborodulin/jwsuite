package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.single

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Call
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.oborodulin.home.common.ui.components.field.DatePickerComponent
import com.oborodulin.home.common.ui.components.field.ExposedDropdownMenuBoxComponent
import com.oborodulin.home.common.ui.components.field.TextFieldComponent
import com.oborodulin.home.common.ui.components.field.util.InputFocusRequester
import com.oborodulin.home.common.ui.components.field.util.inputProcess
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_congregation.R
import com.oborodulin.jwsuite.presentation_congregation.ui.FavoriteCongregationViewModel
import com.oborodulin.jwsuite.presentation_congregation.ui.FavoriteCongregationViewModelImpl
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.congregation.single.CongregationComboBox
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.group.single.GroupComboBox
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationsListItem
import timber.log.Timber

private const val TAG = "Congregating.MemberView"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MemberView(
    sharedViewModel: FavoriteCongregationViewModel<CongregationsListItem?>,
    viewModel: MemberViewModelImpl = hiltViewModel()
) {
    Timber.tag(TAG).d("MemberView(...) called")
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(viewModel.events, lifecycleOwner) {
        viewModel.events.flowWithLifecycle(
            lifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
    }

    Timber.tag(TAG).d("CollectAsStateWithLifecycle for all member fields")
    val congregation by viewModel.congregation.collectAsStateWithLifecycle()
    val group by viewModel.group.collectAsStateWithLifecycle()
    val memberNum by viewModel.memberNum.collectAsStateWithLifecycle()
    val memberName by viewModel.memberName.collectAsStateWithLifecycle()
    val surname by viewModel.surname.collectAsStateWithLifecycle()
    val patronymic by viewModel.patronymic.collectAsStateWithLifecycle()
    val pseudonym by viewModel.pseudonym.collectAsStateWithLifecycle()
    val phoneNumber by viewModel.phoneNumber.collectAsStateWithLifecycle()
    val memberType by viewModel.memberType.collectAsStateWithLifecycle()
    val dateOfBirth by viewModel.dateOfBirth.collectAsStateWithLifecycle()
    val dateOfBaptism by viewModel.dateOfBaptism.collectAsStateWithLifecycle()
    val inactiveDate by viewModel.inactiveDate.collectAsStateWithLifecycle()

    val memberTypes by viewModel.memberTypes.collectAsStateWithLifecycle()

    Timber.tag(TAG).d("Init Focus Requesters for all region fields")
    val focusRequesters: MutableMap<String, InputFocusRequester> = HashMap()
    enumValues<MemberFields>().forEach {
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
                .focusRequester(focusRequesters[MemberFields.MEMBER_CONGREGATION.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = MemberFields.MEMBER_CONGREGATION,
                        isFocused = focusState.isFocused
                    )
                },
            enabled = false,
            sharedViewModel = sharedViewModel,
            inputWrapper = congregation,
            onValueChange = { viewModel.onTextFieldEntered(MemberInputEvent.Congregation(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        GroupComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[MemberFields.MEMBER_GROUP.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = MemberFields.MEMBER_GROUP,
                        isFocused = focusState.isFocused
                    )
                },
            inputWrapper = group,
            sharedViewModel = sharedViewModel,
            onValueChange = { viewModel.onTextFieldEntered(MemberInputEvent.Group(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[MemberFields.MEMBER_NUM.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = MemberFields.MEMBER_NUM,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.member_num_hint,
            leadingPainterResId = com.oborodulin.home.common.R.drawable.ic_123_36,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
            },
            inputWrapper = memberNum,
            onValueChange = { viewModel.onTextFieldEntered(MemberInputEvent.MemberNum(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[MemberFields.MEMBER_SURNAME.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = MemberFields.MEMBER_SURNAME,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.member_surname_hint,
            leadingImageVector = Icons.Outlined.Person,
            keyboardOptions = remember {
                KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                )
            },
            inputWrapper = surname,
            onValueChange = { viewModel.onTextFieldEntered(MemberInputEvent.Surname(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[MemberFields.MEMBER_NAME.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = MemberFields.MEMBER_NAME,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.member_name_hint,
            keyboardOptions = remember {
                KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                )
            },
            inputWrapper = memberName,
            onValueChange = { viewModel.onTextFieldEntered(MemberInputEvent.MemberName(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[MemberFields.MEMBER_PATRONYMIC.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = MemberFields.MEMBER_PATRONYMIC,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.member_patronymic_hint,
            keyboardOptions = remember {
                KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                )
            },
            inputWrapper = patronymic,
            onValueChange = { viewModel.onTextFieldEntered(MemberInputEvent.Patronymic(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[MemberFields.MEMBER_PSEUDONYM.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = MemberFields.MEMBER_PSEUDONYM,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.member_pseudonym_hint,
            keyboardOptions = remember {
                KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                )
            },
            inputWrapper = pseudonym,
            onValueChange = { viewModel.onTextFieldEntered(MemberInputEvent.Pseudonym(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[MemberFields.MEMBER_PHONE_NUMBER.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = MemberFields.MEMBER_PHONE_NUMBER,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.member_phone_number_hint,
            leadingImageVector = Icons.Outlined.Call,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
            },
            inputWrapper = phoneNumber,
            onValueChange = { viewModel.onTextFieldEntered(MemberInputEvent.PhoneNumber(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        ExposedDropdownMenuBoxComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[MemberFields.MEMBER_PHONE_NUMBER.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = MemberFields.MEMBER_PHONE_NUMBER,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.member_type_hint,
            leadingPainterResId = R.drawable.ic_badge_36,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
            },
            inputWrapper = memberType,
            values = memberTypes.values.toList(), // resolve Enums to Resource
            keys = memberTypes.keys.map { it.name }, // Enums
            onValueChange = { viewModel.onTextFieldEntered(MemberInputEvent.MemberType(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        DatePickerComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[MemberFields.MEMBER_DATE_OF_BIRTH.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = MemberFields.MEMBER_DATE_OF_BIRTH,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.member_date_of_birth_hint,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
            },
            inputWrapper = dateOfBirth,
            onValueChange = { viewModel.onTextFieldEntered(MemberInputEvent.DateOfBirth(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        DatePickerComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[MemberFields.MEMBER_DATE_OF_BAPTISM.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = MemberFields.MEMBER_DATE_OF_BAPTISM,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.member_date_of_baptism_hint,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
            },
            inputWrapper = dateOfBaptism,
            onValueChange = { viewModel.onTextFieldEntered(MemberInputEvent.DateOfBaptism(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        DatePickerComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[MemberFields.MEMBER_INACTIVE_DATE.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = MemberFields.MEMBER_INACTIVE_DATE,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.member_inactive_date_hint,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done)
            },
            inputWrapper = inactiveDate,
            onValueChange = { viewModel.onTextFieldEntered(MemberInputEvent.InactiveDate(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
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
