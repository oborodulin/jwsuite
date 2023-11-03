package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorystreet.list

import android.content.res.Configuration
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.navigation.NavController
import com.oborodulin.home.common.ui.ComponentUiAction
import com.oborodulin.home.common.ui.components.list.EmptyListTextComponent
import com.oborodulin.home.common.ui.components.list.items.ListItemComponent
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryInput
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_territory.R
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryStreetsListItem
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

private const val TAG = "Territoring.TerritoryStreetsListView"

@Composable
fun TerritoryStreetsListView(
    viewModel: TerritoryStreetsListViewModelImpl = hiltViewModel(),
    navController: NavController,
    territoryInput: TerritoryInput
) {
    Timber.tag(TAG).d(
        "TerritoryStreetsListView(...) called: territoryInput = %s", territoryInput
    )
    LaunchedEffect(territoryInput.territoryId) {
        Timber.tag(TAG)
            .d("TerritoryStreetsListView: LaunchedEffect() BEFORE collect ui state flow")
        viewModel.submitAction(TerritoryStreetsListUiAction.Load(territoryInput.territoryId))
    }
    viewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        CommonScreen(state = state) {
            TerritoryStreetsEditableList(
                territoryStreets = it,
                onEdit = { territoryStreet ->
                    viewModel.submitAction(
                        TerritoryStreetsListUiAction.EditTerritoryStreet(
                            territoryInput.territoryId, territoryStreet.id
                        )
                    )
                },
                onDelete = { territoryStreet ->
                    viewModel.submitAction(
                        TerritoryStreetsListUiAction.DeleteTerritoryStreet(territoryStreet.id)
                    )
                }
            ) { territoryStreet -> viewModel.singleSelectItem(territoryStreet) }
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG)
            .d("TerritoryStreetsListView: LaunchedEffect() AFTER collect single Event Flow")
        viewModel.singleEventFlow.collectLatest {
            Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
            when (it) {
                is TerritoryStreetsListUiSingleEvent.OpenTerritoryStreetScreen -> {
                    navController.navigate(it.navRoute)
                }
            }
        }
    }
}

@Composable
fun TerritoryStreetsEditableList(
    territoryStreets: List<TerritoryStreetsListItem>,
    onEdit: (TerritoryStreetsListItem) -> Unit,
    onDelete: (TerritoryStreetsListItem) -> Unit,
    onClick: (TerritoryStreetsListItem) -> Unit
) {
    Timber.tag(TAG).d("TerritoryStreetsEditableList(...) called: size = %d", territoryStreets.size)
    if (territoryStreets.isNotEmpty()) {
        val listState =
            rememberLazyListState(initialFirstVisibleItemIndex = territoryStreets.filter { it.selected }
                .getOrNull(0)?.let { territoryStreets.indexOf(it) } ?: 0)
        LazyColumn(
            state = listState,
            modifier = Modifier
                .padding(8.dp)
                .focusable(enabled = true)
        ) {
            itemsIndexed(territoryStreets, key = { _, item -> item.id }) { _, territoryStreet ->
                ListItemComponent(
                    item = territoryStreet,
                    itemActions = listOf(
                        ComponentUiAction.EditListItem { onEdit(territoryStreet) },
                        ComponentUiAction.DeleteListItem(
                            stringResource(
                                R.string.dlg_confirm_del_territory_street,
                                territoryStreet.streetFullName
                            )
                        ) { onDelete(territoryStreet) }),
                    selected = territoryStreet.selected,
                    onClick = { onClick(territoryStreet) }
                )
            }
        }
    } else {
        EmptyListTextComponent(R.string.territory_streets_list_empty_text)
    }
}

@Composable
fun TerritoryStreetsProcessedList(
    territoryStreets: List<TerritoryStreetsListItem>,
    onEdit: (TerritoryStreetsListItem) -> Unit,
    onDelete: (TerritoryStreetsListItem) -> Unit,
    onClick: (TerritoryStreetsListItem) -> Unit
) {
    Timber.tag(TAG).d("TerritoryStreetsProcessedList(...) called: size = %d", territoryStreets.size)
    var selectedIndex by remember { mutableStateOf(-1) }
    if (territoryStreets.isNotEmpty()) {
        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier
                .padding(8.dp)
                .focusable(enabled = true)
        ) {
            items(territoryStreets.size) { index ->
                territoryStreets[index].let { territoryStreet ->
                    val isSelected = (selectedIndex == index)
                    ListItemComponent(
                        item = territoryStreet,
                        itemActions = listOf(
                            ComponentUiAction.EditListItem { onEdit(territoryStreet) },
                            ComponentUiAction.DeleteListItem(
                                stringResource(
                                    R.string.dlg_confirm_del_territory_street,
                                    territoryStreet.streetFullName
                                )
                            ) { onDelete(territoryStreet) }),
                        selected = isSelected,
                        background = if (isSelected) Color.LightGray else Color.Transparent,
                        onClick = {
                            if (selectedIndex != index) selectedIndex = index
                            onClick(territoryStreet)
                        }
                    )
                }
            }
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewTerritoryStreetsList() {
    JWSuiteTheme {
        Surface {
            TerritoryStreetsEditableList(
                territoryStreets = TerritoryStreetsListViewModelImpl.previewList(LocalContext.current),
                onEdit = {},
                onDelete = {},
                onClick = {}
            )
        }
    }
}
