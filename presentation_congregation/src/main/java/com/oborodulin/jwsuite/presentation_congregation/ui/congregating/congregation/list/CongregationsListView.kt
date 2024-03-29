package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.congregation.list

import android.content.res.Configuration
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.ComponentUiAction
import com.oborodulin.home.common.ui.components.list.EmptyListTextComponent
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.home.common.util.Constants.EMPTY_LIST_ITEM_EVENT
import com.oborodulin.home.common.util.LogLevel.LOG_UI_STATE
import com.oborodulin.home.common.util.OnListItemEvent
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_congregation.R
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.list.MembersWithUsernameUiAction
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.list.MembersWithUsernameViewModelImpl
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationsListItem
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

private const val TAG = "Congregating.CongregationsListView"

@Composable
fun CongregationsListView(
    //appState: AppState,
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    congregationsListViewModel: CongregationsListViewModelImpl = hiltViewModel(),
    membersListViewModel: MembersWithUsernameViewModelImpl = hiltViewModel()//,
    //onActionBarSubtitleChange: (String) -> Unit
) {
    Timber.tag(TAG).d("CongregationsListView(...) called")
    val appState = LocalAppState.current
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("CongregationsListView -> LaunchedEffect()")
        congregationsListViewModel.submitAction(CongregationsListUiAction.Load)
    }
    val searchText by congregationsListViewModel.searchText.collectAsStateWithLifecycle()
    congregationsListViewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        if (LOG_UI_STATE) {
            Timber.tag(TAG).d("Collect ui state flow: %s", state)
        }
        CommonScreen(state = state) {
            CongregationsList(
                congregations = it,
                searchedText = searchText.text,
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
                    appState.congregationSharedViewModel.value
                )
                appState.congregationSharedViewModel.value?.submitData(congregation)
                appState.actionBarSubtitle.value = congregation.congregationName
                //onActionBarSubtitleChange(congregation.congregationName)
                congregationsListViewModel.singleSelectItem(congregation)
                membersListViewModel.submitAction(
                    MembersWithUsernameUiAction.LoadByCongregation(congregation.id)
                )
            }
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG)
            .d("CongregationsListView -> LaunchedEffect() -> collect single Event Flow")
        congregationsListViewModel.singleEventFlow.collectLatest {
            Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
            when (it) {
                is CongregationsListUiSingleEvent.OpenCongregationScreen -> {
                    appState.mainNavigate(it.navRoute)
                }
            }
        }
    }
}

@Composable
fun CongregationsList(
    congregations: List<CongregationsListItem>,
    searchedText: String = "",
    onFavorite: OnListItemEvent,
    onEdit: (CongregationsListItem) -> Unit,
    onDelete: (CongregationsListItem) -> Unit,
    onClick: (CongregationsListItem) -> Unit
) {
    Timber.tag(TAG).d("CongregationsList(...) called: size = %d", congregations.size)
    if (congregations.isNotEmpty()) {
        val filteredItems = remember(congregations, searchedText) {
            if (searchedText.isEmpty()) {
                congregations
            } else {
                congregations.filter { it.doesMatchSearchQuery(searchedText) }
            }
        }
        val firstVisibleItem = filteredItems.filter { it.selected }.getOrNull(0)
        LaunchedEffect(firstVisibleItem) {
            Timber.tag(TAG)
                .d("CongregationsList -> LaunchedEffect()")
            firstVisibleItem?.let { if (onClick !== EMPTY_LIST_ITEM_EVENT) onClick(it) }
        }
        val listState = rememberLazyListState(initialFirstVisibleItemIndex = firstVisibleItem?.let {
            filteredItems.indexOf(it)
        } ?: 0)
        LazyColumn(
            state = listState,
            modifier = Modifier
                .selectableGroup() // Optional, for accessibility purpose
                .padding(8.dp)
                .focusable(enabled = true)
        ) {
            itemsIndexed(filteredItems, key = { _, item -> item.id }) { _, congregation ->
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
                    onClick = { onClick(congregation) }
                )
            }
        }
    } else {
        EmptyListTextComponent(R.string.congregations_list_empty_text)
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
                onFavorite = {},
                onEdit = {},
                onDelete = {},
                onClick = {}
            )
        }
    }
}
