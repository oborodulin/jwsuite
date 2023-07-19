package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.grid

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.navigation.NavController
import com.oborodulin.home.common.ui.ComponentUiAction
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.home.common.util.OnListItemEvent
import com.oborodulin.jwsuite.domain.util.TerritoryProcessType
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.CongregationInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryInput
import com.oborodulin.jwsuite.presentation.ui.modules.FavoriteCongregationViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.member.list.MembersListUiAction
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.member.list.MembersListViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.TerritoriesListItem
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import java.util.UUID

private const val TAG = "Territoring.TerritoriesView"

@Composable
fun TerritoriesView(
    sharedViewModel: FavoriteCongregationViewModelImpl = hiltViewModel(),
    territoriesGridViewModel: TerritoriesGridViewModelImpl = hiltViewModel(),
    membersListViewModel: MembersListViewModelImpl = hiltViewModel(),
    navController: NavController,
    territoryProcessType: TerritoryProcessType,
    congregationInput: CongregationInput? = null,
    territoryInput: TerritoryInput? = null,
    districtName: String? = null,
    isPrivateSector: Boolean = false
) {
    Timber.tag(TAG).d("TerritoriesView(...) called")
    val currentCongregation by sharedViewModel.sharedFlow.collectAsStateWithLifecycle(null)
    val congregationId = congregationInput?.congregationId ?: currentCongregation?.id
    Timber.tag(TAG)
        .d("currentCongregation = %s; congregationId = %s", currentCongregation, congregationId)
    LaunchedEffect(congregationId) {
        Timber.tag(TAG).d("TerritoriesView: LaunchedEffect() BEFORE collect ui state flow")
        territoriesGridViewModel.submitAction(
            TerritoriesGridUiAction.Load(
                congregationId = congregationId,
                territoryProcessType = territoryProcessType,
                districtName = districtName,
                isPrivateSector = isPrivateSector
            )
        )
    }
    territoriesGridViewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        CommonScreen(state = state) {
            when (territoryProcessType) {
                TerritoryProcessType.HAND_OUT ->
                    TerritoriesClickableGrid(
                        territories = it
                    ) { territory ->
                        with(membersListViewModel) {
                            submitAction(MembersListUiAction.LoadByCongregation(territory.id))
                        }
                    }

                TerritoryProcessType.AT_WORK ->
                    TerritoriesList(
                        territories = it,
                        territoryInput = territoryInput,
                        onFavorite = { listItem ->
                            /*listItem.itemId?.let { id ->
                                territoriesGridViewModel.submitAction(
                                    TerritoriesGridUiAction.MakeFavoriteCongregation(id)
                                )
                            }*/
                        }
                    ) { territory ->
                        with(membersListViewModel) {
                            submitAction(MembersListUiAction.LoadByCongregation(territory.id))
                        }
                    }

                TerritoryProcessType.IDLE -> {}
                TerritoryProcessType.ALL ->
                    TerritoriesEditableGrid(
                        territories = it,
                        territoryInput = territoryInput,
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
                        with(membersListViewModel) {
                            submitAction(MembersListUiAction.LoadByCongregation(territory.id))
                        }
                    }
            }
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("TerritoriesView: LaunchedEffect() AFTER collect ui state flow")
        territoriesGridViewModel.singleEventFlow.collectLatest {
            Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
            when (it) {
                is TerritoriesGridUiSingleEvent.OpenTerritoryScreen -> {
                    navController.navigate(it.navRoute)
                }
            }
        }
    }
}

@Composable
fun TerritoriesClickableGrid(
    territories: List<TerritoriesListItem>,
    onClick: (TerritoriesListItem) -> Unit
) {
    Timber.tag(TAG).d("TerritoriesClickableGrid(...) called")
    if (territories.isNotEmpty()) {
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
            items(territories.size) { index ->
                territories[index].let { territory ->
                    Card(
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth()
                            .clickable { onClick(territory) },
                        elevation = CardDefaults.cardElevation(8.dp)
                    ) {
                        Text(
                            text = territory.headline,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
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
fun TerritoriesList(
    territories: List<TerritoriesListItem>,
    territoryInput: TerritoryInput?,
    onFavorite: OnListItemEvent,
    onClick: (TerritoriesListItem) -> Unit
) {
    Timber.tag(TAG).d("TerritoriesList(...) called")
    var selectedIndex by remember { mutableStateOf(-1) } // by
    if (territories.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier
                .selectableGroup() // Optional, for accessibility purpose
                .padding(8.dp)
                .focusable(enabled = true)
        ) {
            items(territories.size) { index ->
                territories[index].let { territory ->
                    val isSelected = (selectedIndex == index)
                    TerritoriesListItemComponent(
                        item = territory,
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

@Composable
fun TerritoriesEditableGrid(
    territories: List<TerritoriesListItem>,
    territoryInput: TerritoryInput?,
    onFavorite: OnListItemEvent,
    onEdit: (TerritoriesListItem) -> Unit,
    onDelete: (TerritoriesListItem) -> Unit,
    onClick: (TerritoriesListItem) -> Unit
) {
    Timber.tag(TAG).d("TerritoriesEditableGrid(...) called")
    var selectedIndex by remember { mutableStateOf(-1) } // by
    if (territories.isNotEmpty()) {
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
            items(territories.size) { index ->
                territories[index].let { territory ->
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
                onClick = {}
            )
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewTerritoriesList() {
    JWSuiteTheme {
        Surface {
            TerritoriesList(
                territories = TerritoriesGridViewModelImpl.previewList(LocalContext.current),
                territoryInput = TerritoryInput(UUID.randomUUID()),
                onFavorite = {},
                onClick = {}
            )
        }
    }
}
