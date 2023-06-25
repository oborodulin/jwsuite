package com.oborodulin.jwsuite.presentation.ui.modules.geo.locality.single

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
class LocalityViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val payerUseCases: PayerUseCases,
    private val congregationConverter: CongregationConverter,
    private val payerUiToPayerMapper: PayerUiToPayerMapper
) : LocalityViewModel,
    SingleViewModel<CongregationUi, UiState<CongregationUi>, LocalityUiAction, UiSingleEvent, LocalityFields, InputWrapper>(
        state,
        LocalityFields.LOCALITY_CODE
    ) {
    private val payerId: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            LocalityFields.LOCALITY_ID.name,
            InputWrapper()
        )
    }
    override val ercCode: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            LocalityFields.LOCALITY_CODE.name,
            InputWrapper()
        )
    }
    override val fullName: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            LocalityFields.LOCALITY_NAME.name,
            InputWrapper()
        )
    }
    override val address: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            LocalityFields.ADDRESS.name,
            InputWrapper()
        )
    }
    override val totalArea: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            LocalityFields.TOTAL_AREA.name,
            InputWrapper()
        )
    }
    override val livingSpace: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            LocalityFields.LIVING_SPACE.name,
            InputWrapper()
        )
    }
    override val heatedVolume: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            LocalityFields.HEATED_VOLUME.name,
            InputWrapper()
        )
    }
    override val paymentDay: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            LocalityFields.LOCALITY_TYPE.name,
            InputWrapper()
        )
    }
    override val personsNum: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            LocalityFields.LOCALITY_SHORT_NAME.name,
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

    override suspend fun handleAction(action: LocalityUiAction): Job {
        Timber.tag(TAG).d("handleAction(PayerUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is LocalityUiAction.Create -> {
                submitState(UiState.Success(CongregationUi()))
            }
            is LocalityUiAction.Load -> {
                loadPayer(action.localityId)
            }
            is LocalityUiAction.Save -> {
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

    override fun stateInputFields() = enumValues<LocalityFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: Any): Job? {
        super.initFieldStatesByUiModel(uiModel)
        val congregationUi = uiModel as CongregationUi
        Timber.tag(TAG)
            .d("initFieldStatesByUiModel(PayerModel) called: payerModel = %s", congregationUi)
        congregationUi.id?.let {
            initStateValue(LocalityFields.LOCALITY_ID, payerId, it.toString())
        }
        initStateValue(LocalityFields.LOCALITY_CODE, ercCode, congregationUi.congregationNum)
        initStateValue(LocalityFields.LOCALITY_NAME, fullName, congregationUi.congregationName)
        initStateValue(LocalityFields.ADDRESS, address, congregationUi.territoryMark)
        initStateValue(LocalityFields.TOTAL_AREA, totalArea, congregationUi.totalArea?.toString() ?: "")
        initStateValue(
            LocalityFields.LIVING_SPACE,
            livingSpace,
            congregationUi.livingSpace?.toString() ?: ""
        )
        initStateValue(
            LocalityFields.HEATED_VOLUME,
            heatedVolume,
            congregationUi.heatedVolume?.toString() ?: ""
        )
        initStateValue(LocalityFields.LOCALITY_TYPE, paymentDay, congregationUi.paymentDay.toString())
        initStateValue(LocalityFields.LOCALITY_SHORT_NAME, personsNum, congregationUi.personsNum.toString())
        return null
    }

    override suspend fun observeInputEvents() {
        Timber.tag(TAG).d("observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is LocalityInputEvent.LocalityCode ->
                        when (LocalityInputValidator.ErcCode.errorIdOrNull(event.input)) {
                            null -> setStateValue(LocalityFields.LOCALITY_CODE, ercCode, event.input, true)
                            else -> setStateValue(LocalityFields.LOCALITY_CODE, ercCode, event.input)
                        }
                    is LocalityInputEvent.LocalityName ->
                        when (LocalityInputValidator.FullName.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                LocalityFields.LOCALITY_NAME, fullName, event.input,
                                true
                            )
                            else -> setStateValue(LocalityFields.LOCALITY_NAME, fullName, event.input)
                        }
                    is LocalityInputEvent.RegionId ->
                        when (LocalityInputValidator.Address.errorIdOrNull(event.input)) {
                            null -> setStateValue(LocalityFields.ADDRESS, address, event.input, true)
                            else -> setStateValue(LocalityFields.ADDRESS, address, event.input)
                        }
                    is LocalityInputEvent.RegionDistrictId ->
                        when (LocalityInputValidator.TotalArea.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                LocalityFields.TOTAL_AREA, totalArea, event.input, true
                            )
                            else -> setStateValue(LocalityFields.TOTAL_AREA, totalArea, event.input)
                        }
                    is LocalityInputEvent.LivingSpace ->
                        when (LocalityInputValidator.LivingSpace.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                LocalityFields.LIVING_SPACE, livingSpace, event.input, true
                            )
                            else -> setStateValue(
                                LocalityFields.LIVING_SPACE, livingSpace, event.input
                            )
                        }
                    is LocalityInputEvent.HeatedVolume ->
                        when (LocalityInputValidator.HeatedVolume.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                LocalityFields.HEATED_VOLUME, heatedVolume, event.input, true
                            )
                            else -> setStateValue(
                                LocalityFields.HEATED_VOLUME, heatedVolume, event.input
                            )
                        }
                    is LocalityInputEvent.LocalityType ->
                        when (LocalityInputValidator.PaymentDay.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                LocalityFields.LOCALITY_TYPE, paymentDay, event.input, true
                            )
                            else -> setStateValue(LocalityFields.LOCALITY_TYPE, paymentDay, event.input)
                        }
                    is LocalityInputEvent.LocalityShortName ->
                        when (LocalityInputValidator.PersonsNum.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                LocalityFields.LOCALITY_SHORT_NAME, personsNum, event.input, true
                            )
                            else -> setStateValue(LocalityFields.LOCALITY_SHORT_NAME, personsNum, event.input)
                        }
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is LocalityInputEvent.LocalityCode ->
                        setStateValue(
                            LocalityFields.LOCALITY_CODE, ercCode,
                            LocalityInputValidator.ErcCode.errorIdOrNull(event.input)
                        )
                    is LocalityInputEvent.LocalityName ->
                        setStateValue(
                            LocalityFields.LOCALITY_NAME, fullName,
                            LocalityInputValidator.FullName.errorIdOrNull(event.input)
                        )
                    is LocalityInputEvent.RegionId ->
                        setStateValue(
                            LocalityFields.ADDRESS, address,
                            LocalityInputValidator.Address.errorIdOrNull(event.input)
                        )
                    is LocalityInputEvent.RegionDistrictId ->
                        setStateValue(
                            LocalityFields.TOTAL_AREA, totalArea,
                            LocalityInputValidator.TotalArea.errorIdOrNull(event.input)
                        )
                    is LocalityInputEvent.LivingSpace ->
                        setStateValue(
                            LocalityFields.LIVING_SPACE, livingSpace,
                            LocalityInputValidator.LivingSpace.errorIdOrNull(event.input)
                        )
                    is LocalityInputEvent.HeatedVolume ->
                        setStateValue(
                            LocalityFields.HEATED_VOLUME, heatedVolume,
                            LocalityInputValidator.HeatedVolume.errorIdOrNull(event.input)
                        )
                    is LocalityInputEvent.LocalityType ->
                        setStateValue(
                            LocalityFields.LOCALITY_TYPE, paymentDay,
                            LocalityInputValidator.PaymentDay.errorIdOrNull(event.input)
                        )
                    is LocalityInputEvent.LocalityShortName ->
                        setStateValue(
                            LocalityFields.LOCALITY_SHORT_NAME, personsNum,
                            LocalityInputValidator.PersonsNum.errorIdOrNull(event.input)
                        )
                }
            }
    }

    override fun getInputErrorsOrNull(): List<InputError>? {
        Timber.tag(TAG).d("getInputErrorsOrNull() called")
        val inputErrors: MutableList<InputError> = mutableListOf()
        LocalityInputValidator.ErcCode.errorIdOrNull(ercCode.value.value)?.let {
            inputErrors.add(InputError(fieldName = LocalityFields.LOCALITY_CODE.name, errorId = it))
        }
        LocalityInputValidator.FullName.errorIdOrNull(fullName.value.value)?.let {
            inputErrors.add(InputError(fieldName = LocalityFields.LOCALITY_NAME.name, errorId = it))
        }
        LocalityInputValidator.Address.errorIdOrNull(address.value.value)?.let {
            inputErrors.add(InputError(fieldName = LocalityFields.ADDRESS.name, errorId = it))
        }
        LocalityInputValidator.TotalArea.errorIdOrNull(totalArea.value.value)?.let {
            inputErrors.add(InputError(fieldName = LocalityFields.TOTAL_AREA.name, errorId = it))
        }
        LocalityInputValidator.LivingSpace.errorIdOrNull(livingSpace.value.value)?.let {
            inputErrors.add(InputError(fieldName = LocalityFields.LIVING_SPACE.name, errorId = it))
        }
        LocalityInputValidator.HeatedVolume.errorIdOrNull(heatedVolume.value.value)?.let {
            inputErrors.add(InputError(fieldName = LocalityFields.HEATED_VOLUME.name, errorId = it))
        }
        LocalityInputValidator.PaymentDay.errorIdOrNull(paymentDay.value.value)?.let {
            inputErrors.add(InputError(fieldName = LocalityFields.LOCALITY_TYPE.name, errorId = it))
        }
        LocalityInputValidator.PersonsNum.errorIdOrNull(personsNum.value.value)?.let {
            inputErrors.add(InputError(fieldName = LocalityFields.LOCALITY_SHORT_NAME.name, errorId = it))
        }
        return if (inputErrors.isEmpty()) null else inputErrors
    }

    override fun displayInputErrors(inputErrors: List<InputError>) {
        Timber.tag(TAG)
            .d("displayInputErrors() called: inputErrors.count = %d", inputErrors.size)
        for (error in inputErrors) {
            state[error.fieldName] = when (error.fieldName) {
                //PayerFields.ERC_CODE.name -> ercCode.update{ it.copy(errorId = error.errorId) }
                LocalityFields.LOCALITY_CODE.name -> ercCode.value.copy(errorId = error.errorId)
                LocalityFields.LOCALITY_NAME.name -> fullName.value.copy(errorId = error.errorId)
                LocalityFields.ADDRESS.name -> address.value.copy(errorId = error.errorId)
                LocalityFields.TOTAL_AREA.name -> totalArea.value.copy(errorId = error.errorId)
                LocalityFields.LIVING_SPACE.name -> livingSpace.value.copy(errorId = error.errorId)
                LocalityFields.HEATED_VOLUME.name -> heatedVolume.value.copy(errorId = error.errorId)
                LocalityFields.LOCALITY_TYPE.name -> paymentDay.value.copy(errorId = error.errorId)
                LocalityFields.LOCALITY_SHORT_NAME.name -> personsNum.value.copy(errorId = error.errorId)
                else -> null
            }
        }
    }

    companion object {
        val previewModel =
            object : LocalityViewModel {
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
                    focusedField: LocalityFields, isFocused: Boolean
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