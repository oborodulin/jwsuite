package com.oborodulin.jwsuite.presentation_territory.ui.territoring.room.single

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.compose.ui.text.input.KeyboardCapitalization
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
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_geo.ui.geo.locality.single.LocalityComboBox
import com.oborodulin.jwsuite.presentation_geo.ui.geo.localitydistrict.single.LocalityDistrictComboBox
import com.oborodulin.jwsuite.presentation_geo.ui.geo.microdistrict.single.MicrodistrictComboBox
import com.oborodulin.jwsuite.presentation_geo.ui.geo.street.single.StreetComboBox
import com.oborodulin.jwsuite.presentation_geo.ui.model.toListItemModel
import com.oborodulin.jwsuite.presentation_geo.ui.model.toLocalityDistrictsListItem
import com.oborodulin.jwsuite.presentation_geo.ui.model.toMicrodistrictsListItem
import com.oborodulin.jwsuite.presentation_territory.R
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryUi
import com.oborodulin.jwsuite.presentation_territory.ui.model.toTerritoriesListItem
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.house.single.HouseComboBox
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single.TerritoryComboBox
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single.TerritoryViewModelImpl
import timber.log.Timber
import java.util.EnumMap

private const val TAG = "Territoring.RoomView"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RoomView(
    territoryUiModel: TerritoryUi? = null,
    sharedViewModel: SharedViewModeled<ListItemModel?>?,
    territoryViewModel: TerritoryViewModelImpl = hiltViewModel(),
    viewModel: RoomViewModelImpl = hiltViewModel()
) {
    Timber.tag(TAG).d("RoomView(...) called")
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(viewModel.events, lifecycleOwner) {
        viewModel.events.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }
    territoryUiModel?.let {
        viewModel.onTextFieldEntered(RoomInputEvent.Territory(it.toTerritoriesListItem()))
        with(it) {
            viewModel.onTextFieldEntered(RoomInputEvent.Locality(this.locality.toListItemModel()))
            this.localityDistrict?.let { ld ->
                viewModel.onTextFieldEntered(RoomInputEvent.LocalityDistrict(ld.toLocalityDistrictsListItem()))
            }
            this.microdistrict?.let { md ->
                viewModel.onTextFieldEntered(RoomInputEvent.Microdistrict(md.toMicrodistrictsListItem()))
            }
        }
    }
    Timber.tag(TAG).d("Room: CollectAsStateWithLifecycle for all fields")
    val locality by viewModel.locality.collectAsStateWithLifecycle()
    val localityDistrict by viewModel.localityDistrict.collectAsStateWithLifecycle()
    val microdistrict by viewModel.microdistrict.collectAsStateWithLifecycle()
    val street by viewModel.street.collectAsStateWithLifecycle()
    val house by viewModel.house.collectAsStateWithLifecycle()
    val entrance by viewModel.entrance.collectAsStateWithLifecycle()
    val floor by viewModel.floor.collectAsStateWithLifecycle()
    val territory by viewModel.territory.collectAsStateWithLifecycle()
    val roomNum by viewModel.roomNum.collectAsStateWithLifecycle()
    val isIntercom by viewModel.isIntercom.collectAsStateWithLifecycle()
    val isResidential by viewModel.isResidential.collectAsStateWithLifecycle()
    val isForeignLanguage by viewModel.isForeignLanguage.collectAsStateWithLifecycle()
    val roomDesc by viewModel.roomDesc.collectAsStateWithLifecycle()

    Timber.tag(TAG).d("Room: Init Focus Requesters for all fields")
    val focusRequesters = EnumMap<RoomFields, InputFocusRequester>(RoomFields::class.java)
    enumValues<RoomFields>().forEach {
        focusRequesters[it] = InputFocusRequester(it, remember { FocusRequester() })
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("RoomView(...): LaunchedEffect()")
        events.collect { event ->
            Timber.tag(TAG).d("Collect input events flow: %s", event.javaClass.name)
            inputProcess(context, focusManager, keyboardController, event, focusRequesters)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            //java.lang.IllegalStateException: Vertically scrollable component was measured with an infinity maximum height constraints,
            // which is disallowed. One of the common reasons is nesting layouts like LazyColumn and Column(Modifier.verticalScroll()).
            // If you want to add a header before the list of items please add a header as a separate item() before the main items() inside the LazyColumn scope.
            // There are could be other reasons for this to happen: your ComposeView was added into a LinearLayout with some weight,
            // you applied Modifier.wrapContentSize(unbounded = true) or wrote a custom layout.
            // Please try to remove the source of infinite constraints in the hierarchy above the scrolling container.
            //.height(IntrinsicSize.Min)
            //.wrapContentSize(unbounded = true)
            .clip(RoundedCornerShape(16.dp))
            .border(
                2.dp,
                MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(16.dp)
            ),
        //.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LocalityComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[RoomFields.ROOM_LOCALITY]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = RoomFields.ROOM_LOCALITY,
                        isFocused = focusState.isFocused
                    )
                },
            enabled = territoryUiModel == null,
            inputWrapper = locality,
            onValueChange = { viewModel.onTextFieldEntered(RoomInputEvent.Locality(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        LocalityDistrictComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[RoomFields.ROOM_LOCALITY_DISTRICT]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = RoomFields.ROOM_LOCALITY_DISTRICT,
                        isFocused = focusState.isFocused
                    )
                },
            enabled = territoryUiModel == null,
            localityId = territoryUiModel?.locality?.id ?: locality.item?.itemId,
            inputWrapper = localityDistrict,
            onValueChange = { viewModel.onTextFieldEntered(RoomInputEvent.LocalityDistrict(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        MicrodistrictComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[RoomFields.ROOM_MICRODISTRICT]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = RoomFields.ROOM_MICRODISTRICT,
                        isFocused = focusState.isFocused
                    )
                },
            enabled = territoryUiModel == null,
            localityId = territoryUiModel?.locality?.id ?: locality.item?.itemId,
            localityDistrictId = territoryUiModel?.localityDistrict?.id
                ?: localityDistrict.item?.itemId,
            inputWrapper = microdistrict,
            onValueChange = { viewModel.onTextFieldEntered(RoomInputEvent.Microdistrict(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        StreetComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[RoomFields.ROOM_STREET]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = RoomFields.ROOM_STREET, isFocused = focusState.isFocused
                    )
                },
            localityId = territoryUiModel?.locality?.id ?: locality.item?.itemId,
            localityDistrictId = territoryUiModel?.localityDistrict?.id
                ?: localityDistrict.item?.itemId,
            microdistrictId = territoryUiModel?.microdistrict?.id ?: microdistrict.item?.itemId,
            inputWrapper = street,
            onValueChange = { viewModel.onTextFieldEntered(RoomInputEvent.Street(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        HouseComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[RoomFields.ROOM_HOUSE]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = RoomFields.ROOM_HOUSE, isFocused = focusState.isFocused
                    )
                },
            streetId = street.item?.itemId,
            sharedViewModel = sharedViewModel,
            inputWrapper = house,
            onValueChange = { viewModel.onTextFieldEntered(RoomInputEvent.House(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TerritoryComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[RoomFields.ROOM_TERRITORY]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = RoomFields.ROOM_TERRITORY, isFocused = focusState.isFocused
                    )
                },
            enabled = territoryUiModel == null,
            sharedViewModel = sharedViewModel,
            singleViewModel = territoryViewModel,
            inputWrapper = territory,
            onValueChange = { viewModel.onTextFieldEntered(RoomInputEvent.Territory(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[RoomFields.ROOM_NUM]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = RoomFields.ROOM_NUM, isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.room_num_hint,
            leadingPainterResId = com.oborodulin.home.common.R.drawable.ic_123_36,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
            },
            inputWrapper = roomNum,
            onValueChange = { viewModel.onTextFieldEntered(RoomInputEvent.RoomNum(it.toIntOrNull())) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        RadioBooleanComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[RoomFields.ROOM_IS_INTERCOM]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = RoomFields.ROOM_IS_INTERCOM,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.is_intercom_hint,
            painterResId = R.drawable.ic_intercom_36,
            inputWrapper = isIntercom,
            onValueChange = { viewModel.onTextFieldEntered(RoomInputEvent.IsIntercom(it)) }
        )
        SwitchComponent(
            switchModifier = Modifier
                .height(90.dp)
                .focusRequester(focusRequesters[RoomFields.ROOM_IS_RESIDENTIAL]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = RoomFields.ROOM_IS_RESIDENTIAL,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.is_residential_hint,
            painterResId = R.drawable.ic_residential_36,
            inputWrapper = isResidential,
            onCheckedChange = { viewModel.onTextFieldEntered(RoomInputEvent.IsResidential(it)) }
        )
        SwitchComponent(
            switchModifier = Modifier
                .height(90.dp)
                .focusRequester(focusRequesters[RoomFields.ROOM_IS_FOREIGN_LANGUAGE]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = RoomFields.ROOM_IS_FOREIGN_LANGUAGE,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.is_foreign_language_hint,
            painterResId = com.oborodulin.jwsuite.presentation.R.drawable.ic_language_36,
            inputWrapper = isForeignLanguage,
            onCheckedChange = { viewModel.onTextFieldEntered(RoomInputEvent.IsForeignLanguage(it)) }
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[RoomFields.ROOM_DESC]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = RoomFields.ROOM_DESC, isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.house_desc_hint,
            leadingPainterResId = R.drawable.ic_text_snippet_36,
            keyboardOptions = remember {
                KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                )
            },
            inputWrapper = roomDesc,
            onValueChange = { viewModel.onTextFieldEntered(RoomInputEvent.RoomDesc(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewRoomView() {
    JWSuiteTheme {
        Surface {
            /*RoomView(
                localityViewModel = RoomViewModelImpl.previewModel(LocalContext.current),
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
