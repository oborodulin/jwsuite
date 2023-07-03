package com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.single

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.oborodulin.home.common.ui.components.field.ExposedDropdownMenuBoxComponent
import com.oborodulin.home.common.ui.components.field.TextFieldComponent
import com.oborodulin.home.common.ui.components.field.util.InputFocusRequester
import com.oborodulin.home.common.ui.components.field.util.inputProcess
import com.oborodulin.jwsuite.presentation.R
import timber.log.Timber

private const val TAG = "Geo.ui.LocalityView"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LocalityView(viewModel: RegionDistrictViewModel) {
    Timber.tag(TAG).d("LocalityView(...) called")
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

    Timber.tag(TAG).d("CollectAsStateWithLifecycle for all locality fields")
    val region by viewModel.region.collectAsStateWithLifecycle()
    val regionDistrict by viewModel.regionDistrict.collectAsStateWithLifecycle()
    val localityCode by viewModel.localityCode.collectAsStateWithLifecycle()
    val localityShortName by viewModel.localityShortName.collectAsStateWithLifecycle()
    val localityType by viewModel.localityType.collectAsStateWithLifecycle()
    val localityName by viewModel.localityName.collectAsStateWithLifecycle()

    Timber.tag(TAG).d("Init Focus Requesters for all locality fields")
    val focusRequesters: MutableMap<String, InputFocusRequester> = HashMap()
    enumValues<RegionDistrictFields>().forEach {
        focusRequesters[it.name] = InputFocusRequester(it, remember { FocusRequester() })
    }

    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("LocalityView(...): LaunchedEffect()")
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
        /*
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[LocalityFields.TOTAL_AREA.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = LocalityFields.TOTAL_AREA,
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
            inputWrapper = region,
            onValueChange = {
                viewModel.onTextFieldEntered(
                    LocalityInputEvent.RegionDistrict(
                        it
                    )
                )
            },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[LocalityFields.LIVING_SPACE.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = LocalityFields.LIVING_SPACE,
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
            inputWrapper = regionDistrict,
            onValueChange = { viewModel.onTextFieldEntered(LocalityInputEvent.LivingSpace(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
         */
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[RegionDistrictFields.LOCALITY_CODE.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = RegionDistrictFields.LOCALITY_CODE,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.code_hint,
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
            inputWrapper = localityCode,
            onValueChange = {
                viewModel.onTextFieldEntered(RegionDistrictInputEvent.RegionDistrictCode(it))
            },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[RegionDistrictFields.LOCALITY_SHORT_NAME.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = RegionDistrictFields.LOCALITY_SHORT_NAME,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.short_name_hint,
            leadingIcon = {
                Icon(
                    painterResource(R.drawable.ic_ab_36),
                    null
                )
            },
            keyboardOptions = remember {
                KeyboardOptions(
                    capitalization = KeyboardCapitalization.Characters,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            },
            inputWrapper = localityShortName,
            onValueChange = {
                viewModel.onTextFieldEntered(RegionDistrictInputEvent.RegionDistrictShortName(it))
            },
            onImeKeyAction = viewModel::moveFocusImeAction
            //onImeKeyAction = { } //viewModel.onContinueClick { onSubmit() }
        )
        ExposedDropdownMenuBoxComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[RegionDistrictFields.LOCALITY_TYPE.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = RegionDistrictFields.LOCALITY_TYPE,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.type_hint,
            leadingIcon = {
                Icon(painterResource(R.drawable.ic_signpost_36), null)
            },
            keyboardOptions = remember {
                KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            },
            inputWrapper = localityType,
            listItems = (1..28).map { it.toString() },
            onValueChange = {
                viewModel.onTextFieldEntered(RegionDistrictInputEvent.RegionDistrictType(it))
            },
            onImeKeyAction = viewModel::moveFocusImeAction,
            //colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[RegionDistrictFields.LOCALITY_NAME.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = RegionDistrictFields.LOCALITY_NAME,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.name_hint,
            leadingIcon = {
                Icon(
                    painterResource(R.drawable.ic_abc_36),
                    null
                )
            },
            keyboardOptions = remember {
                KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
            },
            //  visualTransformation = ::creditCardFilter,
            inputWrapper = localityName,
            onValueChange = {
                viewModel.onTextFieldEntered(RegionDistrictInputEvent.RegionDistrictName(it))
            },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewLocalityView() {
    LocalityView(viewModel = RegionDistrictViewModelImpl.previewModel)
}
