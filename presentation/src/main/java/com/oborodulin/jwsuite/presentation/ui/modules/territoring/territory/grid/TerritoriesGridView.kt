package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.grid

import android.content.res.Configuration
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.ComponentUiAction
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.home.common.util.OnListItemEvent
import com.oborodulin.jwsuite.domain.util.TerritoryLocationType
import com.oborodulin.jwsuite.domain.util.TerritoryProcessType
import com.oborodulin.jwsuite.presentation.AppState
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.CongregationInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryInput
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.TerritoriesListItem
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.details.TerritoryDetailsUiAction
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.details.TerritoryDetailsViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.list.TerritoriesListItemComponent
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation.util.Constants.CELL_SIZE
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import java.util.UUID

private const val TAG = "Territoring.TerritoriesGridView"

@Composable
fun TerritoriesGridView(
    appState: AppState,
    territoriesGridViewModel: TerritoriesGridViewModel,
    territoryDetailsViewModel: TerritoryDetailsViewModelImpl = hiltViewModel(),
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
        appState.sharedViewModel.value?.sharedFlow?.collectAsStateWithLifecycle()?.value
    val congregationId = congregationInput?.congregationId ?: currentCongregation?.id
    Timber.tag(TAG)
        .d("currentCongregation = %s; congregationId = %s", currentCongregation, congregationId)
    LaunchedEffect(
        congregationId,
        territoryProcessType,
        territoryLocationType,
        locationId,
        isPrivateSector
    ) {
        Timber.tag(TAG).d("TerritoriesGridView: LaunchedEffect() BEFORE collect ui state flow")
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
    val searchText by territoriesGridViewModel.searchText.collectAsStateWithLifecycle()
    territoriesGridViewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
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
                    onChecked = { territoriesGridViewModel.observeCheckedTerritories() }
                ) { territory ->
                    with(territoryDetailsViewModel) {
                        submitAction(TerritoryDetailsUiAction.Load(territory.id))
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
                        submitAction(TerritoryDetailsUiAction.Load(territory.id))
                    }
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("TerritoriesGridView: LaunchedEffect() AFTER collect ui state flow")
        territoriesGridViewModel.singleEventFlow.collectLatest {
            Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
            when (it) {
                is TerritoriesGridUiSingleEvent.OpenTerritoryScreen -> {
                    appState.commonNavController.navigate(it.navRoute)
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
        var filteredItems: List<TerritoriesListItem>
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
            filteredItems = if (searchedText.isEmpty()) {
                territories
            } else {
                territories.filter { it.doesMatchSearchQuery(searchedText) }
            }
            items(filteredItems.size) { index ->
                filteredItems[index].let { territory ->
                    TerritoriesClickableGridItemComponent(
                        territory = territory,
                        onChecked = onChecked,
                        onClick = onClick
                    )
                }
            }
        }
    } else {
        Text(
            modifier = Modifier.fillMaxSize(),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.territories_list_empty_text),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold
        )
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
        var filteredItems: List<TerritoriesListItem>
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
            filteredItems = if (searchedText.isEmpty()) {
                territories
            } else {
                territories.filter { it.doesMatchSearchQuery(searchedText) }
            }
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
        Text(
            modifier = Modifier.fillMaxSize(),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.territories_list_empty_text),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold
        )
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