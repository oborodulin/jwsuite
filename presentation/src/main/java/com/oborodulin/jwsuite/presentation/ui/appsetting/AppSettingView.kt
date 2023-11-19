package com.oborodulin.jwsuite.presentation.ui.appsetting

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.oborodulin.home.common.ui.components.datatable.SimpleDataTableComponent
import com.oborodulin.home.common.ui.components.field.TextFieldComponent
import com.oborodulin.home.common.ui.components.field.util.InputFocusRequester
import com.oborodulin.home.common.ui.components.field.util.inputProcess
import com.oborodulin.home.common.ui.theme.Typography
import com.oborodulin.jwsuite.domain.util.MemberRoleType
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.ui.model.AppSettingsUiModel
import com.oborodulin.jwsuite.presentation.ui.model.LocalSession
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import timber.log.Timber
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.EnumMap

private const val TAG = "Presentation.AppSettingView"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AppSettingView(
    appSettingsUiModel: AppSettingsUiModel, viewModel: AppSettingViewModelImpl = hiltViewModel()
) {
    Timber.tag(TAG).d("AppSettingView(...) called")
    val session = LocalSession.current
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
                2.dp, MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(16.dp)
            )
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.authorization_subheader),
            style = Typography.displaySmall,
            modifier = Modifier.padding(vertical = 4.dp)
        )
        Box(modifier = Modifier.padding(vertical = 4.dp)) {
            Text(stringResource(R.string.username_hint))
            Text(text = appSettingsUiModel.username, style = Typography.bodySmall)
        }
        // https://alexzh.com/jetpack-compose-building-grids/
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            val roles = MutableList(appSettingsUiModel.roles.size) { emptyList<String>() }
            appSettingsUiModel.roles.forEach {
                roles.add(
                    listOf(
                        it.role.roleName, it.roleExpiredDate?.format(
                            DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
                        ).orEmpty()
                    )
                )
            }
            SimpleDataTableComponent(
                columnHeaders = listOf(
                    stringResource(R.string.role_header_hint),
                    stringResource(R.string.expired_header_hint)
                ), rows = roles
            )/*DataTableComponent(
                modifier = Modifier.matchParentSize(),
                columnCount = 2,
                rowCount = appSettingsUiModel.roles.size,
                cellContent = { columnIndex, rowIndex ->
                    Text(
                        when (columnIndex) {
                            0 -> appSettingsUiModel.roles[rowIndex].role.roleName
                            1 -> appSettingsUiModel.roles[rowIndex].roleExpiredDate?.format(
                                DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
                            ).orEmpty()

                            else -> ""
                        }
                    )
                })*/
        }
        Divider(Modifier.fillMaxWidth())
        Text(
            text = stringResource(R.string.transfer_subheader),
            style = Typography.displaySmall,
            modifier = Modifier.padding(vertical = 4.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            val transferObjects =
                MutableList(appSettingsUiModel.transferObjects.size) { emptyList<String>() }
            appSettingsUiModel.transferObjects.forEach {
                transferObjects.add(
                    listOf(
                        it.transferObject.transferObjectName, if (it.isPersonalData) "Да" else "Нет"
                    )
                )
            }
            SimpleDataTableComponent(
                columnHeaders = listOf(
                    stringResource(R.string.transfer_objects_header_hint),
                    stringResource(R.string.is_personal_header_hint)
                ), rows = transferObjects
            )
        }
        Divider(Modifier.fillMaxWidth())
        if (session.containsRole(MemberRoleType.TERRITORIES)) {
            Text(
                text = stringResource(R.string.territory_subheader),
                style = Typography.displaySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            TextFieldComponent(
                modifier = Modifier
                    .focusRequester(focusRequesters[AppSettingFields.TERRITORY_PROCESSING_PERIOD]!!.focusRequester)
                    .onFocusChanged { focusState ->
                        viewModel.onTextFieldFocusChanged(
                            focusedField = AppSettingFields.TERRITORY_PROCESSING_PERIOD,
                            isFocused = focusState.isFocused
                        )
                    },
                labelResId = R.string.territory_processing_period_hint,
                keyboardOptions = remember {
                    KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
                },
                inputWrapper = territoryProcessingPeriod,
                onValueChange = {
                    viewModel.onTextFieldEntered(
                        AppSettingInputEvent.TerritoryProcessingPeriod(it)
                    )
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
                labelResId = R.string.territory_at_hand_period_hint,
                keyboardOptions = remember {
                    KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
                },
                inputWrapper = territoryAtHandPeriod,
                onValueChange = {
                    viewModel.onTextFieldEntered(AppSettingInputEvent.TerritoryAtHandPeriod(it))
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
                labelResId = R.string.territory_idle_period_hint,
                keyboardOptions = remember {
                    KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
                },
                inputWrapper = territoryIdlePeriod,
                onValueChange = {
                    viewModel.onTextFieldEntered(AppSettingInputEvent.TerritoryIdlePeriod(it))
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
                labelResId = R.string.territory_rooms_limit_hint,
                keyboardOptions = remember {
                    KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
                },
                inputWrapper = territoryRoomsLimit,
                onValueChange = {
                    viewModel.onTextFieldEntered(AppSettingInputEvent.TerritoryRoomsLimit(it))
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
                    }, labelResId = R.string.territory_max_rooms_hint,
                keyboardOptions = remember {
                    KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done)
                },
                inputWrapper = territoryMaxRooms,
                onValueChange = {
                    viewModel.onTextFieldEntered(AppSettingInputEvent.TerritoryMaxRooms(it))
                },
                onImeKeyAction = viewModel::moveFocusImeAction
            )
            Divider(Modifier.fillMaxWidth())
        }
        Text(
            text = stringResource(R.string.about_subheader),
            style = Typography.displaySmall,
            modifier = Modifier.padding(vertical = 4.dp)
        )
        Box(modifier = Modifier.padding(vertical = 4.dp)) {
            Text(stringResource(R.string.version_hint))
            Text(text = appSettingsUiModel.versionName, style = Typography.bodySmall)
        }
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
