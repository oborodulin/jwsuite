package com.oborodulin.jwsuite.presentation_dashboard.ui.dashboarding.datamanagement

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.oborodulin.home.common.ui.components.datatable.SimpleDataTableComponent
import com.oborodulin.home.common.ui.components.field.TextFieldComponent
import com.oborodulin.home.common.ui.components.field.util.InputFocusRequester
import com.oborodulin.home.common.ui.components.field.util.inputProcess
import com.oborodulin.home.common.ui.components.text.makeBulletedList
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.home.common.ui.theme.Typography
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_INPUT
import com.oborodulin.home.common.util.LogLevel.LOG_UI_STATE
import com.oborodulin.home.common.util.OnImeKeyAction
import com.oborodulin.jwsuite.domain.types.MemberRoleType
import com.oborodulin.jwsuite.presentation.ui.model.LocalSession
import com.oborodulin.jwsuite.presentation.ui.model.SessionUi
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation.ui.theme.Purple40
import com.oborodulin.jwsuite.presentation_dashboard.R
import com.oborodulin.jwsuite.presentation_dashboard.ui.components.BackupButtonComponent
import com.oborodulin.jwsuite.presentation_dashboard.ui.components.ReceiveButtonComponent
import com.oborodulin.jwsuite.presentation_dashboard.ui.components.RestoreButtonComponent
import com.oborodulin.jwsuite.presentation_dashboard.ui.components.SendButtonComponent
import com.oborodulin.jwsuite.presentation_dashboard.ui.database.DatabaseUiAction
import com.oborodulin.jwsuite.presentation_dashboard.ui.database.DatabaseViewModelImpl
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.DataManagementSettingsUiModel
import timber.log.Timber
import java.util.EnumMap

private const val TAG = "Dashboarding.DataManagementView"

/**
 * Отображает представление управления данными приложения.
 *
 * @author Oleg Borodulin (o.a.borodulin@yandex.ru)
 * @since 1.0
 */
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DataManagementView(
    modifier: Modifier = Modifier,
    uiModel: DataManagementSettingsUiModel,
    dataManagementViewModel: DataManagementViewModel,//Impl = hiltViewModel()
    databaseViewModel: DatabaseViewModelImpl = hiltViewModel(),
    handleSaveAction: OnImeKeyAction
) {
    Timber.tag(TAG).d("DataManagementView(...) called")
    val session = LocalSession.current
    val context = LocalContext.current
    //val tooltipState = RichTooltipState()
    val scope = rememberCoroutineScope()

    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(dataManagementViewModel.events, lifecycleOwner) {
        dataManagementViewModel.events.flowWithLifecycle(
            lifecycleOwner.lifecycle, Lifecycle.State.STARTED
        )
    }
    Timber.tag(TAG).d("DataManagement: CollectAsStateWithLifecycle for all fields")
    val databaseBackupPeriod by dataManagementViewModel.databaseBackupPeriod.collectAsStateWithLifecycle()

    Timber.tag(TAG).d("DataManagement: Init Focus Requesters for all fields")
    val focusRequesters =
        EnumMap<DataManagementFields, InputFocusRequester>(DataManagementFields::class.java)
    enumValues<DataManagementFields>().forEach {
        focusRequesters[it] = InputFocusRequester(it, remember { FocusRequester() })
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("DataManagementView -> LaunchedEffect()")
        databaseViewModel.submitAction(DatabaseUiAction.Init)
        events.collect { event ->
            if (LOG_FLOW_INPUT) {
                Timber.tag(TAG).d("IF# Collect input events flow: %s", event.javaClass.name)
            }
            inputProcess(context, focusManager, keyboardController, event, focusRequesters)
        }
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
        Row {
            Text(
                text = stringResource(R.string.transfer_subhead),
                style = Typography.titleMedium,
                modifier = Modifier.padding(8.dp)
            )
            val items = listOf(
                "${stringResource(com.oborodulin.jwsuite.data_congregation.R.string.def_trans_obj_name_all)}: ${
                    stringResource(com.oborodulin.jwsuite.data_congregation.R.string.def_role_name_admin)
                }, ${
                    stringResource(com.oborodulin.jwsuite.data_congregation.R.string.def_role_name_reports)
                }",
                "${stringResource(com.oborodulin.jwsuite.data_congregation.R.string.def_trans_obj_name_members)}: ",
                "${stringResource(com.oborodulin.jwsuite.data_congregation.R.string.def_trans_obj_name_territories)}: ",
                "${stringResource(com.oborodulin.jwsuite.data_congregation.R.string.def_trans_obj_name_territory_report)}: ",
                "${stringResource(com.oborodulin.jwsuite.data_congregation.R.string.def_trans_obj_name_bills)}: ",
                "${stringResource(com.oborodulin.jwsuite.data_congregation.R.string.def_trans_obj_name_reports)}: "
            )
            // https://www.develou.com/tooltips-android/
            TooltipBox(
                positionProvider = TooltipDefaults.rememberRichTooltipPositionProvider(),
                tooltip = {
                    RichTooltip(
                        title = { Text(text = stringResource(R.string.transfer_subhead_tooltip)) },
                        text = { Text(text = makeBulletedList(items)) }
                    )
                },
                state = rememberTooltipState()
            )
            {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = "",
                        tint = Purple40
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            val transferObjects =
                MutableList(uiModel.transferObjects.size) { emptyList<String>() }
            uiModel.transferObjects.forEach {
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
        val isSendButtonShow = session.containsAnyRoles(
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
        HorizontalDivider(Modifier.fillMaxWidth())
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[DataManagementFields.DATABASE_BACKUP_PERIOD]!!.focusRequester)
                .onFocusChanged { focusState ->
                    dataManagementViewModel.onTextFieldFocusChanged(
                        focusedField = DataManagementFields.DATABASE_BACKUP_PERIOD,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.database_backup_period_hint,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done)
            },
            inputWrapper = databaseBackupPeriod,
            onValueChange = {
                dataManagementViewModel.onTextFieldEntered(
                    DataManagementInputEvent.DatabaseBackupPeriod(it)
                )
            },
            onImeKeyAction = handleSaveAction
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BackupButtonComponent(
                enabled = true,
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 8.dp)
            ) {
                Timber.tag(TAG).d("DataManagementView: Backup Button click...")
                databaseViewModel.submitAction(DatabaseUiAction.Backup)
            }
            RestoreButtonComponent(
                enabled = true,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Timber.tag(TAG).d("DataManagementView: Restore Button click...")
                databaseViewModel.submitAction(DatabaseUiAction.Restore)
            }
            databaseViewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
                if (LOG_UI_STATE) {
                    Timber.tag(TAG).d("Collect ui state flow: %s", state)
                }
                CommonScreen(state = state) { databaseUi ->
                    if (databaseUi.isDone.not()) {
                        Text(
                            text = databaseUi.entityDesc,
                            fontSize = 14.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        databaseUi.progress?.let {
                            LinearProgressIndicator(
                                progress = { it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                            )
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
fun PreviewDataManagementView() {
    val ctx = LocalContext.current
    JWSuiteTheme {
        Surface {
            // https://jkprajapati.medium.com/preview-was-unable-to-find-a-compositionlocal-d65e55bdee3e
            CompositionLocalProvider(
                LocalSession provides SessionUi()
            ) {
                DataManagementView(
                    uiModel = DataManagementViewModelImpl.previewUiModel(ctx),
                    dataManagementViewModel = DataManagementViewModelImpl.previewModel(ctx),
                    handleSaveAction = {}
                )
            }
        }
    }
}
