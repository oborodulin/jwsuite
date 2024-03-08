package com.oborodulin.jwsuite.presentation_geo.ui.geo.region.list

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
import com.oborodulin.home.common.ui.components.list.ListViewComponent
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.home.common.util.LogLevel.LOG_UI_STATE
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_geo.R
import com.oborodulin.jwsuite.presentation_geo.ui.components.FetchButtonComponent
import com.oborodulin.jwsuite.presentation_geo.ui.geo.locality.list.LocalitiesListUiAction
import com.oborodulin.jwsuite.presentation_geo.ui.geo.locality.list.LocalitiesListViewModelImpl
import com.oborodulin.jwsuite.presentation_geo.ui.geo.regiondistrict.list.RegionDistrictsListUiAction
import com.oborodulin.jwsuite.presentation_geo.ui.geo.regiondistrict.list.RegionDistrictsListViewModelImpl
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

private const val TAG = "Geo.RegionsListView"

@Composable
fun RegionsListView(
    regionsListViewModel: RegionsListViewModelImpl = hiltViewModel(),
    regionDistrictsListViewModel: RegionDistrictsListViewModelImpl = hiltViewModel(),
    localitiesListViewModel: LocalitiesListViewModelImpl = hiltViewModel(),
    navController: NavController,
    countryInput: NavigationInput.CountryInput? = null,
    isEditableList: Boolean = true
) {
    Timber.tag(TAG).d("RegionsListView(...) called: countryInput = %s", countryInput)
    LaunchedEffect(countryInput?.countryId) {
        Timber.tag(TAG).d("RegionsListView -> LaunchedEffect()")
        regionsListViewModel.submitAction(RegionsListUiAction.Load(countryInput?.countryId))
    }
    val searchText by regionsListViewModel.searchText.collectAsStateWithLifecycle()
    regionsListViewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        if (LOG_UI_STATE) {
            Timber.tag(TAG).d("Collect ui state flow: %s", state)
        }
        CommonScreen(state = state) {
            when (isEditableList) {
                true -> {
                    EditableListViewComponent(
                        items = it,
                        searchedText = searchText.text,
                        dlgConfirmDelResId = R.string.dlg_confirm_del_region,
                        emptyListResId = R.string.regions_list_empty_text,
                        isEmptyListTextOutput = true,
                        onEdit = { region ->
                            regionsListViewModel.submitAction(RegionsListUiAction.EditRegion(region.itemId!!))
                        },
                        onDelete = { region ->
                            regionsListViewModel.submitAction(
                                RegionsListUiAction.DeleteRegion(region.itemId!!)
                            )
                        }
                    ) { region ->
                        regionsListViewModel.singleSelectItem(region)
                        regionDistrictsListViewModel.submitAction(
                            RegionDistrictsListUiAction.Load(region.itemId!!)
                        )
                        localitiesListViewModel.submitAction(LocalitiesListUiAction.Load(regionId = region.itemId!!))
                    }
                }

                false -> {
                    ListViewComponent(
                        items = it,
                        emptyListResId = R.string.regions_list_empty_text,
                        isEmptyListTextOutput = countryInput?.countryId != null,
                        fetchListControl = {
                            countryInput?.let {
                                FetchButtonComponent(enabled = true) {
                                    regionsListViewModel.submitAction(
                                        RegionsListUiAction.Load(
                                            it.countryId, it.countryGeocodeArea, true
                                        )
                                    )
                                }
                            }
                        }
                    )
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("RegionsListView -> LaunchedEffect() -> collect single Event Flow")
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

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewRegionsList() {
    JWSuiteTheme {
        Surface {
/*            RegionsList(
                regions = RegionsListViewModelImpl.previewList(LocalContext.current),
                onEdit = {},
                onDelete = {},
                onClick = {}
            )*/
        }
    }
}