package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.congregation.list

import android.content.res.Configuration
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.ComponentUiAction
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.home.common.util.OnListItemEvent
import com.oborodulin.jwsuite.presentation.ui.AppState
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_congregation.R
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.list.MembersListUiAction
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.list.MembersListViewModelImpl
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationsListItem
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

private const val TAG = "Congregating.CongregationsListView"

@Composable
fun CongregationsListView(
    appState: AppState,
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    congregationsListViewModel: CongregationsListViewModelImpl = hiltViewModel(),
    membersListViewModel: MembersListViewModelImpl = hiltViewModel(),
) {
    Timber.tag(TAG).d("CongregationsListView(...) called")
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("CongregationsListView: LaunchedEffect() BEFORE collect ui state flow")
        congregationsListViewModel.submitAction(CongregationsListUiAction.Load)
    }
    congregationsListViewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        CommonScreen(state = state) {
            CongregationsList(
                congregations = it,
                congregationsListViewModel = congregationsListViewModel,
                onFavorite = { listItem ->
                    listItem.itemId?.let { id ->
                        congregationsListViewModel.submitAction(
                            CongregationsListUiAction.MakeFavoriteCongregation(id)
                        )
                    }
                },
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
                Timber.tag(TAG).d(
                    "CongregationsListView: sharedViewModel = %s",
                    appState.sharedViewModel.value
                )
                appState.sharedViewModel.value?.submitData(congregation)
                appState.actionBarSubtitle.value = congregation.congregationName
                with(membersListViewModel) {
                    submitAction(MembersListUiAction.LoadByCongregation(congregation.id))
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("CongregationsListView: LaunchedEffect() AFTER collect single Event Flow")
        congregationsListViewModel.singleEventFlow.collectLatest {
            Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
            when (it) {
                is CongregationsListUiSingleEvent.OpenCongregationScreen -> {
                    appState.commonNavController.navigate(it.navRoute)
                }
            }
        }
    }
}

@Composable
fun CongregationsList(
    congregations: List<CongregationsListItem>,
    congregationsListViewModel: CongregationsListViewModel,
    onFavorite: OnListItemEvent,
    onEdit: (CongregationsListItem) -> Unit,
    onDelete: (CongregationsListItem) -> Unit,
    onClick: (CongregationsListItem) -> Unit
) {
    Timber.tag(TAG).d("CongregationsList(...) called")
    if (congregations.isNotEmpty()) {
        val listState =
            rememberLazyListState(initialFirstVisibleItemIndex = congregations.filter { it.selected }
                .getOrNull(0)?.let { congregations.indexOf(it) } ?: 0)
        LazyColumn(
            state = listState,
            modifier = Modifier
                .selectableGroup() // Optional, for accessibility purpose
                .padding(8.dp)
                .focusable(enabled = true)
        ) {
            items(congregations.size) { index ->
                congregations[index].let { congregation ->
                    CongregationsListItemComponent(
                        item = congregation,
                        itemActions = listOf(
                            ComponentUiAction.EditListItem { onEdit(congregation) },
                            ComponentUiAction.DeleteListItem(
                                stringResource(
                                    R.string.dlg_confirm_del_congregation,
                                    congregation.congregationName
                                )
                            ) { onDelete(congregation) }),
                        selected = congregation.selected,
                        onFavorite = onFavorite,
                        onClick = {
                            congregationsListViewModel.singleSelectItem(congregation)
                            onClick(congregation)
                        }
                    )
                }
            }
        }
    } else {
        Text(
            modifier = Modifier.fillMaxSize(),
            textAlign = TextAlign.Center,
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
                congregationsListViewModel = CongregationsListViewModelImpl.previewModel(
                    LocalContext.current
                ),
                onFavorite = {},
                onEdit = {},
                onDelete = {},
                onClick = {}
            )
        }
    }
}
