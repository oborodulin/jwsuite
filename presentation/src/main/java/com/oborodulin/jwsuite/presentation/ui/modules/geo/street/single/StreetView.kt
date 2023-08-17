package com.oborodulin.jwsuite.presentation.ui.modules.geo.street.single

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

private const val TAG = "Geo.LocalityView"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LocalityView(
    streetViewModel: StreetViewModel,
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

    val events = remember(streetViewModel.events, lifecycleOwner) {
        streetViewModel.events.flowWithLifecycle(
            lifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
    }

    Timber.tag(TAG).d("CollectAsStateWithLifecycle for all Street fields")
    val locality by streetViewModel.locality.collectAsStateWithLifecycle()
    val localityDistrict by streetViewModel.localityDistrict.collectAsStateWithLifecycle()
    val microdistrict by streetViewModel.microdistrict.collectAsStateWithLifecycle()
    val roadType by streetViewModel.roadType.collectAsStateWithLifecycle()
    val isPrivateSector by streetViewModel.isPrivateSector.collectAsStateWithLifecycle()
    val estimatedHouses by streetViewModel.estimatedHouses.collectAsStateWithLifecycle()
    val streetName by streetViewModel.streetName.collectAsStateWithLifecycle()

    val roadTypes by streetViewModel.roadTypes.collectAsStateWithLifecycle()

    Timber.tag(TAG).d("Init Focus Requesters for all Street fields")
    val focusRequesters: MutableMap<String, InputFocusRequester> = HashMap()
    enumValues<StreetFields>().forEach {
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
                .focusRequester(focusRequesters[StreetFields.STREET_LOCALITY.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    streetViewModel.onTextFieldFocusChanged(
                        focusedField = StreetFields.STREET_LOCALITY,
                        isFocused = focusState.isFocused
                    )
                },
            listViewModel = regionsListViewModel,
            singleViewModel = regionViewModel,
            inputWrapper = locality,
            onValueChange = { streetViewModel.onTextFieldEntered(StreetInputEvent.Locality(it)) },
            onImeKeyAction = streetViewModel::moveFocusImeAction
        )
        RegionDistrictComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[StreetFields.STREET_LOCALITY_DISTRICT.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    streetViewModel.onTextFieldFocusChanged(
                        focusedField = StreetFields.STREET_LOCALITY_DISTRICT,
                        isFocused = focusState.isFocused
                    )
                },
            regionId = locality.item?.itemId,
            listViewModel = regionDistrictsListViewModel,
            singleViewModel = regionDistrictViewModel,
            regionsListViewModel = regionsListViewModel,
            regionViewModel = regionViewModel,
            inputWrapper = localityDistrict,
            onValueChange = {
                streetViewModel.onTextFieldEntered(StreetInputEvent.LocalityDistrict(it))
            },
            onImeKeyAction = streetViewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[StreetFields.STREET_MICRODISTRICT.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    streetViewModel.onTextFieldFocusChanged(
                        focusedField = StreetFields.STREET_MICRODISTRICT,
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
                KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
            },
            inputWrapper = microdistrict,
            onValueChange = {
                streetViewModel.onTextFieldEntered(StreetInputEvent.StreetCode(it))
            },
            onImeKeyAction = streetViewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[StreetFields.STREET_IS_PRIVATE_SECTOR.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    streetViewModel.onTextFieldFocusChanged(
                        focusedField = StreetFields.STREET_IS_PRIVATE_SECTOR,
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
            inputWrapper = isPrivateSector,
            onValueChange = {
                streetViewModel.onTextFieldEntered(StreetInputEvent.IsPrivateSector(it))
            },
            onImeKeyAction = streetViewModel::moveFocusImeAction
            //onImeKeyAction = { } //viewModel.onContinueClick { onSubmit() }
        )
        ExposedDropdownMenuBoxComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[StreetFields.STREET_EST_HOUSES.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    streetViewModel.onTextFieldFocusChanged(
                        focusedField = StreetFields.STREET_EST_HOUSES,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.type_hint,
            leadingIcon = { Icon(painterResource(R.drawable.ic_signpost_36), null) },
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
            },
            inputWrapper = roadType,
            values = roadTypes.values.toList(), // resolve Enums to Resource
            keys = roadTypes.keys.map { it.name }, // Enums
            onValueChange = {
                streetViewModel.onTextFieldEntered(StreetInputEvent.EstimatedHouses(it))
            },
            onImeKeyAction = streetViewModel::moveFocusImeAction,
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[StreetFields.STREET_NAME.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    streetViewModel.onTextFieldFocusChanged(
                        focusedField = StreetFields.STREET_NAME,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.name_hint,
            leadingIcon = { Icon(painterResource(R.drawable.ic_abc_36), null) },
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done)
            },
            //  visualTransformation = ::creditCardFilter,
            inputWrapper = streetName,
            onValueChange = {
                streetViewModel.onTextFieldEntered(StreetInputEvent.StreetName(it))
            },
            onImeKeyAction = streetViewModel::moveFocusImeAction
        )
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewLocalityView() {
    JWSuiteTheme {
        Surface {
            LocalityView(
                streetViewModel = StreetViewModelImpl.previewModel(LocalContext.current),
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
