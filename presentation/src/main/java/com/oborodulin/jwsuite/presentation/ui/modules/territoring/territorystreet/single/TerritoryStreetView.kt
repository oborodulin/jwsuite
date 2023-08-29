package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territorystreet.single

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.oborodulin.home.common.ui.components.field.ExposedDropdownMenuBoxComponent
import com.oborodulin.home.common.ui.components.field.TextFieldComponent
import com.oborodulin.home.common.ui.components.field.util.InputFocusRequester
import com.oborodulin.home.common.ui.components.field.util.inputProcess
import com.oborodulin.home.common.ui.components.radio.RadioBooleanComponent
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.ui.modules.geo.street.list.StreetsListUiAction
import com.oborodulin.jwsuite.presentation.ui.modules.geo.street.single.StreetComboBox
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.single.TerritoryComboBox
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.territorystreet.list.TerritoryStreetsListViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import timber.log.Timber

private const val TAG = "Territoring.TerritoryStreetView"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TerritoryStreetView(
    territoryStreetsListViewModel: TerritoryStreetsListViewModelImpl = hiltViewModel(),
    territoryStreetViewModel: TerritoryStreetViewModelImpl = hiltViewModel()
) {
    Timber.tag(TAG).d("TerritoryStreetView(...) called")
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(territoryStreetViewModel.events, lifecycleOwner) {
        territoryStreetViewModel.events.flowWithLifecycle(
            lifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
    }

    Timber.tag(TAG).d("Territory Street: CollectAsStateWithLifecycle for all fields")
    val territory by territoryStreetViewModel.territory.collectAsStateWithLifecycle()
    val street by territoryStreetViewModel.street.collectAsStateWithLifecycle()
    val isPrivateSector by territoryStreetViewModel.isPrivateSector.collectAsStateWithLifecycle()
    val isEvenSide by territoryStreetViewModel.isEvenSide.collectAsStateWithLifecycle()
    val estimatedHouses by territoryStreetViewModel.estimatedHouses.collectAsStateWithLifecycle()

    val territoryStreets by territoryStreetsListViewModel.uiStateFlow.collectAsStateWithLifecycle()

    Timber.tag(TAG).d("Territory Street: Init Focus Requesters for all fields")
    val focusRequesters: MutableMap<String, InputFocusRequester> = HashMap()
    enumValues<TerritoryStreetFields>().forEach {
        focusRequesters[it.name] = InputFocusRequester(it, remember { FocusRequester() })
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("TerritoryStreetView(...): LaunchedEffect()")
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
        TerritoryComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[TerritoryStreetFields.TERRITORY_STREET_TERRITORY.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    territoryStreetViewModel.onTextFieldFocusChanged(
                        focusedField = TerritoryStreetFields.TERRITORY_STREET_TERRITORY,
                        isFocused = focusState.isFocused
                    )
                },
            enabled = false,
            sharedViewModel = null,
            inputWrapper = territory,
            onImeKeyAction = territoryStreetViewModel::moveFocusImeAction
        )
        StreetComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[TerritoryStreetFields.TERRITORY_STREET_STREET.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    territoryStreetViewModel.onTextFieldFocusChanged(
                        focusedField = TerritoryStreetFields.TERRITORY_STREET_STREET,
                        isFocused = focusState.isFocused
                    )
                },
            loadListUiAction = StreetsListUiAction.LoadForTerritory(
                localityId = locality.item?.itemId!!,
                localityDistrictId = localityDistrict.item?.itemId,
                microdistrictId = microdistrict.item?.itemId,
                excludes = (territoryStreets as UiState.Success).data.map { it.streetId }
            ),
            inputWrapper = street,
            onValueChange = {
                territoryStreetViewModel.onTextFieldEntered(TerritoryStreetInputEvent.Street(it))
            },
            onImeKeyAction = territoryStreetViewModel::moveFocusImeAction
        )
        RadioBooleanComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[TerritoryStreetFields.TERRITORY_STREET_IS_PRIVATE_SECTOR.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    territoryStreetViewModel.onTextFieldFocusChanged(
                        focusedField = TerritoryStreetFields.TERRITORY_STREET_IS_PRIVATE_SECTOR,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.is_private_sector_hint,
            painterResId = R.drawable.ic_private_sector_36,
            inputWrapper = isPrivateSector,
            onValueChange = {
                territoryStreetViewModel.onTextFieldEntered(
                    TerritoryStreetInputEvent.IsPrivateSector(it)
                )
            }
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[TerritoryStreetFields.TERRITORY_STREET_IS_EVEN_SIDE.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    territoryStreetViewModel.onTextFieldFocusChanged(
                        focusedField = TerritoryStreetFields.TERRITORY_STREET_IS_EVEN_SIDE,
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
            inputWrapper = isEvenSide,
            onValueChange = {
                territoryStreetViewModel.onTextFieldEntered(
                    TerritoryStreetInputEvent.IsEvenSide(
                        it
                    )
                )
            },
            onImeKeyAction = territoryStreetViewModel::moveFocusImeAction
            //onImeKeyAction = { } //viewModel.onContinueClick { onSubmit() }
        )
        ExposedDropdownMenuBoxComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[TerritoryStreetFields.LOCALITY_TYPE.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    territoryStreetViewModel.onTextFieldFocusChanged(
                        focusedField = TerritoryStreetFields.LOCALITY_TYPE,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.type_hint,
            leadingIcon = { Icon(painterResource(R.drawable.ic_signpost_36), null) },
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
            },
            inputWrapper = localityType,
            values = localityTypes.values.toList(), // resolve Enums to Resource
            keys = localityTypes.keys.map { it.name }, // Enums
            onValueChange = {
                territoryStreetViewModel.onTextFieldEntered(
                    TerritoryStreetInputEvent.TerritoryStreetType(
                        it
                    )
                )
            },
            onImeKeyAction = territoryStreetViewModel::moveFocusImeAction,
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[TerritoryStreetFields.TERRITORY_STREET_EST_HOUSES.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    territoryStreetViewModel.onTextFieldFocusChanged(
                        focusedField = TerritoryStreetFields.TERRITORY_STREET_EST_HOUSES,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.name_hint,
            leadingIcon = { Icon(painterResource(R.drawable.ic_abc_36), null) },
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done)
            },
            //  visualTransformation = ::creditCardFilter,
            inputWrapper = estimatedHouses,
            onValueChange = {
                territoryStreetViewModel.onTextFieldEntered(
                    TerritoryStreetInputEvent.EstHouses(
                        it
                    )
                )
            },
            onImeKeyAction = territoryStreetViewModel::moveFocusImeAction
        )
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewTerritoryStreetView() {
    JWSuiteTheme {
        Surface {
            /*TerritoryStreetView(
                localityViewModel = TerritoryStreetViewModelImpl.previewModel(LocalContext.current),
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
