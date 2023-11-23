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
    isEditableList: Boolean = true
) {
    Timber.tag(TAG).d(
        "MicrodistrictsListView(...) called: localityInput = %s; localityDistrictInput = %s",
        localityInput, localityDistrictInput
    )
    LaunchedEffect(localityInput?.localityId, localityDistrictInput?.localityDistrictId) {
        Timber.tag(TAG).d("MicrodistrictsListView -> LaunchedEffect() BEFORE collect ui state flow")
        microdistrictsListViewModel.submitAction(
            MicrodistrictsListUiAction.Load(
                localityInput?.localityId, localityDistrictInput?.localityDistrictId
            )
        )
    }
    val searchText by microdistrictsListViewModel.searchText.collectAsStateWithLifecycle()
    microdistrictsListViewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        CommonScreen(state = state) {
            when (isEditableList) {
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
                        streetsListViewModel.submitAction(StreetsListUiAction.Load(microdistrictId = microdistrict.itemId!!))
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
