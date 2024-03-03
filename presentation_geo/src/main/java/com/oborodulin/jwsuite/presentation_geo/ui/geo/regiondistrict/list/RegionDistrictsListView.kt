package com.oborodulin.jwsuite.presentation_geo.ui.geo.regiondistrict.list

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
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.RegionInput
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_geo.R
import com.oborodulin.jwsuite.presentation_geo.ui.geo.locality.list.LocalitiesListUiAction
import com.oborodulin.jwsuite.presentation_geo.ui.geo.locality.list.LocalitiesListViewModelImpl
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

private const val TAG = "Geo.RegionDistrictsListView"

@Composable
fun RegionDistrictsListView(
    regionDistrictsListViewModel: RegionDistrictsListViewModelImpl = hiltViewModel(),
    localitiesListViewModel: LocalitiesListViewModelImpl = hiltViewModel(),
    navController: NavController,
    regionInput: RegionInput? = null,
    isEditableList: Boolean = true
) {
    Timber.tag(TAG).d("RegionDistrictsListView(...) called: regionInput = %s", regionInput)
    LaunchedEffect(regionInput?.regionId) {
        Timber.tag(TAG)
            .d("RegionDistrictsListView -> LaunchedEffect()")
        regionDistrictsListViewModel.submitAction(RegionDistrictsListUiAction.Load(regionInput?.regionId))
    }
    val searchText by regionDistrictsListViewModel.searchText.collectAsStateWithLifecycle()
    regionDistrictsListViewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        if (LOG_UI_STATE) Timber.tag(TAG).d("Collect ui state flow: %s", state)
        CommonScreen(state = state) {
            when (isEditableList) {
                true -> {
                    EditableListViewComponent(
                        items = it,
                        searchedText = searchText.text,
                        dlgConfirmDelResId = R.string.dlg_confirm_del_region_district,
                        emptyListResId = R.string.region_districts_list_empty_text,
                        isEmptyListTextOutput = regionInput?.regionId != null,
                        onEdit = { regionDistrict ->
                            regionDistrictsListViewModel.submitAction(
                                RegionDistrictsListUiAction.EditRegionDistrict(regionDistrict.itemId!!)
                            )
                        },
                        onDelete = { regionDistrict ->
                            regionDistrictsListViewModel.submitAction(
                                RegionDistrictsListUiAction.DeleteRegionDistrict(regionDistrict.itemId!!)
                            )
                        }
                    ) { regionDistrict ->
                        regionDistrictsListViewModel.singleSelectItem(regionDistrict)
                        localitiesListViewModel.submitAction(
                            LocalitiesListUiAction.Load(regionDistrictId = regionDistrict.itemId!!)
                        )
                    }
                }

                false -> {
                    ListViewComponent(
                        items = it,
                        emptyListResId = R.string.region_districts_list_empty_text,
                        isEmptyListTextOutput = regionInput?.regionId != null
                    )
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG)
            .d("RegionDistrictsListView -> LaunchedEffect() -> collect single Event Flow")
        regionDistrictsListViewModel.singleEventFlow.collectLatest {
            Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
            when (it) {
                is RegionDistrictsListUiSingleEvent.OpenRegionDistrictScreen -> {
                    navController.navigate(it.navRoute)
                }
            }
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewRegionDistrictsList() {
    JWSuiteTheme {
        Surface {
        }
    }
}
