package com.oborodulin.jwsuite.presentation_territory.ui.reporting.single

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
import com.oborodulin.home.common.ui.components.radio.RadioBooleanComponent
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.SharedViewModeled
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_INPUT
import com.oborodulin.home.common.util.OnImeKeyAction
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_congregation.ui.FavoriteCongregationViewModelImpl
import com.oborodulin.jwsuite.presentation_territory.R
import com.oborodulin.jwsuite.presentation_territory.ui.housing.house.single.HouseComboBox
import com.oborodulin.jwsuite.presentation_territory.ui.housing.room.single.RoomComboBox
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryMemberReportUi
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single.TerritoryViewModelImpl
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorystreet.single.TerritoryStreetComboBox
import timber.log.Timber
import java.util.EnumMap

private const val TAG = "Reporting.MemberReportView"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MemberReportView(
    uiModel: TerritoryMemberReportUi,
    sharedViewModel: SharedViewModeled<ListItemModel?>?,
    viewModel: MemberReportViewModelImpl = hiltViewModel(),
    territoryViewModel: TerritoryViewModelImpl = hiltViewModel(),
    handleSaveAction: OnImeKeyAction
) {
    Timber.tag(TAG).d("MemberReportView(...) called")
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(viewModel.events, lifecycleOwner) {
        viewModel.events.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }

    Timber.tag(TAG).d("MemberReport: CollectAsStateWithLifecycle for all fields")
    val territoryStreet by viewModel.territoryStreet.collectAsStateWithLifecycle()
    val house by viewModel.house.collectAsStateWithLifecycle()
    val room by viewModel.room.collectAsStateWithLifecycle()
    val reportMark by viewModel.reportMark.collectAsStateWithLifecycle()
    val language by viewModel.language.collectAsStateWithLifecycle()
    val gender by viewModel.gender.collectAsStateWithLifecycle()
    val age by viewModel.age.collectAsStateWithLifecycle()
    val reportDesc by viewModel.reportDesc.collectAsStateWithLifecycle()

    val reportMarks by viewModel.reportMarks.collectAsStateWithLifecycle()

    Timber.tag(TAG).d("MemberReport: Init Focus Requesters for all fields")
    val focusRequesters =
        EnumMap<MemberReportFields, InputFocusRequester>(MemberReportFields::class.java)
    enumValues<MemberReportFields>().forEach {
        focusRequesters[it] = InputFocusRequester(it, remember { FocusRequester() })
    }

    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("MemberReportView -> LaunchedEffect()")
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
        territoryStreet.item?.itemId?.let {
            TerritoryStreetComboBox(
                modifier = Modifier
                    .focusRequester(focusRequesters[MemberReportFields.MEMBER_REPORT_TERRITORY_STREET]!!.focusRequester)
                    .onFocusChanged { focusState ->
                        viewModel.onTextFieldFocusChanged(
                            focusedField = MemberReportFields.MEMBER_REPORT_TERRITORY_STREET,
                            isFocused = focusState.isFocused
                        )
                    },
                enabled = false,
                territoryId = uiModel.territoryId!!,
                sharedViewModel = sharedViewModel,
                territoryViewModel = territoryViewModel,
                inputWrapper = territoryStreet,
                onValueChange = {
                    viewModel.onTextFieldEntered(MemberReportInputEvent.TerritoryStreet(it))
                },
                onImeKeyAction = viewModel::moveFocusImeAction
            )
        }
        house.item?.itemId?.let {
            HouseComboBox(
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
                inputWrapper = house,
                onValueChange = { viewModel.onTextFieldEntered(MemberReportInputEvent.House(it)) },
                onImeKeyAction = viewModel::moveFocusImeAction
            )
        }
        room.item?.itemId?.let {
            RoomComboBox(
                modifier = Modifier
                    .focusRequester(focusRequesters[MemberReportFields.MEMBER_REPORT_ROOM]!!.focusRequester)
                    .onFocusChanged { focusState ->
                        viewModel.onTextFieldFocusChanged(
                            focusedField = MemberReportFields.MEMBER_REPORT_ROOM,
                            isFocused = focusState.isFocused
                        )
                    },
                enabled = false,
                sharedViewModel = sharedViewModel,
                inputWrapper = room,
                onValueChange = { viewModel.onTextFieldEntered(MemberReportInputEvent.Room(it)) },
                onImeKeyAction = viewModel::moveFocusImeAction
            )
        }
        ExposedDropdownMenuBoxComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[MemberReportFields.MEMBER_REPORT_MARK]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = MemberReportFields.MEMBER_REPORT_MARK,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.territory_report_mark_hint,
            leadingPainterResId = R.drawable.ic_report_mark_36,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
            },
            inputWrapper = reportMark,
            values = reportMarks.values.toList(), // resolve Enums to Resource
            keys = reportMarks.keys.map { it.name }, // Enums
            onValueChange = {
                viewModel.onTextFieldEntered(MemberReportInputEvent.MemberReportMark(it))
            },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        RadioBooleanComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[MemberReportFields.MEMBER_REPORT_GENDER]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = MemberReportFields.MEMBER_REPORT_GENDER,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.territory_report_gender_hint,
            painterResId = R.drawable.ic_gender_36,
            inputWrapper = gender,
            onValueChange = { viewModel.onTextFieldEntered(MemberReportInputEvent.Gender(it)) }
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
            labelResId = R.string.territory_report_age_hint,
            leadingPainterResId = com.oborodulin.home.common.R.drawable.ic_123_36,
            keyboardOptions = remember {
                KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword, imeAction = ImeAction.Next
                )
            },
            inputWrapper = age,
            onValueChange = { viewModel.onTextFieldEntered(MemberReportInputEvent.Age(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[MemberReportFields.MEMBER_REPORT_DESC]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = MemberReportFields.MEMBER_REPORT_DESC,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.territory_desc_hint,
            leadingPainterResId = R.drawable.ic_text_snippet_36,
            keyboardOptions = remember {
                KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                )
            },
            inputWrapper = reportDesc,
            onValueChange = { viewModel.onTextFieldEntered(MemberReportInputEvent.Desc(it)) },
            onImeKeyAction = handleSaveAction
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
            MemberReportView(
                uiModel = MemberReportViewModelImpl.previewUiModel(ctx),
                sharedViewModel = FavoriteCongregationViewModelImpl.previewModel
            ) {}
        }
    }
}
