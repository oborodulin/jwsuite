package com.oborodulin.jwsuite.presentation.ui.modules.congregating.member.list

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
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.oborodulin.home.common.ui.ComponentUiAction
import com.oborodulin.home.common.ui.components.items.ListItemComponent
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.CongregationInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.GroupInput
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.MembersListItem
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import java.util.Locale

private const val TAG = "Congregating.ui.MembersListView"

@Composable
fun MembersListView(
    viewModel: MembersListViewModelImpl = hiltViewModel(),
    navController: NavController,
    searchState: MutableState<TextFieldValue>,
    congregationInput: CongregationInput? = null,
    groupInput: GroupInput? = null
) {
    Timber.tag(TAG).d(
        "MembersListView(...) called: congregationInput = %s, groupInput = %s",
        congregationInput,
        groupInput
    )
    LaunchedEffect(congregationInput?.congregationId, groupInput?.groupId) {
        Timber.tag(TAG).d("MembersListView: LaunchedEffect() BEFORE collect ui state flow")
        viewModel.submitAction(
            MembersListUiAction.Load(congregationInput?.congregationId, groupInput?.groupId)
        )
    }
    viewModel.uiStateFlow.collectAsState().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        CommonScreen(state = state) {
            MembersList(
                members = it,
                searchState = searchState,
                onEdit = { member -> viewModel.submitAction(MembersListUiAction.EditMember(member.id)) },
                onDelete = { member ->
                    viewModel.submitAction(MembersListUiAction.DeleteMember(member.id))
                }
            )
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("MembersListView: LaunchedEffect() AFTER collect ui state flow")
        viewModel.singleEventFlow.collectLatest {
            Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
            when (it) {
                is MembersListUiSingleEvent.OpenMemberScreen -> {
                    navController.navigate(it.navRoute)
                }
            }
        }
    }
}

@Composable
fun MembersList(
    members: List<MembersListItem>,
    searchState: MutableState<TextFieldValue>,
    onEdit: (MembersListItem) -> Unit,
    onDelete: (MembersListItem) -> Unit
) {
    Timber.tag(TAG).d("MembersList(...) called")
    var selectedIndex by remember { mutableStateOf(-1) } // by
    if (members.isNotEmpty()) {
        var filteredItems: List<MembersListItem>
        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier
                .selectableGroup() // Optional, for accessibility purpose
                .padding(8.dp)
                .focusable(enabled = true)
        ) {
            val searchedText = searchState.value.text
            filteredItems = if (searchedText.isEmpty()) {
                members
            } else {
                val resultList = mutableListOf<MembersListItem>()
                for (item in members) {
                    if (item.headline.lowercase(Locale.getDefault())
                            .contains(searchedText.lowercase(Locale.getDefault()))
                    ) {
                        resultList.add(item)
                    }
                }
                resultList
            }
            items(filteredItems.size) { index ->
                filteredItems[index].let { member ->
                    val isSelected = (selectedIndex == index)
                    ListItemComponent(
                        item = member,
                        itemActions = listOf(
                            ComponentUiAction.EditListItem { onEdit(member) },
                            ComponentUiAction.DeleteListItem(
                                stringResource(R.string.dlg_confirm_del_member, member.headline)
                            ) { onDelete(member) }),
                        selected = isSelected,
                        background = (if (isSelected) Color.LightGray else Color.Transparent),
                        onClick = {
                            if (selectedIndex != index) selectedIndex = index
                        }
                    )
                }
            }
        }
    } else {
        Text(
            text = stringResource(R.string.members_list_empty_text),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewMembersList() {
    val searchMemberState = remember { mutableStateOf(TextFieldValue("")) }
    JWSuiteTheme {
        Surface {
            MembersList(
                members = MembersListViewModelImpl.previewList(LocalContext.current),
                searchState = searchMemberState,
                onEdit = {},
                onDelete = {}
            )
        }
    }
}
