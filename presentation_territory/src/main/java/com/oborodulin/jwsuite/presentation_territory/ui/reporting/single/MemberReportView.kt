package com.oborodulin.jwsuite.presentation_territory.ui.reporting.single

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
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.SharedViewModeled
import com.oborodulin.jwsuite.domain.model.congregation.Member
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_congregation.R
import com.oborodulin.jwsuite.presentation_congregation.ui.FavoriteCongregationViewModelImpl
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.congregation.single.CongregationComboBox
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.group.single.GroupComboBox
import timber.log.Timber
import java.util.EnumMap

private const val TAG = "Reporting.MemberView"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MemberView(
    sharedViewModel: SharedViewModeled<ListItemModel?>?,
    viewModel: MemberReportViewModelImpl = hiltViewModel()
) {
    Timber.tag(TAG).d("MemberView(...) called")
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(viewModel.events, lifecycleOwner) {
        viewModel.events.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }

    Timber.tag(TAG).d("Member: CollectAsStateWithLifecycle for all fields")
    val congregation by viewModel.house.collectAsStateWithLifecycle()
    val group by viewModel.room.collectAsStateWithLifecycle()
    val memberNum by viewModel.gender.collectAsStateWithLifecycle()
    val memberName by viewModel.age.collectAsStateWithLifecycle()
    val surname by viewModel.desc.collectAsStateWithLifecycle()
    val patronymic by viewModel.patronymic.collectAsStateWithLifecycle()
    val pseudonym by viewModel.pseudonym.collectAsStateWithLifecycle()
    val phoneNumber by viewModel.phoneNumber.collectAsStateWithLifecycle()
    val dateOfBirth by viewModel.dateOfBirth.collectAsStateWithLifecycle()
    val dateOfBaptism by viewModel.dateOfBaptism.collectAsStateWithLifecycle()
    val memberType by viewModel.reportMark.collectAsStateWithLifecycle()
    val movementDate by viewModel.movementDate.collectAsStateWithLifecycle()
    val loginExpiredDate by viewModel.loginExpiredDate.collectAsStateWithLifecycle()

    val memberTypes by viewModel.territoryMarks.collectAsStateWithLifecycle()

    Timber.tag(TAG).d("Member: Init Focus Requesters for all fields")
    val focusRequesters = EnumMap<MemberReportFields, InputFocusRequester>(MemberReportFields::class.java)
    enumValues<MemberReportFields>().forEach {
        focusRequesters[it] = InputFocusRequester(it, remember { FocusRequester() })
    }

    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("MemberView -> LaunchedEffect()")
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
                .focusRequester(focusRequesters[MemberReportFields.MEMBER_REPORT_HOUSE]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = MemberReportFields.MEMBER_REPORT_HOUSE,
                        isFocused = focusState.isFocused
                    )
                },
            enabled = false,
            sharedViewModel = sharedViewModel,
            inputWrapper = congregation,
            onValueChange = { viewModel.onTextFieldEntered(MemberReportInputEvent.House(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        GroupComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[MemberReportFields.MEMBER_REPORT_ROOM]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = MemberReportFields.MEMBER_REPORT_ROOM, isFocused = focusState.isFocused
                    )
                },
            inputWrapper = group,
            sharedViewModel = sharedViewModel,
            onValueChange = { groupNum ->
                viewModel.onTextFieldEntered(MemberReportInputEvent.Room(groupNum))
                viewModel.onInsert {
                    val pseudonymVal = Member.getPseudonym(
                        surname.value, memberName.value, groupNum.headline.toIntOrNull(),
                        memberNum.value
                    )
                    viewModel.onTextFieldEntered(MemberReportInputEvent.Pseudonym(pseudonymVal))
                }
            },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[MemberReportFields.MEMBER_REPORT_GENDER]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = MemberReportFields.MEMBER_REPORT_GENDER, isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.member_num_hint,
            leadingPainterResId = com.oborodulin.home.common.R.drawable.ic_123_36,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
            },
            inputWrapper = memberNum,
            onValueChange = { numInGroup ->
                viewModel.onTextFieldEntered(MemberReportInputEvent.Gender(numInGroup))
                viewModel.onInsert {
                    val pseudonymVal = Member.getPseudonym(
                        surname.value, memberName.value, group.item?.headline?.toIntOrNull(),
                        numInGroup
                    )
                    viewModel.onTextFieldEntered(MemberReportInputEvent.Pseudonym(pseudonymVal))
                }
            },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[MemberReportFields.MEMBER_REPORT_DESC]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = MemberReportFields.MEMBER_REPORT_DESC, isFocused = focusState.isFocused
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
            onValueChange = { value ->
                viewModel.onTextFieldEntered(MemberReportInputEvent.Desc(value))
                viewModel.onInsert {
                    val pseudonymVal = Member.getPseudonym(
                        value, memberName.value, group.item?.headline?.toIntOrNull(),
                        memberNum.value
                    )
                    viewModel.onTextFieldEntered(MemberReportInputEvent.Pseudonym(pseudonymVal))
                }
            },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[MemberReportFields.MEMBER_REPORT_AGE]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = MemberReportFields.MEMBER_REPORT_AGE,
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
            onValueChange = { name ->
                viewModel.onTextFieldEntered(MemberReportInputEvent.Age(name))
                viewModel.onInsert {
                    val pseudonymVal = Member.getPseudonym(
                        surname.value, name, group.item?.headline?.toIntOrNull(), memberNum.value
                    )
                    viewModel.onTextFieldEntered(MemberReportInputEvent.Pseudonym(pseudonymVal))
                }
            },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[MemberReportFields.MEMBER_PATRONYMIC]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = MemberReportFields.MEMBER_PATRONYMIC,
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
            onValueChange = { viewModel.onTextFieldEntered(MemberReportInputEvent.Patronymic(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[MemberReportFields.MEMBER_PSEUDONYM]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = MemberReportFields.MEMBER_PSEUDONYM,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.member_pseudonym_hint,
            keyboardOptions = remember {
                KeyboardOptions(
                    capitalization = KeyboardCapitalization.Characters,
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                )
            },
            inputWrapper = pseudonym,
            onValueChange = { viewModel.onTextFieldEntered(MemberReportInputEvent.Pseudonym(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[MemberReportFields.MEMBER_PHONE_NUMBER]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = MemberReportFields.MEMBER_PHONE_NUMBER,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.member_phone_number_hint,
            leadingImageVector = Icons.Outlined.Call,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
            },
            inputWrapper = phoneNumber,
            onValueChange = { viewModel.onTextFieldEntered(MemberReportInputEvent.PhoneNumber(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        DatePickerComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[MemberReportFields.MEMBER_DATE_OF_BIRTH]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = MemberReportFields.MEMBER_DATE_OF_BIRTH,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.member_date_of_birth_hint,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
            },
            inputWrapper = dateOfBirth,
            onValueChange = { viewModel.onTextFieldEntered(MemberReportInputEvent.DateOfBirth(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        DatePickerComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[MemberReportFields.MEMBER_DATE_OF_BAPTISM]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = MemberReportFields.MEMBER_DATE_OF_BAPTISM,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.member_date_of_baptism_hint,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
            },
            inputWrapper = dateOfBaptism,
            onValueChange = { viewModel.onTextFieldEntered(MemberReportInputEvent.DateOfBaptism(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        ExposedDropdownMenuBoxComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[MemberReportFields.MEMBER_PHONE_NUMBER]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = MemberReportFields.MEMBER_PHONE_NUMBER,
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
            onValueChange = { viewModel.onTextFieldEntered(MemberReportInputEvent.ReportMark(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        DatePickerComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[MemberReportFields.MEMBER_MOVEMENT_DATE]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = MemberReportFields.MEMBER_MOVEMENT_DATE,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.member_movement_date_hint,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
            },
            inputWrapper = movementDate,
            onValueChange = { viewModel.onTextFieldEntered(MemberReportInputEvent.MovementDate(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        DatePickerComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[MemberReportFields.MEMBER_LOGIN_EXPIRED_DATE]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = MemberReportFields.MEMBER_LOGIN_EXPIRED_DATE,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.member_login_expired_date_hint,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done)
            },
            inputWrapper = loginExpiredDate,
            onValueChange = { viewModel.onTextFieldEntered(MemberReportInputEvent.LoginExpiredDate(it)) },
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
            MemberView(sharedViewModel = FavoriteCongregationViewModelImpl.previewModel)
        }
    }
}
