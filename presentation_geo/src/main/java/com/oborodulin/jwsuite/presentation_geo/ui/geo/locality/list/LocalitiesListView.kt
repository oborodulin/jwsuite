package com.oborodulin.jwsuite.presentation_geo.ui.geo.locality.list

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
    regionDistrictInput: RegionDistrictInput? = null,
    isEditableList: Boolean = true
) {
    Timber.tag(TAG).d(
        "LocalitiesListView(...) called: regionInput = %s; regionDistrictInput = %s",
        regionInput,
        regionDistrictInput
    )
    LaunchedEffect(regionInput?.regionId, regionDistrictInput?.regionDistrictId) {
        Timber.tag(TAG).d("LocalitiesListView -> LaunchedEffect() BEFORE collect ui state flow")
        localitiesListViewModel.submitAction(
            LocalitiesListUiAction.Load(
                regionInput?.regionId, regionDistrictInput?.regionDistrictId
            )
        )
    }
    val searchText by localitiesListViewModel.searchText.collectAsStateWithLifecycle()
    localitiesListViewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        if (LOG_UI_STATE) Timber.tag(TAG).d("Collect ui state flow: %s", state)
        CommonScreen(state = state) {
            when (isEditableList) {
                true -> {
                    EditableListViewComponent(
                        items = it,
                        searchedText = searchText.text,
                        dlgConfirmDelResId = R.string.dlg_confirm_del_locality,
                        emptyListResId = R.string.localities_list_empty_text,
                        isEmptyListTextOutput = regionInput?.regionId != null || regionDistrictInput?.regionDistrictId != null,
                        onEdit = { locality ->
                            localitiesListViewModel.submitAction(
                                LocalitiesListUiAction.EditLocality(locality.itemId!!)
                            )
                        },
                        onDelete = { locality ->
                            localitiesListViewModel.submitAction(
                                LocalitiesListUiAction.DeleteLocality(locality.itemId!!)
                            )
                        }
                    ) { locality ->
                        localitiesListViewModel.singleSelectItem(locality)
                        localityDistrictsListViewModel.submitAction(
                            LocalityDistrictsListUiAction.Load(localityId = locality.itemId!!)
                        )
                        microdistrictsListViewModel.submitAction(
                            MicrodistrictsListUiAction.Load(localityId = locality.itemId!!)
                        )
                        streetsListViewModel.submitAction(StreetsListUiAction.Load(localityId = locality.itemId!!))
                    }
                }

                false -> {
                    ListViewComponent(
                        items = it,
                        emptyListResId = R.string.localities_list_empty_text,
                        isEmptyListTextOutput = regionInput?.regionId != null || regionDistrictInput?.regionDistrictId != null
                    )
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("LocalitiesListView -> LaunchedEffect() AFTER collect single Event Flow")
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

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewLocalitiesList() {
    JWSuiteTheme {
        Surface {
            /*LocalitiesList(
                localities = LocalitiesListViewModelImpl.previewList(LocalContext.current),
                onEdit = {},
                onDelete = {},
                onClick = {})*/
        }
    }
}