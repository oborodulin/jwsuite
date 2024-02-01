package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.list

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.ListViewModel
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_ACTION
import com.oborodulin.home.common.extensions.toOffsetDateTime
import com.oborodulin.jwsuite.data_territory.R
import com.oborodulin.jwsuite.domain.usecases.territory.GetCongregationTerritoriesUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.TerritoryUseCases
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.congregation.single.CongregationViewModelImpl
import com.oborodulin.jwsuite.presentation_congregation.ui.model.GroupUi
import com.oborodulin.jwsuite.presentation_congregation.ui.model.MemberUi
import com.oborodulin.jwsuite.presentation_geo.ui.geo.locality.single.LocalityViewModelImpl
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoriesListItem
import com.oborodulin.jwsuite.presentation_territory.ui.model.converters.TerritoriesListConverter
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorycategory.single.TerritoryCategoryViewModelImpl
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

private const val TAG = "Territoring.TerritoriesListViewModelImpl"

@HiltViewModel
class TerritoriesListViewModelImpl @Inject constructor(
    private val useCases: TerritoryUseCases,
    private val listConverter: TerritoriesListConverter
) : TerritoriesListViewModel,
    ListViewModel<List<TerritoriesListItem>, UiState<List<TerritoriesListItem>>, TerritoriesListUiAction, UiSingleEvent>() {

    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: TerritoriesListUiAction): Job? {
        if (LOG_FLOW_ACTION) Timber.tag(TAG)
            .d("handleAction(TerritoriesListUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is TerritoriesListUiAction.Load -> {
                loadTerritories(action.congregationId)
            }
        }
        return job
    }

    private fun loadTerritories(congregationId: UUID?): Job {
        Timber.tag(TAG).d("loadTerritories(...) called: congregationId = %s", congregationId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.getCongregationTerritoriesUseCase.execute(
                GetCongregationTerritoriesUseCase.Request(congregationId)
            )
                .map {
                    listConverter.convert(it)
                }
                .collect {
                    submitState(it)
                }
        }
        return job
    }

    override fun initFieldStatesByUiModel(uiModel: List<TerritoriesListItem>) = null

    companion object {
        fun previewModel(ctx: Context) =
            object : TerritoriesListViewModel {
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

                override fun submitAction(action: TerritoriesListUiAction): Job? = null
                override fun handleActionJob(
                    action: () -> Unit,
                    afterAction: (CoroutineScope) -> Unit
                ) {
                }
            }

        fun previewList(ctx: Context) = listOf(
            TerritoriesListItem(
                id = UUID.randomUUID(),
                congregation = CongregationViewModelImpl.previewUiModel(ctx),
                territoryCategory = TerritoryCategoryViewModelImpl.previewUiModel(ctx),
                locality = LocalityViewModelImpl.previewUiModel(ctx),
                cardNum = ctx.resources.getString(R.string.def_territory1_card_num),
                cardLocation = ctx.resources.getString(R.string.def_territory1_card_location),
                fullCardNum = "${ctx.resources.getString(R.string.def_territory1_card_num)} ${
                    ctx.resources.getString(R.string.def_territory1_card_location)
                }",
                territoryNum = 1,
                isPrivateSector = false,
                isBusiness = false,
                isGroupMinistry = false,
                isInPerimeter = false,
                isProcessed = false,
                isActive = true,
                territoryDesc = ctx.resources.getString(R.string.def_territory1_desc),
                member = MemberUi(
                    group = GroupUi(),
                    memberNum = ctx.resources.getString(com.oborodulin.jwsuite.data_congregation.R.string.def_ivanov_member_num),
                    memberFullName = "${ctx.resources.getString(com.oborodulin.jwsuite.data_congregation.R.string.def_ivanov_member_surname)} ${
                        ctx.resources.getString(com.oborodulin.jwsuite.data_congregation.R.string.def_ivanov_member_name)
                    } ${ctx.resources.getString(com.oborodulin.jwsuite.data_congregation.R.string.def_ivanov_member_patronymic)} [${
                        ctx.resources.getString(
                            com.oborodulin.jwsuite.data_congregation.R.string.def_ivanov_member_pseudonym
                        )
                    }]",
                    memberShortName = "${ctx.resources.getString(com.oborodulin.jwsuite.data_congregation.R.string.def_ivanov_member_surname)} ${
                        ctx.resources.getString(com.oborodulin.jwsuite.data_congregation.R.string.def_ivanov_member_name)[0]
                    }.${ctx.resources.getString(com.oborodulin.jwsuite.data_congregation.R.string.def_ivanov_member_patronymic)[0]}. [${
                        ctx.resources.getString(com.oborodulin.jwsuite.data_congregation.R.string.def_ivanov_member_pseudonym)
                    }]",
                    pseudonym = ctx.resources.getString(com.oborodulin.jwsuite.data_congregation.R.string.def_ivanov_member_pseudonym),
                    dateOfBirth = "1981-08-01T14:29:10.212+03:00".toOffsetDateTime()
                )
            ),
            TerritoriesListItem(
                id = UUID.randomUUID(),
                congregation = CongregationViewModelImpl.previewUiModel(ctx),
                territoryCategory = TerritoryCategoryViewModelImpl.previewUiModel(ctx),
                locality = LocalityViewModelImpl.previewUiModel(ctx),
                cardNum = ctx.resources.getString(R.string.def_territory2_card_num),
                cardLocation = ctx.resources.getString(R.string.def_territory2_card_location),
                fullCardNum = "${ctx.resources.getString(R.string.def_territory2_card_num)} ${
                    ctx.resources.getString(R.string.def_territory2_card_location)
                }",
                territoryNum = 2,
                isPrivateSector = false,
                isBusiness = false,
                isGroupMinistry = true,
                isInPerimeter = true,
                isProcessed = false,
                isActive = true,
                territoryDesc = ctx.resources.getString(R.string.def_territory2_desc)
            ),
            TerritoriesListItem(
                id = UUID.randomUUID(),
                congregation = CongregationViewModelImpl.previewUiModel(ctx),
                territoryCategory = TerritoryCategoryViewModelImpl.previewUiModel(ctx),
                locality = LocalityViewModelImpl.previewUiModel(ctx),
                cardNum = ctx.resources.getString(R.string.def_territory3_card_num),
                cardLocation = ctx.resources.getString(R.string.def_territory3_card_location),
                fullCardNum = "${ctx.resources.getString(R.string.def_territory3_card_num)} ${
                    ctx.resources.getString(R.string.def_territory3_card_location)
                }",
                territoryNum = 3,
                isPrivateSector = false,
                isBusiness = true,
                isGroupMinistry = false,
                isInPerimeter = false,
                isProcessed = false,
                isActive = true,
                territoryDesc = ctx.resources.getString(R.string.def_territory3_desc)
            ),
            TerritoriesListItem(
                id = UUID.randomUUID(),
                congregation = CongregationViewModelImpl.previewUiModel(ctx),
                territoryCategory = TerritoryCategoryViewModelImpl.previewUiModel(ctx),
                locality = LocalityViewModelImpl.previewUiModel(ctx),
                cardNum = ctx.resources.getString(R.string.def_territory4_card_num),
                cardLocation = ctx.resources.getString(R.string.def_territory4_card_location),
                fullCardNum = "${ctx.resources.getString(R.string.def_territory4_card_num)} ${
                    ctx.resources.getString(R.string.def_territory4_card_location)
                }",
                territoryNum = 4,
                isPrivateSector = true,
                isBusiness = true,
                isGroupMinistry = false,
                isInPerimeter = false,
                isProcessed = false,
                isActive = true,
                territoryDesc = ctx.resources.getString(R.string.def_territory4_desc)
            )
        )
    }
}