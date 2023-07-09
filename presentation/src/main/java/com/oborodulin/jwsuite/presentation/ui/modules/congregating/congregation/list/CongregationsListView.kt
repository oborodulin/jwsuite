package com.oborodulin.jwsuite.presentation.ui.modules.congregating.congregation.list

import android.content.res.Configuration
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.oborodulin.home.common.ui.ComponentUiAction
import com.oborodulin.home.common.ui.components.items.ListItemComponent
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.jwsuite.presentation.AppState
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.CongregationInput
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.member.list.MembersListUiAction
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.member.list.MembersListViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.CongregationsListItem
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import java.util.UUID

private const val TAG = "Congregating.ui.CongregationsListView"

@Composable
fun CongregationsListView(
    appState: AppState,
    congregationsListViewModel: CongregationsListViewModelImpl = hiltViewModel(),
    membersListViewModel: MembersListViewModelImpl = hiltViewModel(),
    navController: NavController,
    congregationInput: CongregationInput? = null
) {
    Timber.tag(TAG).d("CongregationsListView(...) called")
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("CongregationsListView: LaunchedEffect() BEFORE collect ui state flow")
        congregationsListViewModel.submitAction(CongregationsListUiAction.Load)
    }
    congregationsListViewModel.uiStateFlow.collectAsState().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        CommonScreen(state = state) {
            CongregationsList(
                congregations = it,
                congregationInput = congregationInput,
                onEdit = { congregation ->
                    congregationsListViewModel.submitAction(
                        CongregationsListUiAction.EditCongregation(congregation.id)
                    )
                },
                onDelete = { congregation ->
                    congregationsListViewModel.submitAction(
                        CongregationsListUiAction.DeleteCongregation(congregation.id)
                    )
                }
            ) { congregation ->
                congregationsListViewModel.setPrimaryObjectData(
                    arrayListOf(
                        congregation.id.toString(),
                        congregation.congregationName
                    )
                )
                appState.actionBarSubtitle.value = congregation.congregationName
                with(membersListViewModel) {
                    setPrimaryObjectData(arrayListOf(congregation.id.toString()))
                    submitAction(MembersListUiAction.Load(congregation.id))
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("CongregationsListView: LaunchedEffect() AFTER collect ui state flow")
        congregationsListViewModel.singleEventFlow.collectLatest {
            Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
            when (it) {
                is CongregationsListUiSingleEvent.OpenCongregationScreen -> {
                    navController.navigate(it.navRoute)
                }
            }
        }
    }
}

@Composable
fun CongregationsList(
    congregations: List<CongregationsListItem>,
    congregationInput: CongregationInput?,
    onEdit: (CongregationsListItem) -> Unit,
    onDelete: (CongregationsListItem) -> Unit,
    onClick: (CongregationsListItem) -> Unit
) {
    Timber.tag(TAG).d("CongregationsList(...) called")
    var selectedIndex by remember { mutableStateOf(-1) } // by
    if (congregations.isNotEmpty()) {
        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier
                .selectableGroup() // Optional, for accessibility purpose
                .padding(8.dp)
                .focusable(enabled = true)
        ) {
            items(congregations.size) { index ->
                congregations[index].let { congregation ->
                    val isSelected =
                        ((selectedIndex == -1) and ((congregationInput?.congregationId == congregation.id) || congregation.isFavorite)) || (selectedIndex == index)
                    ListItemComponent(
                        item = congregation,
                        itemActions = listOf(
                            ComponentUiAction.EditListItem { onEdit(congregation) },
                            ComponentUiAction.DeleteListItem(
                                stringResource(
                                    R.string.dlg_confirm_del_congregation,
                                    congregation.congregationName
                                )
                            ) { onDelete(congregation) }),
                        selected = isSelected,
                        background = (if (isSelected) Color.LightGray else Color.Transparent),
                    ) {
                        if (selectedIndex != index) selectedIndex = index
                        onClick(congregation)
                    }
                }
            }
        }
    } else {
        Text(
            text = stringResource(R.string.congregations_list_empty_text),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewCongregationsList() {
    JWSuiteTheme {
        Surface {
            CongregationsList(
                congregations = CongregationsListViewModelImpl.previewList(LocalContext.current),
                congregationInput = CongregationInput(UUID.randomUUID()),
                onEdit = {},
                onDelete = {},
                onClick = {})
        }
    }
}
