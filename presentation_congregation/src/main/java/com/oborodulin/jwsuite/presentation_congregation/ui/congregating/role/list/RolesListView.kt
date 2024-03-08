package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.role.list

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
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

private const val TAG = "Congregating.RolesListView"

@Composable
fun RolesListView(
    viewModel: RolesListViewModelImpl = hiltViewModel(),
    navController: NavController,
    memberInput: NavigationInput.MemberInput? = null,
    isEditableList: Boolean = true
) {
    Timber.tag(TAG).d("RolesListView(...) called: memberInput = %s", memberInput)
    LaunchedEffect(memberInput?.memberId) {
        Timber.tag(TAG)
            .d("RolesListView -> LaunchedEffect()")
        viewModel.submitAction(RolesListUiAction.Load(memberInput?.memberId))
    }
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()
    viewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        if (LOG_UI_STATE) {
            Timber.tag(TAG).d("Collect ui state flow: %s", state)
        }
        CommonScreen(state = state) {
            when (isEditableList) {
                true -> {
                    EditableListViewComponent(
                        items = it,
                        searchedText = searchText.text,
                        dlgConfirmDelResId = R.string.dlg_confirm_del_locality_district,
                        emptyListResId = R.string.locality_districts_list_empty_text,
                        isEmptyListTextOutput = memberInput?.memberId != null,
                        onEdit = { role ->
                            /*viewModel.submitAction(
                                RolesListUiAction.EditLocalityDistrict(role.itemId!!)
                            )*/
                        },
                        onDelete = { role ->
                            /*viewModel.submitAction(
                                RolesListUiAction.DeleteLocalityDistrict(role.itemId!!)
                            )*/
                        }
                    ) { role -> viewModel.singleSelectItem(role) }
                }

                false -> {
                    ListViewComponent(
                        items = it,
                        emptyListResId = R.string.locality_districts_list_empty_text,
                        isEmptyListTextOutput = memberInput?.memberId != null
                    )
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG)
            .d("RolesListView -> LaunchedEffect() -> collect single Event Flow")
        viewModel.singleEventFlow.collectLatest {
            Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
            /*when (it) {
                is RolesListUiSingleEvent.OpenLocalityDistrictScreen -> {
                    navController.navigate(it.navRoute)
                }
            }*/
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
                localityDistricts = RolesListViewModelImpl.previewList(LocalContext.current),
                onEdit = {},
                onDelete = {},
                onClick = {})*/
        }
    }
}
