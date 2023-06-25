package com.oborodulin.jwsuite.presentation.ui.modules.geo.region.single

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
class RegionViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val payerUseCases: PayerUseCases,
    private val congregationConverter: CongregationConverter,
    private val payerUiToPayerMapper: PayerUiToPayerMapper
) : RegionViewModel,
    SingleViewModel<CongregationUi, UiState<CongregationUi>, RegionUiAction, UiSingleEvent, RegionFields, InputWrapper>(
        state,
        RegionFields.LOCALITY_CODE
    ) {
    private val payerId: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            RegionFields.LOCALITY_ID.name,
            InputWrapper()
        )
    }
    override val ercCode: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            RegionFields.LOCALITY_CODE.name,
            InputWrapper()
        )
    }
    override val fullName: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            RegionFields.LOCALITY_NAME.name,
            InputWrapper()
        )
    }
    override val address: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            RegionFields.ADDRESS.name,
            InputWrapper()
        )
    }
    override val totalArea: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            RegionFields.TOTAL_AREA.name,
            InputWrapper()
        )
    }
    override val livingSpace: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            RegionFields.LIVING_SPACE.name,
            InputWrapper()
        )
    }
    override val heatedVolume: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            RegionFields.HEATED_VOLUME.name,
            InputWrapper()
        )
    }
    override val paymentDay: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            RegionFields.LOCALITY_TYPE.name,
            InputWrapper()
        )
    }
    override val personsNum: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            RegionFields.LOCALITY_SHORT_NAME.name,
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

    override suspend fun handleAction(action: RegionUiAction): Job {
        Timber.tag(TAG).d("handleAction(PayerUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is RegionUiAction.Create -> {
                submitState(UiState.Success(CongregationUi()))
            }
            is RegionUiAction.Load -> {
                loadPayer(action.localityId)
            }
            is RegionUiAction.Save -> {
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

    override fun stateInputFields() = enumValues<RegionFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: Any): Job? {
        super.initFieldStatesByUiModel(uiModel)
        val congregationUi = uiModel as CongregationUi
        Timber.tag(TAG)
            .d("initFieldStatesByUiModel(PayerModel) called: payerModel = %s", congregationUi)
        congregationUi.id?.let {
            initStateValue(RegionFields.LOCALITY_ID, payerId, it.toString())
        }
        initStateValue(RegionFields.LOCALITY_CODE, ercCode, congregationUi.congregationNum)
        initStateValue(RegionFields.LOCALITY_NAME, fullName, congregationUi.congregationName)
        initStateValue(RegionFields.ADDRESS, address, congregationUi.territoryMark)
        initStateValue(RegionFields.TOTAL_AREA, totalArea, congregationUi.totalArea?.toString() ?: "")
        initStateValue(
            RegionFields.LIVING_SPACE,
            livingSpace,
            congregationUi.livingSpace?.toString() ?: ""
        )
        initStateValue(
            RegionFields.HEATED_VOLUME,
            heatedVolume,
            congregationUi.heatedVolume?.toString() ?: ""
        )
        initStateValue(RegionFields.LOCALITY_TYPE, paymentDay, congregationUi.paymentDay.toString())
        initStateValue(RegionFields.LOCALITY_SHORT_NAME, personsNum, congregationUi.personsNum.toString())
        return null
    }

    override suspend fun observeInputEvents() {
        Timber.tag(TAG).d("observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is RegionInputEvent.RegionCode ->
                        when (RegionInputValidator.ErcCode.errorIdOrNull(event.input)) {
                            null -> setStateValue(RegionFields.LOCALITY_CODE, ercCode, event.input, true)
                            else -> setStateValue(RegionFields.LOCALITY_CODE, ercCode, event.input)
                        }
                    is RegionInputEvent.RegionName ->
                        when (RegionInputValidator.FullName.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                RegionFields.LOCALITY_NAME, fullName, event.input,
                                true
                            )
                            else -> setStateValue(RegionFields.LOCALITY_NAME, fullName, event.input)
                        }
                    is RegionInputEvent.RegionId ->
                        when (RegionInputValidator.Address.errorIdOrNull(event.input)) {
                            null -> setStateValue(RegionFields.ADDRESS, address, event.input, true)
                            else -> setStateValue(RegionFields.ADDRESS, address, event.input)
                        }
                    is RegionInputEvent.RegionDistrictId ->
                        when (RegionInputValidator.TotalArea.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                RegionFields.TOTAL_AREA, totalArea, event.input, true
                            )
                            else -> setStateValue(RegionFields.TOTAL_AREA, totalArea, event.input)
                        }
                    is RegionInputEvent.LivingSpace ->
                        when (RegionInputValidator.LivingSpace.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                RegionFields.LIVING_SPACE, livingSpace, event.input, true
                            )
                            else -> setStateValue(
                                RegionFields.LIVING_SPACE, livingSpace, event.input
                            )
                        }
                    is RegionInputEvent.HeatedVolume ->
                        when (RegionInputValidator.HeatedVolume.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                RegionFields.HEATED_VOLUME, heatedVolume, event.input, true
                            )
                            else -> setStateValue(
                                RegionFields.HEATED_VOLUME, heatedVolume, event.input
                            )
                        }
                    is RegionInputEvent.RegionType ->
                        when (RegionInputValidator.PaymentDay.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                RegionFields.LOCALITY_TYPE, paymentDay, event.input, true
                            )
                            else -> setStateValue(RegionFields.LOCALITY_TYPE, paymentDay, event.input)
                        }
                    is RegionInputEvent.RegionShortName ->
                        when (RegionInputValidator.PersonsNum.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                RegionFields.LOCALITY_SHORT_NAME, personsNum, event.input, true
                            )
                            else -> setStateValue(RegionFields.LOCALITY_SHORT_NAME, personsNum, event.input)
                        }
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is RegionInputEvent.RegionCode ->
                        setStateValue(
                            RegionFields.LOCALITY_CODE, ercCode,
                            RegionInputValidator.ErcCode.errorIdOrNull(event.input)
                        )
                    is RegionInputEvent.RegionName ->
                        setStateValue(
                            RegionFields.LOCALITY_NAME, fullName,
                            RegionInputValidator.FullName.errorIdOrNull(event.input)
                        )
                    is RegionInputEvent.RegionId ->
                        setStateValue(
                            RegionFields.ADDRESS, address,
                            RegionInputValidator.Address.errorIdOrNull(event.input)
                        )
                    is RegionInputEvent.RegionDistrictId ->
                        setStateValue(
                            RegionFields.TOTAL_AREA, totalArea,
                            RegionInputValidator.TotalArea.errorIdOrNull(event.input)
                        )
                    is RegionInputEvent.LivingSpace ->
                        setStateValue(
                            RegionFields.LIVING_SPACE, livingSpace,
                            RegionInputValidator.LivingSpace.errorIdOrNull(event.input)
                        )
                    is RegionInputEvent.HeatedVolume ->
                        setStateValue(
                            RegionFields.HEATED_VOLUME, heatedVolume,
                            RegionInputValidator.HeatedVolume.errorIdOrNull(event.input)
                        )
                    is RegionInputEvent.RegionType ->
                        setStateValue(
                            RegionFields.LOCALITY_TYPE, paymentDay,
                            RegionInputValidator.PaymentDay.errorIdOrNull(event.input)
                        )
                    is RegionInputEvent.RegionShortName ->
                        setStateValue(
                            RegionFields.LOCALITY_SHORT_NAME, personsNum,
                            RegionInputValidator.PersonsNum.errorIdOrNull(event.input)
                        )
                }
            }
    }

    override fun getInputErrorsOrNull(): List<InputError>? {
        Timber.tag(TAG).d("getInputErrorsOrNull() called")
        val inputErrors: MutableList<InputError> = mutableListOf()
        RegionInputValidator.ErcCode.errorIdOrNull(ercCode.value.value)?.let {
            inputErrors.add(InputError(fieldName = RegionFields.LOCALITY_CODE.name, errorId = it))
        }
        RegionInputValidator.FullName.errorIdOrNull(fullName.value.value)?.let {
            inputErrors.add(InputError(fieldName = RegionFields.LOCALITY_NAME.name, errorId = it))
        }
        RegionInputValidator.Address.errorIdOrNull(address.value.value)?.let {
            inputErrors.add(InputError(fieldName = RegionFields.ADDRESS.name, errorId = it))
        }
        RegionInputValidator.TotalArea.errorIdOrNull(totalArea.value.value)?.let {
            inputErrors.add(InputError(fieldName = RegionFields.TOTAL_AREA.name, errorId = it))
        }
        RegionInputValidator.LivingSpace.errorIdOrNull(livingSpace.value.value)?.let {
            inputErrors.add(InputError(fieldName = RegionFields.LIVING_SPACE.name, errorId = it))
        }
        RegionInputValidator.HeatedVolume.errorIdOrNull(heatedVolume.value.value)?.let {
            inputErrors.add(InputError(fieldName = RegionFields.HEATED_VOLUME.name, errorId = it))
        }
        RegionInputValidator.PaymentDay.errorIdOrNull(paymentDay.value.value)?.let {
            inputErrors.add(InputError(fieldName = RegionFields.LOCALITY_TYPE.name, errorId = it))
        }
        RegionInputValidator.PersonsNum.errorIdOrNull(personsNum.value.value)?.let {
            inputErrors.add(InputError(fieldName = RegionFields.LOCALITY_SHORT_NAME.name, errorId = it))
        }
        return if (inputErrors.isEmpty()) null else inputErrors
    }

    override fun displayInputErrors(inputErrors: List<InputError>) {
        Timber.tag(TAG)
            .d("displayInputErrors() called: inputErrors.count = %d", inputErrors.size)
        for (error in inputErrors) {
            state[error.fieldName] = when (error.fieldName) {
                //PayerFields.ERC_CODE.name -> ercCode.update{ it.copy(errorId = error.errorId) }
                RegionFields.LOCALITY_CODE.name -> ercCode.value.copy(errorId = error.errorId)
                RegionFields.LOCALITY_NAME.name -> fullName.value.copy(errorId = error.errorId)
                RegionFields.ADDRESS.name -> address.value.copy(errorId = error.errorId)
                RegionFields.TOTAL_AREA.name -> totalArea.value.copy(errorId = error.errorId)
                RegionFields.LIVING_SPACE.name -> livingSpace.value.copy(errorId = error.errorId)
                RegionFields.HEATED_VOLUME.name -> heatedVolume.value.copy(errorId = error.errorId)
                RegionFields.LOCALITY_TYPE.name -> paymentDay.value.copy(errorId = error.errorId)
                RegionFields.LOCALITY_SHORT_NAME.name -> personsNum.value.copy(errorId = error.errorId)
                else -> null
            }
        }
    }

    companion object {
        val previewModel =
            object : RegionViewModel {
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
                    focusedField: RegionFields, isFocused: Boolean
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