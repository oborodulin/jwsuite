package com.oborodulin.jwsuite.presentation_dashboard.ui.dashboarding.setting

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
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.oborodulin.home.common.ui.components.datatable.SimpleDataTableComponent
import com.oborodulin.home.common.ui.components.field.TextFieldComponent
import com.oborodulin.home.common.ui.components.field.util.InputFocusRequester
import com.oborodulin.home.common.ui.components.field.util.inputProcess
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.home.common.ui.theme.Typography
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_INPUT
import com.oborodulin.home.common.util.LogLevel.LOG_UI_STATE
import com.oborodulin.jwsuite.domain.types.MemberRoleType
import com.oborodulin.jwsuite.presentation.ui.components.SignoutButtonComponent
import com.oborodulin.jwsuite.presentation.ui.components.SignoutConfirmDialogComponent
import com.oborodulin.jwsuite.presentation.ui.model.LocalSession
import com.oborodulin.jwsuite.presentation.ui.model.SessionUi
import com.oborodulin.jwsuite.presentation.ui.session.SessionUiAction
import com.oborodulin.jwsuite.presentation.ui.session.SessionViewModel
import com.oborodulin.jwsuite.presentation.ui.session.SessionViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_dashboard.R
import com.oborodulin.jwsuite.presentation_dashboard.ui.components.BackupButtonComponent
import com.oborodulin.jwsuite.presentation_dashboard.ui.components.ReceiveButtonComponent
import com.oborodulin.jwsuite.presentation_dashboard.ui.components.RestoreButtonComponent
import com.oborodulin.jwsuite.presentation_dashboard.ui.components.SendButtonComponent
import com.oborodulin.jwsuite.presentation_dashboard.ui.database.DatabaseUiAction
import com.oborodulin.jwsuite.presentation_dashboard.ui.database.DatabaseViewModelImpl
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.DashboardSettingsUiModel
import timber.log.Timber
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.EnumMap

private const val TAG = "Dashboarding.DashboardSettingView"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DashboardSettingView(
    modifier: Modifier = Modifier,
    dashboardSettingsUiModel: DashboardSettingsUiModel,
    dashboardSettingViewModel: DashboardSettingViewModel,//Impl = hiltViewModel()
    sessionViewModel: SessionViewModel,//Impl = hiltViewModel()
    databaseViewModel: DatabaseViewModelImpl = hiltViewModel()
) {
    Timber.tag(TAG).d("DashboardSettingView(...) called")
    val session = LocalSession.current
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(dashboardSettingViewModel.events, lifecycleOwner) {
        dashboardSettingViewModel.events.flowWithLifecycle(
            lifecycleOwner.lifecycle, Lifecycle.State.STARTED
        )
    }

    Timber.tag(TAG).d("DashboardSetting: CollectAsStateWithLifecycle for all fields")
    val databaseBackupPeriod by dashboardSettingViewModel.databaseBackupPeriod.collectAsStateWithLifecycle()

    Timber.tag(TAG).d("DashboardSetting: Init Focus Requesters for all fields")
    val focusRequesters =
        EnumMap<DashboardSettingFields, InputFocusRequester>(DashboardSettingFields::class.java)
    enumValues<DashboardSettingFields>().forEach {
        focusRequesters[it] = InputFocusRequester(it, remember { FocusRequester() })
    }

    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("DashboardSettingView -> LaunchedEffect()")
        events.collect { event ->
            if (LOG_FLOW_INPUT) Timber.tag(TAG)
                .d("IF# Collect input events flow: %s", event.javaClass.name)
            inputProcess(context, focusManager, keyboardController, event, focusRequesters)
        }
    }
    val handleSignoutButtonClick: () -> Unit = {
        Timber.tag(TAG).d("DashboardSettingView: Signout Button click...")
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
            )
            .then(modifier),
        //.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(R.string.authorization_subhead),
            style = Typography.titleMedium,
            modifier = Modifier.padding(8.dp)
        )
        val isSignoutShowAlert = rememberSaveable { mutableStateOf(false) }
        SignoutConfirmDialogComponent(
            isShow = isSignoutShowAlert,
            text = stringResource(com.oborodulin.jwsuite.presentation.R.string.dlg_confirm_signout),
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
                        append("\n${dashboardSettingsUiModel.username}")
                    }
                }, modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
                    .alignByBaseline()
            )
            SignoutButtonComponent(modifier = Modifier.alignByBaseline()) {
                isSignoutShowAlert.value = true
            }
        }
        // https://alexzh.com/jetpack-compose-building-grids/
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            val roles = MutableList(dashboardSettingsUiModel.roles.size) { emptyList<String>() }
            dashboardSettingsUiModel.roles.forEach {
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
            text = stringResource(R.string.transfer_subhead),
            style = Typography.titleMedium,
            modifier = Modifier.padding(8.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            val transferObjects =
                MutableList(dashboardSettingsUiModel.transferObjects.size) { emptyList<String>() }
            dashboardSettingsUiModel.transferObjects.forEach {
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
                SendButtonComponent(
                    modifier = Modifier
                        .weight(1f)
                        .alignByBaseline()
                )
            }
            ReceiveButtonComponent(modifier = Modifier.alignByBaseline())
        }
        Divider(Modifier.fillMaxWidth())
        Text(
            text = stringResource(R.string.about_subhead),
            style = Typography.titleMedium,
            modifier = Modifier.padding(8.dp)
        )
        Row {
            Column {
                Text(
                    buildAnnotatedString {
                        append(stringResource(R.string.app_version_hint))
                        withStyle(style = SpanStyle(fontSize = 14.sp)) {
                            append("\n${dashboardSettingsUiModel.appVersionName} (${stringResource(R.string.framework_api_version_hint)}${dashboardSettingsUiModel.frameworkVersion})")
                        }
                    }, modifier = Modifier.padding(8.dp)
                )
                Text(
                    buildAnnotatedString {
                        append(stringResource(R.string.database_version_hint))
                        withStyle(style = SpanStyle(fontSize = 14.sp)) {
                            append("\n${dashboardSettingsUiModel.dbVersion} (${stringResource(R.string.sqlite_version_hint)}${dashboardSettingsUiModel.sqliteVersion})")
                        }
                    }, modifier = Modifier.padding(8.dp)
                )
            }
            Column {
                TextFieldComponent(
                    modifier = Modifier
                        .focusRequester(focusRequesters[DashboardSettingFields.DATABASE_BACKUP_PERIOD]!!.focusRequester)
                        .onFocusChanged { focusState ->
                            dashboardSettingViewModel.onTextFieldFocusChanged(
                                focusedField = DashboardSettingFields.DATABASE_BACKUP_PERIOD,
                                isFocused = focusState.isFocused
                            )
                        },
                    labelResId = R.string.database_backup_period_hint,
                    keyboardOptions = remember {
                        KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        )
                    },
                    inputWrapper = databaseBackupPeriod,
                    onValueChange = {
                        dashboardSettingViewModel.onTextFieldEntered(
                            DashboardSettingInputEvent.DatabaseBackupPeriod(it)
                        )
                    },
                    onImeKeyAction = dashboardSettingViewModel::moveFocusImeAction
                )
                BackupButtonComponent(
                    enabled = true,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Timber.tag(TAG).d("DashboardSettingView: Backup Button click...")
                    databaseViewModel.submitAction(DatabaseUiAction.Backup)
                }
                RestoreButtonComponent(
                    enabled = true,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Timber.tag(TAG).d("DashboardSettingView: Restore Button click...")
                    databaseViewModel.submitAction(DatabaseUiAction.Restore)
                }
                databaseViewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
                    if (LOG_UI_STATE) Timber.tag(TAG).d("Collect ui state flow: %s", state)
                    CommonScreen(state = state) { databaseUi ->
                        if (databaseUi.isDone.not()) {
                            Text(
                                text = databaseUi.entityDesc,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            databaseUi.progress?.let {
                                LinearProgressIndicator(
                                    progress = it,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                }
            }
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
                DashboardSettingView(
                    dashboardSettingsUiModel = DashboardSettingViewModelImpl.previewUiModel(ctx),
                    dashboardSettingViewModel = DashboardSettingViewModelImpl.previewModel(ctx),
                    sessionViewModel = SessionViewModelImpl.previewModel(ctx)
                )
            }
        }
    }
}