package com.oborodulin.jwsuite.presentation_geo.ui.geo.localitydistrict.list

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
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.LocalityInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.StreetInput
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_geo.R
import com.oborodulin.jwsuite.presentation_geo.ui.geo.microdistrict.list.MicrodistrictsListUiAction
import com.oborodulin.jwsuite.presentation_geo.ui.geo.microdistrict.list.MicrodistrictsListViewModelImpl
import com.oborodulin.jwsuite.presentation_geo.ui.geo.street.list.StreetsListUiAction
import com.oborodulin.jwsuite.presentation_geo.ui.geo.street.list.StreetsListViewModelImpl
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
    streetInput: StreetInput? = null,
    isEditableList: Boolean = true
) {
    Timber.tag(TAG).d(
        "LocalityDistrictsListView(...) called: localityInput = %s; streetInput = %s",
        localityInput, streetInput
    )
    LaunchedEffect(localityInput?.localityId, streetInput?.streetId) {
        Timber.tag(TAG)
            .d("LocalityDistrictsListView -> LaunchedEffect()")
        localityDistrictsListViewModel.submitAction(
            LocalityDistrictsListUiAction.Load(localityInput?.localityId, streetInput?.streetId)
        )
    }
    val searchText by localityDistrictsListViewModel.searchText.collectAsStateWithLifecycle()
    localityDistrictsListViewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        if (LOG_UI_STATE) Timber.tag(TAG).d("Collect ui state flow: %s", state)
        CommonScreen(state = state) {
            when (streetInput?.streetId) {
                null -> when (isEditableList) {
                    true -> {
                        EditableListViewComponent(
                            items = it,
                            searchedText = searchText.text,
                            dlgConfirmDelResId = R.string.dlg_confirm_del_locality_district,
                            emptyListResId = R.string.locality_districts_list_empty_text,
                            isEmptyListTextOutput = localityInput?.localityId != null,
                            onEdit = { localityDistrict ->
                                localityDistrictsListViewModel.submitAction(
                                    LocalityDistrictsListUiAction.EditLocalityDistrict(
                                        localityDistrict.itemId!!
                                    )
                                )
                            },
                            onDelete = { localityDistrict ->
                                localityDistrictsListViewModel.submitAction(
                                    LocalityDistrictsListUiAction.DeleteLocalityDistrict(
                                        localityDistrict.itemId!!
                                    )
                                )
                            }
                        ) { localityDistrict ->
                            localityDistrictsListViewModel.singleSelectItem(localityDistrict)
                            microdistrictsListViewModel.submitAction(
                                MicrodistrictsListUiAction.Load(localityDistrictId = localityDistrict.itemId!!)
                            )
                            streetsListViewModel.submitAction(
                                StreetsListUiAction.Load(
                                    localityDistrictId = localityDistrict.itemId!!
                                )
                            )
                        }
                    }

                    false -> {
                        ListViewComponent(
                            items = it,
                            emptyListResId = R.string.locality_districts_list_empty_text,
                            isEmptyListTextOutput = localityInput?.localityId != null
                        )
                    }
                }

                else -> when (isEditableList) {
                    true -> {
                        EditableListViewComponent(
                            items = it,
                            searchedText = searchText.text,
                            dlgConfirmDelResId = R.string.dlg_confirm_del_street_locality_district,
                            emptyListResId = R.string.street_locality_districts_list_empty_text,
                            onDelete = { localityDistrict ->
                                localityDistrictsListViewModel.submitAction(
                                    LocalityDistrictsListUiAction.DeleteStreetLocalityDistrict(
                                        streetInput.streetId, localityDistrict.itemId!!
                                    )
                                )
                            }
                        )
                    }

                    false -> {
                        ListViewComponent(
                            items = it,
                            emptyListResId = R.string.street_locality_districts_list_empty_text
                        )
                    }
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG)
            .d("LocalityDistrictsListView -> LaunchedEffect() AFTER collect single Event Flow")
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

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewLocalityDistrictsList() {
    JWSuiteTheme {
        Surface {
            /*LocalityDistrictsList(
                localityDistricts = LocalityDistrictsListViewModelImpl.previewList(LocalContext.current),
                onEdit = {},
                onDelete = {},
                onClick = {})*/
        }
    }
}
