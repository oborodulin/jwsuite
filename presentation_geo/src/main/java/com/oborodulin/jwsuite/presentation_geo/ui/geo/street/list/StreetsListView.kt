package com.oborodulin.jwsuite.presentation_geo.ui.geo.street.list

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
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.LocalityDistrictInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.LocalityInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.MicrodistrictInput
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_geo.R
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

private const val TAG = "Geo.StreetsListView"

@Composable
fun StreetsListView(
    viewModel: StreetsListViewModelImpl = hiltViewModel(),
    navController: NavController,
    localityInput: LocalityInput? = null,
    localityDistrictInput: LocalityDistrictInput? = null,
    microdistrictInput: MicrodistrictInput? = null,
    isPrivateSector: Boolean = false,
    isEditableList: Boolean = true
) {
    Timber.tag(TAG).d(
        "StreetsListView(...) called: isPrivateSector = %s; localityInput = %s; localityDistrictInput = %s; microdistrictInput = %s",
        isPrivateSector,
        localityInput,
        localityDistrictInput,
        microdistrictInput
    )
    LaunchedEffect(
        localityInput?.localityId,
        localityDistrictInput?.localityDistrictId,
        microdistrictInput?.microdistrictId,
        isPrivateSector
    ) {
        Timber.tag(TAG).d("StreetsListView -> LaunchedEffect() BEFORE collect ui state flow")
        viewModel.submitAction(
            StreetsListUiAction.Load(
                localityInput?.localityId,
                localityDistrictInput?.localityDistrictId,
                microdistrictInput?.microdistrictId,
                isPrivateSector
            )
        )
    }
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()
    viewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        if (LOG_UI_STATE) Timber.tag(TAG).d("Collect ui state flow: %s", state)
        CommonScreen(state = state) {
            when (isEditableList) {
                true -> {
                    EditableListViewComponent(
                        items = it,
                        searchedText = searchText.text,
                        dlgConfirmDelResId = R.string.dlg_confirm_del_street,
                        emptyListResId = R.string.streets_list_empty_text,
                        isEmptyListTextOutput = localityInput?.localityId != null || localityDistrictInput?.localityDistrictId != null || microdistrictInput?.microdistrictId != null,
                        onEdit = { street ->
                            viewModel.submitAction(StreetsListUiAction.EditStreet(street.itemId!!))
                        },
                        onDelete = { street ->
                            viewModel.submitAction(StreetsListUiAction.DeleteStreet(street.itemId!!))
                        }
                    ) { street -> viewModel.singleSelectItem(street) }
                }

                false -> {
                    ListViewComponent(
                        items = it,
                        emptyListResId = R.string.streets_list_empty_text,
                        isEmptyListTextOutput = localityInput?.localityId != null || localityDistrictInput?.localityDistrictId != null || microdistrictInput?.microdistrictId != null
                    )
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("StreetsListView -> LaunchedEffect() AFTER collect single Event Flow")
        viewModel.singleEventFlow.collectLatest {
            Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
            when (it) {
                is StreetsListUiSingleEvent.OpenStreetScreen -> navController.navigate(it.navRoute)
            }
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewStreetsList() {
    JWSuiteTheme {
        Surface {
            /*StreetsList(
                streets = StreetsListViewModelImpl.previewList(LocalContext.current),
                onEdit = {},
                onDelete = {},
                onClick = {}
            )*/
        }
    }
}
