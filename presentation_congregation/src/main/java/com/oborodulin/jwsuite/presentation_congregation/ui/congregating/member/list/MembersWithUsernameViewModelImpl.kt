package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.list

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.MviViewModel
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.home.common.util.LogLevel
import com.oborodulin.home.common.util.LogLevel.LOG_MVI_LIST
import com.oborodulin.jwsuite.data_congregation.R
import com.oborodulin.jwsuite.domain.usecases.member.DeleteMemberUseCase
import com.oborodulin.jwsuite.domain.usecases.member.GetMembersWithUsernameUseCase
import com.oborodulin.jwsuite.domain.usecases.member.MemberUseCases
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput
import com.oborodulin.jwsuite.presentation_congregation.ui.model.MembersWithUsernameUi
import com.oborodulin.jwsuite.presentation_congregation.ui.model.converters.MembersWithUsernameConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

private const val TAG = "Congregating.MembersWithUsernameViewModelImpl"

@HiltViewModel
class MembersWithUsernameViewModelImpl @Inject constructor(
    private val useCases: MemberUseCases,
    private val converter: MembersWithUsernameConverter
) : MembersWithUsernameViewModel,
    MviViewModel<MembersWithUsernameUi, UiState<MembersWithUsernameUi>, MembersWithUsernameUiAction, MembersWithUsernameUiSingleEvent>() {
    private val _selectedItem: MutableStateFlow<ListItemModel?> = MutableStateFlow(null)
    override val areSingleSelected = _selectedItem.map { item -> item != null }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), false
    )

    // https://medium.com/@wunder.saqib/compose-single-selection-with-data-binding-37a12cf51bc8
    override fun singleSelectItem(selectedItem: ListItemModel) {
        if (LOG_MVI_LIST) {
            Timber.tag(TAG).d("singleSelectItem(...) called")
        }
        uiState()?.let { uiState ->
            uiState.members.forEach { it.selected = false }
            val item = uiState.members.find { it.itemId == selectedItem.itemId }
            item?.selected = true
            _selectedItem.value = item
            if (LOG_MVI_LIST) {
                Timber.tag(TAG).d("selected %s list item", item)
            }
        }
    }

    override fun singleSelectedItem(): ListItemModel? {
        if (LOG_MVI_LIST) {
            Timber.tag(TAG).d("singleSelectedItem() called")
        }
        var selectedItem: ListItemModel? = null
        uiState()?.let { uiState ->
            selectedItem = try {
                uiState.members.first { it.selected }
            } catch (e: NoSuchElementException) {
                uiState.members.getOrNull(0)
                //Timber.tag(TAG).e(e)
            }
            if (LOG_MVI_LIST) {
                Timber.tag(TAG).d("selected %s list item", selectedItem)
            }
        }
        return selectedItem
    }

    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: MembersWithUsernameUiAction): Job {
        if (LogLevel.LOG_FLOW_ACTION) Timber.tag(TAG)
            .d("handleAction(MembersWithUsernameUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is MembersWithUsernameUiAction.LoadByCongregation -> loadMembersWithUsername(
                congregationId = action.congregationId, isService = action.isService,
                byCongregation = true
            )

            is MembersWithUsernameUiAction.LoadByGroup -> loadMembersWithUsername(
                groupId = action.groupId, isService = action.isService,
                byCongregation = false
            )

            is MembersWithUsernameUiAction.EditMember -> submitSingleEvent(
                MembersWithUsernameUiSingleEvent.OpenMemberScreen(
                    NavRoutes.Member.routeForMember(NavigationInput.MemberInput(action.memberId))
                )
            )

            is MembersWithUsernameUiAction.DeleteMember -> deleteMember(action.memberId)
        }
        return job
    }

    private fun loadMembersWithUsername(
        congregationId: UUID? = null, groupId: UUID? = null,
        isService: Boolean = false, byCongregation: Boolean
    ): Job {
        Timber.tag(TAG)
            .d(
                "loadMembersWithUsername(...) called: congregationId = %s; groupId = %s, isService = %s; byCongregation = %s",
                congregationId, groupId, isService, byCongregation
            )
        val job = viewModelScope.launch(errorHandler) {
            useCases.getMembersWithUsernameUseCase.execute(
                GetMembersWithUsernameUseCase.Request(
                    congregationId = congregationId, groupId = groupId, isService = isService,
                    byCongregation = byCongregation
                )
            )
                .map {
                    converter.convert(it)
                }
                .collect {
                    submitState(it)
                }
        }
        return job
    }

    private fun deleteMember(memberId: UUID): Job {
        Timber.tag(TAG).d("deleteMember(...) called: memberId = %s", memberId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.deleteMemberUseCase.execute(DeleteMemberUseCase.Request(memberId)).collect {}
        }
        return job
    }

    override fun initFieldStatesByUiModel(uiModel: MembersWithUsernameUi): Job? = null

    companion object {
        fun previewModel(ctx: Context) =
            object : MembersWithUsernameViewModel {
                override val uiStateFlow = MutableStateFlow(UiState.Success(previewUiModel(ctx)))
                override val singleEventFlow =
                    Channel<MembersWithUsernameUiSingleEvent>().receiveAsFlow()
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

                override fun submitAction(action: MembersWithUsernameUiAction): Job? = null
            }

        fun previewUiModel(ctx: Context) = MembersWithUsernameUi(
            username = ctx.resources.getString(R.string.def_admin_member_pseudonym),
            members = MembersListViewModelImpl.previewList(ctx)
        )
    }
}