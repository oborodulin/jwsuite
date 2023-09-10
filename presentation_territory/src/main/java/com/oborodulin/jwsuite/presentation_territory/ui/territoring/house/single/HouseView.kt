package com.oborodulin.jwsuite.presentation_territory.ui.territoring.house.single

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.oborodulin.home.common.ui.components.field.TextFieldComponent
import com.oborodulin.home.common.ui.components.field.util.InputFocusRequester
import com.oborodulin.home.common.ui.components.field.util.inputProcess
import com.oborodulin.home.common.ui.components.radio.RadioBooleanComponent
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.SharedViewModeled
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_geo.ui.geo.street.list.StreetsListUiAction
import com.oborodulin.jwsuite.presentation_geo.ui.geo.street.single.StreetComboBox
import com.oborodulin.jwsuite.presentation_territory.R
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryStreetUiModel
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single.TerritoryComboBox
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single.TerritoryViewModel
import timber.log.Timber

private const val TAG = "Territoring.TerritoryStreetView"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TerritoryStreetView(
    uiModel: TerritoryStreetUiModel,
    sharedViewModel: SharedViewModeled<ListItemModel?>?,
    territoryViewModel: TerritoryViewModel,
    viewModel: HouseViewModelImpl = hiltViewModel()
) {
    Timber.tag(TAG).d("TerritoryStreetView(...) called")
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(viewModel.events, lifecycleOwner) {
        viewModel.events.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }

    Timber.tag(TAG).d("Territory Street: CollectAsStateWithLifecycle for all fields")
    val territory by viewModel.territory.collectAsStateWithLifecycle()
    val street by viewModel.street.collectAsStateWithLifecycle()
    val isPrivateSector by viewModel.isPrivateSector.collectAsStateWithLifecycle()
    val isEvenSide by viewModel.isEvenSide.collectAsStateWithLifecycle()
    val estimatedHouses by viewModel.estimatedHouses.collectAsStateWithLifecycle()

    Timber.tag(TAG).d("Territory Street: Init Focus Requesters for all fields")
    val focusRequesters: MutableMap<String, InputFocusRequester> = HashMap()
    enumValues<HouseFields>().forEach {
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
                .focusRequester(focusRequesters[HouseFields.TERRITORY_STREET_TERRITORY.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = HouseFields.TERRITORY_STREET_TERRITORY,
                        isFocused = focusState.isFocused
                    )
                },
            enabled = false,
            sharedViewModel = sharedViewModel,
            singleViewModel = territoryViewModel,
            inputWrapper = territory,
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        StreetComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[HouseFields.TERRITORY_STREET_STREET.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = HouseFields.TERRITORY_STREET_STREET,
                        isFocused = focusState.isFocused
                    )
                },
            loadListUiAction = StreetsListUiAction.LoadForTerritory(
                localityId = uiModel.territory.locality.id!!,
                localityDistrictId = uiModel.territory.localityDistrict?.id,
                microdistrictId = uiModel.territory.microdistrict?.id,
                excludes = uiModel.streets.map { it.id }
            ),
            inputWrapper = street,
            onValueChange = { viewModel.onTextFieldEntered(HouseInputEvent.Street(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        RadioBooleanComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[HouseFields.TERRITORY_STREET_IS_PRIVATE_SECTOR.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = HouseFields.TERRITORY_STREET_IS_PRIVATE_SECTOR,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = com.oborodulin.jwsuite.presentation.R.string.is_private_sector_hint,
            painterResId = com.oborodulin.jwsuite.presentation.R.drawable.ic_private_sector_36,
            inputWrapper = isPrivateSector,
            onValueChange = {
                viewModel.onTextFieldEntered(HouseInputEvent.IsPrivateSector(it))
            }
        )
        RadioBooleanComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[HouseFields.TERRITORY_STREET_IS_EVEN_SIDE.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = HouseFields.TERRITORY_STREET_IS_EVEN_SIDE,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.territory_street_is_even_side_hint,
            painterResId = R.drawable.ic_street_side_36,
            inputWrapper = isEvenSide,
            onValueChange = { viewModel.onTextFieldEntered(HouseInputEvent.IsEvenSide(it)) }
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[HouseFields.TERRITORY_STREET_EST_HOUSES.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = HouseFields.TERRITORY_STREET_EST_HOUSES,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.territory_street_estimated_houses_hint,
            leadingImageVector = Icons.Outlined.Home,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done)
            },
            //  visualTransformation = ::creditCardFilter,
            inputWrapper = estimatedHouses,
            onValueChange = { viewModel.onTextFieldEntered(HouseInputEvent.EstHouses(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
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
