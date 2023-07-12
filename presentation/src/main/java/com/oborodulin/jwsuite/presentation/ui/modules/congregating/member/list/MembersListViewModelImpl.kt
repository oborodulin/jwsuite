package com.oborodulin.jwsuite.presentation.ui.modules.congregating.member.list

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.ui.state.MviViewModel
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.home.common.util.Utils
import com.oborodulin.jwsuite.data.R
import com.oborodulin.jwsuite.domain.usecases.member.DeleteMemberUseCase
import com.oborodulin.jwsuite.domain.usecases.member.GetMembersUseCase
import com.oborodulin.jwsuite.domain.usecases.member.MemberUseCases
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.CongregationInput
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.GroupUi
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.MembersListItem
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.converters.MembersListConverter
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

private const val TAG = "Congregating.ui.MembersListViewModelImpl"

@HiltViewModel
class MembersListViewModelImpl @Inject constructor(
    private val useCases: MemberUseCases,
    private val converter: MembersListConverter
) : MembersListViewModel,
    MviViewModel<List<MembersListItem>, UiState<List<MembersListItem>>, MembersListUiAction, MembersListUiSingleEvent>() {

    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: MembersListUiAction): Job {
        Timber.tag(TAG)
            .d("handleAction(MembersListUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is MembersListUiAction.Load -> {
                loadMembers(action.congregationId, action.groupId)
            }

            is MembersListUiAction.EditMember -> {
                submitSingleEvent(
                    MembersListUiSingleEvent.OpenMemberScreen(
                        NavRoutes.Congregation.routeForCongregation(
                            CongregationInput(action.memberId)
                        )
                    )
                )
            }

            is MembersListUiAction.DeleteMember -> {
                deleteMember(action.memberId)
            }
        }
        return job
    }

    private fun loadMembers(congregationId: UUID?, groupId: UUID?): Job {
        Timber.tag(TAG)
            .d("loadMembers() called: congregationId = %s, groupId = %s", congregationId, groupId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.getMembersUseCase.execute(GetMembersUseCase.Request(congregationId, groupId))
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
        Timber.tag(TAG).d("deleteMember() called: memberId = %s", memberId.toString())
        val job = viewModelScope.launch(errorHandler) {
            useCases.deleteMemberUseCase.execute(DeleteMemberUseCase.Request(memberId)).collect {}
        }
        return job
    }

    override fun initFieldStatesByUiModel(uiModel: Any): Job? = null

    companion object {
        fun previewModel(ctx: Context) =
            object : MembersListViewModel {
                override val uiStateFlow = MutableStateFlow(UiState.Success(previewList(ctx)))
                override val singleEventFlow = Channel<MembersListUiSingleEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()

                //fun viewModelScope(): CoroutineScope = CoroutineScope(Dispatchers.Main)
                override fun handleActionJob(action: () -> Unit, afterAction: () -> Unit) {}
                override fun submitAction(action: MembersListUiAction): Job? = null
            }

        fun previewList(ctx: Context) = listOf(
            MembersListItem(
                id = UUID.randomUUID(),
                group = GroupUi(),
                memberNum = ctx.resources.getString(R.string.def_ivanov_member_num),
                memberName = ctx.resources.getString(R.string.def_ivanov_member_name),
                surname = ctx.resources.getString(R.string.def_ivanov_member_surname),
                patronymic = ctx.resources.getString(R.string.def_ivanov_member_patronymic),
                pseudonym = ctx.resources.getString(R.string.def_ivanov_member_pseudonym),
                dateOfBirth = Utils.toOffsetDateTime("1981-08-01T14:29:10.212+03:00")
            ),
            MembersListItem(
                id = UUID.randomUUID(),
                group = GroupUi(),
                memberNum = ctx.resources.getString(R.string.def_tarasova_member_num),
                memberName = ctx.resources.getString(R.string.def_tarasova_member_name),
                surname = ctx.resources.getString(R.string.def_tarasova_member_surname),
                patronymic = ctx.resources.getString(R.string.def_tarasova_member_patronymic),
                pseudonym = ctx.resources.getString(R.string.def_tarasova_member_pseudonym),
                dateOfBirth = Utils.toOffsetDateTime("1979-08-01T14:29:10.212+03:00")
            )
        )
    }
}