package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.details

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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.ComponentUiAction
import com.oborodulin.home.common.ui.components.items.ListItemComponent
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.jwsuite.presentation.AppState
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.MemberInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryInput
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.MembersListItem
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import java.util.UUID

private const val TAG = "Territoring.TerritoryDetailsView"

@Composable
fun TerritoryDetailsView(
    appState: AppState,
    viewModel: TerritoryDetailsViewModelImpl = hiltViewModel(),
    territoryInput: TerritoryInput
) {
    Timber.tag(TAG).d("TerritoryDetailsView(...) called: territoryInput = %s", territoryInput)
    LaunchedEffect(territoryInput.territoryId) {
        Timber.tag(TAG).d("TerritoryDetailsView: LaunchedEffect() BEFORE collect ui state flow")
            viewModel.submitAction(TerritoryDetailsUiAction.Load(territoryInput.territoryId))
    }
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()
    viewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        CommonScreen(state = state) {
            TerritoryDetails(
                congregationId = congregationId,
                groupId = territoryInput?.groupId,
                searchedText = searchText.text,
                members = it,
                memberInput = memberInput,
                onEdit = { member ->
                    viewModel.submitAction(
                        TerritoryDetailsUiAction.EditMember(
                            member.id
                        )
                    )
                },
                onDelete = { member ->
                    viewModel.submitAction(TerritoryDetailsUiAction.DeleteMember(member.id))
                }
            )
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("TerritoryDetailsView: LaunchedEffect() AFTER collect ui state flow")
        viewModel.singleEventFlow.collectLatest {
            Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
            when (it) {
                is TerritoryDetailsUiSingleEvent.OpenMemberScreen -> {
                    appState.commonNavController.navigate(it.navRoute)
                }
            }
        }
    }
}

@Composable
fun TerritoryDetails(
    congregationId: UUID?,
    groupId: UUID?,
    searchedText: String = "",
    members: List<MembersListItem>,
    memberInput: MemberInput? = null,
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
            filteredItems = if (searchedText.isEmpty()) {
                members
            } else {
                members.filter { it.doesMatchSearchQuery(searchedText) }
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
        if (congregationId != null || groupId != null) {
            Text(
                text = stringResource(R.string.members_list_empty_text),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewMembersList() {
    JWSuiteTheme {
        Surface {
            TerritoryDetails(
                congregationId = UUID.randomUUID(),
                groupId = UUID.randomUUID(),
                members = TerritoryDetailsViewModelImpl.previewList(LocalContext.current),
                onEdit = {},
                onDelete = {}
            )
        }
    }
}
