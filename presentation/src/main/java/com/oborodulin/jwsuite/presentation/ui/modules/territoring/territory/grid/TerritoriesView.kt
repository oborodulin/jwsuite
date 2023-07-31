package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.grid

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.oborodulin.home.common.ui.ComponentUiAction
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.home.common.util.OnListItemEvent
import com.oborodulin.jwsuite.domain.util.TerritoryLocationType
import com.oborodulin.jwsuite.domain.util.TerritoryProcessType
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.CongregationInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryInput
import com.oborodulin.jwsuite.presentation.ui.modules.FavoriteCongregationViewModelImpl
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
//    membersListViewModel: MembersListViewModelImpl = hiltViewModel(),
    navController: NavController,
    territoryProcessType: TerritoryProcessType,
    congregationInput: CongregationInput? = null,
    territoryInput: TerritoryInput? = null,
    territoryLocationType: TerritoryLocationType,
    locationId: UUID? = null,
    isPrivateSector: Boolean = false
) {
    Timber.tag(TAG).d("TerritoriesView(...) called")
    val currentCongregation by sharedViewModel.sharedFlow.collectAsStateWithLifecycle(null)
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
        Timber.tag(TAG).d("TerritoriesView: LaunchedEffect() BEFORE collect ui state flow")
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
                    onFavorite = { listItem ->
                        /*listItem.itemId?.let { id ->
                            territoriesGridViewModel.submitAction(
                                TerritoriesGridUiAction.MakeFavoriteCongregation(id)
                            )
                        }*/
                    }
                ) { territory ->
                    /*with(membersListViewModel) {
                        submitAction(MembersListUiAction.LoadByCongregation(territory.id))
                    }*/
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
                    /*with(membersListViewModel) {
                        submitAction(MembersListUiAction.LoadByCongregation(territory.id))
                    }*/
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
    territoryInput: TerritoryInput?,
    searchedText: String = "",
    onFavorite: OnListItemEvent,
    onClick: (TerritoriesListItem) -> Unit
) {
    Timber.tag(TAG).d("TerritoriesClickableGrid(...) called")
    if (territories.isNotEmpty()) {
        var filteredItems: List<TerritoriesListItem>
        val cellSize = 110.dp
        LazyVerticalGrid(
            columns = GridCells.Adaptive(cellSize),
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
                    Card(
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable { onClick(territory) },
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                val territoryMarks = territory.cardNum.split("-")
                                if (territoryMarks.isNotEmpty()) {
                                    // CongregationMark + TerritoryCategoryMark -
                                    Text(
                                        modifier = Modifier
                                            .padding(start = 2.dp)
                                            .alignByBaseline(),
                                        text = "${territoryMarks[0]}-",
                                        fontSize = 16.sp,
                                        style = MaterialTheme.typography.titleMedium,
                                        textAlign = TextAlign.Start,
                                    )
                                }
                                if (territoryMarks.size > 1) {
                                    // TerritoryNum + TerritoryBusinessMark?
                                    Text(
                                        modifier = Modifier
                                            .padding(end = 2.dp)
                                            .alignByBaseline(),
                                        text = territoryMarks[1],
                                        fontSize = 20.sp,
                                        style = MaterialTheme.typography.titleMedium,
                                        textAlign = TextAlign.End,
                                    )
                                }
                                var checkedState by rememberSaveable { mutableStateOf(territory.isChecked) }
                                Checkbox(
                                    modifier = Modifier
                                        .padding(0.dp)
                                        //.align(Alignment.CenterVertically),
                                        .alignByBaseline(),
                                    checked = checkedState,
                                    onCheckedChange = {
                                        checkedState = it
                                        territory.isChecked = checkedState
                                    }
                                )
                            }
                            Divider(Modifier.width(cellSize.times(0.9f)), thickness = 2.dp)
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .align(Alignment.CenterHorizontally),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                territory.isPrivateSector?.let {
                                    if (it) {
                                        Image(
                                            modifier = Modifier.clip(RoundedCornerShape(4.dp)),
                                            painter = painterResource(R.drawable.ic_cottage_18),
                                            contentDescription = stringResource(R.string.ter_private_sector_cnt_desc)
                                        )
                                    }
                                }
                                if (territory.isInPerimeter) {
                                    Image(
                                        modifier = Modifier.clip(RoundedCornerShape(4.dp)),
                                        painter = painterResource(R.drawable.ic_aspect_ratio_18),
                                        contentDescription = stringResource(R.string.ter_in_perimeter_cnt_desc)
                                    )
                                }
                                if (territory.isBusiness) {
                                    Image(
                                        modifier = Modifier.clip(RoundedCornerShape(4.dp)),
                                        painter = painterResource(R.drawable.ic_business_center_18),
                                        contentDescription = stringResource(R.string.ter_business_cnt_desc)
                                    )
                                }
                                if (territory.isGroupMinistry) {
                                    Image(
                                        modifier = Modifier.clip(RoundedCornerShape(4.dp)),
                                        painter = painterResource(R.drawable.ic_group_18),
                                        contentDescription = stringResource(R.string.ter_group_ministry_cnt_desc)
                                    )
                                }
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable { onClick(territory) },
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .align(Alignment.CenterHorizontally),
                                    text = territory.cardLocation,
                                    fontSize = 12.sp,
                                    style = MaterialTheme.typography.titleMedium,
                                    textAlign = TextAlign.End,
                                    overflow = TextOverflow.Ellipsis,
                                )
                                territory.member?.memberShortName?.let {
                                    Text(
                                        modifier = Modifier.padding(2.dp),
                                        text = it,
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.bodySmall,
                                        textAlign = TextAlign.Start,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                    )
                                }
                                territory.territoryDesc?.let {
                                    Divider(
                                        Modifier
                                            .width(cellSize.times(0.9f))
                                            .align(Alignment.CenterHorizontally), thickness = 2.dp
                                    )
                                    Text(
                                        modifier = Modifier.padding(2.dp),
                                        text = it,
                                        style = MaterialTheme.typography.bodySmall,
                                        textAlign = TextAlign.Start,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis,
                                    )
                                }
                            }
                        }
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
                onFavorite = {},
                onClick = {}
            )
        }
    }
}