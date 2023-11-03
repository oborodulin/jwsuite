package com.oborodulin.jwsuite.presentation_geo.ui.geo.region.list

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
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.RegionInput
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_geo.R
import com.oborodulin.jwsuite.presentation_geo.ui.geo.locality.list.LocalitiesListUiAction
import com.oborodulin.jwsuite.presentation_geo.ui.geo.locality.list.LocalitiesListViewModelImpl
import com.oborodulin.jwsuite.presentation_geo.ui.geo.regiondistrict.list.RegionDistrictsListUiAction
import com.oborodulin.jwsuite.presentation_geo.ui.geo.regiondistrict.list.RegionDistrictsListViewModelImpl
import com.oborodulin.jwsuite.presentation_geo.ui.model.RegionsListItem
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

private const val TAG = "Geo.RegionsListView"

@Composable
fun RegionsListView(
    regionsListViewModel: RegionsListViewModelImpl = hiltViewModel(),
    regionDistrictsListViewModel: RegionDistrictsListViewModelImpl = hiltViewModel(),
    localitiesListViewModel: LocalitiesListViewModelImpl = hiltViewModel(),
    navController: NavController,
    regionInput: RegionInput? = null
) {
    Timber.tag(TAG).d("RegionsListView(...) called: regionInput = %s", regionInput)
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("RegionsListView: LaunchedEffect() BEFORE collect ui state flow")
        regionsListViewModel.submitAction(RegionsListUiAction.Load)
    }
    regionsListViewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        CommonScreen(state = state) {
            RegionsList(
                regions = it,
                onEdit = { region ->
                    regionsListViewModel.submitAction(RegionsListUiAction.EditRegion(region.id))
                },
                onDelete = { region ->
                    regionsListViewModel.submitAction(RegionsListUiAction.DeleteRegion(region.id))
                }
            ) { region ->
                regionsListViewModel.singleSelectItem(region)
                regionDistrictsListViewModel.submitAction(RegionDistrictsListUiAction.Load(region.id))
                localitiesListViewModel.submitAction(LocalitiesListUiAction.Load(regionId = region.id))
            }
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("RegionsListView: LaunchedEffect() AFTER collect single Event Flow")
        regionsListViewModel.singleEventFlow.collectLatest {
            Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
            when (it) {
                is RegionsListUiSingleEvent.OpenRegionScreen -> {
                    navController.navigate(it.navRoute)
                }
            }
        }
    }
}

@Composable
fun RegionsList(
    regions: List<RegionsListItem>,
    onEdit: (RegionsListItem) -> Unit,
    onDelete: (RegionsListItem) -> Unit,
    onClick: (RegionsListItem) -> Unit
) {
    Timber.tag(TAG).d("RegionsList(...) called: size = %d", regions.size)
    if (regions.isNotEmpty()) {
        val listState =
            rememberLazyListState(initialFirstVisibleItemIndex = regions.filter { it.selected }
                .getOrNull(0)?.let { regions.indexOf(it) } ?: 0)
        LazyColumn(
            state = listState,
            modifier = Modifier
                .padding(8.dp)
                .focusable(enabled = true)
        ) {
            itemsIndexed(regions, key = { _, item -> item.id }) { _, region ->
                ListItemComponent(
                    item = region,
                    itemActions = listOf(
                        ComponentUiAction.EditListItem { onEdit(region) },
                        ComponentUiAction.DeleteListItem(
                            stringResource(R.string.dlg_confirm_del_region, region.regionName)
                        ) { onDelete(region) }),
                    selected = region.selected,
                    onClick = { onClick(region) }
                )
            }
        }
    } else {
        EmptyListTextComponent(R.string.regions_list_empty_text)
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewRegionsList() {
    JWSuiteTheme {
        Surface {
            RegionsList(
                regions = RegionsListViewModelImpl.previewList(LocalContext.current),
                onEdit = {},
                onDelete = {},
                onClick = {}
            )
        }
    }
}
