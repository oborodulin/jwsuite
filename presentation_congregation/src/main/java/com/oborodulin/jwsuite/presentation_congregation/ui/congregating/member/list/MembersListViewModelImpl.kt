package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.list

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.extensions.toOffsetDateTime
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.ListViewModel
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_ACTION
import com.oborodulin.jwsuite.data_congregation.R
import com.oborodulin.jwsuite.domain.usecases.member.GetMembersUseCase
import com.oborodulin.jwsuite.domain.usecases.member.MemberUseCases
import com.oborodulin.jwsuite.presentation_congregation.ui.model.MembersListItem
import com.oborodulin.jwsuite.presentation_congregation.ui.model.converters.MembersListConverter
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

private const val TAG = "Congregating.MembersListViewModelImpl"

@HiltViewModel
class MembersListViewModelImpl @Inject constructor(
    private val useCases: MemberUseCases,
    private val converter: MembersListConverter
) : MembersListViewModel,
    ListViewModel<List<MembersListItem>, UiState<List<MembersListItem>>, MembersListUiAction, UiSingleEvent>() {
    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: MembersListUiAction): Job {
        if (LOG_FLOW_ACTION) {
            Timber.tag(TAG)
                .d("handleAction(MembersListUiAction) called: %s", action.javaClass.name)
        }
        val job = when (action) {
            is MembersListUiAction.Load -> loadMembers(
                congregationId = action.congregationId, isService = action.isService,
                byCongregation = true
            )
        }
        return job
    }

    private fun loadMembers(
        congregationId: UUID? = null, groupId: UUID? = null,
        isService: Boolean = false, byCongregation: Boolean
    ): Job {
        Timber.tag(TAG)
            .d(
                "loadMembers(...) called: congregationId = %s; groupId = %s, isService = %s; byCongregation = %s",
                congregationId, groupId, isService, byCongregation
            )
        val job = viewModelScope.launch(errorHandler) {
            useCases.getMembersUseCase.execute(
                GetMembersUseCase.Request(
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

    override fun initFieldStatesByUiModel(uiModel: List<MembersListItem>): Job? = null

    companion object {
        fun previewModel(ctx: Context) =
            object : MembersListViewModel {
                override val uiStateFlow = MutableStateFlow(UiState.Success(previewList(ctx)))
                override val singleEventFlow = Channel<UiSingleEvent>().receiveAsFlow()
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

                override fun submitAction(action: MembersListUiAction): Job? = null
            }

        fun previewList(ctx: Context) = listOf(
            MembersListItem(
                id = UUID.randomUUID(),
                memberNum = ctx.resources.getString(R.string.def_ivanov_member_num),
                fullNum = "${ctx.resources.getInteger(R.integer.def_group1)}.${
                    ctx.resources.getString(R.string.def_ivanov_member_num)
                }",
                memberFullName = "${ctx.resources.getString(R.string.def_ivanov_member_surname)} ${
                    ctx.resources.getString(
                        R.string.def_ivanov_member_name
                    )
                } ${ctx.resources.getString(R.string.def_ivanov_member_patronymic)} [${
                    ctx.resources.getString(
                        R.string.def_ivanov_member_pseudonym
                    )
                }]",
                memberShortName = "${ctx.resources.getString(R.string.def_ivanov_member_surname)} ${
                    ctx.resources.getString(
                        R.string.def_ivanov_member_name
                    )[0]
                }.${ctx.resources.getString(R.string.def_ivanov_member_patronymic)[0]}. [${
                    ctx.resources.getString(
                        R.string.def_ivanov_member_pseudonym
                    )
                }]",
                dateOfBirth = "1981-08-01T14:29:10.212+03:00".toOffsetDateTime()
            ),
            MembersListItem(
                id = UUID.randomUUID(),
                memberNum = ctx.resources.getString(R.string.def_tarasova_member_num),
                fullNum = "${ctx.resources.getInteger(R.integer.def_group2)}.${
                    ctx.resources.getString(R.string.def_tarasova_member_num)
                }",
                memberFullName = "${ctx.resources.getString(R.string.def_tarasova_member_surname)} ${
                    ctx.resources.getString(
                        R.string.def_tarasova_member_name
                    )
                } ${ctx.resources.getString(R.string.def_tarasova_member_patronymic)} [${
                    ctx.resources.getString(
                        R.string.def_tarasova_member_pseudonym
                    )
                }]",
                memberShortName = "${ctx.resources.getString(R.string.def_tarasova_member_surname)} ${
                    ctx.resources.getString(
                        R.string.def_tarasova_member_name
                    )[0]
                }.${ctx.resources.getString(R.string.def_tarasova_member_patronymic)[0]}. [${
                    ctx.resources.getString(
                        R.string.def_tarasova_member_pseudonym
                    )
                }]",
                dateOfBirth = "1979-08-01T14:29:10.212+03:00".toOffsetDateTime()
            )
        )
    }
}