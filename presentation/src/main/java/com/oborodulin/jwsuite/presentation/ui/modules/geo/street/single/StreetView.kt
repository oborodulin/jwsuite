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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.oborodulin.home.common.ui.components.field.ExposedDropdownMenuBoxComponent
import com.oborodulin.home.common.ui.components.field.SwitchComponent
import com.oborodulin.home.common.ui.components.field.TextFieldComponent
import com.oborodulin.home.common.ui.components.field.util.InputFocusRequester
import com.oborodulin.home.common.ui.components.field.util.inputProcess
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.ui.modules.geo.locality.single.LocalityComboBox
import com.oborodulin.jwsuite.presentation.ui.modules.geo.localitydistrict.single.LocalityDistrictComboBox
import com.oborodulin.jwsuite.presentation.ui.modules.geo.microdistrict.single.MicrodistrictComboBox
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import timber.log.Timber

private const val TAG = "Geo.StreetView"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun StreetView(viewModel: StreetViewModelImpl = hiltViewModel()) {
    Timber.tag(TAG).d("StreetView(...) called")
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

    Timber.tag(TAG).d("CollectAsStateWithLifecycle for all Street fields")
    val locality by viewModel.locality.collectAsStateWithLifecycle()
    val localityDistrict by viewModel.localityDistrict.collectAsStateWithLifecycle()
    val microdistrict by viewModel.microdistrict.collectAsStateWithLifecycle()
    val roadType by viewModel.roadType.collectAsStateWithLifecycle()
    val isPrivateSector by viewModel.isPrivateSector.collectAsStateWithLifecycle()
    val estimatedHouses by viewModel.estimatedHouses.collectAsStateWithLifecycle()
    val streetName by viewModel.streetName.collectAsStateWithLifecycle()

    val roadTypes by viewModel.roadTypes.collectAsStateWithLifecycle()

    Timber.tag(TAG).d("Init Focus Requesters for all Street fields")
    val focusRequesters: MutableMap<String, InputFocusRequester> = HashMap()
    enumValues<StreetFields>().forEach {
        focusRequesters[it.name] = InputFocusRequester(it, remember { FocusRequester() })
    }

    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("StreetView(...): LaunchedEffect()")
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
        LocalityComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[StreetFields.STREET_LOCALITY.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = StreetFields.STREET_LOCALITY,
                        isFocused = focusState.isFocused
                    )
                },
            inputWrapper = locality,
            onValueChange = { viewModel.onTextFieldEntered(StreetInputEvent.Locality(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        LocalityDistrictComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[StreetFields.STREET_LOCALITY_DISTRICT.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = StreetFields.STREET_LOCALITY_DISTRICT,
                        isFocused = focusState.isFocused
                    )
                },
            localityId = locality.item?.itemId,
            inputWrapper = localityDistrict,
            onValueChange = {
                viewModel.onTextFieldEntered(StreetInputEvent.LocalityDistrict(it))
            },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        MicrodistrictComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[StreetFields.STREET_MICRODISTRICT.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = StreetFields.STREET_MICRODISTRICT,
                        isFocused = focusState.isFocused
                    )
                },
            localityId = locality.item?.itemId,
            localityDistrictId = localityDistrict.item?.itemId,
            inputWrapper = microdistrict,
            onValueChange = {
                viewModel.onTextFieldEntered(StreetInputEvent.Microdistrict(it))
            },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        ExposedDropdownMenuBoxComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[StreetFields.STREET_ROAD_TYPE.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = StreetFields.STREET_ROAD_TYPE,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.type_hint,
            leadingIcon = { Icon(painterResource(R.drawable.ic_road_36), null) },
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
            },
            inputWrapper = roadType,
            values = roadTypes.values.toList(), // resolve Enums to Resource
            keys = roadTypes.keys.map { it.name }, // Enums
            onValueChange = {
                viewModel.onTextFieldEntered(StreetInputEvent.RoadType(it))
            },
            onImeKeyAction = viewModel::moveFocusImeAction,
        )
        SwitchComponent(
            switchModifier = Modifier
                .height(90.dp)
                .focusRequester(focusRequesters[StreetFields.STREET_IS_PRIVATE_SECTOR.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = StreetFields.STREET_IS_PRIVATE_SECTOR,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.private_sector_hint,
            inputWrapper = isPrivateSector,
            onCheckedChange = {
                viewModel.onTextFieldEntered(StreetInputEvent.IsPrivateSector(it))
            }
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[StreetFields.STREET_EST_HOUSES.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = StreetFields.STREET_EST_HOUSES,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.estimated_houses_hint,
            leadingIcon = { Icon(painterResource(R.drawable.ic_format_list_numbered_36), null) },
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
            },
            inputWrapper = estimatedHouses,
            onValueChange = {
                viewModel.onTextFieldEntered(StreetInputEvent.EstimatedHouses(it.toIntOrNull()))
            },
            onImeKeyAction = viewModel::moveFocusImeAction
            //onImeKeyAction = { } //viewModel.onContinueClick { onSubmit() }
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[StreetFields.STREET_NAME.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
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
            onValueChange = { viewModel.onTextFieldEntered(StreetInputEvent.StreetName(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewStreetView() {
    JWSuiteTheme {
        Surface {
            /*StreetView(
                viewModel = StreetViewModelImpl.previewModel(LocalContext.current),
                regionsListViewModel = RegionsListViewModelImpl.previewModel(LocalContext.current),
                regionViewModel = RegionViewModelImpl.previewModel(LocalContext.current),
                regionDistrictsListViewModel = RegionDistrictsListViewModelImpl.previewModel(
                    LocalContext.current
                ),
                regionDistrictViewModel = RegionDistrictViewModelImpl.previewModel(LocalContext.current)
            )*/
        }
    }
}
