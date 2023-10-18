package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.group.list

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.ListViewModel
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.jwsuite.data_congregation.R
import com.oborodulin.jwsuite.domain.usecases.group.DeleteGroupUseCase
import com.oborodulin.jwsuite.domain.usecases.group.GetGroupsUseCase
import com.oborodulin.jwsuite.domain.usecases.group.GroupUseCases
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.GroupInput
import com.oborodulin.jwsuite.presentation_congregation.ui.model.GroupsListItem
import com.oborodulin.jwsuite.presentation_congregation.ui.model.converters.GroupsListConverter
import dagger.hilt.android.lifecycle.HiltViewModel
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

private const val TAG = "Congregating.GroupsListViewModelImpl"

@HiltViewModel
class GroupsListViewModelImpl @Inject constructor(
    private val useCases: GroupUseCases,
    private val converter: GroupsListConverter
) : GroupsListViewModel,
    ListViewModel<List<GroupsListItem>, UiState<List<GroupsListItem>>, GroupsListUiAction, GroupsListUiSingleEvent>() {

    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: GroupsListUiAction): Job {
        Timber.tag(TAG)
            .d("handleAction(GroupsListUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is GroupsListUiAction.Load -> {
                loadGroups(action.congregationId)
            }

            is GroupsListUiAction.EditGroup -> {
                submitSingleEvent(
                    GroupsListUiSingleEvent.OpenGroupScreen(
                        NavRoutes.Group.routeForGroup(GroupInput(action.groupId))
                    )
                )
            }

            is GroupsListUiAction.DeleteGroup -> {
                deleteGroup(action.groupId)
            }

        }
        return job
    }

    private fun loadGroups(congregationId: UUID?): Job {
        Timber.tag(TAG).d("loadGroups() called: congregationId = %s", congregationId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.getGroupsUseCase.execute(GetGroupsUseCase.Request(congregationId))
                .map {
                    converter.convert(it)
                }
                .collect {
                    submitState(it)
                }
        }
        return job
    }

    private fun deleteGroup(groupId: UUID): Job {
        Timber.tag(TAG)
            .d("deleteGroup() called: congregationId = %s", groupId.toString())
        val job = viewModelScope.launch(errorHandler) {
            useCases.deleteGroupUseCase.execute(DeleteGroupUseCase.Request(groupId)).collect {}
        }
        return job
    }

    override fun initFieldStatesByUiModel(uiModel: List<GroupsListItem>): Job? = null

    companion object {
        fun previewModel(ctx: Context) =
            object : GroupsListViewModel {
                override val uiStateFlow = MutableStateFlow(UiState.Success(previewList(ctx)))
                override val singleEventFlow =
                    Channel<GroupsListUiSingleEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()
                override val uiStateErrorMsg = MutableStateFlow("")

                override val searchText = MutableStateFlow(TextFieldValue(""))
                override val isSearching = MutableStateFlow(false)
                override fun onSearchTextChange(text: TextFieldValue) {}
                override fun singleSelectItem(selectedItem: ListItemModel) {}

                //fun viewModelScope(): CoroutineScope = CoroutineScope(Dispatchers.Main)
                override fun handleActionJob(action: () -> Unit, afterAction: () -> Unit) {}
                override fun submitAction(action: GroupsListUiAction): Job? = null
            }

        fun previewList(ctx: Context) = listOf(
            GroupsListItem(
                id = UUID.randomUUID(),
                groupNum = ctx.resources.getInteger(R.integer.def_group1)
            ),
            GroupsListItem(
                id = UUID.randomUUID(),
                groupNum = ctx.resources.getInteger(R.integer.def_group2)
            ),
            GroupsListItem(
                id = UUID.randomUUID(),
                groupNum = ctx.resources.getInteger(R.integer.def_group3)
            ),
            GroupsListItem(
                id = UUID.randomUUID(),
                groupNum = ctx.resources.getInteger(R.integer.def_group4)
            ),
            GroupsListItem(
                id = UUID.randomUUID(),
                groupNum = ctx.resources.getInteger(R.integer.def_group5)
            ),
            GroupsListItem(
                id = UUID.randomUUID(),
                groupNum = ctx.resources.getInteger(R.integer.def_group6)
            ),
            GroupsListItem(
                id = UUID.randomUUID(),
                groupNum = ctx.resources.getInteger(R.integer.def_group7)
            ),
        )
    }
}