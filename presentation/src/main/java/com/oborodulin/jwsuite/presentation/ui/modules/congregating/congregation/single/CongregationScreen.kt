package com.oborodulin.jwsuite.presentation.ui.modules.congregating.congregation.single

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.home.common.ui.components.field.ExposedDropdownMenuBoxComponent
import com.oborodulin.home.common.ui.components.field.TextFieldComponent
import com.oborodulin.home.common.ui.components.field.util.InputFocusRequester
import com.oborodulin.home.common.ui.components.field.util.inputProcess
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.home.common.ui.theme.HomeComposableTheme
import com.oborodulin.jwsuite.presentation.AppState
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.navigation.inputs.CongregationInput
import com.oborodulin.jwsuite.presentation.rememberAppState
import kotlinx.coroutines.launch
import timber.log.Timber

private const val TAG = "Congregating.ui.CongregationScreen"

@Composable
fun CongregationScreen(
    appState: AppState,
    viewModel: CongregationViewModelImpl = hiltViewModel(),
    congregationInput: CongregationInput? = null
) {
    Timber.tag(TAG).d("CongregationScreen(...) called: payerInput = %s", congregationInput)
    LaunchedEffect(congregationInput?.congregationId) {
        Timber.tag(TAG).d("CongregationScreen: LaunchedEffect() BEFORE collect ui state flow")
        when (congregationInput) {
            null -> viewModel.submitAction(CongregationUiAction.Create)
            else -> viewModel.submitAction(CongregationUiAction.Load(congregationInput.congregationId))
        }
    }
    val topBarTitleId = when (congregationInput) {
        null -> R.string.congregation_new_subheader
        else -> R.string.congregation_subheader
    }
    viewModel.uiStateFlow.collectAsState().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        HomeComposableTheme { //(darkTheme = true)
            ScaffoldComponent(
                appState = appState,
                //scaffoldState = appState.payerScaffoldState,
                topBarTitleId = topBarTitleId,
                topBarNavigationIcon = {
                    IconButton(onClick = { appState.backToBottomBarScreen() }) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                }
            ) { it ->
                CommonScreen(paddingValues = it, state = state) {
                    //viewModel.initFieldStatesByUiModel(payerModel)
                    Congregation(appState, viewModel) {
                        viewModel.submitAction(CongregationUiAction.Save)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Congregation(appState: AppState, viewModel: CongregationViewModel, onSubmit: () -> Unit) {
    Timber.tag(TAG).d("Congregation(...) called")
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

    Timber.tag(TAG).d("CollectAsStateWithLifecycle for all payer fields")
    val ercCode by viewModel.ercCode.collectAsStateWithLifecycle()
    val fullName by viewModel.fullName.collectAsStateWithLifecycle()
    val address by viewModel.address.collectAsStateWithLifecycle()
    val totalArea by viewModel.totalArea.collectAsStateWithLifecycle()
    val livingSpace by viewModel.livingSpace.collectAsStateWithLifecycle()
    val heatedVolume by viewModel.heatedVolume.collectAsStateWithLifecycle()
    val paymentDay by viewModel.paymentDay.collectAsStateWithLifecycle()
    val personsNum by viewModel.personsNum.collectAsStateWithLifecycle()

    val areInputsValid by viewModel.areInputsValid.collectAsStateWithLifecycle()

    Timber.tag(TAG).d("Init Focus Requesters for all payer fields")
    val focusRequesters: MutableMap<String, InputFocusRequester> = HashMap()
    enumValues<CongregationFields>().forEach {
        focusRequesters[it.name] = InputFocusRequester(it, remember { FocusRequester() })
    }

    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("Congregation(...): LaunchedEffect()")
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
            .border(2.dp, MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(16.dp))
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[CongregationFields.ERC_CODE.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = CongregationFields.ERC_CODE,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.congregation_num_hint,
            leadingIcon = {
                Icon(
                    painterResource(com.oborodulin.home.common.R.drawable.ic_123_36),
                    null
                )
            },
            keyboardOptions = remember {
                KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            },
            inputWrapper = ercCode,
            onValueChange = { viewModel.onTextFieldEntered(CongregationInputEvent.ErcCode(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[CongregationFields.FULL_NAME.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = CongregationFields.FULL_NAME,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.congregation_name_hint,
            leadingIcon = {
                Icon(
                    painterResource(com.oborodulin.jwsuite.presentation.R.drawable.ic_person_36),
                    null
                )
            },
            keyboardOptions = remember {
                KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            },
            //  visualTransformation = ::creditCardFilter,
            inputWrapper = fullName,
            onValueChange = { viewModel.onTextFieldEntered(CongregationInputEvent.FullName(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .height(90.dp)
                .focusRequester(focusRequesters[CongregationFields.ADDRESS.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = CongregationFields.ADDRESS,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.territory_mark_hint,
            leadingIcon = {
                Icon(
                    painterResource(com.oborodulin.jwsuite.presentation.R.drawable.outline_house_black_36),
                    null
                )
            },
            maxLines = 2,
            keyboardOptions = remember {
                KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            },
            //  visualTransformation = ::creditCardFilter,
            inputWrapper = address,
            onValueChange = { viewModel.onTextFieldEntered(CongregationInputEvent.Address(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[CongregationFields.TOTAL_AREA.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = CongregationFields.TOTAL_AREA,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.total_area_hint,
            leadingIcon = {
                Icon(
                    painterResource(com.oborodulin.jwsuite.presentation.R.drawable.outline_space_dashboard_black_36),
                    null
                )
            },
            keyboardOptions = remember {
                KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Next
                )
            },
            inputWrapper = totalArea,
            onValueChange = { viewModel.onTextFieldEntered(CongregationInputEvent.TotalArea(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[CongregationFields.LIVING_SPACE.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = CongregationFields.LIVING_SPACE,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.living_space_hint,
            leadingIcon = {
                Icon(
                    painterResource(com.oborodulin.jwsuite.presentation.R.drawable.ic_aspect_ratio_36),
                    null
                )
            },
            keyboardOptions = remember {
                KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Next
                )
            },
            inputWrapper = livingSpace,
            onValueChange = { viewModel.onTextFieldEntered(CongregationInputEvent.LivingSpace(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[CongregationFields.HEATED_VOLUME.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = CongregationFields.HEATED_VOLUME,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.heated_volume_hint,
            leadingIcon = {
                Icon(
                    painterResource(com.oborodulin.jwsuite.presentation.R.drawable.outline_outbox_black_36),
                    null
                )
            },
            keyboardOptions = remember {
                KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Next
                )
            },
            inputWrapper = heatedVolume,
            onValueChange = { viewModel.onTextFieldEntered(CongregationInputEvent.HeatedVolume(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        ExposedDropdownMenuBoxComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[CongregationFields.PAYMENT_DAY.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = CongregationFields.PAYMENT_DAY,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.payment_day_hint,
            leadingIcon = {
                Icon(
                    painterResource(com.oborodulin.home.common.R.drawable.outline_calendar_month_black_36),
                    null
                )
            },
            keyboardOptions = remember {
                KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Next
                )
            },
            inputWrapper = paymentDay,
            listItems = (1..28).map { it.toString() },
            onValueChange = { viewModel.onTextFieldEntered(CongregationInputEvent.PaymentDay(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction,
            //colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[CongregationFields.PERSONS_NUM.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = CongregationFields.PERSONS_NUM,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.persons_num_hint,
            leadingIcon = {
                Icon(
                    painterResource(com.oborodulin.jwsuite.presentation.R.drawable.outline_people_black_36),
                    null
                )
            },
            keyboardOptions = remember {
                KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Next
                    //imeAction = ImeAction.Done
                )
            },
            inputWrapper = personsNum,
            onValueChange = { viewModel.onTextFieldEntered(CongregationInputEvent.PersonsNum(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
            //onImeKeyAction = { } //viewModel.onContinueClick { onSubmit() }
        )
        Spacer(Modifier.height(8.dp))
        Button(onClick = {
            viewModel.onContinueClick {
                Timber.tag(TAG).d("CongregationScreen(...): Start viewModelScope.launch")
                viewModel.viewModelScope().launch {
                    viewModel.actionsJobFlow.collect {
                        Timber.tag(TAG).d(
                            "CongregationScreen(...): Start actionsJobFlow.collect [job = %s]",
                            it?.toString()
                        )
                        it?.join()
                        appState.backToBottomBarScreen()
                    }
                }
                onSubmit()
                Timber.tag(TAG).d("CongregationScreen(...): onSubmit() executed")
            }
        }, enabled = areInputsValid) {
            Text(text = stringResource(com.oborodulin.home.common.R.string.btn_save_lbl))
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewCongregation() {
    Congregation(
        appState = rememberAppState(),
        viewModel = CongregationViewModelImpl.previewModel,
        onSubmit = {})
}
