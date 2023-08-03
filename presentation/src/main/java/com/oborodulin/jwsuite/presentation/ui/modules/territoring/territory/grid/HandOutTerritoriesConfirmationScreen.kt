package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.grid

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.oborodulin.home.common.ui.components.field.DatePickerComponent
import com.oborodulin.home.common.ui.components.field.util.InputFocusRequester
import com.oborodulin.home.common.ui.components.field.util.inputProcess
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.jwsuite.presentation.AppState
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.rememberAppState
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.member.single.MemberComboBox
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation.util.Constants.CELL_SIZE
import kotlinx.coroutines.launch
import timber.log.Timber

private const val TAG = "Territoring.HandOutTerritoriesConfirmationScreen"

@Composable
fun HandOutTerritoriesConfirmationScreen(
    appState: AppState,
    viewModel: TerritoriesGridViewModelImpl = hiltViewModel()
) {
    Timber.tag(TAG).d("HandOutTerritoriesConfirmationScreen(...) called")
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        Timber.tag(TAG)
            .d("HandOutTerritoriesConfirmationScreen: LaunchedEffect() BEFORE collect ui state flow")
        viewModel.submitAction(TerritoriesGridUiAction.HandOutConfirmation)
    }
    viewModel.dialogTitleResId.collectAsStateWithLifecycle().value?.let { dialogTitleResId ->
        Timber.tag(TAG).d("Collect ui state flow")
        appState.actionBarSubtitle.value = stringResource(dialogTitleResId)
        JWSuiteTheme { //(darkTheme = true)
            ScaffoldComponent(
                appState = appState,
                topBarNavigationIcon = {
                    IconButton(onClick = { appState.backToBottomBarScreen() }) {
                        Icon(Icons.Outlined.ArrowBack, null)
                    }
                }
            ) { paddingValues ->
                val areInputsValid by viewModel.areInputsValid.collectAsStateWithLifecycle()
                HandOutTerritoriesConfirmationView(
                    appState = appState, paddingValues = paddingValues, viewModel = viewModel
                )
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = {
                        viewModel.onContinueClick {
                            Timber.tag(TAG)
                                .d("HandOutTerritoriesConfirmationScreen(...): Hand Out Territory Button onClick...")
                            // checks all errors
                            viewModel.onContinueClick {
                                // if success, then save and backToBottomBarScreen
                                // https://stackoverflow.com/questions/72987545/how-to-navigate-to-another-screen-after-call-a-viemodelscope-method-in-viewmodel
                                coroutineScope.launch {
                                    viewModel.submitAction(TerritoriesGridUiAction.HandOut).join()
                                    appState.backToBottomBarScreen()
                                }
                            }
                        }
                    },
                    enabled = areInputsValid
                ) { Text(text = stringResource(com.oborodulin.jwsuite.presentation.R.string.btn_hand_out_lbl)) }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HandOutTerritoriesConfirmationView(
    appState: AppState,
    paddingValues: PaddingValues? = null,
    viewModel: TerritoriesGridViewModel
) {
    Timber.tag(TAG).d("HandOutTerritoriesConfirmationView(...) called")
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(viewModel.events, lifecycleOwner) {
        viewModel.events.flowWithLifecycle(
            lifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
    }

    Timber.tag(TAG).d("CollectAsStateWithLifecycle for all hand out territories fields")
    val member by viewModel.member.collectAsStateWithLifecycle()
    val receivingDate by viewModel.receivingDate.collectAsStateWithLifecycle()
    val territories by viewModel.uiStateFlow.collectAsStateWithLifecycle()

    Timber.tag(TAG).d("Init Focus Requesters for all region fields")
    val focusRequesters: MutableMap<String, InputFocusRequester> = HashMap()
    enumValues<TerritoriesFields>().forEach {
        focusRequesters[it.name] = InputFocusRequester(it, remember { FocusRequester() })
    }

    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("HandOutTerritoriesConfirmationView(...): LaunchedEffect()")
        events.collect { event ->
            Timber.tag(TAG).d("Collect input events flow: %s", event.javaClass.name)
            inputProcess(context, focusManager, keyboardController, event, focusRequesters)
        }
    }
    val modifier = paddingValues?.let { Modifier.padding(it) } ?: Modifier.padding(4.dp)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clip(RoundedCornerShape(16.dp))
            .border(
                2.dp,
                MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(16.dp)
            )
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        member.item?.let { viewModel.onTextFieldEntered(TerritoriesInputEvent.Member(it)) }
        MemberComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[TerritoriesFields.TERRITORY_MEMBER.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = TerritoriesFields.TERRITORY_MEMBER,
                        isFocused = focusState.isFocused
                    )
                },
            sharedViewModel = appState.sharedViewModel.value,
            inputWrapper = member,
            onValueChange = { viewModel.onTextFieldEntered(TerritoriesInputEvent.Member(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        DatePickerComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[TerritoriesFields.TERRITORY_RECEIVING_DATE.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = TerritoriesFields.TERRITORY_RECEIVING_DATE,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = com.oborodulin.jwsuite.presentation.R.string.territory_receiving_date_hint,
            datePickerTitleResId = com.oborodulin.jwsuite.presentation.R.string.dlg_title_set_territory_receiving_date,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
            },
            inputWrapper = receivingDate,
            onValueChange = {
                viewModel.onTextFieldEntered(TerritoriesInputEvent.ReceivingDate(it))
            },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        Divider(thickness = 2.dp)
        LazyVerticalGrid(
            columns = GridCells.Adaptive(CELL_SIZE),
            modifier = Modifier
                .selectableGroup() // Optional, for accessibility purpose
                .padding(4.dp)
                .focusable(enabled = true),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            val checkedItems = (territories as UiState.Success).data.filter { it.isChecked }
            items(checkedItems.size) { index ->
                TerritoriesClickableGridItemComponent(
                    territory = checkedItems[index]
                )
            }
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewGroupView() {
    val ctx = LocalContext.current
    JWSuiteTheme {
        Surface {
            HandOutTerritoriesConfirmationView(
                appState = rememberAppState(),
                viewModel = TerritoriesGridViewModelImpl.previewModel(ctx)
            )
        }
    }
}
