package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorycategory.list

import android.content.res.Configuration
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
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
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryCategoryInput
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_territory.R
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryCategoriesListItem
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

private const val TAG = "Territoring.TerritoryCategoriesListView"

@Composable
fun TerritoryCategoriesListView(
    viewModel: TerritoryCategoriesListViewModelImpl = hiltViewModel(),
    navController: NavController,
    territoryCategoryInput: TerritoryCategoryInput? = null
) {
    Timber.tag(TAG).d(
        "TerritoryCategoriesListView(...) called: territoryCategoryInput = %s",
        territoryCategoryInput
    )
    LaunchedEffect(Unit) {
        Timber.tag(TAG)
            .d("TerritoryCategoriesListView -> LaunchedEffect() BEFORE collect ui state flow")
        viewModel.submitAction(TerritoryCategoriesListUiAction.Load)
    }
    viewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        CommonScreen(state = state) {
            TerritoryCategoriesList(
                territoryCategories = it,
                onEdit = { territoryCategory ->
                    viewModel.submitAction(
                        TerritoryCategoriesListUiAction.EditTerritoryCategory(territoryCategory.id)
                    )
                },
                onDelete = { territoryCategory ->
                    viewModel.submitAction(
                        TerritoryCategoriesListUiAction.DeleteTerritoryCategory(territoryCategory.id)
                    )
                }
            ) { territoryCategory -> viewModel.singleSelectItem(territoryCategory) }
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG)
            .d("TerritoryCategoriesListView -> LaunchedEffect() AFTER collect single Event Flow")
        viewModel.singleEventFlow.collectLatest {
            Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
            when (it) {
                is TerritoryCategoriesListUiSingleEvent.OpenTerritoryCategoryScreen -> {
                    navController.navigate(it.navRoute)
                }
            }
        }
    }
}

@Composable
fun TerritoryCategoriesList(
    territoryCategories: List<TerritoryCategoriesListItem>,
    onEdit: (TerritoryCategoriesListItem) -> Unit,
    onDelete: (TerritoryCategoriesListItem) -> Unit,
    onClick: (TerritoryCategoriesListItem) -> Unit
) {
    Timber.tag(TAG).d("TerritoryCategoriesList(...) called")
    if (territoryCategories.isNotEmpty()) {
        val listState =
            rememberLazyListState(initialFirstVisibleItemIndex = territoryCategories.filter { it.selected }
                .getOrNull(0)?.let { territoryCategories.indexOf(it) } ?: 0)
        LazyColumn(
            state = listState,
            modifier = Modifier
                .padding(8.dp)
                .focusable(enabled = true)
        ) {
            itemsIndexed(
                territoryCategories,
                key = { _, item -> item.id }) { _, territoryCategory ->
                ListItemComponent(
                    item = territoryCategory,
                    itemActions = listOf(
                        ComponentUiAction.EditListItem { onEdit(territoryCategory) },
                        ComponentUiAction.DeleteListItem(
                            stringResource(
                                R.string.dlg_confirm_del_territory_category,
                                territoryCategory.territoryCategoryName
                            )
                        ) { onDelete(territoryCategory) }),
                    selected = territoryCategory.selected,
                    onClick = { onClick(territoryCategory) }
                )
            }
        }
    } else {
        EmptyListTextComponent(R.string.territory_categories_list_empty_text)
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewTerritoryCategoriesList() {
    JWSuiteTheme {
        Surface {
            TerritoryCategoriesList(
                territoryCategories = TerritoryCategoriesListViewModelImpl.previewList(LocalContext.current),
                onEdit = {},
                onDelete = {},
                onClick = {}
            )
        }
    }
}
