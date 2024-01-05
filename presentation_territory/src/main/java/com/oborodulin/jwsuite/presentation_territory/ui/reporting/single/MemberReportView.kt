package com.oborodulin.jwsuite.presentation_territory.ui.reporting.single

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
import com.oborodulin.home.common.ui.components.field.ExposedDropdownMenuBoxComponent
import com.oborodulin.home.common.ui.components.field.TextFieldComponent
import com.oborodulin.home.common.ui.components.field.util.InputFocusRequester
import com.oborodulin.home.common.ui.components.field.util.inputProcess
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.SharedViewModeled
import com.oborodulin.jwsuite.domain.model.congregation.Member
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_congregation.R
import com.oborodulin.jwsuite.presentation_congregation.ui.FavoriteCongregationViewModelImpl
import com.oborodulin.jwsuite.presentation_territory.ui.housing.house.single.HouseComboBox
import com.oborodulin.jwsuite.presentation_territory.ui.housing.house.single.HouseViewModelImpl
import com.oborodulin.jwsuite.presentation_territory.ui.housing.room.single.RoomComboBox
import com.oborodulin.jwsuite.presentation_territory.ui.housing.room.single.RoomViewModelImpl
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryMemberReportUi
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorystreet.single.TerritoryStreetComboBox
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorystreet.single.TerritoryStreetViewModelImpl
import timber.log.Timber
import java.util.EnumMap

private const val TAG = "Reporting.MemberReportView"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MemberReportView(
    uiModel: TerritoryMemberReportUi,
    sharedViewModel: SharedViewModeled<ListItemModel?>?,
    memberReportViewModel: MemberReportViewModelImpl = hiltViewModel(),
    territoryStreetViewModel: TerritoryStreetViewModelImpl = hiltViewModel(),
    houseViewModel: HouseViewModelImpl = hiltViewModel(),
    roomViewModel: RoomViewModelImpl = hiltViewModel(),
    memberReportInput: NavigationInput.MemberReportInput
) {
    Timber.tag(TAG).d("MemberReportView(...) called")
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(memberReportViewModel.events, lifecycleOwner) {
        memberReportViewModel.events.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }

    Timber.tag(TAG).d("MemberReport: CollectAsStateWithLifecycle for all fields")
    val territoryStreet by memberReportViewModel.territoryStreet.collectAsStateWithLifecycle()
    val house by memberReportViewModel.house.collectAsStateWithLifecycle()
    val room by memberReportViewModel.room.collectAsStateWithLifecycle()
    val reportMark by memberReportViewModel.reportMark.collectAsStateWithLifecycle()
    val language by memberReportViewModel.language.collectAsStateWithLifecycle()
    val gender by memberReportViewModel.gender.collectAsStateWithLifecycle()
    val age by memberReportViewModel.age.collectAsStateWithLifecycle()
    val desc by memberReportViewModel.desc.collectAsStateWithLifecycle()

    val reportMarks by memberReportViewModel.reportMarks.collectAsStateWithLifecycle()

    Timber.tag(TAG).d("MemberReport: Init Focus Requesters for all fields")
    val focusRequesters =
        EnumMap<MemberReportFields, InputFocusRequester>(MemberReportFields::class.java)
    enumValues<MemberReportFields>().forEach {
        focusRequesters[it] = InputFocusRequester(it, remember { FocusRequester() })
    }

    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("MemberReportView -> LaunchedEffect()")
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
        TerritoryStreetComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[MemberReportFields.MEMBER_REPORT_TERRITORY_STREET]!!.focusRequester)
                .onFocusChanged { focusState ->
                    memberReportViewModel.onTextFieldFocusChanged(
                        focusedField = MemberReportFields.MEMBER_REPORT_TERRITORY_STREET,
                        isFocused = focusState.isFocused
                    )
                },
            enabled = false,
            sharedViewModel = sharedViewModel,
            inputWrapper = territoryStreet,
            onValueChange = { memberReportViewModel.onTextFieldEntered(MemberReportInputEvent.House(it)) },
            onImeKeyAction = memberReportViewModel::moveFocusImeAction
        )
        HouseComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[MemberReportFields.MEMBER_REPORT_HOUSE]!!.focusRequester)
                .onFocusChanged { focusState ->
                    memberReportViewModel.onTextFieldFocusChanged(
                        focusedField = MemberReportFields.MEMBER_REPORT_HOUSE,
                        isFocused = focusState.isFocused
                    )
                },
            enabled = false,
            sharedViewModel = sharedViewModel,
            inputWrapper = house,
            onValueChange = { memberReportViewModel.onTextFieldEntered(MemberReportInputEvent.House(it)) },
            onImeKeyAction = memberReportViewModel::moveFocusImeAction
        )
        RoomComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[MemberReportFields.MEMBER_REPORT_ROOM]!!.focusRequester)
                .onFocusChanged { focusState ->
                    memberReportViewModel.onTextFieldFocusChanged(
                        focusedField = MemberReportFields.MEMBER_REPORT_ROOM,
                        isFocused = focusState.isFocused
                    )
                },
            enabled = false,
            sharedViewModel = sharedViewModel,
            inputWrapper = room,
            onValueChange = { groupNum ->
                memberReportViewModel.onTextFieldEntered(MemberReportInputEvent.Room(groupNum))
                memberReportViewModel.onInsert {
                    val pseudonymVal = Member.getPseudonym(
                        desc.value, age.value, groupNum.headline.toIntOrNull(),
                        gender.value
                    )
                }
            },
            onImeKeyAction = memberReportViewModel::moveFocusImeAction
        )
        ExposedDropdownMenuBoxComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[MemberReportFields.MEMBER_REPORT_MARK]!!.focusRequester)
                .onFocusChanged { focusState ->
                    memberReportViewModel.onTextFieldFocusChanged(
                        focusedField = MemberReportFields.MEMBER_REPORT_MARK,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.member_type_hint,
            leadingPainterResId = R.drawable.ic_badge_36,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
            },
            inputWrapper = reportMark,
            values = reportMarks.values.toList(), // resolve Enums to Resource
            keys = reportMarks.keys.map { it.name }, // Enums
            onValueChange = {
                memberReportViewModel.onTextFieldEntered(MemberReportInputEvent.MemberReportMark(it))
            },
            onImeKeyAction = memberReportViewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[MemberReportFields.MEMBER_REPORT_GENDER]!!.focusRequester)
                .onFocusChanged { focusState ->
                    memberReportViewModel.onTextFieldFocusChanged(
                        focusedField = MemberReportFields.MEMBER_REPORT_GENDER,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.member_num_hint,
            leadingPainterResId = com.oborodulin.home.common.R.drawable.ic_123_36,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
            },
            inputWrapper = gender,
            onValueChange = { numInGroup ->
                memberReportViewModel.onTextFieldEntered(MemberReportInputEvent.Gender(numInGroup))
                memberReportViewModel.onInsert {
                    val pseudonymVal = Member.getPseudonym(
                        desc.value, age.value, room.item?.headline?.toIntOrNull(),
                        numInGroup
                    )
                    memberReportViewModel.onTextFieldEntered(MemberReportInputEvent.Pseudonym(pseudonymVal))
                }
            },
            onImeKeyAction = memberReportViewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[MemberReportFields.MEMBER_REPORT_AGE]!!.focusRequester)
                .onFocusChanged { focusState ->
                    memberReportViewModel.onTextFieldFocusChanged(
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
            inputWrapper = age,
            onValueChange = { name ->
                memberReportViewModel.onTextFieldEntered(MemberReportInputEvent.Age(name))
                memberReportViewModel.onInsert {
                    val pseudonymVal = Member.getPseudonym(
                        desc.value, name, room.item?.headline?.toIntOrNull(), gender.value
                    )
                    memberReportViewModel.onTextFieldEntered(MemberReportInputEvent.Pseudonym(pseudonymVal))
                }
            },
            onImeKeyAction = memberReportViewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[MemberReportFields.MEMBER_REPORT_DESC]!!.focusRequester)
                .onFocusChanged { focusState ->
                    memberReportViewModel.onTextFieldFocusChanged(
                        focusedField = MemberReportFields.MEMBER_REPORT_DESC,
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
            inputWrapper = desc,
            onValueChange = { value ->
                memberReportViewModel.onTextFieldEntered(MemberReportInputEvent.Desc(value))
                memberReportViewModel.onInsert {
                    val pseudonymVal = Member.getPseudonym(
                        value, age.value, room.item?.headline?.toIntOrNull(),
                        gender.value
                    )
                    memberReportViewModel.onTextFieldEntered(MemberReportInputEvent.Pseudonym(pseudonymVal))
                }
            },
            onImeKeyAction = memberReportViewModel::moveFocusImeAction
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
            MemberReportView(sharedViewModel = FavoriteCongregationViewModelImpl.previewModel)
        }
    }
}
