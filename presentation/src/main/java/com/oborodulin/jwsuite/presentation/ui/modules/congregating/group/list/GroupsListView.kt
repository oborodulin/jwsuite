package com.oborodulin.jwsuite.presentation.ui.modules.congregating.group.list

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.CongregationInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.GroupInput
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.member.list.MembersListUiAction
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.member.list.MembersListViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.GroupsListItem
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import java.util.UUID

private const val TAG = "Congregating.GroupsListView"

@Composable
fun GroupsListView(
    appState: AppState,
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
    val currentCongregation =
        appState.sharedViewModel.value?.sharedFlow?.collectAsStateWithLifecycle()?.value
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
                groupInput = groupInput,
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
    groupInput: GroupInput?,
    onClick: (GroupsListItem) -> Unit,
    onEdit: (GroupsListItem) -> Unit,
    onDelete: (GroupsListItem) -> Unit
) {
    Timber.tag(TAG).d("GroupsList(...) called")
    var selectedIndex by remember { mutableStateOf(-1) } // by
    if (groups.isNotEmpty()) {
        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier
                .selectableGroup() // Optional, for accessibility purpose
                .padding(8.dp)
                .focusable(enabled = true)
        ) {
            items(groups.size) { index ->
                groups[index].let { group ->
                    val isSelected =
                        ((selectedIndex == -1) and (groupInput?.groupId == group.id)) || (selectedIndex == index)
                    ListItemComponent(
                        item = group,
                        itemActions = listOf(
                            ComponentUiAction.EditListItem { onEdit(group) },
                            ComponentUiAction.DeleteListItem(
                                stringResource(R.string.dlg_confirm_del_group, group.groupNum)
                            ) { onDelete(group) }),
                        selected = isSelected,
                        background = (if (isSelected) Color.LightGray else Color.Transparent),
                        onClick = {
                            if (selectedIndex != index) selectedIndex = index
                            onClick(group)
                        }
                    )
                }
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
                groupInput = GroupInput(UUID.randomUUID()),
                onClick = {},
                onEdit = {},
                onDelete = {}
            )
        }
    }
}
