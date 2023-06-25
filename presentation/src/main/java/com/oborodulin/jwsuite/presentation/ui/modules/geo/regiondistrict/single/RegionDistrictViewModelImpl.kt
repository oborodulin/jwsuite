package com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.single

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.oborodulin.jwsuite.presentation.ui.congregating.model.CongregationUi
import com.oborodulin.jwsuite.presentation.ui.congregating.model.converters.CongregationConverter
import com.oborodulin.jwsuite.presentation.ui.congregating.model.mappers.PayerUiToPayerMapper
import com.oborodulin.home.common.ui.components.*
import com.oborodulin.home.common.ui.components.field.*
import com.oborodulin.home.common.ui.components.field.util.*
import com.oborodulin.home.common.ui.state.SingleViewModel
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.home.domain.usecases.GetPayerUseCase
import com.oborodulin.home.accounting.domain.usecases.PayerUseCases
import com.oborodulin.home.domain.usecases.SavePayerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject

private const val TAG = "Accounting.ui.PayerViewModel"

@OptIn(FlowPreview::class)
@HiltViewModel
class RegionDistrictViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val payerUseCases: PayerUseCases,
    private val congregationConverter: CongregationConverter,
    private val payerUiToPayerMapper: PayerUiToPayerMapper
) : RegionDistrictViewModel,
    SingleViewModel<CongregationUi, UiState<CongregationUi>, RegionDistrictUiAction, UiSingleEvent, RegionDistrictFields, InputWrapper>(
        state,
        RegionDistrictFields.LOCALITY_CODE
    ) {
    private val payerId: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            RegionDistrictFields.LOCALITY_ID.name,
            InputWrapper()
        )
    }
    override val ercCode: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            RegionDistrictFields.LOCALITY_CODE.name,
            InputWrapper()
        )
    }
    override val fullName: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            RegionDistrictFields.LOCALITY_NAME.name,
            InputWrapper()
        )
    }
    override val address: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            RegionDistrictFields.ADDRESS.name,
            InputWrapper()
        )
    }
    override val totalArea: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            RegionDistrictFields.TOTAL_AREA.name,
            InputWrapper()
        )
    }
    override val livingSpace: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            RegionDistrictFields.LIVING_SPACE.name,
            InputWrapper()
        )
    }
    override val heatedVolume: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            RegionDistrictFields.HEATED_VOLUME.name,
            InputWrapper()
        )
    }
    override val paymentDay: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            RegionDistrictFields.LOCALITY_TYPE.name,
            InputWrapper()
        )
    }
    override val personsNum: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            RegionDistrictFields.LOCALITY_SHORT_NAME.name,
            InputWrapper()
        )
    }

    override val areInputsValid =
        combine(
            combine(
                ercCode,
                fullName,
                address,
                totalArea
            ) { ercCode, fullName, address, totalArea ->
                ercCode.errorId == null && fullName.errorId == null && address.errorId == null && totalArea.errorId == null
            },
            combine(
                livingSpace,
                heatedVolume,
                paymentDay,
                personsNum
            ) { livingSpace, heatedVolume, paymentDay, personsNum ->
                livingSpace.errorId == null && heatedVolume.errorId == null && paymentDay.errorId == null && personsNum.errorId == null
            }) { fieldsPartOne, fieldsPartTwo -> fieldsPartOne && fieldsPartTwo }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            false
        )

    override fun initState(): UiState<CongregationUi> = UiState.Loading

    override suspend fun handleAction(action: RegionDistrictUiAction): Job {
        Timber.tag(TAG).d("handleAction(PayerUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is RegionDistrictUiAction.Create -> {
                submitState(UiState.Success(CongregationUi()))
            }
            is RegionDistrictUiAction.Load -> {
                loadPayer(action.localityId)
            }
            is RegionDistrictUiAction.Save -> {
                savePayer()
            }
        }
        return job
    }

    private fun loadPayer(payerId: UUID): Job {
        Timber.tag(TAG).d("loadPayer(UUID) called: %s", payerId.toString())
        val job = viewModelScope.launch(errorHandler) {
            payerUseCases.getPayerUseCase.execute(GetPayerUseCase.Request(payerId))
                .map {
                    congregationConverter.convert(it)
                }
                .collect {
                    submitState(it)
                }
        }
        return job
    }

    private fun savePayer(): Job {
        val congregationUi = CongregationUi(
            id = if (payerId.value.value.isNotEmpty()) {
                UUID.fromString(payerId.value.value)
            } else null,
            congregationNum = ercCode.value.value,
            congregationName = fullName.value.value,
            territoryMark = address.value.value,
            totalArea = totalArea.value.value.toBigDecimalOrNull(),
            livingSpace = livingSpace.value.value.toBigDecimalOrNull(),
            heatedVolume = heatedVolume.value.value.toBigDecimalOrNull(),
            paymentDay = paymentDay.value.value.toInt(),
            personsNum = personsNum.value.value.toInt()
        )
        Timber.tag(TAG).d("savePayer() called: UI model %s", congregationUi)
        val job = viewModelScope.launch(errorHandler) {
            payerUseCases.savePayerUseCase.execute(
                SavePayerUseCase.Request(payerUiToPayerMapper.map(congregationUi))
            ).collect {}
        }
        return job
    }

    override fun stateInputFields() = enumValues<RegionDistrictFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: Any): Job? {
        super.initFieldStatesByUiModel(uiModel)
        val congregationUi = uiModel as CongregationUi
        Timber.tag(TAG)
            .d("initFieldStatesByUiModel(PayerModel) called: payerModel = %s", congregationUi)
        congregationUi.id?.let {
            initStateValue(RegionDistrictFields.LOCALITY_ID, payerId, it.toString())
        }
        initStateValue(RegionDistrictFields.LOCALITY_CODE, ercCode, congregationUi.congregationNum)
        initStateValue(RegionDistrictFields.LOCALITY_NAME, fullName, congregationUi.congregationName)
        initStateValue(RegionDistrictFields.ADDRESS, address, congregationUi.territoryMark)
        initStateValue(RegionDistrictFields.TOTAL_AREA, totalArea, congregationUi.totalArea?.toString() ?: "")
        initStateValue(
            RegionDistrictFields.LIVING_SPACE,
            livingSpace,
            congregationUi.livingSpace?.toString() ?: ""
        )
        initStateValue(
            RegionDistrictFields.HEATED_VOLUME,
            heatedVolume,
            congregationUi.heatedVolume?.toString() ?: ""
        )
        initStateValue(RegionDistrictFields.LOCALITY_TYPE, paymentDay, congregationUi.paymentDay.toString())
        initStateValue(RegionDistrictFields.LOCALITY_SHORT_NAME, personsNum, congregationUi.personsNum.toString())
        return null
    }

    override suspend fun observeInputEvents() {
        Timber.tag(TAG).d("observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is RegionDistrictInputEvent.RegionDistrictCode ->
                        when (RegionDistrictInputValidator.ErcCode.errorIdOrNull(event.input)) {
                            null -> setStateValue(RegionDistrictFields.LOCALITY_CODE, ercCode, event.input, true)
                            else -> setStateValue(RegionDistrictFields.LOCALITY_CODE, ercCode, event.input)
                        }
                    is RegionDistrictInputEvent.RegionDistrictName ->
                        when (RegionDistrictInputValidator.FullName.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                RegionDistrictFields.LOCALITY_NAME, fullName, event.input,
                                true
                            )
                            else -> setStateValue(RegionDistrictFields.LOCALITY_NAME, fullName, event.input)
                        }
                    is RegionDistrictInputEvent.RegionId ->
                        when (RegionDistrictInputValidator.Address.errorIdOrNull(event.input)) {
                            null -> setStateValue(RegionDistrictFields.ADDRESS, address, event.input, true)
                            else -> setStateValue(RegionDistrictFields.ADDRESS, address, event.input)
                        }
                    is RegionDistrictInputEvent.RegionDistrictDistrictId ->
                        when (RegionDistrictInputValidator.TotalArea.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                RegionDistrictFields.TOTAL_AREA, totalArea, event.input, true
                            )
                            else -> setStateValue(RegionDistrictFields.TOTAL_AREA, totalArea, event.input)
                        }
                    is RegionDistrictInputEvent.LivingSpace ->
                        when (RegionDistrictInputValidator.LivingSpace.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                RegionDistrictFields.LIVING_SPACE, livingSpace, event.input, true
                            )
                            else -> setStateValue(
                                RegionDistrictFields.LIVING_SPACE, livingSpace, event.input
                            )
                        }
                    is RegionDistrictInputEvent.HeatedVolume ->
                        when (RegionDistrictInputValidator.HeatedVolume.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                RegionDistrictFields.HEATED_VOLUME, heatedVolume, event.input, true
                            )
                            else -> setStateValue(
                                RegionDistrictFields.HEATED_VOLUME, heatedVolume, event.input
                            )
                        }
                    is RegionDistrictInputEvent.RegionDistrictType ->
                        when (RegionDistrictInputValidator.PaymentDay.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                RegionDistrictFields.LOCALITY_TYPE, paymentDay, event.input, true
                            )
                            else -> setStateValue(RegionDistrictFields.LOCALITY_TYPE, paymentDay, event.input)
                        }
                    is RegionDistrictInputEvent.RegionDistrictShortName ->
                        when (RegionDistrictInputValidator.PersonsNum.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                RegionDistrictFields.LOCALITY_SHORT_NAME, personsNum, event.input, true
                            )
                            else -> setStateValue(RegionDistrictFields.LOCALITY_SHORT_NAME, personsNum, event.input)
                        }
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is RegionDistrictInputEvent.RegionDistrictCode ->
                        setStateValue(
                            RegionDistrictFields.LOCALITY_CODE, ercCode,
                            RegionDistrictInputValidator.ErcCode.errorIdOrNull(event.input)
                        )
                    is RegionDistrictInputEvent.RegionDistrictName ->
                        setStateValue(
                            RegionDistrictFields.LOCALITY_NAME, fullName,
                            RegionDistrictInputValidator.FullName.errorIdOrNull(event.input)
                        )
                    is RegionDistrictInputEvent.RegionId ->
                        setStateValue(
                            RegionDistrictFields.ADDRESS, address,
                            RegionDistrictInputValidator.Address.errorIdOrNull(event.input)
                        )
                    is RegionDistrictInputEvent.RegionDistrictDistrictId ->
                        setStateValue(
                            RegionDistrictFields.TOTAL_AREA, totalArea,
                            RegionDistrictInputValidator.TotalArea.errorIdOrNull(event.input)
                        )
                    is RegionDistrictInputEvent.LivingSpace ->
                        setStateValue(
                            RegionDistrictFields.LIVING_SPACE, livingSpace,
                            RegionDistrictInputValidator.LivingSpace.errorIdOrNull(event.input)
                        )
                    is RegionDistrictInputEvent.HeatedVolume ->
                        setStateValue(
                            RegionDistrictFields.HEATED_VOLUME, heatedVolume,
                            RegionDistrictInputValidator.HeatedVolume.errorIdOrNull(event.input)
                        )
                    is RegionDistrictInputEvent.RegionDistrictType ->
                        setStateValue(
                            RegionDistrictFields.LOCALITY_TYPE, paymentDay,
                            RegionDistrictInputValidator.PaymentDay.errorIdOrNull(event.input)
                        )
                    is RegionDistrictInputEvent.RegionDistrictShortName ->
                        setStateValue(
                            RegionDistrictFields.LOCALITY_SHORT_NAME, personsNum,
                            RegionDistrictInputValidator.PersonsNum.errorIdOrNull(event.input)
                        )
                }
            }
    }

    override fun getInputErrorsOrNull(): List<InputError>? {
        Timber.tag(TAG).d("getInputErrorsOrNull() called")
        val inputErrors: MutableList<InputError> = mutableListOf()
        RegionDistrictInputValidator.ErcCode.errorIdOrNull(ercCode.value.value)?.let {
            inputErrors.add(InputError(fieldName = RegionDistrictFields.LOCALITY_CODE.name, errorId = it))
        }
        RegionDistrictInputValidator.FullName.errorIdOrNull(fullName.value.value)?.let {
            inputErrors.add(InputError(fieldName = RegionDistrictFields.LOCALITY_NAME.name, errorId = it))
        }
        RegionDistrictInputValidator.Address.errorIdOrNull(address.value.value)?.let {
            inputErrors.add(InputError(fieldName = RegionDistrictFields.ADDRESS.name, errorId = it))
        }
        RegionDistrictInputValidator.TotalArea.errorIdOrNull(totalArea.value.value)?.let {
            inputErrors.add(InputError(fieldName = RegionDistrictFields.TOTAL_AREA.name, errorId = it))
        }
        RegionDistrictInputValidator.LivingSpace.errorIdOrNull(livingSpace.value.value)?.let {
            inputErrors.add(InputError(fieldName = RegionDistrictFields.LIVING_SPACE.name, errorId = it))
        }
        RegionDistrictInputValidator.HeatedVolume.errorIdOrNull(heatedVolume.value.value)?.let {
            inputErrors.add(InputError(fieldName = RegionDistrictFields.HEATED_VOLUME.name, errorId = it))
        }
        RegionDistrictInputValidator.PaymentDay.errorIdOrNull(paymentDay.value.value)?.let {
            inputErrors.add(InputError(fieldName = RegionDistrictFields.LOCALITY_TYPE.name, errorId = it))
        }
        RegionDistrictInputValidator.PersonsNum.errorIdOrNull(personsNum.value.value)?.let {
            inputErrors.add(InputError(fieldName = RegionDistrictFields.LOCALITY_SHORT_NAME.name, errorId = it))
        }
        return if (inputErrors.isEmpty()) null else inputErrors
    }

    override fun displayInputErrors(inputErrors: List<InputError>) {
        Timber.tag(TAG)
            .d("displayInputErrors() called: inputErrors.count = %d", inputErrors.size)
        for (error in inputErrors) {
            state[error.fieldName] = when (error.fieldName) {
                //PayerFields.ERC_CODE.name -> ercCode.update{ it.copy(errorId = error.errorId) }
                RegionDistrictFields.LOCALITY_CODE.name -> ercCode.value.copy(errorId = error.errorId)
                RegionDistrictFields.LOCALITY_NAME.name -> fullName.value.copy(errorId = error.errorId)
                RegionDistrictFields.ADDRESS.name -> address.value.copy(errorId = error.errorId)
                RegionDistrictFields.TOTAL_AREA.name -> totalArea.value.copy(errorId = error.errorId)
                RegionDistrictFields.LIVING_SPACE.name -> livingSpace.value.copy(errorId = error.errorId)
                RegionDistrictFields.HEATED_VOLUME.name -> heatedVolume.value.copy(errorId = error.errorId)
                RegionDistrictFields.LOCALITY_TYPE.name -> paymentDay.value.copy(errorId = error.errorId)
                RegionDistrictFields.LOCALITY_SHORT_NAME.name -> personsNum.value.copy(errorId = error.errorId)
                else -> null
            }
        }
    }

    companion object {
        val previewModel =
            object : RegionDistrictViewModel {
                override val events = Channel<ScreenEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()

                override val ercCode = MutableStateFlow(InputWrapper())
                override val fullName = MutableStateFlow(InputWrapper())
                override val address = MutableStateFlow(InputWrapper())
                override val totalArea = MutableStateFlow(InputWrapper())
                override val livingSpace = MutableStateFlow(InputWrapper())
                override val heatedVolume = MutableStateFlow(InputWrapper())
                override val paymentDay = MutableStateFlow(InputWrapper())
                override val personsNum = MutableStateFlow(InputWrapper())

                override val areInputsValid = MutableStateFlow(true)

                override fun viewModelScope(): CoroutineScope = CoroutineScope(Dispatchers.Main)
                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: RegionDistrictFields, isFocused: Boolean
                ) {
                }

                override fun moveFocusImeAction() {}
                override fun onContinueClick(onSuccess: () -> Unit) {}
            }
    }
}
/*
        private val _payerState = mutableStateOf(Payer())

    private val payerState: State<Payer>
        get() = _payerState
    fun onEvent(event: PayerEvent) {
        when (event) {
            is PayerEvent.SavePayer -> viewModelScope.launch {
                //payerUseCases.savePayerUseCase(payerState.value)
            }
            is PayerEvent.ChangeErcCode -> _payerState.value =
                payerState.value.copy(ercCode = event.newErcCode)
            is PayerEvent.ChangeFullName -> _payerState.value =
                payerState.value.copy(fullName = event.newFullName)
            is PayerEvent.ChangeAddress -> _payerState.value =
                payerState.value.copy(address = event.newAddress)
            is PayerEvent.ChangeTotalArea -> _payerState.value =
                payerState.value.copy(totalArea = event.newTotalArea)
            is PayerEvent.ChangeLivingSpace -> _payerState.value =
                payerState.value.copy(livingSpace = event.newLivingSpace)
            is PayerEvent.ChangeHeatedVolume -> _payerState.value =
                payerState.value.copy(heatedVolume = event.newHeatedVolume)
            is PayerEvent.ChangePaymentDay -> _payerState.value =
                payerState.value.copy(paymentDay = event.newPaymentDay)
            is PayerEvent.ChangePersonsNum -> _payerState.value =
                payerState.value.copy(personsNum = event.newPersonsNum)
        }
    }
    fun fetchPayer(id: UUID) {
        viewModelScope.launch() {
            /*payerUseCases.getPayer(id).collect {
                _payerState.value = it
            }*/
        }
    }
}
 */