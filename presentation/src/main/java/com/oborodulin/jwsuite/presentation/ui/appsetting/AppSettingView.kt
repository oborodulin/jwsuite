package com.oborodulin.jwsuite.presentation.ui.appsetting

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
import com.oborodulin.home.common.ui.components.field.TextFieldComponent
import com.oborodulin.home.common.ui.components.field.util.InputFocusRequester
import com.oborodulin.home.common.ui.components.field.util.inputProcess
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_congregation.R
import timber.log.Timber
import java.util.EnumMap

private const val TAG = "Presentation.AppSettingView"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AppSettingView(
    appSettingsUiModel: AppSettingsUiModel,
    viewModel: AppSettingViewModelImpl = hiltViewModel()
) {
    Timber.tag(TAG).d("AppSettingView(...) called")
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(viewModel.events, lifecycleOwner) {
        viewModel.events.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }

    Timber.tag(TAG).d("AppSetting: CollectAsStateWithLifecycle for all fields")
    val territoryProcessingPeriod by viewModel.territoryProcessingPeriod.collectAsStateWithLifecycle()
    val territoryAtHandPeriod by viewModel.territoryAtHandPeriod.collectAsStateWithLifecycle()
    val territoryIdlePeriod by viewModel.territoryIdlePeriod.collectAsStateWithLifecycle()
    val territoryRoomsLimit by viewModel.territoryRoomsLimit.collectAsStateWithLifecycle()
    val territoryMaxRooms by viewModel.territoryMaxRooms.collectAsStateWithLifecycle()

    Timber.tag(TAG).d("AppSetting: Init Focus Requesters for all fields")
    val focusRequesters =
        EnumMap<AppSettingFields, InputFocusRequester>(AppSettingFields::class.java)
    enumValues<AppSettingFields>().forEach {
        focusRequesters[it] = InputFocusRequester(it, remember { FocusRequester() })
    }

    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("AppSettingView -> LaunchedEffect()")
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
                .focusRequester(focusRequesters[AppSettingFields.TERRITORY_PROCESSING_PERIOD]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = AppSettingFields.TERRITORY_PROCESSING_PERIOD,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.member_num_hint,
            leadingPainterResId = com.oborodulin.home.common.R.drawable.ic_123_36,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
            },
            inputWrapper = territoryProcessingPeriod,
            onValueChange = { numInGroup ->
                viewModel.onTextFieldEntered(
                    AppSettingInputEvent.TerritoryProcessingPeriod(
                        numInGroup
                    )
                )
                viewModel.onInsert {
                    val pseudonymVal = viewModel.getPseudonym(
                        territoryIdlePeriod.value,
                        territoryAtHandPeriod.value,
                        group.item?.headline?.toIntOrNull(),
                        numInGroup
                    )
                    viewModel.onTextFieldEntered(AppSettingInputEvent.TerritoryMaxRooms(pseudonymVal))
                }
            },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[AppSettingFields.TERRITORY_IDLE_PERIOD]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = AppSettingFields.TERRITORY_IDLE_PERIOD,
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
            inputWrapper = territoryIdlePeriod,
            onValueChange = { value ->
                viewModel.onTextFieldEntered(AppSettingInputEvent.TerritoryIdlePeriod(value))
                viewModel.onInsert {
                    val pseudonymVal = viewModel.getPseudonym(
                        value, territoryAtHandPeriod.value, group.item?.headline?.toIntOrNull(),
                        territoryProcessingPeriod.value
                    )
                    viewModel.onTextFieldEntered(AppSettingInputEvent.TerritoryMaxRooms(pseudonymVal))
                }
            },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[AppSettingFields.TERRITORY_AT_HAND_PERIOD]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = AppSettingFields.TERRITORY_AT_HAND_PERIOD,
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
            inputWrapper = territoryAtHandPeriod,
            onValueChange = { name ->
                viewModel.onTextFieldEntered(AppSettingInputEvent.TerritoryAtHandPeriod(name))
                viewModel.onInsert {
                    val pseudonymVal = viewModel.getPseudonym(
                        territoryIdlePeriod.value, name, group.item?.headline?.toIntOrNull(),
                        territoryProcessingPeriod.value
                    )
                    viewModel.onTextFieldEntered(AppSettingInputEvent.TerritoryMaxRooms(pseudonymVal))
                }
            },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[AppSettingFields.TERRITORY_ROOMS_LIMIT]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = AppSettingFields.TERRITORY_ROOMS_LIMIT,
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
            inputWrapper = territoryRoomsLimit,
            onValueChange = {
                viewModel.onTextFieldEntered(
                    AppSettingInputEvent.TerritoryRoomsLimit(
                        it
                    )
                )
            },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[AppSettingFields.TERRITORY_MAX_ROOMS]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = AppSettingFields.TERRITORY_MAX_ROOMS,
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
            inputWrapper = territoryMaxRooms,
            onValueChange = { viewModel.onTextFieldEntered(AppSettingInputEvent.TerritoryMaxRooms(it)) },
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
            //AppSettingView(sharedViewModel = FavoriteCongregationViewModelImpl.previewModel)
        }
    }
}
