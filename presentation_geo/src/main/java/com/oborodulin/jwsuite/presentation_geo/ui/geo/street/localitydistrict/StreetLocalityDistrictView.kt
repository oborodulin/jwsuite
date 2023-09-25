package com.oborodulin.jwsuite.presentation_geo.ui.geo.street.localitydistrict

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.oborodulin.home.common.ui.components.field.util.InputFocusRequester
import com.oborodulin.home.common.ui.components.field.util.inputProcess
import com.oborodulin.home.common.ui.components.search.SearchComponent
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.SharedViewModeled
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput
import com.oborodulin.jwsuite.presentation.ui.AppState
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryUi
import com.oborodulin.jwsuite.presentation_territory.ui.model.toTerritoriesListItem
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.house.list.HousesListUiAction
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.house.list.HousesListView
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.house.list.HousesListViewModel
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single.TerritoryComboBox
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single.TerritoryViewModel
import timber.log.Timber
import java.util.EnumMap

private const val TAG = "Territoring.StreetLocalityDistrictView"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun StreetLocalityDistrictView(
    appState: AppState,
    territoryUi: TerritoryUi? = null,
    sharedViewModel: SharedViewModeled<ListItemModel?>?,
    territoryViewModel: TerritoryViewModel,
    housesListViewModel: HousesListViewModel,
    territoryHouseViewModel: StreetLocalityDistrictViewModelImpl = hiltViewModel()
) {
    Timber.tag(TAG).d("StreetLocalityDistrictView(...) called")
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(territoryHouseViewModel.events, lifecycleOwner) {
        territoryHouseViewModel.events.flowWithLifecycle(
            lifecycleOwner.lifecycle, Lifecycle.State.STARTED
        )
    }

    territoryUi?.let {
        territoryHouseViewModel.onTextFieldEntered(StreetLocalityDistrictInputEvent.Street(it.toTerritoriesListItem()))
    }
    Timber.tag(TAG).d("Territory House: CollectAsStateWithLifecycle for all fields")
    val territory by territoryHouseViewModel.street.collectAsStateWithLifecycle()
    val house by territoryHouseViewModel.house.collectAsStateWithLifecycle()
    val searchText by housesListViewModel.searchText.collectAsStateWithLifecycle()

    Timber.tag(TAG).d("Territory House: Init Focus Requesters for all fields")
    val focusRequesters =
        EnumMap<StreetLocalityDistrictFields, InputFocusRequester>(StreetLocalityDistrictFields::class.java)
    enumValues<StreetLocalityDistrictFields>().forEach {
        focusRequesters[it] = InputFocusRequester(it, remember { FocusRequester() })
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("StreetLocalityDistrictView(...): LaunchedEffect()")
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
                .focusRequester(focusRequesters[StreetLocalityDistrictFields.STREET_LOCALITY_DISTRICT_STREET]!!.focusRequester)
                .onFocusChanged { focusState ->
                    territoryHouseViewModel.onTextFieldFocusChanged(
                        focusedField = StreetLocalityDistrictFields.STREET_LOCALITY_DISTRICT_STREET,
                        isFocused = focusState.isFocused
                    )
                },
            enabled = false,
            sharedViewModel = sharedViewModel,
            singleViewModel = territoryViewModel,
            inputWrapper = territory,
            onImeKeyAction = territoryHouseViewModel::moveFocusImeAction
        )
        StreetLocalityDistrictComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[StreetLocalityDistrictFields.TERRITORY_HOUSE_HOUSE]!!.focusRequester)
                .onFocusChanged { focusState ->
                    territoryHouseViewModel.onTextFieldFocusChanged(
                        focusedField = StreetLocalityDistrictFields.TERRITORY_HOUSE_HOUSE,
                        isFocused = focusState.isFocused
                    )
                },
            territoryId = territory.item?.itemId!!,
            sharedViewModel = sharedViewModel,
            inputWrapper = house,
            onValueChange = {
                territoryHouseViewModel.onTextFieldEntered(StreetLocalityDistrictInputEvent.House(it))
                territoryUi?.id?.let { territoryId ->
                    housesListViewModel.submitAction(HousesListUiAction.LoadForTerritory(territoryId))
                }
            },
            onImeKeyAction = territoryHouseViewModel::moveFocusImeAction
        )
        territoryUi?.id?.let { territoryId ->
            SearchComponent(searchText, onValueChange = housesListViewModel::onSearchTextChange)
            HousesListView(
                navController = appState.commonNavController,
                territoryInput = NavigationInput.TerritoryInput(territoryId),
                isForTerritory = true
            )
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewStreetLocalityDistrictView() {
    JWSuiteTheme {
        Surface {
            /*StreetLocalityDistrictView(
                localityViewModel = StreetLocalityDistrictViewModelImpl.previewModel(LocalContext.current),
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
