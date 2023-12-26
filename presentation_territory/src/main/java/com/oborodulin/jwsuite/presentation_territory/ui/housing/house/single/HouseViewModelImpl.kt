package com.oborodulin.jwsuite.presentation_territory.ui.housing.house.single

import android.content.Context
import androidx.annotation.ArrayRes
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.domain.entities.Result
import com.oborodulin.home.common.ui.components.field.util.InputError
import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.components.field.util.ScreenEvent
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.DialogViewModel
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_ACTION
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_INPUT
import com.oborodulin.home.common.util.ResourcesHelper
import com.oborodulin.home.common.util.toUUIDOrNull
import com.oborodulin.jwsuite.domain.types.BuildingType
import com.oborodulin.jwsuite.domain.usecases.house.GetHouseUseCase
import com.oborodulin.jwsuite.domain.usecases.house.HouseUseCases
import com.oborodulin.jwsuite.domain.usecases.house.SaveHouseUseCase
import com.oborodulin.jwsuite.presentation_geo.ui.geo.localitydistrict.single.LocalityDistrictViewModelImpl
import com.oborodulin.jwsuite.presentation_geo.ui.geo.microdistrict.single.MicrodistrictViewModelImpl
import com.oborodulin.jwsuite.presentation_geo.ui.geo.street.single.StreetViewModelImpl
import com.oborodulin.jwsuite.presentation_geo.ui.model.LocalityDistrictUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.MicrodistrictUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.StreetUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.StreetsListItem
import com.oborodulin.jwsuite.presentation_geo.ui.model.toListItemModel
import com.oborodulin.jwsuite.presentation_geo.ui.model.toStreetsListItem
import com.oborodulin.jwsuite.presentation_territory.ui.model.HouseUi
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryUi
import com.oborodulin.jwsuite.presentation_territory.ui.model.converters.HouseConverter
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.house.HouseToHousesListItemMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.house.HouseUiToHouseMapper
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single.TerritoryViewModelImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

private const val TAG = "Territoring.HouseViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class HouseViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val resHelper: ResourcesHelper,
    private val useCases: HouseUseCases,
    private val converter: HouseConverter,
    private val houseUiMapper: HouseUiToHouseMapper,
    private val houseMapper: HouseToHousesListItemMapper
) : HouseViewModel,
    DialogViewModel<HouseUi, UiState<HouseUi>, HouseUiAction, UiSingleEvent, HouseFields, InputWrapper>(
        state, HouseFields.HOUSE_ID.name, HouseFields.HOUSE_TERRITORY
    ) {
    private val _buildingTypes: MutableStateFlow<MutableMap<BuildingType, String>> =
        MutableStateFlow(mutableMapOf())
    override val buildingTypes = _buildingTypes.asStateFlow()

    override val locality: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(HouseFields.HOUSE_LOCALITY.name, InputListItemWrapper())
    }
    override val street: StateFlow<InputListItemWrapper<StreetsListItem>> by lazy {
        state.getStateFlow(HouseFields.HOUSE_STREET.name, InputListItemWrapper())
    }
    override val localityDistrict: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(HouseFields.HOUSE_LOCALITY_DISTRICT.name, InputListItemWrapper())
    }
    override val microdistrict: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(HouseFields.HOUSE_MICRODISTRICT.name, InputListItemWrapper())
    }
    override val territory: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(HouseFields.HOUSE_TERRITORY.name, InputListItemWrapper())
    }
    override val zipCode: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(HouseFields.HOUSE_ZIP_CODE.name, InputWrapper())
    }
    override val houseNum: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(HouseFields.HOUSE_NUM.name, InputWrapper())
    }
    override val houseLetter: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(HouseFields.HOUSE_LETTER.name, InputWrapper())
    }
    override val buildingNum: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(HouseFields.HOUSE_BUILDING_NUM.name, InputWrapper())
    }
    override val buildingType: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(HouseFields.HOUSE_BUILDING_TYPE.name, InputWrapper())
    }
    override val isBusiness: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(HouseFields.HOUSE_IS_BUSINESS.name, InputWrapper())
    }
    override val isSecurity: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(HouseFields.HOUSE_IS_SECURITY.name, InputWrapper())
    }
    override val isIntercom: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(HouseFields.HOUSE_IS_INTERCOM.name, InputWrapper())
    }
    override val isResidential: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(HouseFields.HOUSE_IS_RESIDENTIAL.name, InputWrapper())
    }
    override val houseEntrancesQty: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(HouseFields.HOUSE_ENTRANCES_QTY.name, InputWrapper())
    }
    override val floorsByEntrance: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(HouseFields.HOUSE_FLOORS_BY_ENTRANCE.name, InputWrapper())
    }
    override val roomsByHouseFloor: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(HouseFields.HOUSE_ROOMS_BY_FLOOR.name, InputWrapper())
    }
    override val estimatedRooms: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(HouseFields.HOUSE_ESTIMATED_ROOMS.name, InputWrapper())
    }
    override val isForeignLanguage: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(HouseFields.HOUSE_IS_FOREIGN_LANGUAGE.name, InputWrapper())
    }
    override val isPrivateSector: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(HouseFields.HOUSE_IS_PRIVATE_SECTOR.name, InputWrapper())
    }
    override val houseDesc: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(HouseFields.HOUSE_DESC.name, InputWrapper())
    }

    override val areInputsValid = combine(street, houseNum, buildingType)
    { street, houseNum, buildingType ->
        street.errorId == null && houseNum.errorId == null && buildingType.errorId == null
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    init {
        initLocalityTypes(com.oborodulin.jwsuite.domain.R.array.building_types)
    }

    private fun initLocalityTypes(@ArrayRes arrayId: Int) {
        val resArray = resHelper.appContext.resources.getStringArray(arrayId)
        for (type in BuildingType.entries) _buildingTypes.value[type] = resArray[type.ordinal]
    }

    override fun initState(): UiState<HouseUi> = UiState.Loading

    override suspend fun handleAction(action: HouseUiAction): Job {
        if (LOG_FLOW_ACTION) Timber.tag(TAG)
            .d("handleAction(HouseUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is HouseUiAction.Load -> when (action.houseId) {
                null -> {
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation_territory.R.string.house_new_subheader)
                    submitState(UiState.Success(HouseUi()))
                }

                else -> {
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation_territory.R.string.house_subheader)
                    loadHouseUi(action.houseId)
                }
            }

            is HouseUiAction.Save -> saveHouse()
        }
        return job
    }

    private fun loadHouseUi(houseId: UUID): Job {
        Timber.tag(TAG).d("loadHouse(UUID) called: %s", houseId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.getHouseUseCase.execute(GetHouseUseCase.Request(houseId)).map {
                converter.convert(it)
            }.collect {
                submitState(it)
            }
        }
        return job
    }

    private fun saveHouse(): Job {
        val streetUi = StreetUi()
        streetUi.id = street.value.item?.itemId
        val localityDistrictUi = LocalityDistrictUi()
        localityDistrictUi.id = localityDistrict.value.item?.itemId
        val microdistrictUi = MicrodistrictUi()
        microdistrictUi.id = microdistrict.value.item?.itemId
        val territoryUi = TerritoryUi()
        territoryUi.id = territory.value.item?.itemId

        val houseUi = HouseUi(
            street = streetUi,
            localityDistrict = localityDistrictUi,
            microdistrict = microdistrictUi,
            territory = territoryUi,
            zipCode = zipCode.value.value.ifEmpty { null },
            houseNum = houseNum.value.value.toInt(),
            houseLetter = houseLetter.value.value.ifEmpty { null },
            buildingNum = buildingNum.value.value.toIntOrNull(),
            buildingType = BuildingType.valueOf(buildingType.value.value),
            isBusiness = isBusiness.value.value.toBoolean(),
            isSecurity = isSecurity.value.value.toBoolean(),
            isIntercom = isIntercom.value.value.toBooleanStrictOrNull(),
            isResidential = isResidential.value.value.toBoolean(),
            houseEntrancesQty = houseEntrancesQty.value.value.toIntOrNull(),
            floorsByEntrance = floorsByEntrance.value.value.toIntOrNull(),
            roomsByHouseFloor = roomsByHouseFloor.value.value.toIntOrNull(),
            estimatedRooms = estimatedRooms.value.value.toIntOrNull(),
            isForeignLanguage = isForeignLanguage.value.value.toBoolean(),
            isPrivateSector = isPrivateSector.value.value.toBoolean(),
            houseDesc = houseDesc.value.value.ifEmpty { null }
        )
        houseUi.id = id.value.value.toUUIDOrNull()
        Timber.tag(TAG).d(
            "saveHouse() called: UI model %s; streetUi.id = %s",
            houseUi,
            streetUi.id
        )
        val job = viewModelScope.launch(errorHandler) {
            useCases.saveHouseUseCase.execute(SaveHouseUseCase.Request(houseUiMapper.map(houseUi)))
                .collect {
                    Timber.tag(TAG).d("saveHouse() collect: %s", it)
                    if (it is Result.Success) {
                        setStateValue(HouseFields.HOUSE_ID, id, it.data.house.id.toString(), true)
                        setSavedListItem(houseMapper.map(it.data.house))
                    }
                }
        }
        return job
    }

    override fun stateInputFields() = enumValues<HouseFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: HouseUi): Job? {
        super.initFieldStatesByUiModel(uiModel)
        Timber.tag(TAG)
            .d(
                "initFieldStatesByUiModel(HouseUi) called: uiModel = %s",
                uiModel
            )
        uiModel.id?.let { initStateValue(HouseFields.HOUSE_ID, id, it.toString()) }
        initStateValue(
            HouseFields.HOUSE_LOCALITY, locality, uiModel.street.locality.toListItemModel()
        )
        initStateValue(HouseFields.HOUSE_STREET, street, uiModel.street.toStreetsListItem())
        initStateValue(
            HouseFields.HOUSE_LOCALITY_DISTRICT, localityDistrict,
            ListItemModel(
                uiModel.localityDistrict?.id, uiModel.localityDistrict?.districtName.orEmpty()
            )
        )
        initStateValue(
            HouseFields.HOUSE_MICRODISTRICT, microdistrict,
            ListItemModel(
                uiModel.microdistrict?.id, uiModel.microdistrict?.microdistrictName.orEmpty()
            )
        )
        initStateValue(
            HouseFields.HOUSE_TERRITORY, territory,
            ListItemModel(uiModel.territory?.id, uiModel.territory?.fullCardNum.orEmpty())
        )
        initStateValue(HouseFields.HOUSE_ZIP_CODE, zipCode, uiModel.zipCode.orEmpty())
        initStateValue(HouseFields.HOUSE_NUM, houseNum, uiModel.houseNum?.toString().orEmpty())
        initStateValue(HouseFields.HOUSE_LETTER, houseLetter, uiModel.houseLetter.orEmpty())
        initStateValue(
            HouseFields.HOUSE_BUILDING_NUM, buildingNum, uiModel.buildingNum?.toString().orEmpty()
        )
        initStateValue(HouseFields.HOUSE_BUILDING_TYPE, buildingType, uiModel.buildingType.name)
        initStateValue(HouseFields.HOUSE_IS_BUSINESS, isBusiness, uiModel.isBusiness.toString())
        initStateValue(HouseFields.HOUSE_IS_SECURITY, isSecurity, uiModel.isSecurity.toString())
        initStateValue(
            HouseFields.HOUSE_IS_INTERCOM, isIntercom, uiModel.isIntercom?.toString().orEmpty()
        )
        initStateValue(
            HouseFields.HOUSE_IS_RESIDENTIAL, isResidential, uiModel.isResidential.toString()
        )
        initStateValue(
            HouseFields.HOUSE_ENTRANCES_QTY, houseEntrancesQty,
            uiModel.houseEntrancesQty?.toString().orEmpty()
        )
        initStateValue(
            HouseFields.HOUSE_FLOORS_BY_ENTRANCE, floorsByEntrance,
            uiModel.floorsByEntrance?.toString().orEmpty()
        )
        initStateValue(
            HouseFields.HOUSE_ROOMS_BY_FLOOR, roomsByHouseFloor,
            uiModel.roomsByHouseFloor?.toString().orEmpty()
        )
        initStateValue(
            HouseFields.HOUSE_ESTIMATED_ROOMS, estimatedRooms,
            uiModel.estimatedRooms?.toString().orEmpty()
        )
        initStateValue(
            HouseFields.HOUSE_IS_FOREIGN_LANGUAGE, isForeignLanguage,
            uiModel.isForeignLanguage.toString()
        )
        initStateValue(
            HouseFields.HOUSE_IS_PRIVATE_SECTOR, isPrivateSector, uiModel.isPrivateSector.toString()
        )
        initStateValue(HouseFields.HOUSE_DESC, houseDesc, uiModel.houseDesc.orEmpty())
        return null
    }

    override suspend fun observeInputEvents() {
        if (LOG_FLOW_INPUT) Timber.tag(TAG).d("IF# observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is HouseInputEvent.Locality -> setStateValue(
                        HouseFields.HOUSE_LOCALITY, locality, event.input,
                        HouseInputValidator.Locality.isValid(event.input.headline)
                    )

                    is HouseInputEvent.Street -> setStateValue(
                        HouseFields.HOUSE_STREET, street, event.input,
                        HouseInputValidator.Street.isValid(event.input.headline)
                    )

                    is HouseInputEvent.LocalityDistrict -> setStateValue(
                        HouseFields.HOUSE_LOCALITY_DISTRICT, localityDistrict, event.input,
                        true
                    )

                    is HouseInputEvent.Microdistrict -> setStateValue(
                        HouseFields.HOUSE_MICRODISTRICT, microdistrict, event.input, true
                    )

                    is HouseInputEvent.Territory ->
                        setStateValue(HouseFields.HOUSE_TERRITORY, territory, event.input, true)

                    is HouseInputEvent.ZipCode ->
                        setStateValue(HouseFields.HOUSE_ZIP_CODE, zipCode, event.input, true)

                    is HouseInputEvent.HouseNum -> setStateValue(
                        HouseFields.HOUSE_NUM, houseNum, event.input.toString(),
                        HouseInputValidator.HouseNum.isValid(event.input.toString())
                    )

                    is HouseInputEvent.HouseLetter ->
                        setStateValue(HouseFields.HOUSE_LETTER, houseLetter, event.input, true)

                    is HouseInputEvent.BuildingNum -> setStateValue(
                        HouseFields.HOUSE_BUILDING_NUM, buildingNum,
                        event.input?.toString().orEmpty(), true
                    )

                    is HouseInputEvent.BuildingType -> setStateValue(
                        HouseFields.HOUSE_BUILDING_TYPE, buildingType, event.input.name,
                        HouseInputValidator.BuildingType.isValid(event.input.name)
                    )

                    is HouseInputEvent.IsBusiness -> setStateValue(
                        HouseFields.HOUSE_IS_BUSINESS, isBusiness, event.input.toString(), true
                    )

                    is HouseInputEvent.IsSecurity -> setStateValue(
                        HouseFields.HOUSE_IS_SECURITY, isSecurity, event.input.toString(), true
                    )

                    is HouseInputEvent.IsIntercom -> setStateValue(
                        HouseFields.HOUSE_IS_INTERCOM, isIntercom,
                        event.input?.toString().orEmpty(), true
                    )

                    is HouseInputEvent.IsResidential -> setStateValue(
                        HouseFields.HOUSE_IS_RESIDENTIAL, isResidential, event.input.toString(),
                        true
                    )

                    is HouseInputEvent.HouseEntrancesQty -> setStateValue(
                        HouseFields.HOUSE_ENTRANCES_QTY, houseEntrancesQty,
                        event.input?.toString().orEmpty(), true
                    )

                    is HouseInputEvent.FloorsByEntrance -> setStateValue(
                        HouseFields.HOUSE_FLOORS_BY_ENTRANCE, floorsByEntrance,
                        event.input?.toString().orEmpty(), true
                    )

                    is HouseInputEvent.RoomsByHouseFloor -> setStateValue(
                        HouseFields.HOUSE_ROOMS_BY_FLOOR, roomsByHouseFloor,
                        event.input?.toString().orEmpty(), true
                    )

                    is HouseInputEvent.EstimatedRooms -> setStateValue(
                        HouseFields.HOUSE_ESTIMATED_ROOMS, estimatedRooms,
                        event.input?.toString().orEmpty(), true
                    )

                    is HouseInputEvent.IsForeignLanguage -> setStateValue(
                        HouseFields.HOUSE_IS_FOREIGN_LANGUAGE, isForeignLanguage,
                        event.input.toString(), true
                    )

                    is HouseInputEvent.IsPrivateSector -> setStateValue(
                        HouseFields.HOUSE_IS_PRIVATE_SECTOR,
                        isPrivateSector, event.input.toString(), true
                    )

                    is HouseInputEvent.HouseDesc -> setStateValue(
                        HouseFields.HOUSE_DESC, houseDesc, event.input.orEmpty(), true
                    )
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is HouseInputEvent.Locality -> setStateValue(
                        HouseFields.HOUSE_LOCALITY, locality,
                        HouseInputValidator.Locality.errorIdOrNull(event.input.headline)
                    )

                    is HouseInputEvent.Street -> setStateValue(
                        HouseFields.HOUSE_STREET, street,
                        HouseInputValidator.Street.errorIdOrNull(event.input.headline)
                    )

                    is HouseInputEvent.LocalityDistrict ->
                        setStateValue(HouseFields.HOUSE_LOCALITY_DISTRICT, localityDistrict, null)

                    is HouseInputEvent.Microdistrict ->
                        setStateValue(HouseFields.HOUSE_MICRODISTRICT, microdistrict, null)

                    is HouseInputEvent.Territory ->
                        setStateValue(HouseFields.HOUSE_TERRITORY, territory, null)

                    is HouseInputEvent.ZipCode ->
                        setStateValue(HouseFields.HOUSE_ZIP_CODE, zipCode, null)

                    is HouseInputEvent.HouseNum -> setStateValue(
                        HouseFields.HOUSE_NUM, houseNum,
                        HouseInputValidator.HouseNum.errorIdOrNull(
                            event.input?.toString().orEmpty()
                        )
                    )

                    is HouseInputEvent.HouseLetter ->
                        setStateValue(HouseFields.HOUSE_LETTER, houseLetter, null)

                    is HouseInputEvent.BuildingNum ->
                        setStateValue(HouseFields.HOUSE_BUILDING_NUM, buildingNum, null)

                    is HouseInputEvent.BuildingType -> setStateValue(
                        HouseFields.HOUSE_BUILDING_TYPE, buildingType,
                        HouseInputValidator.BuildingType.errorIdOrNull(event.input.name)
                    )

                    is HouseInputEvent.IsBusiness ->
                        setStateValue(HouseFields.HOUSE_IS_BUSINESS, isBusiness, null)

                    is HouseInputEvent.IsSecurity ->
                        setStateValue(HouseFields.HOUSE_IS_SECURITY, isSecurity, null)

                    is HouseInputEvent.IsIntercom ->
                        setStateValue(HouseFields.HOUSE_IS_INTERCOM, isIntercom, null)

                    is HouseInputEvent.IsResidential ->
                        setStateValue(HouseFields.HOUSE_IS_RESIDENTIAL, isResidential, null)

                    is HouseInputEvent.HouseEntrancesQty ->
                        setStateValue(HouseFields.HOUSE_ENTRANCES_QTY, houseEntrancesQty, null)

                    is HouseInputEvent.FloorsByEntrance ->
                        setStateValue(HouseFields.HOUSE_FLOORS_BY_ENTRANCE, floorsByEntrance, null)

                    is HouseInputEvent.RoomsByHouseFloor ->
                        setStateValue(HouseFields.HOUSE_ROOMS_BY_FLOOR, roomsByHouseFloor, null)

                    is HouseInputEvent.EstimatedRooms ->
                        setStateValue(HouseFields.HOUSE_ESTIMATED_ROOMS, estimatedRooms, null)

                    is HouseInputEvent.IsForeignLanguage -> setStateValue(
                        HouseFields.HOUSE_IS_FOREIGN_LANGUAGE, isForeignLanguage, null
                    )

                    is HouseInputEvent.IsPrivateSector ->
                        setStateValue(HouseFields.HOUSE_IS_PRIVATE_SECTOR, isPrivateSector, null)

                    is HouseInputEvent.HouseDesc ->
                        setStateValue(HouseFields.HOUSE_DESC, houseDesc, null)
                }
            }
    }

    override fun performValidation() {}
    override fun getInputErrorsOrNull(): List<InputError>? {
        if (LOG_FLOW_INPUT) Timber.tag(TAG).d("#IF getInputErrorsOrNull() called")
        val inputErrors: MutableList<InputError> = mutableListOf()
        HouseInputValidator.Locality.errorIdOrNull(locality.value.item?.headline)?.let {
            inputErrors.add(InputError(fieldName = HouseFields.HOUSE_LOCALITY.name, errorId = it))
        }
        HouseInputValidator.Street.errorIdOrNull(street.value.item?.headline)?.let {
            inputErrors.add(InputError(fieldName = HouseFields.HOUSE_STREET.name, errorId = it))
        }
        HouseInputValidator.HouseNum.errorIdOrNull(houseNum.value.value)?.let {
            inputErrors.add(InputError(fieldName = HouseFields.HOUSE_NUM.name, errorId = it))
        }
        HouseInputValidator.BuildingType.errorIdOrNull(buildingType.value.value)?.let {
            inputErrors.add(
                InputError(fieldName = HouseFields.HOUSE_BUILDING_TYPE.name, errorId = it)
            )
        }
        return inputErrors.ifEmpty { null }
    }

    override fun displayInputErrors(inputErrors: List<InputError>) {
        if (LOG_FLOW_INPUT) Timber.tag(TAG)
            .d("#IF displayInputErrors() called: inputErrors.count = %d", inputErrors.size)
        for (error in inputErrors) {
            state[error.fieldName] = when (HouseFields.valueOf(error.fieldName)) {
                HouseFields.HOUSE_LOCALITY -> locality.value.copy(errorId = error.errorId)
                HouseFields.HOUSE_STREET -> street.value.copy(errorId = error.errorId)
                HouseFields.HOUSE_NUM -> houseNum.value.copy(errorId = error.errorId)
                HouseFields.HOUSE_BUILDING_TYPE -> buildingType.value.copy(errorId = error.errorId)

                else -> null
            }
        }
    }

    companion object {
        fun previewModel(ctx: Context) =
            object : HouseViewModel {
                override val uiStateErrorMsg = MutableStateFlow("")
                override val isUiStateChanged = MutableStateFlow(true)
                override val dialogTitleResId =
                    MutableStateFlow(com.oborodulin.home.common.R.string.preview_blank_title)
                override val savedListItem = MutableStateFlow(ListItemModel())
                override val showDialog = MutableStateFlow(true)

                override val uiStateFlow = MutableStateFlow(UiState.Success(previewUiModel(ctx)))
                override val singleEventFlow = Channel<UiSingleEvent>().receiveAsFlow()
                override val events = Channel<ScreenEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()

                override fun redirectedErrorMessage() = null
                override val searchText = MutableStateFlow(TextFieldValue(""))
                override val isSearching = MutableStateFlow(false)
                override fun onSearchTextChange(text: TextFieldValue) {}
                override fun clearSearchText() {}

                override val buildingTypes = MutableStateFlow(mutableMapOf<BuildingType, String>())

                override val id = MutableStateFlow(InputWrapper())
                override fun id() = null
                override val locality = MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val street =
                    MutableStateFlow(InputListItemWrapper<StreetsListItem>())
                override val localityDistrict =
                    MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val microdistrict = MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val territory = MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val zipCode = MutableStateFlow(InputWrapper())
                override val houseNum = MutableStateFlow(InputWrapper())
                override val houseLetter = MutableStateFlow(InputWrapper())
                override val buildingNum = MutableStateFlow(InputWrapper())
                override val buildingType = MutableStateFlow(InputWrapper())
                override val isBusiness = MutableStateFlow(InputWrapper())
                override val isSecurity = MutableStateFlow(InputWrapper())
                override val isIntercom = MutableStateFlow(InputWrapper())
                override val isResidential = MutableStateFlow(InputWrapper())
                override val houseEntrancesQty = MutableStateFlow(InputWrapper())
                override val floorsByEntrance = MutableStateFlow(InputWrapper())
                override val roomsByHouseFloor = MutableStateFlow(InputWrapper())
                override val estimatedRooms = MutableStateFlow(InputWrapper())
                override val isForeignLanguage = MutableStateFlow(InputWrapper())
                override val isPrivateSector = MutableStateFlow(InputWrapper())
                override val houseDesc = MutableStateFlow(InputWrapper())

                override val areInputsValid = MutableStateFlow(true)

                override fun submitAction(action: HouseUiAction): Job? = null
                override fun handleActionJob(
                    action: () -> Unit,
                    afterAction: (CoroutineScope) -> Unit
                ) {
                }

                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: HouseFields, isFocused: Boolean
                ) {
                }

                override fun moveFocusImeAction() {}
                override fun onContinueClick(
                    isPartialInputsValid: Boolean,
                    onSuccess: () -> Unit
                ) {
                }

                override fun setDialogTitleResId(dialogTitleResId: Int) {}
                override fun setSavedListItem(savedListItem: ListItemModel) {}
                override fun onOpenDialogClicked() {}
                override fun onDialogConfirm(onConfirm: () -> Unit) {}
                override fun onDialogDismiss(onDismiss: () -> Unit) {}
            }

        fun previewUiModel(ctx: Context): HouseUi {
            val houseUi = HouseUi(
                street = StreetViewModelImpl.previewUiModel(ctx),
                localityDistrict = LocalityDistrictViewModelImpl.previewUiModel(ctx),
                microdistrict = MicrodistrictViewModelImpl.previewUiModel(ctx),
                territory = TerritoryViewModelImpl.previewUiModel(ctx),
                zipCode = "830004",
                houseNum = 1,
                houseLetter = "Б",
                buildingNum = null,
                buildingType = BuildingType.HOUSE,
                isBusiness = false,
                isSecurity = false,
                isIntercom = null,
                isResidential = true,
                houseEntrancesQty = null,
                floorsByEntrance = null,
                roomsByHouseFloor = null,
                estimatedRooms = null,
                isForeignLanguage = false,
                isPrivateSector = false,
                houseDesc = ""
            )
            houseUi.id = UUID.randomUUID()
            return houseUi
        }
    }
}