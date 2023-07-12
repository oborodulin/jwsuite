package com.oborodulin.jwsuite.presentation.ui.modules.geo.locality.single

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
import androidx.compose.ui.res.stringArrayResource
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
import com.oborodulin.jwsuite.domain.util.LocalityType
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.ui.modules.geo.region.list.RegionsListViewModel
import com.oborodulin.jwsuite.presentation.ui.modules.geo.region.list.RegionsListViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.geo.region.single.RegionComboBox
import com.oborodulin.jwsuite.presentation.ui.modules.geo.region.single.RegionViewModel
import com.oborodulin.jwsuite.presentation.ui.modules.geo.region.single.RegionViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.list.RegionDistrictsListViewModel
import com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.list.RegionDistrictsListViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.single.RegionDistrictComboBox
import com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.single.RegionDistrictViewModel
import com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.single.RegionDistrictViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import timber.log.Timber

private const val TAG = "Geo.ui.LocalityView"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LocalityView(
    localityViewModel: LocalityViewModel,
    regionsListViewModel: RegionsListViewModel,
    regionViewModel: RegionViewModel,
    regionDistrictsListViewModel: RegionDistrictsListViewModel,
    regionDistrictViewModel: RegionDistrictViewModel
) {
    Timber.tag(TAG).d("LocalityView(...) called")
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(localityViewModel.events, lifecycleOwner) {
        localityViewModel.events.flowWithLifecycle(
            lifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
    }

    Timber.tag(TAG).d("CollectAsStateWithLifecycle for all locality fields")
    val region by localityViewModel.region.collectAsStateWithLifecycle()
    val regionDistrict by localityViewModel.regionDistrict.collectAsStateWithLifecycle()
    val localityCode by localityViewModel.localityCode.collectAsStateWithLifecycle()
    val localityShortName by localityViewModel.localityShortName.collectAsStateWithLifecycle()
    val localityType by localityViewModel.localityType.collectAsStateWithLifecycle()
    val localityName by localityViewModel.localityName.collectAsStateWithLifecycle()

    Timber.tag(TAG).d("Init Focus Requesters for all locality fields")
    val focusRequesters: MutableMap<String, InputFocusRequester> = HashMap()
    enumValues<LocalityFields>().forEach {
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
        RegionComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[LocalityFields.LOCALITY_REGION.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    localityViewModel.onTextFieldFocusChanged(
                        focusedField = LocalityFields.LOCALITY_REGION,
                        isFocused = focusState.isFocused
                    )
                },
            listViewModel = regionsListViewModel,
            singleViewModel = regionViewModel,
            inputWrapper = region,
            onValueChange = { localityViewModel.onTextFieldEntered(LocalityInputEvent.Region(it)) },
            onImeKeyAction = localityViewModel::moveFocusImeAction
        )
        RegionDistrictComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[LocalityFields.LOCALITY_REGION_DISTRICT.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    localityViewModel.onTextFieldFocusChanged(
                        focusedField = LocalityFields.LOCALITY_REGION_DISTRICT,
                        isFocused = focusState.isFocused
                    )
                },
            regionId = region.item.itemId,
            listViewModel = regionDistrictsListViewModel,
            singleViewModel = regionDistrictViewModel,
            regionsListViewModel = regionsListViewModel,
            regionViewModel = regionViewModel,
            inputWrapper = regionDistrict,
            onValueChange = {
                localityViewModel.onTextFieldEntered(LocalityInputEvent.RegionDistrict(it))
            },
            onImeKeyAction = localityViewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[LocalityFields.LOCALITY_CODE.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    localityViewModel.onTextFieldFocusChanged(
                        focusedField = LocalityFields.LOCALITY_CODE,
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
                localityViewModel.onTextFieldEntered(LocalityInputEvent.LocalityCode(it))
            },
            onImeKeyAction = localityViewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[LocalityFields.LOCALITY_SHORT_NAME.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    localityViewModel.onTextFieldFocusChanged(
                        focusedField = LocalityFields.LOCALITY_SHORT_NAME,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.short_name_hint,
            leadingIcon = { Icon(painterResource(R.drawable.ic_ab_36), null) },
            keyboardOptions = remember {
                KeyboardOptions(
                    capitalization = KeyboardCapitalization.Characters,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            },
            inputWrapper = localityShortName,
            onValueChange = {
                localityViewModel.onTextFieldEntered(LocalityInputEvent.LocalityShortName(it))
            },
            onImeKeyAction = localityViewModel::moveFocusImeAction
            //onImeKeyAction = { } //viewModel.onContinueClick { onSubmit() }
        )
        ExposedDropdownMenuBoxComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[LocalityFields.LOCALITY_TYPE.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    localityViewModel.onTextFieldFocusChanged(
                        focusedField = LocalityFields.LOCALITY_TYPE,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.type_hint,
            leadingIcon = { Icon(painterResource(R.drawable.ic_signpost_36), null) },
            keyboardOptions = remember {
                KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            },
            inputWrapper = localityType,  //(1..28).map { it.toString() } stringArrayResource(com.oborodulin.jwsuite.domain.R.array.locality_types).toList()
            resourceResolver = { resolveLocalityTypeValue(localityType.value) },
            listItems = LocalityType.values().map { it.name },
            onValueChange = {
                localityViewModel.onTextFieldEntered(LocalityInputEvent.LocalityType(it))
            },
            onImeKeyAction = localityViewModel::moveFocusImeAction,
            //colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[LocalityFields.LOCALITY_NAME.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    localityViewModel.onTextFieldFocusChanged(
                        focusedField = LocalityFields.LOCALITY_NAME,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.name_hint,
            leadingIcon = { Icon(painterResource(R.drawable.ic_abc_36), null) },
            keyboardOptions = remember {
                KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
            },
            //  visualTransformation = ::creditCardFilter,
            inputWrapper = localityName,
            onValueChange = {
                localityViewModel.onTextFieldEntered(LocalityInputEvent.LocalityName(it))
            },
            onImeKeyAction = localityViewModel::moveFocusImeAction
        )
    }
}

@Composable
fun resolveLocalityTypeValue(localityTypeValue: String? = LocalityType.CITY.name): String {
    val resArray = stringArrayResource(com.oborodulin.jwsuite.domain.R.array.locality_types)
    return resArray[LocalityType.valueOf(if (localityTypeValue.isNullOrEmpty()) LocalityType.CITY.name else localityTypeValue).ordinal]
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewLocalityView() {
    JWSuiteTheme {
        Surface {
            LocalityView(
                localityViewModel = LocalityViewModelImpl.previewModel(LocalContext.current),
                regionsListViewModel = RegionsListViewModelImpl.previewModel(LocalContext.current),
                regionViewModel = RegionViewModelImpl.previewModel(LocalContext.current),
                regionDistrictsListViewModel = RegionDistrictsListViewModelImpl.previewModel(
                    LocalContext.current
                ),
                regionDistrictViewModel = RegionDistrictViewModelImpl.previewModel(LocalContext.current)
            )
        }
    }
}
