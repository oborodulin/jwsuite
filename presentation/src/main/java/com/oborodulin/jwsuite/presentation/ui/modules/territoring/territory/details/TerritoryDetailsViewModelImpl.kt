package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.details

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.MviViewModel
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.home.common.util.Utils
import com.oborodulin.jwsuite.data.R
import com.oborodulin.jwsuite.domain.usecases.member.DeleteMemberUseCase
import com.oborodulin.jwsuite.domain.usecases.member.GetMembersUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.TerritoryUseCases
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.GroupUi
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.MembersListItem
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.converters.TerritoriesListConverter
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

private const val TAG = "Territoring.TerritoryDetailsViewModelImpl"

@HiltViewModel
class TerritoryDetailsViewModelImpl @Inject constructor(
    private val useCases: TerritoryUseCases,
    private val converter: TerritoriesListConverter
) : TerritoryDetailsViewModel,
    MviViewModel<List<MembersListItem>, UiState<List<MembersListItem>>, TerritoryDetailsUiAction, UiSingleEvent>() {

    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: TerritoryDetailsUiAction): Job {
        Timber.tag(TAG)
            .d("handleAction(MembersListUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is TerritoryDetailsUiAction.Load -> {
                loadTerritoryDetail(territoryId = action.territoryId)
            }
        }
        return job
    }

    private fun loadTerritoryDetail(territoryId: UUID): Job {
        Timber.tag(TAG).d("loadTerritoryDetails() called: territoryId = %s", territoryId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.getMembersUseCase.execute(
                GetMembersUseCase.Request(
                    congregationId = territoryId, groupId = groupId,
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
        Timber.tag(TAG).d("deleteMember() called: memberId = %s", memberId.toString())
        val job = viewModelScope.launch(errorHandler) {
            useCases.deleteMemberUseCase.execute(DeleteMemberUseCase.Request(memberId)).collect {}
        }
        return job
    }

    override fun initFieldStatesByUiModel(uiModel: Any): Job? = null

    companion object {
        fun previewModel(ctx: Context) =
            object : TerritoryDetailsViewModel {
                override val uiStateFlow = MutableStateFlow(UiState.Success(previewList(ctx)))
                override val singleEventFlow = Channel<TerritoryDetailsUiSingleEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()

                override val searchText = MutableStateFlow(TextFieldValue(""))
                override val isSearching = MutableStateFlow(false)
                override fun onSearchTextChange(text: TextFieldValue) {}

                override fun singleSelectItem(selectedItem: ListItemModel) {}
                override fun handleActionJob(action: () -> Unit, afterAction: () -> Unit) {}
                override fun submitAction(action: TerritoryDetailsUiAction): Job? = null
            }

        fun previewList(ctx: Context) = listOf(
            MembersListItem(
                id = UUID.randomUUID(),
                group = GroupUi(),
                memberNum = ctx.resources.getString(R.string.def_ivanov_member_num),
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
                dateOfBirth = Utils.toOffsetDateTime("1981-08-01T14:29:10.212+03:00")
            ),
            MembersListItem(
                id = UUID.randomUUID(),
                group = GroupUi(),
                memberNum = ctx.resources.getString(R.string.def_tarasova_member_num),
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
                dateOfBirth = Utils.toOffsetDateTime("1979-08-01T14:29:10.212+03:00")
            )
        )
    }
}