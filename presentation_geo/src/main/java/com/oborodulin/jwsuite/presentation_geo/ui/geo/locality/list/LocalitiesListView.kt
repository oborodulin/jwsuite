package com.oborodulin.jwsuite.presentation_geo.ui.geo.locality.list

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
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.RegionDistrictInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.RegionInput
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_geo.R
import com.oborodulin.jwsuite.presentation_geo.ui.geo.localitydistrict.list.LocalityDistrictsListUiAction
import com.oborodulin.jwsuite.presentation_geo.ui.geo.localitydistrict.list.LocalityDistrictsListViewModelImpl
import com.oborodulin.jwsuite.presentation_geo.ui.geo.microdistrict.list.MicrodistrictsListUiAction
import com.oborodulin.jwsuite.presentation_geo.ui.geo.microdistrict.list.MicrodistrictsListViewModelImpl
import com.oborodulin.jwsuite.presentation_geo.ui.geo.street.list.StreetsListUiAction
import com.oborodulin.jwsuite.presentation_geo.ui.geo.street.list.StreetsListViewModelImpl
import com.oborodulin.jwsuite.presentation_geo.ui.model.LocalitiesListItem
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

private const val TAG = "Geo.LocalitiesListView"

@Composable
fun LocalitiesListView(
    localitiesListViewModel: LocalitiesListViewModelImpl = hiltViewModel(),
    localityDistrictsListViewModel: LocalityDistrictsListViewModelImpl = hiltViewModel(),
    microdistrictsListViewModel: MicrodistrictsListViewModelImpl = hiltViewModel(),
    streetsListViewModel: StreetsListViewModelImpl = hiltViewModel(),
    navController: NavController,
    regionInput: RegionInput? = null,
    regionDistrictInput: RegionDistrictInput? = null
) {
    Timber.tag(TAG).d(
        "LocalitiesListView(...) called: regionInput = %s; regionDistrictInput = %s",
        regionInput,
        regionDistrictInput
    )
    LaunchedEffect(regionInput?.regionId, regionDistrictInput?.regionDistrictId) {
        Timber.tag(TAG).d("LocalitiesListView: LaunchedEffect() BEFORE collect ui state flow")
        localitiesListViewModel.submitAction(
            LocalitiesListUiAction.Load(
                regionInput?.regionId, regionDistrictInput?.regionDistrictId
            )
        )
    }
    localitiesListViewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        CommonScreen(state = state) {
            LocalitiesList(
                localities = it,
                onEdit = { locality ->
                    localitiesListViewModel.submitAction(
                        LocalitiesListUiAction.EditLocality(locality.id)
                    )
                },
                onDelete = { locality ->
                    localitiesListViewModel.submitAction(
                        LocalitiesListUiAction.DeleteLocality(locality.id)
                    )
                }
            ) { locality ->
                localitiesListViewModel.singleSelectItem(locality)
                localityDistrictsListViewModel.submitAction(
                    LocalityDistrictsListUiAction.Load(localityId = locality.id)
                )
                microdistrictsListViewModel.submitAction(MicrodistrictsListUiAction.Load(localityId = locality.id))
                streetsListViewModel.submitAction(StreetsListUiAction.Load(localityId = locality.id))
            }
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("LocalitiesListView: LaunchedEffect() AFTER collect single Event Flow")
        localitiesListViewModel.singleEventFlow.collectLatest {
            Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
            when (it) {
                is LocalitiesListUiSingleEvent.OpenLocalityScreen -> {
                    navController.navigate(it.navRoute)
                }
            }
        }
    }
}

@Composable
fun LocalitiesList(
    localities: List<LocalitiesListItem>,
    onEdit: (LocalitiesListItem) -> Unit,
    onDelete: (LocalitiesListItem) -> Unit,
    onClick: (LocalitiesListItem) -> Unit
) {
    Timber.tag(TAG).d("LocalitiesList(...) called: size = %d", localities.size)
    if (localities.isNotEmpty()) {
        val listState =
            rememberLazyListState(initialFirstVisibleItemIndex = localities.filter { it.selected }
                .getOrNull(0)?.let { localities.indexOf(it) } ?: 0)
        LazyColumn(
            state = listState,
            modifier = Modifier
                .padding(8.dp)
                .focusable(enabled = true)
        ) {
            itemsIndexed(localities, key = { _, item -> item.id }) { _, locality ->
                ListItemComponent(
                    item = locality,
                    itemActions = listOf(
                        ComponentUiAction.EditListItem { onEdit(locality) },
                        ComponentUiAction.DeleteListItem(
                            stringResource(
                                R.string.dlg_confirm_del_locality,
                                locality.localityFullName
                            )
                        ) { onDelete(locality) }),
                    selected = locality.selected,
                    onClick = { onClick(locality) }
                )
            }
        }
    } else {
        EmptyListTextComponent(R.string.localities_list_empty_text)
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewLocalitiesList() {
    JWSuiteTheme {
        Surface {
            LocalitiesList(
                localities = LocalitiesListViewModelImpl.previewList(LocalContext.current),
                onEdit = {},
                onDelete = {},
                onClick = {})
        }
    }
}
