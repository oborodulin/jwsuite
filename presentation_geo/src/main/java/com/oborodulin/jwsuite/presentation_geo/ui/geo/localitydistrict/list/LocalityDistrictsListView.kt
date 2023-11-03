package com.oborodulin.jwsuite.presentation_geo.ui.geo.localitydistrict.list

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
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.LocalityInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.StreetInput
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_geo.R
import com.oborodulin.jwsuite.presentation_geo.ui.geo.microdistrict.list.MicrodistrictsListUiAction
import com.oborodulin.jwsuite.presentation_geo.ui.geo.microdistrict.list.MicrodistrictsListViewModelImpl
import com.oborodulin.jwsuite.presentation_geo.ui.geo.street.list.StreetsListUiAction
import com.oborodulin.jwsuite.presentation_geo.ui.geo.street.list.StreetsListViewModelImpl
import com.oborodulin.jwsuite.presentation_geo.ui.model.LocalityDistrictsListItem
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

private const val TAG = "Geo.LocalityDistrictsListView"

@Composable
fun LocalityDistrictsListView(
    localityDistrictsListViewModel: LocalityDistrictsListViewModelImpl = hiltViewModel(),
    microdistrictsListViewModel: MicrodistrictsListViewModelImpl = hiltViewModel(),
    streetsListViewModel: StreetsListViewModelImpl = hiltViewModel(),
    navController: NavController,
    localityInput: LocalityInput? = null,
    streetInput: StreetInput? = null
) {
    Timber.tag(TAG).d(
        "LocalityDistrictsListView(...) called: localityInput = %s; streetInput = %s",
        localityInput,
        streetInput
    )
    LaunchedEffect(localityInput?.localityId, streetInput?.streetId) {
        Timber.tag(TAG)
            .d("LocalityDistrictsListView: LaunchedEffect() BEFORE collect ui state flow")
        localityDistrictsListViewModel.submitAction(
            LocalityDistrictsListUiAction.Load(localityInput?.localityId, streetInput?.streetId)
        )
    }
    localityDistrictsListViewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        CommonScreen(state = state) {
            when (streetInput?.streetId) {
                null -> LocalityDistrictsList(
                    localityDistricts = it,
                    onEdit = { localityDistrict ->
                        localityDistrictsListViewModel.submitAction(
                            LocalityDistrictsListUiAction.EditLocalityDistrict(localityDistrict.id)
                        )
                    },
                    onDelete = { localityDistrict ->
                        localityDistrictsListViewModel.submitAction(
                            LocalityDistrictsListUiAction.DeleteLocalityDistrict(localityDistrict.id)
                        )
                    }
                ) { localityDistrict ->
                    localityDistrictsListViewModel.singleSelectItem(localityDistrict)
                    microdistrictsListViewModel.submitAction(
                        MicrodistrictsListUiAction.Load(localityDistrictId = localityDistrict.id)
                    )
                    streetsListViewModel.submitAction(StreetsListUiAction.Load(localityDistrictId = localityDistrict.id))
                }

                else -> StreetLocalityDistrictsList(
                    localityDistricts = it,
                    onDelete = { localityDistrict ->
                        localityDistrictsListViewModel.submitAction(
                            LocalityDistrictsListUiAction.DeleteStreetLocalityDistrict(
                                streetInput.streetId, localityDistrict.id
                            )
                        )
                    }
                )
            }
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG)
            .d("LocalityDistrictsListView: LaunchedEffect() AFTER collect single Event Flow")
        localityDistrictsListViewModel.singleEventFlow.collectLatest {
            Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
            when (it) {
                is LocalityDistrictsListUiSingleEvent.OpenLocalityDistrictScreen -> {
                    navController.navigate(it.navRoute)
                }
            }
        }
    }
}

@Composable
fun LocalityDistrictsList(
    localityDistricts: List<LocalityDistrictsListItem>,
    onEdit: (LocalityDistrictsListItem) -> Unit,
    onDelete: (LocalityDistrictsListItem) -> Unit,
    onClick: (LocalityDistrictsListItem) -> Unit
) {
    Timber.tag(TAG).d("LocalityDistrictsList(...) called")
    if (localityDistricts.isNotEmpty()) {
        val listState =
            rememberLazyListState(initialFirstVisibleItemIndex = localityDistricts.filter { it.selected }
                .getOrNull(0)?.let { localityDistricts.indexOf(it) } ?: 0)
        LazyColumn(
            state = listState,
            modifier = Modifier
                .padding(8.dp)
                .focusable(enabled = true)
        ) {
            itemsIndexed(localityDistricts, key = { _, item -> item.id }) { _, localityDistrict ->
                ListItemComponent(
                    item = localityDistrict,
                    itemActions = listOf(
                        ComponentUiAction.EditListItem { onEdit(localityDistrict) },
                        ComponentUiAction.DeleteListItem(
                            stringResource(
                                R.string.dlg_confirm_del_locality_district,
                                localityDistrict.districtName
                            )
                        ) { onDelete(localityDistrict) }),
                    selected = localityDistrict.selected,
                    onClick = { onClick(localityDistrict) }
                )
            }
        }
    } else {
        EmptyListTextComponent(R.string.locality_districts_list_empty_text)
    }
}

@Composable
fun StreetLocalityDistrictsList(
    localityDistricts: List<LocalityDistrictsListItem>,
    onDelete: (LocalityDistrictsListItem) -> Unit
) {
    Timber.tag(TAG).d("StreetLocalityDistrictsList(...) called")
    if (localityDistricts.isNotEmpty()) {
        val listState =
            rememberLazyListState(initialFirstVisibleItemIndex = localityDistricts.filter { it.selected }
                .getOrNull(0)?.let { localityDistricts.indexOf(it) } ?: 0)
        LazyColumn(
            state = listState,
            modifier = Modifier
                .padding(8.dp)
                .focusable(enabled = true)
        ) {
            itemsIndexed(localityDistricts, key = { _, item -> item.id }) { _, localityDistrict ->
                ListItemComponent(
                    item = localityDistrict,
                    itemActions = listOf(
                        ComponentUiAction.DeleteListItem(
                            stringResource(
                                R.string.dlg_confirm_del_street_locality_district,
                                localityDistrict.districtName
                            )
                        ) { onDelete(localityDistrict) }),
                    selected = localityDistrict.selected,
                    //onClick = { onClick(localityDistrict) }
                )
            }
        }
    } else {
        EmptyListTextComponent(R.string.street_locality_districts_list_empty_text)
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewLocalityDistrictsList() {
    JWSuiteTheme {
        Surface {
            LocalityDistrictsList(
                localityDistricts = LocalityDistrictsListViewModelImpl.previewList(LocalContext.current),
                onEdit = {},
                onDelete = {},
                onClick = {})
        }
    }
}
