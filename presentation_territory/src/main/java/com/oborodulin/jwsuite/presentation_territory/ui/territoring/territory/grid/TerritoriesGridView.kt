package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid

import android.content.res.Configuration
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.ComponentUiAction
import com.oborodulin.home.common.ui.components.list.EmptyListTextComponent
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.home.common.util.LogLevel.LOG_UI_STATE
import com.oborodulin.home.common.util.OnListItemEvent
import com.oborodulin.jwsuite.domain.types.TerritoryLocationType
import com.oborodulin.jwsuite.domain.types.TerritoryProcessType
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.CongregationInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryInput
import com.oborodulin.jwsuite.presentation.ui.AppState
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_territory.R
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoriesListItem
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.details.list.TerritoryDetailsListUiAction
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.details.list.TerritoryDetailsListViewModelImpl
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.list.TerritoriesListItemComponent
import com.oborodulin.jwsuite.presentation_territory.util.Constants.CELL_SIZE
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import java.util.UUID

private const val TAG = "Territoring.TerritoriesGridView"

@Composable
fun TerritoriesGridView(
    appState: AppState,
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    territoriesGridViewModel: TerritoriesGridViewModel,
    territoryDetailsViewModel: TerritoryDetailsListViewModelImpl = hiltViewModel(),
    territoryProcessType: TerritoryProcessType,
    congregationInput: CongregationInput? = null,
    territoryInput: TerritoryInput? = null,
    territoryLocationType: TerritoryLocationType,
    locationId: UUID? = null,
    isPrivateSector: Boolean = false
) {
    Timber.tag(TAG).d(
        "TerritoriesGridView(...) called: territoryProcessType = %s; territoryLocationType = %s; isPrivateSector = %s; locationId = %s",
        territoryProcessType,
        territoryLocationType,
        isPrivateSector,
        locationId
    )
    val currentCongregation =
        appState.congregationSharedViewModel.value?.sharedFlow?.collectAsStateWithLifecycle()?.value
    val congregationId = congregationInput?.congregationId ?: currentCongregation?.itemId
    Timber.tag(TAG)
        .d("currentCongregation = %s; congregationId = %s", currentCongregation, congregationId)
    LaunchedEffect(
        congregationId,
        territoryProcessType,
        territoryLocationType,
        locationId,
        isPrivateSector
    ) {
        Timber.tag(TAG).d("TerritoriesGridView -> LaunchedEffect()")
        territoriesGridViewModel.submitAction(
            TerritoriesGridUiAction.Load(
                congregationId = congregationId,
                territoryProcessType = territoryProcessType,
                territoryLocationType = territoryLocationType,
                locationId = locationId,
                isPrivateSector = isPrivateSector
            )
        )
    }

    val searchText = when (territoryProcessType) {
        TerritoryProcessType.HAND_OUT -> territoriesGridViewModel.handOutSearchText.collectAsStateWithLifecycle().value
        TerritoryProcessType.AT_WORK -> territoriesGridViewModel.atWorkSearchText.collectAsStateWithLifecycle().value
        TerritoryProcessType.IDLE -> territoriesGridViewModel.idleSearchText.collectAsStateWithLifecycle().value
        TerritoryProcessType.ALL -> territoriesGridViewModel.searchText.collectAsStateWithLifecycle().value
    }
    territoriesGridViewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        if (LOG_UI_STATE) {
            Timber.tag(TAG).d("Collect ui state flow: %s", state)
        }
        CommonScreen(state = state) {
            if (listOf(
                    TerritoryProcessType.HAND_OUT,
                    TerritoryProcessType.AT_WORK,
                    TerritoryProcessType.IDLE
                ).contains(territoryProcessType)
            ) {
                TerritoriesClickableGrid(
                    territories = it,
                    territoryInput = territoryInput,
                    searchedText = searchText.text,
                    onChecked = { territoriesGridViewModel.observeCheckedListItems() }
                ) { territory ->
                    with(territoryDetailsViewModel) {
                        submitAction(TerritoryDetailsListUiAction.Load(territory.id))
                    }
                }
            } else { // TerritoryProcessType.ALL
                TerritoriesEditableGrid(
                    territories = it,
                    territoryInput = territoryInput,
                    searchedText = searchText.text,
                    onFavorite = { listItem ->
                        listItem.itemId?.let { id ->
                            /*territoriesGridViewModel.submitAction(
                                TerritoriesGridUiAction.MakeFavoriteCongregation(id)
                            )*/
                        }
                    },
                    onEdit = { territory ->
                        territoriesGridViewModel.submitAction(
                            TerritoriesGridUiAction.EditTerritory(territory.id)
                        )
                    },
                    onDelete = { territory ->
                        territoriesGridViewModel.submitAction(
                            TerritoriesGridUiAction.DeleteTerritory(territory.id)
                        )
                    }
                ) { territory ->
                    with(territoryDetailsViewModel) {
                        submitAction(TerritoryDetailsListUiAction.Load(territory.id))
                    }
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("TerritoriesGridView -> LaunchedEffect() -> collect single Event Flow")
        territoriesGridViewModel.singleEventFlow.collectLatest {
            Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
            when (it) {
                is TerritoriesGridUiSingleEvent.OpenTerritoryScreen -> {
                    appState.mainNavigate(it.navRoute)
                }

                is TerritoriesGridUiSingleEvent.OpenHandOutConfirmationScreen -> {
                    appState.mainNavigate(it.navRoute)
                }

                is TerritoriesGridUiSingleEvent.OpenProcessConfirmationScreen -> {
                    appState.mainNavigate(it.navRoute)
                }

                is TerritoriesGridUiSingleEvent.OpenReportStreetsScreen -> {
                    appState.mainNavigate(it.navRoute)
                }

                is TerritoriesGridUiSingleEvent.OpenReportHousesScreen -> {
                    appState.mainNavigate(it.navRoute)
                }

                is TerritoriesGridUiSingleEvent.OpenReportRoomsScreen -> {
                    appState.mainNavigate(it.navRoute)
                }
            }
        }
    }
}

@Composable
fun TerritoriesClickableGrid(
    territories: List<TerritoriesListItem>,
    territoryInput: TerritoryInput?,
    searchedText: String = "",
    onChecked: (Boolean) -> Unit,
    onClick: (TerritoriesListItem) -> Unit
) {
    Timber.tag(TAG).d("TerritoriesClickableGrid(...) called")
    if (territories.isNotEmpty()) {
        val filteredItems = remember(territories, searchedText) {
            if (searchedText.isEmpty()) {
                territories
            } else {
                territories.filter { it.doesMatchSearchQuery(searchedText) }
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Adaptive(CELL_SIZE),
            modifier = Modifier
                .selectableGroup() // Optional, for accessibility purpose
                .padding(4.dp)
                .focusable(enabled = true),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(filteredItems.size) { index ->
                TerritoriesClickableGridItemComponent(
                    territory = filteredItems[index],
                    onChecked = onChecked,
                    onClick = onClick
                )
            }
        }
    } else {
        EmptyListTextComponent(R.string.territories_list_empty_text)
    }
}

@Composable
fun TerritoriesEditableGrid(
    territories: List<TerritoriesListItem>,
    territoryInput: TerritoryInput?,
    searchedText: String = "",
    onFavorite: OnListItemEvent,
    onEdit: (TerritoriesListItem) -> Unit,
    onDelete: (TerritoriesListItem) -> Unit,
    onClick: (TerritoriesListItem) -> Unit
) {
    Timber.tag(TAG).d("TerritoriesEditableGrid(...) called")
    var selectedIndex by remember { mutableStateOf(-1) } // by
    if (territories.isNotEmpty()) {
        val filteredItems = remember(territories, searchedText) {
            if (searchedText.isEmpty()) {
                territories
            } else {
                territories.filter { it.doesMatchSearchQuery(searchedText) }
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Adaptive(100.dp),
            modifier = Modifier
                .selectableGroup() // Optional, for accessibility purpose
                .padding(8.dp)
                .focusable(enabled = true),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(24.dp)
        ) {
            items(filteredItems.size) { index ->
                filteredItems[index].let { territory ->
                    val isSelected =
                        ((selectedIndex == -1) and (territoryInput?.territoryId == territory.id)) || (selectedIndex == index)
                    TerritoriesListItemComponent(
                        item = territory,
                        itemActions = listOf(
                            ComponentUiAction.EditListItem { onEdit(territory) },
                            ComponentUiAction.DeleteListItem(
                                stringResource(
                                    R.string.dlg_confirm_del_territory,
                                    territory.headline
                                )
                            ) { onDelete(territory) }),
                        selected = isSelected,
                        background = (if (isSelected) Color.LightGray else Color.Transparent),
                        onFavorite = onFavorite,
                        onClick = {
                            if (selectedIndex != index) selectedIndex = index
                            onClick(territory)
                        }
                    )
                }
            }
        }
    } else {
        EmptyListTextComponent(R.string.territories_list_empty_text)
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewTerritoriesClickableGrid() {
    JWSuiteTheme {
        Surface {
            TerritoriesClickableGrid(
                territories = TerritoriesGridViewModelImpl.previewList(LocalContext.current),
                territoryInput = TerritoryInput(UUID.randomUUID()),
                onChecked = {},
                onClick = {}
            )
        }
    }
}