package com.oborodulin.jwsuite.presentation_territory.ui.territoring.setting

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.oborodulin.home.common.ui.components.field.TextFieldComponent
import com.oborodulin.home.common.ui.components.field.util.InputFocusRequester
import com.oborodulin.home.common.ui.components.field.util.inputProcess
import com.oborodulin.home.common.ui.theme.Typography
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_INPUT
import com.oborodulin.home.common.util.OnImeKeyAction
import com.oborodulin.jwsuite.presentation.ui.model.LocalSession
import com.oborodulin.jwsuite.presentation.ui.model.SessionUi
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_territory.R
import timber.log.Timber
import java.util.EnumMap

private const val TAG = "Territoring.TerritorySettingView"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TerritorySettingView(
    modifier: Modifier = Modifier,
    viewModel: TerritorySettingViewModel,
    handleSaveAction: OnImeKeyAction
) {
    Timber.tag(TAG).d("TerritorySettingView(...) called")
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(viewModel.events, lifecycleOwner) {
        viewModel.events.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }

    Timber.tag(TAG).d("TerritorySetting: CollectAsStateWithLifecycle for all fields")
    val territoryProcessingPeriod by viewModel.territoryProcessingPeriod.collectAsStateWithLifecycle()
    val territoryAtHandPeriod by viewModel.territoryAtHandPeriod.collectAsStateWithLifecycle()
    val territoryIdlePeriod by viewModel.territoryIdlePeriod.collectAsStateWithLifecycle()
    val territoryRoomsLimit by viewModel.territoryRoomsLimit.collectAsStateWithLifecycle()
    val territoryMaxRooms by viewModel.territoryMaxRooms.collectAsStateWithLifecycle()

    Timber.tag(TAG).d("TerritorySetting: Init Focus Requesters for all fields")
    val focusRequesters =
        EnumMap<TerritorySettingFields, InputFocusRequester>(TerritorySettingFields::class.java)
    enumValues<TerritorySettingFields>().forEach {
        focusRequesters[it] = InputFocusRequester(it, remember { FocusRequester() })
    }

    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("TerritorySettingView -> LaunchedEffect()")
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
            .verticalScroll(rememberScrollState())
            .then(modifier),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(R.string.territories_subhead),
            style = Typography.titleMedium,
            modifier = Modifier.padding(8.dp)
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[TerritorySettingFields.TERRITORY_PROCESSING_PERIOD]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = TerritorySettingFields.TERRITORY_PROCESSING_PERIOD,
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
                    TerritorySettingInputEvent.TerritoryProcessingPeriod(it)
                )
            },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[TerritorySettingFields.TERRITORY_AT_HAND_PERIOD]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = TerritorySettingFields.TERRITORY_AT_HAND_PERIOD,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.territory_at_hand_period_hint,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
            },
            inputWrapper = territoryAtHandPeriod,
            onValueChange = {
                viewModel.onTextFieldEntered(TerritorySettingInputEvent.TerritoryAtHandPeriod(it))
            },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[TerritorySettingFields.TERRITORY_IDLE_PERIOD]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = TerritorySettingFields.TERRITORY_IDLE_PERIOD,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.territory_idle_period_hint,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
            },
            inputWrapper = territoryIdlePeriod,
            onValueChange = {
                viewModel.onTextFieldEntered(TerritorySettingInputEvent.TerritoryIdlePeriod(it))
            },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[TerritorySettingFields.TERRITORY_ROOMS_LIMIT]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = TerritorySettingFields.TERRITORY_ROOMS_LIMIT,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.territory_rooms_limit_hint,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
            },
            inputWrapper = territoryRoomsLimit,
            onValueChange = {
                viewModel.onTextFieldEntered(TerritorySettingInputEvent.TerritoryRoomsLimit(it))
            },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[TerritorySettingFields.TERRITORY_MAX_ROOMS]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = TerritorySettingFields.TERRITORY_MAX_ROOMS,
                        isFocused = focusState.isFocused
                    )
                }, labelResId = R.string.territory_max_rooms_hint,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done)
            },
            inputWrapper = territoryMaxRooms,
            onValueChange = {
                viewModel.onTextFieldEntered(TerritorySettingInputEvent.TerritoryMaxRooms(it))
            },
            onImeKeyAction = handleSaveAction
        )
        Divider(Modifier.fillMaxWidth())
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
                TerritorySettingView(
                    viewModel = TerritorySettingViewModelImpl.previewModel(ctx),
                    handleSaveAction = {}
                )
            }
        }
    }
}
