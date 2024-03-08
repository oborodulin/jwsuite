package com.oborodulin.jwsuite.presentation_territory.ui.housing.house.single

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
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
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.oborodulin.home.common.ui.components.field.ExposedDropdownMenuBoxComponent
import com.oborodulin.home.common.ui.components.field.SwitchComponent
import com.oborodulin.home.common.ui.components.field.TextFieldComponent
import com.oborodulin.home.common.ui.components.field.util.InputFocusRequester
import com.oborodulin.home.common.ui.components.field.util.inputProcess
import com.oborodulin.home.common.ui.components.radio.RadioBooleanComponent
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.SharedViewModeled
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_INPUT
import com.oborodulin.home.common.util.OnImeKeyAction
import com.oborodulin.jwsuite.domain.types.BuildingType
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
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single.TerritoryComboBox
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single.TerritoryViewModelImpl
import timber.log.Timber
import java.util.EnumMap

private const val TAG = "Housing.HouseView"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HouseView(
    modifier: Modifier = Modifier,
    territoryUiModel: TerritoryUi? = null,
    sharedViewModel: SharedViewModeled<ListItemModel?>?,
    territoryViewModel: TerritoryViewModelImpl = hiltViewModel(),
    viewModel: HouseViewModelImpl = hiltViewModel(),
    handleSaveAction: OnImeKeyAction
) {
    Timber.tag(TAG).d("HouseView(...) called")
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(viewModel.events, lifecycleOwner) {
        viewModel.events.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }
    territoryUiModel?.let {
        viewModel.onTextFieldEntered(HouseInputEvent.Territory(it.toTerritoriesListItem()))
        with(it) {
            viewModel.onTextFieldEntered(HouseInputEvent.Locality(this.locality.toListItemModel()))
            this.localityDistrict?.let { ld ->
                viewModel.onTextFieldEntered(HouseInputEvent.LocalityDistrict(ld.toLocalityDistrictsListItem()))
            }
            this.microdistrict?.let { md ->
                viewModel.onTextFieldEntered(HouseInputEvent.Microdistrict(md.toMicrodistrictsListItem()))
            }
        }
    }
    Timber.tag(TAG).d("House: CollectAsStateWithLifecycle for all fields")
    val locality by viewModel.locality.collectAsStateWithLifecycle()
    val street by viewModel.street.collectAsStateWithLifecycle()
    val localityDistrict by viewModel.localityDistrict.collectAsStateWithLifecycle()
    val microdistrict by viewModel.microdistrict.collectAsStateWithLifecycle()
    val territory by viewModel.territory.collectAsStateWithLifecycle()
    val zipCode by viewModel.zipCode.collectAsStateWithLifecycle()
    val houseNum by viewModel.houseNum.collectAsStateWithLifecycle()
    val houseLetter by viewModel.houseLetter.collectAsStateWithLifecycle()
    val buildingNum by viewModel.buildingNum.collectAsStateWithLifecycle()
    val buildingType by viewModel.buildingType.collectAsStateWithLifecycle()
    val isBusiness by viewModel.isBusiness.collectAsStateWithLifecycle()
    val isSecurity by viewModel.isSecurity.collectAsStateWithLifecycle()
    val isIntercom by viewModel.isIntercom.collectAsStateWithLifecycle()
    val isResidential by viewModel.isResidential.collectAsStateWithLifecycle()
    val houseEntrancesQty by viewModel.houseEntrancesQty.collectAsStateWithLifecycle()
    val floorsByEntrance by viewModel.floorsByEntrance.collectAsStateWithLifecycle()
    val roomsByHouseFloor by viewModel.roomsByHouseFloor.collectAsStateWithLifecycle()
    val estimatedRooms by viewModel.estimatedRooms.collectAsStateWithLifecycle()
    val isForeignLanguage by viewModel.isForeignLanguage.collectAsStateWithLifecycle()
    val isPrivateSector by viewModel.isPrivateSector.collectAsStateWithLifecycle()
    val houseDesc by viewModel.houseDesc.collectAsStateWithLifecycle()

    val buildingTypes by viewModel.buildingTypes.collectAsStateWithLifecycle()

    Timber.tag(TAG).d("House: Init Focus Requesters for all fields")
    val focusRequesters = EnumMap<HouseFields, InputFocusRequester>(HouseFields::class.java)
    enumValues<HouseFields>().forEach {
        focusRequesters[it] = InputFocusRequester(it, remember { FocusRequester() })
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("HouseView -> LaunchedEffect()")
        events.collect { event ->
            if (LOG_FLOW_INPUT) {
                Timber.tag(TAG).d("IF# Collect input events flow: %s", event.javaClass.name)
            }
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
            )
            .then(modifier),
        //.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LocalityComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[HouseFields.HOUSE_LOCALITY]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = HouseFields.HOUSE_LOCALITY,
                        isFocused = focusState.isFocused
                    )
                },
            enabled = territoryUiModel == null,
            inputWrapper = locality,
            onValueChange = { viewModel.onTextFieldEntered(HouseInputEvent.Locality(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        StreetComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[HouseFields.HOUSE_STREET]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = HouseFields.HOUSE_STREET, isFocused = focusState.isFocused
                    )
                },
            localityId = territoryUiModel?.locality?.id ?: locality.item?.itemId,
            localityDistrictId = territoryUiModel?.localityDistrict?.id,
            microdistrictId = territoryUiModel?.microdistrict?.id,
            inputWrapper = street,
            onValueChange = { viewModel.onTextFieldEntered(HouseInputEvent.Street(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        LocalityDistrictComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[HouseFields.HOUSE_LOCALITY_DISTRICT]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = HouseFields.HOUSE_LOCALITY_DISTRICT,
                        isFocused = focusState.isFocused
                    )
                },
            enabled = territoryUiModel == null,
            localityId = territoryUiModel?.locality?.id ?: locality.item?.itemId,
            inputWrapper = localityDistrict,
            onValueChange = { viewModel.onTextFieldEntered(HouseInputEvent.LocalityDistrict(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        MicrodistrictComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[HouseFields.HOUSE_MICRODISTRICT]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = HouseFields.HOUSE_MICRODISTRICT,
                        isFocused = focusState.isFocused
                    )
                },
            enabled = territoryUiModel == null,
            localityId = territoryUiModel?.locality?.id ?: locality.item?.itemId,
            localityDistrictId = territoryUiModel?.localityDistrict?.id
                ?: localityDistrict.item?.itemId,
            inputWrapper = microdistrict,
            onValueChange = { viewModel.onTextFieldEntered(HouseInputEvent.Microdistrict(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TerritoryComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[HouseFields.HOUSE_TERRITORY]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = HouseFields.HOUSE_TERRITORY, isFocused = focusState.isFocused
                    )
                },
            enabled = territoryUiModel == null,
            sharedViewModel = sharedViewModel,
            singleViewModel = territoryViewModel,
            inputWrapper = territory,
            onValueChange = { viewModel.onTextFieldEntered(HouseInputEvent.Territory(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[HouseFields.HOUSE_ZIP_CODE]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = HouseFields.HOUSE_ZIP_CODE, isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.house_zip_code_hint,
            leadingImageVector = Icons.Outlined.Email,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
            },
            inputWrapper = zipCode,
            onValueChange = { viewModel.onTextFieldEntered(HouseInputEvent.ZipCode(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[HouseFields.HOUSE_NUM]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = HouseFields.HOUSE_NUM, isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.house_num_hint,
            leadingPainterResId = com.oborodulin.home.common.R.drawable.ic_123_36,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
            },
            inputWrapper = houseNum,
            onValueChange = { viewModel.onTextFieldEntered(HouseInputEvent.HouseNum(it.toIntOrNull())) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[HouseFields.HOUSE_LETTER]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = HouseFields.HOUSE_LETTER, isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.house_letter_hint,
            leadingPainterResId = com.oborodulin.jwsuite.presentation.R.drawable.ic_ab_36,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
            },
            inputWrapper = houseLetter,
            onValueChange = { viewModel.onTextFieldEntered(HouseInputEvent.HouseLetter(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[HouseFields.HOUSE_BUILDING_NUM]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = HouseFields.HOUSE_BUILDING_NUM,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.house_building_num_hint,
            leadingPainterResId = com.oborodulin.home.common.R.drawable.ic_123_36,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
            },
            inputWrapper = buildingNum,
            onValueChange = { viewModel.onTextFieldEntered(HouseInputEvent.BuildingNum(it.toIntOrNull())) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )

        ExposedDropdownMenuBoxComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[HouseFields.HOUSE_BUILDING_TYPE]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = HouseFields.HOUSE_BUILDING_TYPE,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.house_building_type_hint,
            leadingPainterResId = com.oborodulin.jwsuite.presentation_geo.R.drawable.ic_signpost_36,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
            },
            inputWrapper = buildingType,
            values = buildingTypes.values.toList(), // resolve Enums to Resource
            keys = buildingTypes.keys.map { it.name }, // Enums
            onValueChange = {
                viewModel.onTextFieldEntered(HouseInputEvent.BuildingType(BuildingType.valueOf(it)))
            },
            onImeKeyAction = viewModel::moveFocusImeAction,
        )
        SwitchComponent(
            switchModifier = Modifier
                .height(90.dp)
                .focusRequester(focusRequesters[HouseFields.HOUSE_IS_BUSINESS]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = HouseFields.HOUSE_IS_BUSINESS,
                        isFocused = focusState.isFocused
                    )
                },
            painterResId = R.drawable.ic_business_center_36,
            labelResId = R.string.territory_is_business_hint,
            inputWrapper = isBusiness,
            onCheckedChange = { viewModel.onTextFieldEntered(HouseInputEvent.IsBusiness(it)) }
        )
        SwitchComponent(
            switchModifier = Modifier
                .height(90.dp)
                .focusRequester(focusRequesters[HouseFields.HOUSE_IS_SECURITY]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = HouseFields.HOUSE_IS_SECURITY,
                        isFocused = focusState.isFocused
                    )
                },
            imageVector = Icons.Outlined.Lock,
            labelResId = R.string.house_is_security_hint,
            inputWrapper = isSecurity,
            onCheckedChange = { viewModel.onTextFieldEntered(HouseInputEvent.IsSecurity(it)) }
        )
        RadioBooleanComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[HouseFields.HOUSE_IS_INTERCOM]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = HouseFields.HOUSE_IS_INTERCOM,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.is_intercom_hint,
            painterResId = R.drawable.ic_intercom_36,
            inputWrapper = isIntercom,
            onValueChange = { viewModel.onTextFieldEntered(HouseInputEvent.IsIntercom(it)) }
        )
        SwitchComponent(
            switchModifier = Modifier
                .height(90.dp)
                .focusRequester(focusRequesters[HouseFields.HOUSE_IS_RESIDENTIAL]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = HouseFields.HOUSE_IS_RESIDENTIAL,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.is_residential_hint,
            painterResId = R.drawable.ic_residential_36,
            inputWrapper = isResidential,
            onCheckedChange = { viewModel.onTextFieldEntered(HouseInputEvent.IsResidential(it)) }
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[HouseFields.HOUSE_ENTRANCES_QTY]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = HouseFields.HOUSE_ENTRANCES_QTY,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.house_entrances_qty_hint,
            leadingPainterResId = R.drawable.ic_entrance_36,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
            },
            inputWrapper = houseEntrancesQty,
            onValueChange = { viewModel.onTextFieldEntered(HouseInputEvent.HouseEntrancesQty(it.toIntOrNull())) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[HouseFields.HOUSE_FLOORS_BY_ENTRANCE]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = HouseFields.HOUSE_FLOORS_BY_ENTRANCE,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.house_floors_by_entrance_hint,
            leadingPainterResId = R.drawable.ic_floors_36,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
            },
            inputWrapper = floorsByEntrance,
            onValueChange = { viewModel.onTextFieldEntered(HouseInputEvent.FloorsByEntrance(it.toIntOrNull())) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[HouseFields.HOUSE_ROOMS_BY_FLOOR]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = HouseFields.HOUSE_ROOMS_BY_FLOOR,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.house_rooms_by_floor_hint,
            leadingPainterResId = R.drawable.ic_floor_rooms_36,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
            },
            inputWrapper = roomsByHouseFloor,
            onValueChange = { viewModel.onTextFieldEntered(HouseInputEvent.RoomsByHouseFloor(it.toIntOrNull())) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[HouseFields.HOUSE_ESTIMATED_ROOMS]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = HouseFields.HOUSE_ESTIMATED_ROOMS,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.house_estimated_rooms_hint,
            leadingPainterResId = R.drawable.ic_room_36,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
            },
            inputWrapper = estimatedRooms,
            onValueChange = { viewModel.onTextFieldEntered(HouseInputEvent.EstimatedRooms(it.toIntOrNull())) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        SwitchComponent(
            switchModifier = Modifier
                .height(90.dp)
                .focusRequester(focusRequesters[HouseFields.HOUSE_IS_FOREIGN_LANGUAGE]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = HouseFields.HOUSE_IS_FOREIGN_LANGUAGE,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.is_foreign_language_hint,
            painterResId = com.oborodulin.jwsuite.presentation.R.drawable.ic_language_36,
            inputWrapper = isForeignLanguage,
            onCheckedChange = { viewModel.onTextFieldEntered(HouseInputEvent.IsForeignLanguage(it)) }
        )
        SwitchComponent(
            switchModifier = Modifier
                .height(90.dp)
                .focusRequester(focusRequesters[HouseFields.HOUSE_IS_PRIVATE_SECTOR]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = HouseFields.HOUSE_IS_PRIVATE_SECTOR,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = com.oborodulin.jwsuite.presentation.R.string.is_private_sector_hint,
            painterResId = com.oborodulin.jwsuite.presentation.R.drawable.ic_private_sector_36,
            inputWrapper = isPrivateSector,
            onCheckedChange = { viewModel.onTextFieldEntered(HouseInputEvent.IsPrivateSector(it)) }
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[HouseFields.HOUSE_DESC]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = HouseFields.HOUSE_DESC, isFocused = focusState.isFocused
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
            inputWrapper = houseDesc,
            onValueChange = { viewModel.onTextFieldEntered(HouseInputEvent.HouseDesc(it)) },
            onImeKeyAction = handleSaveAction
        )
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewHouseView() {
    JWSuiteTheme {
        Surface {
            /*HouseView(
                localityViewModel = HouseViewModelImpl.previewModel(LocalContext.current),
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
