package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorystreet.single

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.oborodulin.home.common.ui.components.field.SwitchComponent
import com.oborodulin.home.common.ui.components.field.TextFieldComponent
import com.oborodulin.home.common.ui.components.field.util.InputFocusRequester
import com.oborodulin.home.common.ui.components.field.util.inputProcess
import com.oborodulin.home.common.ui.components.radio.RadioBooleanComponent
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.SharedViewModeled
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_INPUT
import com.oborodulin.home.common.util.OnImeKeyAction
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_geo.ui.geo.street.list.StreetsListUiAction
import com.oborodulin.jwsuite.presentation_geo.ui.geo.street.single.StreetComboBox
import com.oborodulin.jwsuite.presentation_territory.R
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryStreetUiModel
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single.TerritoryComboBox
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single.TerritoryViewModel
import timber.log.Timber
import java.util.EnumMap

private const val TAG = "Territoring.TerritoryStreetView"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TerritoryStreetView(
    uiModel: TerritoryStreetUiModel,
    sharedViewModel: SharedViewModeled<ListItemModel?>?,
    territoryViewModel: TerritoryViewModel,
    viewModel: TerritoryStreetViewModelImpl = hiltViewModel(),
    onConfirmShowAlertChange: (Boolean) -> Unit,
    onConfirmTextChange: (String) -> Unit,
    handleSaveAction: OnImeKeyAction
) {
    Timber.tag(TAG).d("TerritoryStreetView(...) called")
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(viewModel.events, lifecycleOwner) {
        viewModel.events.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }

    Timber.tag(TAG).d("Territory Street: CollectAsStateWithLifecycle for all fields")
    val territory by viewModel.territory.collectAsStateWithLifecycle()
    val street by viewModel.street.collectAsStateWithLifecycle()
    val isPrivateSector by viewModel.isPrivateSector.collectAsStateWithLifecycle()
    val isEvenSide by viewModel.isEvenSide.collectAsStateWithLifecycle()
    val estimatedHouses by viewModel.estimatedHouses.collectAsStateWithLifecycle()
    val isNeedAddEstHouses by viewModel.isNeedAddEstHouses.collectAsStateWithLifecycle()

    Timber.tag(TAG).d("Territory Street: Init Focus Requesters for all fields")
    val focusRequesters =
        EnumMap<TerritoryStreetFields, InputFocusRequester>(TerritoryStreetFields::class.java)
    enumValues<TerritoryStreetFields>().forEach {
        focusRequesters[it] = InputFocusRequester(it, remember { FocusRequester() })
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("TerritoryStreetView -> LaunchedEffect()")
        events.collect { event ->
            if (LOG_FLOW_INPUT) Timber.tag(TAG)
                .d("IF# Collect input events flow: %s", event.javaClass.name)
            inputProcess(context, focusManager, keyboardController, event, focusRequesters)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            //.height(IntrinsicSize.Min)
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
        TerritoryComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[TerritoryStreetFields.TERRITORY_STREET_TERRITORY]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = TerritoryStreetFields.TERRITORY_STREET_TERRITORY,
                        isFocused = focusState.isFocused
                    )
                },
            enabled = false,
            sharedViewModel = sharedViewModel,
            singleViewModel = territoryViewModel,
            inputWrapper = territory,
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        StreetComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[TerritoryStreetFields.TERRITORY_STREET_STREET]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = TerritoryStreetFields.TERRITORY_STREET_STREET,
                        isFocused = focusState.isFocused
                    )
                },
            loadListUiAction = StreetsListUiAction.LoadForTerritory(
                localityId = uiModel.territory.locality.id!!,
                localityDistrictId = uiModel.territory.localityDistrict?.id,
                microdistrictId = uiModel.territory.microdistrict?.id,
                excludes = uiModel.streets.map { it.id }
            ),
            inputWrapper = street,
            onValueChange = { viewModel.onTextFieldEntered(TerritoryStreetInputEvent.Street(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        RadioBooleanComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[TerritoryStreetFields.TERRITORY_STREET_IS_PRIVATE_SECTOR]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = TerritoryStreetFields.TERRITORY_STREET_IS_PRIVATE_SECTOR,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = com.oborodulin.jwsuite.presentation.R.string.is_private_sector_hint,
            painterResId = com.oborodulin.jwsuite.presentation.R.drawable.ic_private_sector_36,
            inputWrapper = isPrivateSector,
            onValueChange = {
                viewModel.onTextFieldEntered(TerritoryStreetInputEvent.IsPrivateSector(it))
            }
        )
        RadioBooleanComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[TerritoryStreetFields.TERRITORY_STREET_IS_EVEN_SIDE]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = TerritoryStreetFields.TERRITORY_STREET_IS_EVEN_SIDE,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.territory_street_is_even_side_hint,
            painterResId = R.drawable.ic_street_side_36,
            inputWrapper = isEvenSide,
            onValueChange = { viewModel.onTextFieldEntered(TerritoryStreetInputEvent.IsEvenSide(it)) }
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[TerritoryStreetFields.TERRITORY_STREET_EST_HOUSES]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = TerritoryStreetFields.TERRITORY_STREET_EST_HOUSES,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.territory_street_estimated_houses_hint,
            leadingImageVector = Icons.Outlined.Home,
            keyboardOptions = remember {
                KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword, imeAction = ImeAction.Next
                )
            },
            //  visualTransformation = ::creditCardFilter,
            inputWrapper = estimatedHouses,
            onValueChange = {
                viewModel.onTextFieldEntered(TerritoryStreetInputEvent.EstHouses(it))
                if (it.isEmpty()) {
                    viewModel.onTextFieldEntered(TerritoryStreetInputEvent.IsNeedAddEstHouses(false))
                    onConfirmShowAlertChange(false)
                    onConfirmTextChange("")
                }
            },
            onImeKeyAction = viewModel::moveFocusImeAction //handleSaveAction
        )
        val areNeedAddEstHousesValid by viewModel.areNeedAddEstHousesValid.collectAsStateWithLifecycle()
        val confirmText =
            stringResource(
                R.string.dlg_confirm_done_territory_street_is_need_add_est_houses,
                street.item?.streetFullName.orEmpty(),
                estimatedHouses.value.ifEmpty { "0" }.toInt()
            )
        SwitchComponent(
            switchModifier = Modifier
                .height(90.dp)
                .focusRequester(focusRequesters[TerritoryStreetFields.TERRITORY_STREET_IS_NEED_ADD_EST_HOUSES]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = TerritoryStreetFields.TERRITORY_STREET_IS_NEED_ADD_EST_HOUSES,
                        isFocused = focusState.isFocused
                    )
                },
            enabled = areNeedAddEstHousesValid,
            painterResId = R.drawable.ic_add_house_36,
            labelResId = R.string.territory_street_is_need_add_est_houses_hint,
            inputWrapper = isNeedAddEstHouses,
            onCheckedChange = {
                viewModel.onTextFieldEntered(TerritoryStreetInputEvent.IsNeedAddEstHouses(it))
                onConfirmShowAlertChange(it)
                onConfirmTextChange(confirmText)
            }
        )
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewTerritoryStreetView() {
    JWSuiteTheme {
        Surface {
            /*TerritoryStreetView(
                localityViewModel = TerritoryStreetViewModelImpl.previewModel(LocalContext.current),
                regionsListViewModel = RegionsListViewModelImpl.previewModel(LocalContext.current),
                regionViewModel = RegionViewModelImpl.previewModel(LocalContext.current),
                regionDistrictsListViewModel = RegionDistrictsListViewModelImpl.previewModel(
                    LocalContext.current
                ),
                regionDistrictViewModel = RegionDistrictViewModelImpl.previewModel(LocalContext.current)
            )*/
        }
    }
}
