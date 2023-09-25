package com.oborodulin.jwsuite.presentation_geo.ui.geo.street.localitydistrict

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.components.buttons.SaveButtonComponent
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.StreetLocalityDistrictInput
import com.oborodulin.jwsuite.presentation.ui.AppState
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.house.list.HousesListViewModelImpl
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single.TerritoryViewModel
import timber.log.Timber

private const val TAG = "Territoring.StreetLocalityDistrictScreen"

@Composable
fun StreetLocalityDistrictScreen(
    appState: AppState,
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    territoryViewModel: TerritoryViewModel,
    housesListViewModel: HousesListViewModelImpl = hiltViewModel(),
    territoryHouseViewModel: StreetLocalityDistrictViewModelImpl = hiltViewModel(),
    territoryHouseInput: StreetLocalityDistrictInput? = null
) {
    Timber.tag(TAG)
        .d("StreetLocalityDistrictScreen(...) called: territoryHouseInput = %s", territoryHouseInput)

    val house by territoryHouseViewModel.house.collectAsStateWithLifecycle()
    val checkedHouses by housesListViewModel.checkedListItems.collectAsStateWithLifecycle()
    val areListItemsChecked by housesListViewModel.areListItemsChecked.collectAsStateWithLifecycle()

    val saveButtonOnClick = {
        // checks all errors
        territoryHouseViewModel.onContinueClick(areListItemsChecked) {
            Timber.tag(TAG).d("StreetLocalityDistrictScreen(...): Save Button onClick...")
            // if success, save then backToBottomBarScreen
            territoryHouseViewModel.handleActionJob(
                {
                    val houses = checkedHouses.map { it.id }.toMutableSet()
                    house.item?.itemId?.let { houses.add(it) }
                    territoryHouseViewModel.submitAction(StreetLocalityDistrictUiAction.Save(houses.toList()))
                },
                { appState.commonNavigateUp() })
        }
    }
    LaunchedEffect(territoryHouseInput?.territoryId) {
        Timber.tag(TAG).d("StreetLocalityDistrictScreen: LaunchedEffect() BEFORE collect ui state flow")
        territoryHouseInput?.let {
            territoryHouseViewModel.submitAction(StreetLocalityDistrictUiAction.Load(it.territoryId))
        }
    }
    territoryHouseViewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        territoryHouseViewModel.dialogTitleResId.collectAsStateWithLifecycle().value?.let {
            appState.actionBarSubtitle.value = stringResource(it)
        }
        val areInputsValid by territoryHouseViewModel.areInputsValid.collectAsStateWithLifecycle()
        JWSuiteTheme { //(darkTheme = true)
            ScaffoldComponent(
                appState = appState,
                topBarNavigationIcon = {
                    IconButton(onClick = { appState.commonNavigateUp() }) {
                        Icon(Icons.Outlined.ArrowBack, null)
                    }
                },
                topBarActions = {
                    IconButton(
                        enabled = areInputsValid || areListItemsChecked, onClick = saveButtonOnClick
                    ) { Icon(Icons.Outlined.Done, null) }
                }
            ) { paddingValues ->
                CommonScreen(paddingValues = paddingValues, state = state) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        StreetLocalityDistrictView(
                            appState = appState,
                            territoryUi = it,
                            sharedViewModel = appState.sharedViewModel.value,
                            housesListViewModel = housesListViewModel,
                            territoryViewModel = territoryViewModel
                        )
                        Spacer(Modifier.height(8.dp))
                        SaveButtonComponent(
                            enabled = areInputsValid || areListItemsChecked,
                            onClick = saveButtonOnClick
                        )
                    }
                }
            }
        }
    }
}