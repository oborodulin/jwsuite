package com.oborodulin.jwsuite.presentation.ui.appsetting

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.oborodulin.home.common.ui.components.datatable.SimpleDataTableComponent
import com.oborodulin.home.common.ui.components.field.TextFieldComponent
import com.oborodulin.home.common.ui.components.field.util.InputFocusRequester
import com.oborodulin.home.common.ui.components.field.util.inputProcess
import com.oborodulin.home.common.ui.theme.Typography
import com.oborodulin.jwsuite.domain.types.MemberRoleType
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.ui.components.ReceiveButtonComponent
import com.oborodulin.jwsuite.presentation.ui.components.SendButtonComponent
import com.oborodulin.jwsuite.presentation.ui.components.SignoutButtonComponent
import com.oborodulin.jwsuite.presentation.ui.components.SignoutConfirmDialogComponent
import com.oborodulin.jwsuite.presentation.ui.model.AppSettingsUiModel
import com.oborodulin.jwsuite.presentation.ui.model.LocalSession
import com.oborodulin.jwsuite.presentation.ui.model.SessionUi
import com.oborodulin.jwsuite.presentation.ui.session.SessionUiAction
import com.oborodulin.jwsuite.presentation.ui.session.SessionViewModel
import com.oborodulin.jwsuite.presentation.ui.session.SessionViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import timber.log.Timber
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.EnumMap

private const val TAG = "Presentation.AppSettingView"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AppSettingView(
    appSettingsUiModel: AppSettingsUiModel,
    appSettingViewModel: AppSettingViewModel,//Impl = hiltViewModel()
    sessionViewModel: SessionViewModel//Impl = hiltViewModel()
) {
    Timber.tag(TAG).d("AppSettingView(...) called")
    val session = LocalSession.current
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(appSettingViewModel.events, lifecycleOwner) {
        appSettingViewModel.events.flowWithLifecycle(
            lifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
    }

    Timber.tag(TAG).d("AppSetting: CollectAsStateWithLifecycle for all fields")
    val territoryProcessingPeriod by appSettingViewModel.territoryProcessingPeriod.collectAsStateWithLifecycle()
    val territoryAtHandPeriod by appSettingViewModel.territoryAtHandPeriod.collectAsStateWithLifecycle()
    val territoryIdlePeriod by appSettingViewModel.territoryIdlePeriod.collectAsStateWithLifecycle()
    val territoryRoomsLimit by appSettingViewModel.territoryRoomsLimit.collectAsStateWithLifecycle()
    val territoryMaxRooms by appSettingViewModel.territoryMaxRooms.collectAsStateWithLifecycle()

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
    val handleSignoutButtonClick: () -> Unit = {
        Timber.tag(TAG).d("AppSettingView: Signout Button click...")
        sessionViewModel.submitAction(SessionUiAction.Signout)
        /*sessionViewModel.handleActionJob({ sessionViewModel.submitAction(SessionUiAction.Signout) }) {
            sessionViewModel.submitAction(SessionUiAction.Registration)
        }*/
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            //.height(IntrinsicSize.Min)
            .clip(RoundedCornerShape(16.dp))
            .border(
                2.dp, MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(16.dp)
            ),
        //.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(R.string.authorization_subheader),
            style = Typography.titleMedium,
            modifier = Modifier.padding(8.dp)
        )
        val isSignoutShowAlert = rememberSaveable { mutableStateOf(false) }
        SignoutConfirmDialogComponent(
            isShow = isSignoutShowAlert,
            text = stringResource(R.string.dlg_confirm_signout),
            onConfirm = handleSignoutButtonClick
        )
        // https://stackoverflow.com/questions/71476719/android-compose-text-can-we-have-two-different-texts-set-to-the-start-and-to-the
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                buildAnnotatedString {
                    append(stringResource(R.string.username_hint))
                    withStyle(style = SpanStyle(fontSize = 14.sp)) {
                        append("\n${appSettingsUiModel.username}")
                    }
                }, modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
            )
            SignoutButtonComponent { isSignoutShowAlert.value = true }
        }
        // https://alexzh.com/jetpack-compose-building-grids/
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
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
            )
            /*DataTableComponent(
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
            style = Typography.titleMedium,
            modifier = Modifier.padding(8.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            val transferObjects =
                MutableList(appSettingsUiModel.transferObjects.size) { emptyList<String>() }
            appSettingsUiModel.transferObjects.forEach {
                transferObjects.add(
                    listOf(
                        it.transferObject.transferObjectName,
                        if (it.isPersonalData) stringResource(com.oborodulin.home.common.R.string.yes_expr)
                        else stringResource(com.oborodulin.home.common.R.string.no_expr)
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
        val isSendButtonShow = session.containsRoles(
            listOf(
                MemberRoleType.TERRITORIES,
                MemberRoleType.BILLS
            )
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = if (isSendButtonShow) Arrangement.SpaceBetween else Arrangement.End,
        ) {
            if (isSendButtonShow) {
                SendButtonComponent(modifier = Modifier.weight(1f))
            }
            ReceiveButtonComponent()
        }
        Divider(Modifier.fillMaxWidth())
        Text(
            text = stringResource(R.string.about_subheader),
            style = Typography.titleMedium,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            buildAnnotatedString {
                append(stringResource(R.string.app_version_hint))
                withStyle(style = SpanStyle(fontSize = 14.sp)) {
                    append("\n${appSettingsUiModel.appVersionName}")
                }
            }, modifier = Modifier.padding(8.dp)
        )
        Text(
            buildAnnotatedString {
                append(stringResource(R.string.famework_api_hint))
                withStyle(style = SpanStyle(fontSize = 14.sp)) {
                    append("\n${appSettingsUiModel.frameworkVersion}")
                }
            }, modifier = Modifier.padding(8.dp)
        )
        Text(
            buildAnnotatedString {
                append(stringResource(R.string.sqlite_hint))
                withStyle(style = SpanStyle(fontSize = 14.sp)) {
                    append("\n${appSettingsUiModel.sqliteVersion}")
                }
            }, modifier = Modifier.padding(8.dp)
        )
        Text(
            buildAnnotatedString {
                append(stringResource(R.string.database_hint))
                withStyle(style = SpanStyle(fontSize = 14.sp)) {
                    append("\n${appSettingsUiModel.dbVersion}")
                }
            }, modifier = Modifier.padding(8.dp)
        )
        Divider(Modifier.fillMaxWidth())
        if (session.containsRole(MemberRoleType.TERRITORIES)) {
            Text(
                text = stringResource(R.string.territory_subheader),
                style = Typography.titleMedium,
                modifier = Modifier.padding(8.dp)
            )
            TextFieldComponent(
                modifier = Modifier
                    .focusRequester(focusRequesters[AppSettingFields.TERRITORY_PROCESSING_PERIOD]!!.focusRequester)
                    .onFocusChanged { focusState ->
                        appSettingViewModel.onTextFieldFocusChanged(
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
                    appSettingViewModel.onTextFieldEntered(
                        AppSettingInputEvent.TerritoryProcessingPeriod(it)
                    )
                },
                onImeKeyAction = appSettingViewModel::moveFocusImeAction
            )
            TextFieldComponent(
                modifier = Modifier
                    .focusRequester(focusRequesters[AppSettingFields.TERRITORY_AT_HAND_PERIOD]!!.focusRequester)
                    .onFocusChanged { focusState ->
                        appSettingViewModel.onTextFieldFocusChanged(
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
                    appSettingViewModel.onTextFieldEntered(
                        AppSettingInputEvent.TerritoryAtHandPeriod(it)
                    )
                },
                onImeKeyAction = appSettingViewModel::moveFocusImeAction
            )
            TextFieldComponent(
                modifier = Modifier
                    .focusRequester(focusRequesters[AppSettingFields.TERRITORY_IDLE_PERIOD]!!.focusRequester)
                    .onFocusChanged { focusState ->
                        appSettingViewModel.onTextFieldFocusChanged(
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
                    appSettingViewModel.onTextFieldEntered(
                        AppSettingInputEvent.TerritoryIdlePeriod(it)
                    )
                },
                onImeKeyAction = appSettingViewModel::moveFocusImeAction
            )
            TextFieldComponent(
                modifier = Modifier
                    .focusRequester(focusRequesters[AppSettingFields.TERRITORY_ROOMS_LIMIT]!!.focusRequester)
                    .onFocusChanged { focusState ->
                        appSettingViewModel.onTextFieldFocusChanged(
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
                    appSettingViewModel.onTextFieldEntered(
                        AppSettingInputEvent.TerritoryRoomsLimit(it)
                    )
                },
                onImeKeyAction = appSettingViewModel::moveFocusImeAction
            )
            TextFieldComponent(
                modifier = Modifier
                    .focusRequester(focusRequesters[AppSettingFields.TERRITORY_MAX_ROOMS]!!.focusRequester)
                    .onFocusChanged { focusState ->
                        appSettingViewModel.onTextFieldFocusChanged(
                            focusedField = AppSettingFields.TERRITORY_MAX_ROOMS,
                            isFocused = focusState.isFocused
                        )
                    }, labelResId = R.string.territory_max_rooms_hint,
                keyboardOptions = remember {
                    KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done)
                },
                inputWrapper = territoryMaxRooms,
                onValueChange = {
                    appSettingViewModel.onTextFieldEntered(AppSettingInputEvent.TerritoryMaxRooms(it))
                },
                onImeKeyAction = appSettingViewModel::moveFocusImeAction
            )
            Divider(Modifier.fillMaxWidth())
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewGroupView() {
    val ctx = LocalContext.current
    JWSuiteTheme {
        Surface {
            // https://jkprajapati.medium.com/preview-was-unable-to-find-a-compositionlocal-d65e55bdee3e
            CompositionLocalProvider(
                LocalSession provides SessionUi()
            ) {
                AppSettingView(
                    appSettingsUiModel = AppSettingViewModelImpl.previewUiModel(ctx),
                    appSettingViewModel = AppSettingViewModelImpl.previewModel(ctx),
                    sessionViewModel = SessionViewModelImpl.previewModel(ctx)
                )
            }
        }
    }
}
