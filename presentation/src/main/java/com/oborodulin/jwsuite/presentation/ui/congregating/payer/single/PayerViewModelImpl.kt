package com.oborodulin.home.accounting.ui.payer.single

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.accounting.ui.model.PayerUi
import com.oborodulin.home.accounting.ui.model.converters.PayerConverter
import com.oborodulin.home.accounting.ui.model.mappers.PayerUiToPayerMapper
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
class PayerViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val payerUseCases: PayerUseCases,
    private val payerConverter: PayerConverter,
    private val payerUiToPayerMapper: PayerUiToPayerMapper
) : PayerViewModel,
    SingleViewModel<PayerUi, UiState<PayerUi>, PayerUiAction, UiSingleEvent, PayerFields, InputWrapper>(
        state,
        PayerFields.ERC_CODE
    ) {
    private val payerId: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            PayerFields.PAYER_ID.name,
            InputWrapper()
        )
    }
    override val ercCode: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            PayerFields.ERC_CODE.name,
            InputWrapper()
        )
    }
    override val fullName: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            PayerFields.FULL_NAME.name,
            InputWrapper()
        )
    }
    override val address: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            PayerFields.ADDRESS.name,
            InputWrapper()
        )
    }
    override val totalArea: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            PayerFields.TOTAL_AREA.name,
            InputWrapper()
        )
    }
    override val livingSpace: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            PayerFields.LIVING_SPACE.name,
            InputWrapper()
        )
    }
    override val heatedVolume: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            PayerFields.HEATED_VOLUME.name,
            InputWrapper()
        )
    }
    override val paymentDay: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            PayerFields.PAYMENT_DAY.name,
            InputWrapper()
        )
    }
    override val personsNum: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            PayerFields.PERSONS_NUM.name,
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

    override fun initState(): UiState<PayerUi> = UiState.Loading

    override suspend fun handleAction(action: PayerUiAction): Job {
        Timber.tag(TAG).d("handleAction(PayerUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is PayerUiAction.Create -> {
                submitState(UiState.Success(PayerUi()))
            }
            is PayerUiAction.Load -> {
                loadPayer(action.payerId)
            }
            is PayerUiAction.Save -> {
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
                    payerConverter.convert(it)
                }
                .collect {
                    submitState(it)
                }
        }
        return job
    }

    private fun savePayer(): Job {
        val payerUi = PayerUi(
            id = if (payerId.value.value.isNotEmpty()) {
                UUID.fromString(payerId.value.value)
            } else null,
            ercCode = ercCode.value.value,
            fullName = fullName.value.value,
            address = address.value.value,
            totalArea = totalArea.value.value.toBigDecimalOrNull(),
            livingSpace = livingSpace.value.value.toBigDecimalOrNull(),
            heatedVolume = heatedVolume.value.value.toBigDecimalOrNull(),
            paymentDay = paymentDay.value.value.toInt(),
            personsNum = personsNum.value.value.toInt()
        )
        Timber.tag(TAG).d("savePayer() called: UI model %s", payerUi)
        val job = viewModelScope.launch(errorHandler) {
            payerUseCases.savePayerUseCase.execute(
                SavePayerUseCase.Request(payerUiToPayerMapper.map(payerUi))
            ).collect {}
        }
        return job
    }

    override fun stateInputFields() = enumValues<PayerFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: Any): Job? {
        super.initFieldStatesByUiModel(uiModel)
        val payerUi = uiModel as PayerUi
        Timber.tag(TAG)
            .d("initFieldStatesByUiModel(PayerModel) called: payerModel = %s", payerUi)
        payerUi.id?.let {
            initStateValue(PayerFields.PAYER_ID, payerId, it.toString())
        }
        initStateValue(PayerFields.ERC_CODE, ercCode, payerUi.ercCode)
        initStateValue(PayerFields.FULL_NAME, fullName, payerUi.fullName)
        initStateValue(PayerFields.ADDRESS, address, payerUi.address)
        initStateValue(PayerFields.TOTAL_AREA, totalArea, payerUi.totalArea?.toString() ?: "")
        initStateValue(
            PayerFields.LIVING_SPACE,
            livingSpace,
            payerUi.livingSpace?.toString() ?: ""
        )
        initStateValue(
            PayerFields.HEATED_VOLUME,
            heatedVolume,
            payerUi.heatedVolume?.toString() ?: ""
        )
        initStateValue(PayerFields.PAYMENT_DAY, paymentDay, payerUi.paymentDay.toString())
        initStateValue(PayerFields.PERSONS_NUM, personsNum, payerUi.personsNum.toString())
        return null
    }

    override suspend fun observeInputEvents() {
        Timber.tag(TAG).d("observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is PayerInputEvent.ErcCode ->
                        when (PayerInputValidator.ErcCode.errorIdOrNull(event.input)) {
                            null -> setStateValue(PayerFields.ERC_CODE, ercCode, event.input, true)
                            else -> setStateValue(PayerFields.ERC_CODE, ercCode, event.input)
                        }
                    is PayerInputEvent.FullName ->
                        when (PayerInputValidator.FullName.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                PayerFields.FULL_NAME, fullName, event.input,
                                true
                            )
                            else -> setStateValue(PayerFields.FULL_NAME, fullName, event.input)
                        }
                    is PayerInputEvent.Address ->
                        when (PayerInputValidator.Address.errorIdOrNull(event.input)) {
                            null -> setStateValue(PayerFields.ADDRESS, address, event.input, true)
                            else -> setStateValue(PayerFields.ADDRESS, address, event.input)
                        }
                    is PayerInputEvent.TotalArea ->
                        when (PayerInputValidator.TotalArea.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                PayerFields.TOTAL_AREA, totalArea, event.input, true
                            )
                            else -> setStateValue(PayerFields.TOTAL_AREA, totalArea, event.input)
                        }
                    is PayerInputEvent.LivingSpace ->
                        when (PayerInputValidator.LivingSpace.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                PayerFields.LIVING_SPACE, livingSpace, event.input, true
                            )
                            else -> setStateValue(
                                PayerFields.LIVING_SPACE, livingSpace, event.input
                            )
                        }
                    is PayerInputEvent.HeatedVolume ->
                        when (PayerInputValidator.HeatedVolume.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                PayerFields.HEATED_VOLUME, heatedVolume, event.input, true
                            )
                            else -> setStateValue(
                                PayerFields.HEATED_VOLUME, heatedVolume, event.input
                            )
                        }
                    is PayerInputEvent.PaymentDay ->
                        when (PayerInputValidator.PaymentDay.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                PayerFields.PAYMENT_DAY, paymentDay, event.input, true
                            )
                            else -> setStateValue(PayerFields.PAYMENT_DAY, paymentDay, event.input)
                        }
                    is PayerInputEvent.PersonsNum ->
                        when (PayerInputValidator.PersonsNum.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                PayerFields.PERSONS_NUM, personsNum, event.input, true
                            )
                            else -> setStateValue(PayerFields.PERSONS_NUM, personsNum, event.input)
                        }
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is PayerInputEvent.ErcCode ->
                        setStateValue(
                            PayerFields.ERC_CODE, ercCode,
                            PayerInputValidator.ErcCode.errorIdOrNull(event.input)
                        )
                    is PayerInputEvent.FullName ->
                        setStateValue(
                            PayerFields.FULL_NAME, fullName,
                            PayerInputValidator.FullName.errorIdOrNull(event.input)
                        )
                    is PayerInputEvent.Address ->
                        setStateValue(
                            PayerFields.ADDRESS, address,
                            PayerInputValidator.Address.errorIdOrNull(event.input)
                        )
                    is PayerInputEvent.TotalArea ->
                        setStateValue(
                            PayerFields.TOTAL_AREA, totalArea,
                            PayerInputValidator.TotalArea.errorIdOrNull(event.input)
                        )
                    is PayerInputEvent.LivingSpace ->
                        setStateValue(
                            PayerFields.LIVING_SPACE, livingSpace,
                            PayerInputValidator.LivingSpace.errorIdOrNull(event.input)
                        )
                    is PayerInputEvent.HeatedVolume ->
                        setStateValue(
                            PayerFields.HEATED_VOLUME, heatedVolume,
                            PayerInputValidator.HeatedVolume.errorIdOrNull(event.input)
                        )
                    is PayerInputEvent.PaymentDay ->
                        setStateValue(
                            PayerFields.PAYMENT_DAY, paymentDay,
                            PayerInputValidator.PaymentDay.errorIdOrNull(event.input)
                        )
                    is PayerInputEvent.PersonsNum ->
                        setStateValue(
                            PayerFields.PERSONS_NUM, personsNum,
                            PayerInputValidator.PersonsNum.errorIdOrNull(event.input)
                        )
                }
            }
    }

    override fun getInputErrorsOrNull(): List<InputError>? {
        Timber.tag(TAG).d("getInputErrorsOrNull() called")
        val inputErrors: MutableList<InputError> = mutableListOf()
        PayerInputValidator.ErcCode.errorIdOrNull(ercCode.value.value)?.let {
            inputErrors.add(InputError(fieldName = PayerFields.ERC_CODE.name, errorId = it))
        }
        PayerInputValidator.FullName.errorIdOrNull(fullName.value.value)?.let {
            inputErrors.add(InputError(fieldName = PayerFields.FULL_NAME.name, errorId = it))
        }
        PayerInputValidator.Address.errorIdOrNull(address.value.value)?.let {
            inputErrors.add(InputError(fieldName = PayerFields.ADDRESS.name, errorId = it))
        }
        PayerInputValidator.TotalArea.errorIdOrNull(totalArea.value.value)?.let {
            inputErrors.add(InputError(fieldName = PayerFields.TOTAL_AREA.name, errorId = it))
        }
        PayerInputValidator.LivingSpace.errorIdOrNull(livingSpace.value.value)?.let {
            inputErrors.add(InputError(fieldName = PayerFields.LIVING_SPACE.name, errorId = it))
        }
        PayerInputValidator.HeatedVolume.errorIdOrNull(heatedVolume.value.value)?.let {
            inputErrors.add(InputError(fieldName = PayerFields.HEATED_VOLUME.name, errorId = it))
        }
        PayerInputValidator.PaymentDay.errorIdOrNull(paymentDay.value.value)?.let {
            inputErrors.add(InputError(fieldName = PayerFields.PAYMENT_DAY.name, errorId = it))
        }
        PayerInputValidator.PersonsNum.errorIdOrNull(personsNum.value.value)?.let {
            inputErrors.add(InputError(fieldName = PayerFields.PERSONS_NUM.name, errorId = it))
        }
        return if (inputErrors.isEmpty()) null else inputErrors
    }

    override fun displayInputErrors(inputErrors: List<InputError>) {
        Timber.tag(TAG)
            .d("displayInputErrors() called: inputErrors.count = %d", inputErrors.size)
        for (error in inputErrors) {
            state[error.fieldName] = when (error.fieldName) {
                //PayerFields.ERC_CODE.name -> ercCode.update{ it.copy(errorId = error.errorId) }
                PayerFields.ERC_CODE.name -> ercCode.value.copy(errorId = error.errorId)
                PayerFields.FULL_NAME.name -> fullName.value.copy(errorId = error.errorId)
                PayerFields.ADDRESS.name -> address.value.copy(errorId = error.errorId)
                PayerFields.TOTAL_AREA.name -> totalArea.value.copy(errorId = error.errorId)
                PayerFields.LIVING_SPACE.name -> livingSpace.value.copy(errorId = error.errorId)
                PayerFields.HEATED_VOLUME.name -> heatedVolume.value.copy(errorId = error.errorId)
                PayerFields.PAYMENT_DAY.name -> paymentDay.value.copy(errorId = error.errorId)
                PayerFields.PERSONS_NUM.name -> personsNum.value.copy(errorId = error.errorId)
                else -> null
            }
        }
    }

    companion object {
        val previewModel =
            object : PayerViewModel {
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
                    focusedField: PayerFields, isFocused: Boolean
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