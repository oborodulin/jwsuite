package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorycategory.list

import android.content.res.Configuration
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.oborodulin.home.common.ui.components.list.EditableListViewComponent
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.home.common.util.LogLevel.LOG_UI_STATE
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryCategoryInput
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_territory.R
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
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()
    viewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        if (LOG_UI_STATE) Timber.tag(TAG).d("Collect ui state flow: %s", state)
        CommonScreen(state = state) {
            EditableListViewComponent(
                items = it,
                searchedText = searchText.text,
                dlgConfirmDelResId = R.string.dlg_confirm_del_territory_category,
                emptyListResId = R.string.territory_categories_list_empty_text,
                onEdit = { territoryCategory ->
                    viewModel.submitAction(
                        TerritoryCategoriesListUiAction.EditTerritoryCategory(territoryCategory.itemId!!)
                    )
                },
                onDelete = { territoryCategory ->
                    viewModel.submitAction(
                        TerritoryCategoriesListUiAction.DeleteTerritoryCategory(territoryCategory.itemId!!)
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

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewTerritoryCategoriesList() {
    JWSuiteTheme {
        Surface {
            /*TerritoryCategoriesList(
                territoryCategories = TerritoryCategoriesListViewModelImpl.previewList(LocalContext.current),
                onEdit = {},
                onDelete = {},
                onClick = {}
            )*/
        }
    }
}
