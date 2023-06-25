package com.oborodulin.jwsuite.presentation.ui.modules.congregating.congregation.single

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
class CongregationViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val payerUseCases: PayerUseCases,
    private val congregationConverter: CongregationConverter,
    private val payerUiToPayerMapper: PayerUiToPayerMapper
) : CongregationViewModel,
    SingleViewModel<CongregationUi, UiState<CongregationUi>, CongregationUiAction, UiSingleEvent, CongregationFields, InputWrapper>(
        state,
        CongregationFields.ERC_CODE
    ) {
    private val payerId: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            CongregationFields.PAYER_ID.name,
            InputWrapper()
        )
    }
    override val ercCode: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            CongregationFields.ERC_CODE.name,
            InputWrapper()
        )
    }
    override val fullName: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            CongregationFields.FULL_NAME.name,
            InputWrapper()
        )
    }
    override val address: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            CongregationFields.ADDRESS.name,
            InputWrapper()
        )
    }
    override val totalArea: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            CongregationFields.TOTAL_AREA.name,
            InputWrapper()
        )
    }
    override val livingSpace: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            CongregationFields.LIVING_SPACE.name,
            InputWrapper()
        )
    }
    override val heatedVolume: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            CongregationFields.HEATED_VOLUME.name,
            InputWrapper()
        )
    }
    override val paymentDay: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            CongregationFields.PAYMENT_DAY.name,
            InputWrapper()
        )
    }
    override val personsNum: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            CongregationFields.PERSONS_NUM.name,
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

    override suspend fun handleAction(action: CongregationUiAction): Job {
        Timber.tag(TAG).d("handleAction(PayerUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is CongregationUiAction.Create -> {
                submitState(UiState.Success(CongregationUi()))
            }
            is CongregationUiAction.Load -> {
                loadPayer(action.payerId)
            }
            is CongregationUiAction.Save -> {
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

    override fun stateInputFields() = enumValues<CongregationFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: Any): Job? {
        super.initFieldStatesByUiModel(uiModel)
        val congregationUi = uiModel as CongregationUi
        Timber.tag(TAG)
            .d("initFieldStatesByUiModel(PayerModel) called: payerModel = %s", congregationUi)
        congregationUi.id?.let {
            initStateValue(CongregationFields.PAYER_ID, payerId, it.toString())
        }
        initStateValue(CongregationFields.ERC_CODE, ercCode, congregationUi.congregationNum)
        initStateValue(CongregationFields.FULL_NAME, fullName, congregationUi.congregationName)
        initStateValue(CongregationFields.ADDRESS, address, congregationUi.territoryMark)
        initStateValue(CongregationFields.TOTAL_AREA, totalArea, congregationUi.totalArea?.toString() ?: "")
        initStateValue(
            CongregationFields.LIVING_SPACE,
            livingSpace,
            congregationUi.livingSpace?.toString() ?: ""
        )
        initStateValue(
            CongregationFields.HEATED_VOLUME,
            heatedVolume,
            congregationUi.heatedVolume?.toString() ?: ""
        )
        initStateValue(CongregationFields.PAYMENT_DAY, paymentDay, congregationUi.paymentDay.toString())
        initStateValue(CongregationFields.PERSONS_NUM, personsNum, congregationUi.personsNum.toString())
        return null
    }

    override suspend fun observeInputEvents() {
        Timber.tag(TAG).d("observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is CongregationInputEvent.ErcCode ->
                        when (CongregationInputValidator.ErcCode.errorIdOrNull(event.input)) {
                            null -> setStateValue(CongregationFields.ERC_CODE, ercCode, event.input, true)
                            else -> setStateValue(CongregationFields.ERC_CODE, ercCode, event.input)
                        }
                    is CongregationInputEvent.FullName ->
                        when (CongregationInputValidator.FullName.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                CongregationFields.FULL_NAME, fullName, event.input,
                                true
                            )
                            else -> setStateValue(CongregationFields.FULL_NAME, fullName, event.input)
                        }
                    is CongregationInputEvent.Address ->
                        when (CongregationInputValidator.Address.errorIdOrNull(event.input)) {
                            null -> setStateValue(CongregationFields.ADDRESS, address, event.input, true)
                            else -> setStateValue(CongregationFields.ADDRESS, address, event.input)
                        }
                    is CongregationInputEvent.TotalArea ->
                        when (CongregationInputValidator.TotalArea.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                CongregationFields.TOTAL_AREA, totalArea, event.input, true
                            )
                            else -> setStateValue(CongregationFields.TOTAL_AREA, totalArea, event.input)
                        }
                    is CongregationInputEvent.LivingSpace ->
                        when (CongregationInputValidator.LivingSpace.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                CongregationFields.LIVING_SPACE, livingSpace, event.input, true
                            )
                            else -> setStateValue(
                                CongregationFields.LIVING_SPACE, livingSpace, event.input
                            )
                        }
                    is CongregationInputEvent.HeatedVolume ->
                        when (CongregationInputValidator.HeatedVolume.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                CongregationFields.HEATED_VOLUME, heatedVolume, event.input, true
                            )
                            else -> setStateValue(
                                CongregationFields.HEATED_VOLUME, heatedVolume, event.input
                            )
                        }
                    is CongregationInputEvent.PaymentDay ->
                        when (CongregationInputValidator.PaymentDay.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                CongregationFields.PAYMENT_DAY, paymentDay, event.input, true
                            )
                            else -> setStateValue(CongregationFields.PAYMENT_DAY, paymentDay, event.input)
                        }
                    is CongregationInputEvent.PersonsNum ->
                        when (CongregationInputValidator.PersonsNum.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                CongregationFields.PERSONS_NUM, personsNum, event.input, true
                            )
                            else -> setStateValue(CongregationFields.PERSONS_NUM, personsNum, event.input)
                        }
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is CongregationInputEvent.ErcCode ->
                        setStateValue(
                            CongregationFields.ERC_CODE, ercCode,
                            CongregationInputValidator.ErcCode.errorIdOrNull(event.input)
                        )
                    is CongregationInputEvent.FullName ->
                        setStateValue(
                            CongregationFields.FULL_NAME, fullName,
                            CongregationInputValidator.FullName.errorIdOrNull(event.input)
                        )
                    is CongregationInputEvent.Address ->
                        setStateValue(
                            CongregationFields.ADDRESS, address,
                            CongregationInputValidator.Address.errorIdOrNull(event.input)
                        )
                    is CongregationInputEvent.TotalArea ->
                        setStateValue(
                            CongregationFields.TOTAL_AREA, totalArea,
                            CongregationInputValidator.TotalArea.errorIdOrNull(event.input)
                        )
                    is CongregationInputEvent.LivingSpace ->
                        setStateValue(
                            CongregationFields.LIVING_SPACE, livingSpace,
                            CongregationInputValidator.LivingSpace.errorIdOrNull(event.input)
                        )
                    is CongregationInputEvent.HeatedVolume ->
                        setStateValue(
                            CongregationFields.HEATED_VOLUME, heatedVolume,
                            CongregationInputValidator.HeatedVolume.errorIdOrNull(event.input)
                        )
                    is CongregationInputEvent.PaymentDay ->
                        setStateValue(
                            CongregationFields.PAYMENT_DAY, paymentDay,
                            CongregationInputValidator.PaymentDay.errorIdOrNull(event.input)
                        )
                    is CongregationInputEvent.PersonsNum ->
                        setStateValue(
                            CongregationFields.PERSONS_NUM, personsNum,
                            CongregationInputValidator.PersonsNum.errorIdOrNull(event.input)
                        )
                }
            }
    }

    override fun getInputErrorsOrNull(): List<InputError>? {
        Timber.tag(TAG).d("getInputErrorsOrNull() called")
        val inputErrors: MutableList<InputError> = mutableListOf()
        CongregationInputValidator.ErcCode.errorIdOrNull(ercCode.value.value)?.let {
            inputErrors.add(InputError(fieldName = CongregationFields.ERC_CODE.name, errorId = it))
        }
        CongregationInputValidator.FullName.errorIdOrNull(fullName.value.value)?.let {
            inputErrors.add(InputError(fieldName = CongregationFields.FULL_NAME.name, errorId = it))
        }
        CongregationInputValidator.Address.errorIdOrNull(address.value.value)?.let {
            inputErrors.add(InputError(fieldName = CongregationFields.ADDRESS.name, errorId = it))
        }
        CongregationInputValidator.TotalArea.errorIdOrNull(totalArea.value.value)?.let {
            inputErrors.add(InputError(fieldName = CongregationFields.TOTAL_AREA.name, errorId = it))
        }
        CongregationInputValidator.LivingSpace.errorIdOrNull(livingSpace.value.value)?.let {
            inputErrors.add(InputError(fieldName = CongregationFields.LIVING_SPACE.name, errorId = it))
        }
        CongregationInputValidator.HeatedVolume.errorIdOrNull(heatedVolume.value.value)?.let {
            inputErrors.add(InputError(fieldName = CongregationFields.HEATED_VOLUME.name, errorId = it))
        }
        CongregationInputValidator.PaymentDay.errorIdOrNull(paymentDay.value.value)?.let {
            inputErrors.add(InputError(fieldName = CongregationFields.PAYMENT_DAY.name, errorId = it))
        }
        CongregationInputValidator.PersonsNum.errorIdOrNull(personsNum.value.value)?.let {
            inputErrors.add(InputError(fieldName = CongregationFields.PERSONS_NUM.name, errorId = it))
        }
        return if (inputErrors.isEmpty()) null else inputErrors
    }

    override fun displayInputErrors(inputErrors: List<InputError>) {
        Timber.tag(TAG)
            .d("displayInputErrors() called: inputErrors.count = %d", inputErrors.size)
        for (error in inputErrors) {
            state[error.fieldName] = when (error.fieldName) {
                //PayerFields.ERC_CODE.name -> ercCode.update{ it.copy(errorId = error.errorId) }
                CongregationFields.ERC_CODE.name -> ercCode.value.copy(errorId = error.errorId)
                CongregationFields.FULL_NAME.name -> fullName.value.copy(errorId = error.errorId)
                CongregationFields.ADDRESS.name -> address.value.copy(errorId = error.errorId)
                CongregationFields.TOTAL_AREA.name -> totalArea.value.copy(errorId = error.errorId)
                CongregationFields.LIVING_SPACE.name -> livingSpace.value.copy(errorId = error.errorId)
                CongregationFields.HEATED_VOLUME.name -> heatedVolume.value.copy(errorId = error.errorId)
                CongregationFields.PAYMENT_DAY.name -> paymentDay.value.copy(errorId = error.errorId)
                CongregationFields.PERSONS_NUM.name -> personsNum.value.copy(errorId = error.errorId)
                else -> null
            }
        }
    }

    companion object {
        val previewModel =
            object : CongregationViewModel {
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
                    focusedField: CongregationFields, isFocused: Boolean
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