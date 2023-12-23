package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.role.list

import android.content.res.Configuration
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.components.list.EditableListViewComponent
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.home.common.util.LogLevel.LOG_UI_STATE
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.MemberInput
import com.oborodulin.jwsuite.presentation.ui.AppState
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_congregation.R
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

private const val TAG = "Congregating.MemberRolesListView"

@Composable
fun MemberRolesListView(
    appState: AppState,
    viewModel: MemberRolesListViewModelImpl = hiltViewModel(),
    memberInput: MemberInput? = null
) {
    Timber.tag(TAG).d("MemberRolesListView(...) called: memberInput = %s", memberInput)
    LaunchedEffect(memberInput?.memberId) {
        Timber.tag(TAG).d("MemberRolesListView -> LaunchedEffect() BEFORE collect ui state flow")
        memberInput?.memberId?.let { viewModel.submitAction(MemberRolesListUiAction.Load(it)) }
    }
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()
    viewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        if (LOG_UI_STATE) Timber.tag(TAG).d("Collect ui state flow: %s", state)
        CommonScreen(state = state) {
            EditableListViewComponent(
                items = it,
                searchedText = searchText.text,
                dlgConfirmDelResId = R.string.dlg_confirm_del_member_role,
                emptyListResId = R.string.member_roles_list_empty_text,
                isEmptyListTextOutput = memberInput?.memberId != null,
                onEdit = { memberRole ->
                    viewModel.submitAction(MemberRolesListUiAction.EditMemberRole(memberRole.itemId!!))
                },
                onDelete = { memberRole ->
                    viewModel.submitAction(MemberRolesListUiAction.DeleteMemberRole(memberRole.itemId!!))
                }
            ) { memberRole -> viewModel.singleSelectItem(memberRole) }
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("MemberRolesListView -> LaunchedEffect() AFTER collect single Event Flow")
        viewModel.singleEventFlow.collectLatest {
            Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
            when (it) {
                is MemberRolesListUiSingleEvent.OpenMemberRoleScreen -> {
                    appState.mainNavigate(it.navRoute)
                }
            }
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewMemberRolesListView() {
    JWSuiteTheme {
        Surface {
            /*GroupsList(
                congregationId = UUID.randomUUID(),
                groups = GroupsListViewModelImpl.previewList(LocalContext.current),
                onClick = {},
                onEdit = {},
                onDelete = {}
            )*/
        }
    }
}
