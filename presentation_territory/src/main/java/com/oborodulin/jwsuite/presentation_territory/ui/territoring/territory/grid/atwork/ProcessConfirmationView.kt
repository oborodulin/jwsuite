package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid.atwork

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.oborodulin.home.common.ui.components.field.DatePickerComponent
import com.oborodulin.home.common.ui.components.field.util.InputFocusRequester
import com.oborodulin.home.common.ui.components.field.util.inputProcess
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.SharedViewModeled
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_INPUT
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_congregation.ui.FavoriteCongregationViewModelImpl
import com.oborodulin.jwsuite.presentation_territory.R
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid.TerritoriesClickableGridItemComponent
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid.TerritoriesFields
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid.TerritoriesGridViewModel
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid.TerritoriesGridViewModelImpl
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid.TerritoriesInputEvent
import com.oborodulin.jwsuite.presentation_territory.util.Constants.CELL_SIZE
import timber.log.Timber
import java.util.EnumMap

private const val TAG = "Territoring.ProcessConfirmationView"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ProcessConfirmationView(
    sharedViewModel: SharedViewModeled<ListItemModel?>?,
    paddingValues: PaddingValues? = null,
    viewModel: TerritoriesGridViewModel
) {
    Timber.tag(TAG).d("ProcessConfirmationView(...) called")
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(viewModel.events, lifecycleOwner) {
        viewModel.events.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }

    Timber.tag(TAG)
        .d("At Work Territories Confirmation: CollectAsStateWithLifecycle for all fields")
    val deliveryDate by viewModel.deliveryDate.collectAsStateWithLifecycle()
    val checkedTerritories by viewModel.checkedListItems.collectAsStateWithLifecycle()

    Timber.tag(TAG).d("At Work Territories Confirmation: Init Focus Requesters for all fields")
    val focusRequesters =
        EnumMap<TerritoriesFields, InputFocusRequester>(TerritoriesFields::class.java)
    enumValues<TerritoriesFields>().forEach {
        focusRequesters[it] = InputFocusRequester(it, remember { FocusRequester() })
    }

    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("ProcessConfirmationView -> LaunchedEffect()")
        events.collect { event ->
            if (LOG_FLOW_INPUT) Timber.tag(TAG)
                .d("IF# Collect input events flow: %s", event.javaClass.name)
            inputProcess(context, focusManager, keyboardController, event, focusRequesters)
        }
    }
    val modifier = paddingValues?.let { Modifier.padding(it) } ?: Modifier.padding(4.dp)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(max = 350.dp)
            //java.lang.IllegalStateException: Asking for intrinsic measurements of SubcomposeLayout layouts is not supported. This includes components that are built on top of SubcomposeLayout, such as lazy lists, BoxWithConstraints, TabRow, etc. To mitigate this:
            //- if intrinsic measurements are used to achieve 'match parent' sizing,, consider replacing the parent of the component with a custom layout which controls the order in which children are measured, making intrinsic measurement not needed
            //- adding a size modifier to the component, in order to fast return the queried intrinsic measurement.
            //.height(IntrinsicSize.Min)
            .clip(RoundedCornerShape(16.dp))
            .border(
                2.dp,
                MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(16.dp)
            ),
        //java.lang.IllegalStateException: Vertically scrollable component was measured with an infinity maximum height constraints, which is disallowed. One of the common reasons is nesting layouts like LazyColumn and Column(Modifier.verticalScroll()). If you want to add a header before the list of items please add a header as a separate item() before the main items() inside the LazyColumn scope. There are could be other reasons for this to happen: your ComposeView was added into a LinearLayout with some weight, you applied Modifier.wrapContentSize(unbounded = true) or wrote a custom layout. Please try to remove the source of infinite constraints in the hierarchy above the scrolling container.
        //.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DatePickerComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[TerritoriesFields.TERRITORY_DELIVERY_DATE]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = TerritoriesFields.TERRITORY_DELIVERY_DATE,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.territory_delivery_date_hint,
            datePickerTitleResId = R.string.date_dlg_title_set_territory_delivery,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
            },
            inputWrapper = deliveryDate,
            onValueChange = { viewModel.onTextFieldEntered(TerritoriesInputEvent.DeliveryDate(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        LazyVerticalGrid(
            columns = GridCells.Adaptive(CELL_SIZE),
            modifier = Modifier
                .selectableGroup() // Optional, for accessibility purpose
                .padding(4.dp)
                .focusable(enabled = true)
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(checkedTerritories.size) { index ->
                TerritoriesClickableGridItemComponent(
                    territory = checkedTerritories[index],
                    onChecked = { viewModel.observeCheckedListItems() }
                )
            }
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewProcessConfirmationView() {
    val ctx = LocalContext.current
    JWSuiteTheme {
        Surface {
            ProcessConfirmationView(
                //appState = rememberAppState(),
                sharedViewModel = FavoriteCongregationViewModelImpl.previewModel,
                viewModel = TerritoriesGridViewModelImpl.previewModel(ctx)
            )
        }
    }
}
