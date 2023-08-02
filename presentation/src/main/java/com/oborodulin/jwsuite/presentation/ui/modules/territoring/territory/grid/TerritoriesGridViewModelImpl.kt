package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.grid

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.ui.components.field.util.InputError
import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.components.field.util.ScreenEvent
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.DialogSingleViewModel
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.home.common.util.Constants
import com.oborodulin.home.common.util.Utils
import com.oborodulin.jwsuite.data.R
import com.oborodulin.jwsuite.domain.usecases.territory.DeleteTerritoryUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.GetTerritoriesUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.HandOutTerritoriesUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.TerritoryUseCases
import com.oborodulin.jwsuite.domain.util.TerritoryLocationType
import com.oborodulin.jwsuite.domain.util.TerritoryProcessType
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryInput
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.congregation.single.CongregationViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.GroupUi
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.MemberUi
import com.oborodulin.jwsuite.presentation.ui.modules.geo.locality.single.LocalityViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.TerritoriesListItem
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.converters.TerritoriesListConverter
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.territorycategory.single.TerritoryCategoryViewModelImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID
import javax.inject.Inject

// ic_hand_map.png - https://www.flaticon.com/free-icon/hand_6242467?term=hand+map&page=1&position=9&origin=search&related_id=6242467

private const val TAG = "Territoring.TerritoriesListViewModelImpl"

@HiltViewModel
class TerritoriesGridViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val useCases: TerritoryUseCases,
    private val listConverter: TerritoriesListConverter
) : TerritoriesGridViewModel,
    DialogSingleViewModel<List<TerritoriesListItem>, UiState<List<TerritoriesListItem>>, TerritoriesGridUiAction, TerritoriesGridUiSingleEvent, TerritoriesFields, InputWrapper>(
        state,
        //TerritoriesFields.TERRITORY_MEMBER
    ) {
    override val member: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(TerritoriesFields.TERRITORY_MEMBER.name, InputListItemWrapper())
    }
    override val receivingDate: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            TerritoriesFields.TERRITORY_RECEIVING_DATE.name, InputWrapper(
                OffsetDateTime.now().format(
                    DateTimeFormatter.ofPattern(Constants.APP_OFFSET_DATE_TIME)
                )
            )
        )
    }

    override val areInputsValid =
        combine(
            member,
            uiStateFlow
        ) { member, uiState ->
            member.errorId == null && uiState is UiState.Success && uiState.data.any { it.isChecked }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: TerritoriesGridUiAction): Job? {
        Timber.tag(TAG)
            .d("handleAction(TerritoriesListUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is TerritoriesGridUiAction.Load -> {
                loadTerritories(
                    action.congregationId,
                    action.territoryProcessType, action.territoryLocationType,
                    action.locationId, action.isPrivateSector
                )
            }

            is TerritoriesGridUiAction.EditTerritory -> {
                submitSingleEvent(
                    TerritoriesGridUiSingleEvent.OpenTerritoryScreen(
                        NavRoutes.Territory.routeForTerritory(TerritoryInput(action.territoryId))
                    )
                )
            }

            is TerritoriesGridUiAction.DeleteTerritory -> {
                deleteTerritory(action.territoryId)
            }

            is TerritoriesGridUiAction.HandOutConfirmation -> {
                setDialogTitleResId(com.oborodulin.jwsuite.presentation.R.string.territory_hand_out_subheader)
                submitSingleEvent(
                    TerritoriesGridUiSingleEvent.OpenHandOutTerritoriesConfirmationScreen(
                        NavRoutes.HandOutTerritoriesConfirmation.routeForHandOutTerritoriesConfirmation()
                    )
                )
            }

            is TerritoriesGridUiAction.HandOut -> {
                handOutTerritories()
            }

            is TerritoriesGridUiAction.Process -> {
                processTerritories()
            }
        }
        return job
    }

    private fun loadTerritories(
        congregationId: UUID?,
        territoryProcessType: TerritoryProcessType, territoryLocationType: TerritoryLocationType,
        districtId: UUID? = null, isPrivateSector: Boolean = false
    ): Job {
        Timber.tag(TAG).d("loadTerritories(...) called: congregationId = %s", congregationId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.getTerritoriesUseCase.execute(
                GetTerritoriesUseCase.Request(
                    congregationId, territoryProcessType, territoryLocationType,
                    districtId, isPrivateSector
                )
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

    private fun deleteTerritory(territoryId: UUID): Job {
        Timber.tag(TAG)
            .d("deleteTerritory() called: territoryId = %s", territoryId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.deleteTerritoryUseCase.execute(DeleteTerritoryUseCase.Request(territoryId))
                .collect {}
        }
        return job
    }

    private fun handOutTerritories(): Job {
        val job = viewModelScope.launch(errorHandler) {
            member.value.item?.itemId?.let { memberId ->
                useCases.handOutTerritoriesUseCase.execute(
                    HandOutTerritoriesUseCase.Request(
                        memberId,
                        (uiStateFlow.value as UiState.Success<List<TerritoriesListItem>>).data.filter { it.isChecked }
                            .map { it.id },
                        DateTimeFormatter.ofPattern(Constants.APP_OFFSET_DATE_TIME)
                            .parse(receivingDate.value.value, OffsetDateTime::from)
                    )
                ).collect {}
            }
        }
        return job
    }

    private fun processTerritories(): Job {
        val job = viewModelScope.launch(errorHandler) {
            useCases.deleteTerritoryUseCase.execute(DeleteTerritoryUseCase.Request(UUID.randomUUID()))
                .collect {}
        }
        return job
    }

    override fun stateInputFields() = enumValues<TerritoriesFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: Any): Job? = null

    override suspend fun observeInputEvents() {
        Timber.tag(TAG).d("observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is TerritoriesInputEvent.Member ->
                        when (TerritoriesInputValidator.Member.errorIdOrNull(event.input.headline)) {
                            null -> setStateValue(
                                TerritoriesFields.TERRITORY_MEMBER, member, event.input,
                                true
                            )

                            else -> setStateValue(
                                TerritoriesFields.TERRITORY_MEMBER, member, event.input
                            )
                        }
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is TerritoriesInputEvent.Member ->
                        setStateValue(
                            TerritoriesFields.TERRITORY_MEMBER, member,
                            TerritoriesInputValidator.Member.errorIdOrNull(event.input.headline)
                        )
                }
            }
    }

    override fun performValidation() {}
    override fun getInputErrorsOrNull(): List<InputError>? {
        Timber.tag(TAG).d("getInputErrorsOrNull() called")
        val inputErrors: MutableList<InputError> = mutableListOf()
        TerritoriesInputValidator.Member.errorIdOrNull(member.value.item?.headline)?.let {
            inputErrors.add(
                InputError(
                    fieldName = TerritoriesFields.TERRITORY_MEMBER.name,
                    errorId = it
                )
            )
        }
        return if (inputErrors.isEmpty()) null else inputErrors
    }

    override fun displayInputErrors(inputErrors: List<InputError>) {
        Timber.tag(TAG)
            .d("displayInputErrors() called: inputErrors.count = %d", inputErrors.size)
        for (error in inputErrors) {
            state[error.fieldName] = when (error.fieldName) {
                TerritoriesFields.TERRITORY_MEMBER.name -> member.value.copy(errorId = error.errorId)
                else -> null
            }
        }
    }

    companion object {
        fun previewModel(ctx: Context) =
            object : TerritoriesGridViewModel {
                override val dialogTitleResId =
                    MutableStateFlow(com.oborodulin.home.common.R.string.preview_blank_title)
                override val savedListItem = MutableStateFlow(ListItemModel())
                override val showDialog = MutableStateFlow(true)

                override val uiStateFlow = MutableStateFlow(UiState.Success(previewList(ctx)))
                override val singleEventFlow =
                    Channel<TerritoriesGridUiSingleEvent>().receiveAsFlow()
                override val events = Channel<ScreenEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()

                override val searchText = MutableStateFlow(TextFieldValue(""))
                override val isSearching = MutableStateFlow(false)
                override fun onSearchTextChange(text: TextFieldValue) {}

                override val member = MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val receivingDate = MutableStateFlow(InputWrapper())

                override val areInputsValid = MutableStateFlow(true)

                override fun handleActionJob(action: () -> Unit, afterAction: () -> Unit) {}
                override fun submitAction(action: TerritoriesGridUiAction): Job? = null
                override fun onTextFieldEntered(inputEvent: Inputable) {}

                override fun onTextFieldFocusChanged(
                    focusedField: TerritoriesFields, isFocused: Boolean
                ) {
                }

                override fun moveFocusImeAction() {}
                override fun onContinueClick(onSuccess: () -> Unit) {}

                override fun setDialogTitleResId(dialogTitleResId: Int) {}
                override fun setSavedListItem(savedListItem: ListItemModel) {}
                override fun onOpenDialogClicked() {}
                override fun onDialogConfirm(onConfirm: () -> Unit) {}
                override fun onDialogDismiss(onDismiss: () -> Unit) {}
            }

        fun previewList(ctx: Context) = listOf(
            TerritoriesListItem(
                id = UUID.randomUUID(),
                congregation = CongregationViewModelImpl.previewUiModel(ctx),
                territoryCategory = TerritoryCategoryViewModelImpl.previewUiModel(ctx),
                locality = LocalityViewModelImpl.previewUiModel(ctx),
                cardNum = ctx.resources.getString(R.string.def_territory1_card_num),
                cardLocation = ctx.resources.getString(R.string.def_territory1_card_location),
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
                    pseudonym = ctx.resources.getString(R.string.def_ivanov_member_pseudonym),
                    dateOfBirth = Utils.toOffsetDateTime("1981-08-01T14:29:10.212+03:00")
                )
            ),
            TerritoriesListItem(
                id = UUID.randomUUID(),
                congregation = CongregationViewModelImpl.previewUiModel(ctx),
                territoryCategory = TerritoryCategoryViewModelImpl.previewUiModel(ctx),
                locality = LocalityViewModelImpl.previewUiModel(ctx),
                cardNum = ctx.resources.getString(R.string.def_territory2_card_num),
                cardLocation = ctx.resources.getString(R.string.def_territory2_card_location),
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