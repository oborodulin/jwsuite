package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.group.list

import android.content.res.Configuration
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.oborodulin.home.common.ui.components.items.ListItemComponent
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.jwsuite.presentation.AppState
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.CongregationInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.GroupInput
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_congregation.R
import com.oborodulin.jwsuite.presentation_congregation.ui.FavoriteCongregationViewModel
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.list.MembersListUiAction
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.list.MembersListViewModelImpl
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationsListItem
import com.oborodulin.jwsuite.presentation_congregation.ui.model.GroupsListItem
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import java.util.UUID

private const val TAG = "Congregating.GroupsListView"

@Composable
fun GroupsListView(
    appState: AppState,
    sharedViewModel: FavoriteCongregationViewModel<CongregationsListItem?>,
    groupsListViewModel: GroupsListViewModelImpl = hiltViewModel(),
    membersListViewModel: MembersListViewModelImpl = hiltViewModel(),
    congregationInput: CongregationInput? = null,
    groupInput: GroupInput? = null
) {
    Timber.tag(TAG).d(
        "GroupsListView(...) called: congregationInput = %s; groupInput = %s",
        congregationInput,
        groupInput
    )
    //val lifecycleOwner = LocalLifecycleOwner.current
    //var currentCongregation = remember(sharedViewModel.sharedFlow, lifecycleOwner) {
    //    sharedViewModel.sharedFlow.flowWithLifecycle(
    //        lifecycleOwner.lifecycle,
    //        Lifecycle.State.STARTED
    //    )
    //}
    val currentCongregation = sharedViewModel.sharedFlow.collectAsStateWithLifecycle().value
    val congregationId = congregationInput?.congregationId ?: currentCongregation?.id
    Timber.tag(TAG)
        .d(
            "currentCongregation = %s; congregationId = %s", currentCongregation, congregationId
        )
    LaunchedEffect(congregationId) {
        Timber.tag(TAG).d("GroupsListView: LaunchedEffect() BEFORE collect ui state flow")
        when (congregationId) {
            null -> groupsListViewModel.submitAction(GroupsListUiAction.Load())
            else -> groupsListViewModel.submitAction(GroupsListUiAction.Load(congregationId))
        }
    }
    groupsListViewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        CommonScreen(state = state) {
            GroupsList(
                congregationId = congregationInput?.congregationId ?: currentCongregation?.id,
                groups = it,
                groupsListViewModel = groupsListViewModel,
                onClick = { group ->
                    with(membersListViewModel) {
                        submitAction(MembersListUiAction.LoadByGroup(groupId = group.id))
                    }
                },
                onEdit = { group ->
                    groupsListViewModel.submitAction(GroupsListUiAction.EditGroup(group.id))
                }
            ) { group ->
                groupsListViewModel.submitAction(GroupsListUiAction.DeleteGroup(group.id))
            }
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("GroupsListView: LaunchedEffect() AFTER collect ui state flow")
        groupsListViewModel.singleEventFlow.collectLatest {
            Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
            when (it) {
                is GroupsListUiSingleEvent.OpenGroupScreen -> {
                    appState.commonNavController.navigate(it.navRoute)
                }
            }
        }
    }
}

@Composable
fun GroupsList(
    congregationId: UUID?,
    groups: List<GroupsListItem>,
    groupsListViewModel: GroupsListViewModel,
    onClick: (GroupsListItem) -> Unit,
    onEdit: (GroupsListItem) -> Unit,
    onDelete: (GroupsListItem) -> Unit
) {
    Timber.tag(TAG).d("GroupsList(...) called")
    // https://stackoverflow.com/questions/72531840/how-to-select-only-one-item-in-a-list-lazycolumn
    //var selectedIndex by remember { mutableStateOf(-1) }
    if (groups.isNotEmpty()) {
        val listState =
            rememberLazyListState(initialFirstVisibleItemIndex = groups.filter { it.selected }
                .getOrNull(0)?.let { groups.indexOf(it) } ?: 0)
        LazyColumn(
            state = listState,
            modifier = Modifier
                .selectableGroup() // Optional, for accessibility purpose
                .padding(8.dp)
                .focusable(enabled = true)
        ) {
            itemsIndexed(groups, key = { _, item -> item.id }) { _, group ->
                ListItemComponent(
                    item = group,
                    itemActions = listOf(
                        ComponentUiAction.EditListItem { onEdit(group) },
                        ComponentUiAction.DeleteListItem(
                            stringResource(R.string.dlg_confirm_del_group, group.groupNum)
                        ) { onDelete(group) }),
                    selected = group.selected, //((selectedIndex == -1) && group.selected) || selectedIndex == index,
                    onClick = {
                        //if (selectedIndex != index) selectedIndex = index
                        groupsListViewModel.singleSelectItem(group)
                        // allow deselection: selectedIndex = if (selectedIndex == index) -1 else index
                        onClick(group)
                    }
                )
            }
        }
    } else {
        congregationId?.let {
            Text(
                modifier = Modifier.fillMaxSize(),
                textAlign = TextAlign.Center,
                text = stringResource(R.string.groups_list_empty_text),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewGroupsCongregating() {
    JWSuiteTheme {
        Surface {
            GroupsList(
                congregationId = UUID.randomUUID(),
                groups = GroupsListViewModelImpl.previewList(LocalContext.current),
                groupsListViewModel = GroupsListViewModelImpl.previewModel(LocalContext.current),
                onClick = {},
                onEdit = {},
                onDelete = {}
            )
        }
    }
}
