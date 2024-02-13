package com.oborodulin.jwsuite.presentation_geo.ui.geo.microdistrict.list

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
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.LocalityDistrictInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.LocalityInput
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_geo.R
import com.oborodulin.jwsuite.presentation_geo.ui.geo.street.list.StreetsListUiAction
import com.oborodulin.jwsuite.presentation_geo.ui.geo.street.list.StreetsListViewModelImpl
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

private const val TAG = "Geo.MicrodistrictsListView"

@Composable
fun MicrodistrictsListView(
    microdistrictsListViewModel: MicrodistrictsListViewModelImpl = hiltViewModel(),
    streetsListViewModel: StreetsListViewModelImpl = hiltViewModel(),
    navController: NavController,
    localityInput: LocalityInput? = null,
    localityDistrictInput: LocalityDistrictInput? = null,
    streetInput: NavigationInput.StreetInput? = null,
    isEditableList: Boolean = true
) {
    Timber.tag(TAG).d(
        "MicrodistrictsListView(...) called: localityInput = %s; localityDistrictInput = %s; streetInput = %s",
        localityInput, localityDistrictInput, streetInput
    )
    LaunchedEffect(
        localityInput?.localityId,
        localityDistrictInput?.localityDistrictId,
        streetInput?.streetId
    ) {
        Timber.tag(TAG).d("MicrodistrictsListView -> LaunchedEffect()")
        microdistrictsListViewModel.submitAction(
            MicrodistrictsListUiAction.Load(
                localityId = localityInput?.localityId,
                localityDistrictId = localityDistrictInput?.localityDistrictId,
                streetId = streetInput?.streetId
            )
        )
    }
    val searchText by microdistrictsListViewModel.searchText.collectAsStateWithLifecycle()
    microdistrictsListViewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        if (LOG_UI_STATE) Timber.tag(TAG).d("Collect ui state flow: %s", state)
        CommonScreen(state = state) {
            when (streetInput?.streetId) {
                null -> when (isEditableList) {
                    true -> {
                        EditableListViewComponent(
                            items = it,
                            searchedText = searchText.text,
                            dlgConfirmDelResId = R.string.dlg_confirm_del_microdistrict,
                            emptyListResId = R.string.microdistricts_list_empty_text,
                            isEmptyListTextOutput = localityInput?.localityId != null || localityDistrictInput?.localityDistrictId != null,
                            onEdit = { microdistrict ->
                                microdistrictsListViewModel.submitAction(
                                    MicrodistrictsListUiAction.EditMicrodistrict(microdistrict.itemId!!)
                                )
                            },
                            onDelete = { microdistrict ->
                                microdistrictsListViewModel.submitAction(
                                    MicrodistrictsListUiAction.DeleteMicrodistrict(microdistrict.itemId!!)
                                )
                            }
                        ) { microdistrict ->
                            microdistrictsListViewModel.singleSelectItem(microdistrict)
                            streetsListViewModel.submitAction(
                                StreetsListUiAction.Load(
                                    microdistrictId = microdistrict.itemId!!
                                )
                            )
                        }
                    }

                    false -> {
                        ListViewComponent(
                            items = it,
                            emptyListResId = R.string.microdistricts_list_empty_text,
                            isEmptyListTextOutput = localityInput?.localityId != null || localityDistrictInput?.localityDistrictId != null
                        )
                    }
                }

                else -> when (isEditableList) {
                    true -> {
                        EditableListViewComponent(
                            items = it,
                            searchedText = searchText.text,
                            dlgConfirmDelResId = R.string.dlg_confirm_del_street_microdistrict,
                            emptyListResId = R.string.street_microdistricts_list_empty_text,
                            onDelete = { microdistrict ->
                                microdistrictsListViewModel.submitAction(
                                    MicrodistrictsListUiAction.DeleteStreetMicrodistrict(
                                        streetInput.streetId, microdistrict.itemId!!
                                    )
                                )
                            }
                        )
                    }

                    false -> {
                        ListViewComponent(
                            items = it,
                            emptyListResId = R.string.street_microdistricts_list_empty_text
                        )
                    }
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG)
            .d("MicrodistrictsListView -> LaunchedEffect() AFTER collect single Event Flow")
        microdistrictsListViewModel.singleEventFlow.collectLatest {
            Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
            when (it) {
                is MicrodistrictsListUiSingleEvent.OpenMicrodistrictScreen -> {
                    navController.navigate(it.navRoute)
                }
            }
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewMicrodistrictsList() {
    JWSuiteTheme {
        Surface {
            /*MicrodistrictsList(
                microdistricts = MicrodistrictsListViewModelImpl.previewList(LocalContext.current),
                onEdit = {},
                onDelete = {},
                onClick = {})*/
        }
    }
}
