package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.role.list

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.ListViewModel
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_ACTION
import com.oborodulin.home.common.util.toOffsetDateTime
import com.oborodulin.jwsuite.domain.usecases.member.MemberUseCases
import com.oborodulin.jwsuite.domain.usecases.member.role.DeleteMemberRoleUseCase
import com.oborodulin.jwsuite.domain.usecases.member.role.GetMemberRolesUseCase
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.MemberRoleInput
import com.oborodulin.jwsuite.presentation.ui.model.MemberRolesListItem
import com.oborodulin.jwsuite.presentation.ui.model.RolesListItem
import com.oborodulin.jwsuite.presentation_congregation.ui.model.converters.MemberRolesListConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

private const val TAG = "Congregating.MemberRolesListViewModelImpl"

@HiltViewModel
class MemberRolesListViewModelImpl @Inject constructor(
    private val useCases: MemberUseCases,
    private val converter: MemberRolesListConverter
) : MemberRolesListViewModel,
    ListViewModel<List<MemberRolesListItem>, UiState<List<MemberRolesListItem>>, MemberRolesListUiAction, MemberRolesListUiSingleEvent>() {

    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: MemberRolesListUiAction): Job {
        if (LOG_FLOW_ACTION) Timber.tag(TAG)
            .d("handleAction(MemberRolesListUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is MemberRolesListUiAction.Load -> loadMemberRoles(action.memberId)

            is MemberRolesListUiAction.EditMemberRole -> {
                submitSingleEvent(
                    MemberRolesListUiSingleEvent.OpenMemberRoleScreen(
                        NavRoutes.MemberRole.routeForMemberRole(MemberRoleInput(action.memberRoleId))
                    )
                )
            }

            is MemberRolesListUiAction.DeleteMemberRole -> deleteMemberRole(action.memberRoleId)

        }
        return job
    }

    private fun loadMemberRoles(memberId: UUID): Job {
        Timber.tag(TAG).d("loadMemberRoles() called: memberId = %s", memberId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.getMemberRolesUseCase.execute(GetMemberRolesUseCase.Request(memberId))
                .map {
                    converter.convert(it)
                }
                .collect {
                    submitState(it)
                }
        }
        return job
    }

    private fun deleteMemberRole(memberRoleId: UUID): Job {
        Timber.tag(TAG)
            .d("deleteMemberRole(...) called: memberRoleId = %s", memberRoleId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.deleteMemberRoleUseCase.execute(DeleteMemberRoleUseCase.Request(memberRoleId))
                .collect {}
        }
        return job
    }

    override fun initFieldStatesByUiModel(uiModel: List<MemberRolesListItem>): Job? = null

    companion object {
        fun previewModel(ctx: Context) =
            object : MemberRolesListViewModel {
                override val uiStateFlow = MutableStateFlow(UiState.Success(previewList(ctx)))
                override val singleEventFlow =
                    Channel<MemberRolesListUiSingleEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()
                override val uiStateErrorMsg = MutableStateFlow("")

                override fun redirectedErrorMessage() = null
                override val searchText = MutableStateFlow(TextFieldValue(""))
                override val isSearching = MutableStateFlow(false)
                override fun onSearchTextChange(text: TextFieldValue) {}
                override fun clearSearchText() {}

                override val areSingleSelected = MutableStateFlow(false)
                override fun singleSelectItem(selectedItem: ListItemModel) {}
                override fun singleSelectedItem() = null

                override fun handleActionJob(
                    action: () -> Unit,
                    afterAction: (CoroutineScope) -> Unit
                ) {
                }

                override fun submitAction(action: MemberRolesListUiAction): Job? = null
            }

        fun previewList(ctx: Context) = listOf(
            MemberRolesListItem(
                id = UUID.randomUUID(),
                role = RolesListItem(),
                roleExpiredDate = "2023-12-01T14:29:10.212+03:00".toOffsetDateTime()
            )
        )
    }
}