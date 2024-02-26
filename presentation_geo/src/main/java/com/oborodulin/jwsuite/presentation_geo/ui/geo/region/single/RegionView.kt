package com.oborodulin.jwsuite.presentation_geo.ui.geo.region.single

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.oborodulin.home.common.ui.components.field.ExposedDropdownMenuBoxComponent
import com.oborodulin.home.common.ui.components.field.TextFieldComponent
import com.oborodulin.home.common.ui.components.field.util.InputFocusRequester
import com.oborodulin.home.common.ui.components.field.util.inputProcess
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_INPUT
import com.oborodulin.home.common.util.OnImeKeyAction
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_geo.ui.geo.country.single.CountryComboBox
import timber.log.Timber
import java.util.EnumMap

private const val TAG = "Geo.RegionView"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegionView(viewModel: RegionViewModel, handleSaveAction: OnImeKeyAction) {
    Timber.tag(TAG).d("RegionView(...) called")
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(viewModel.events, lifecycleOwner) {
        viewModel.events.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }

    Timber.tag(TAG).d("Region: CollectAsStateWithLifecycle for all fields")
    val country by viewModel.country.collectAsStateWithLifecycle()
    val regionCode by viewModel.regionCode.collectAsStateWithLifecycle()
    val regionType by viewModel.regionType.collectAsStateWithLifecycle()
    val regionName by viewModel.regionName.collectAsStateWithLifecycle()
    val regionGeocode by viewModel.regionGeocode.collectAsStateWithLifecycle()
    val regionOsmId by viewModel.regionOsmId.collectAsStateWithLifecycle()
    val latitude by viewModel.latitude.collectAsStateWithLifecycle()
    val longitude by viewModel.longitude.collectAsStateWithLifecycle()

    val regionTypes by viewModel.regionTypes.collectAsStateWithLifecycle()

    Timber.tag(TAG).d("Region: Init Focus Requesters for all fields")
    val focusRequesters = EnumMap<RegionFields, InputFocusRequester>(RegionFields::class.java)
    enumValues<RegionFields>().forEach {
        focusRequesters[it] = InputFocusRequester(it, remember { FocusRequester() })
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("RegionView -> LaunchedEffect()")
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
        CountryComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[RegionFields.REGION_COUNTRY]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = RegionFields.REGION_COUNTRY,
                        isFocused = focusState.isFocused
                    )
                },
            enabled = regionOsmId.value.isEmpty(),
            inputWrapper = country,
            onValueChange = { viewModel.onTextFieldEntered(RegionInputEvent.Country(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[RegionFields.REGION_CODE]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = RegionFields.REGION_CODE,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.code_hint,
            helperResId = com.oborodulin.jwsuite.presentation_geo.R.string.region_code_helper,
            leadingPainterResId = com.oborodulin.home.common.R.drawable.ic_123_36,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
            },
            inputWrapper = regionCode,
            onValueChange = { viewModel.onTextFieldEntered(RegionInputEvent.RegionCode(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        ExposedDropdownMenuBoxComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[RegionFields.REGION_TYPE]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = RegionFields.REGION_TYPE,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.type_hint,
            leadingPainterResId = com.oborodulin.jwsuite.presentation_geo.R.drawable.ic_region_type_36,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
            },
            inputWrapper = regionType,
            values = regionTypes.values.toList(), // resolve Enums to Resource
            keys = regionTypes.keys.map { it.name }, // Enums
            onValueChange = { viewModel.onTextFieldEntered(RegionInputEvent.RegionType(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction,
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[RegionFields.REGION_NAME]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = RegionFields.REGION_NAME,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.name_hint,
            helperResId = com.oborodulin.jwsuite.presentation_geo.R.string.region_name_helper,
            leadingPainterResId = R.drawable.ic_abc_36,
            keyboardOptions = remember {
                KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                )
            },
            //  visualTransformation = ::creditCardFilter,
            inputWrapper = regionName,
            onValueChange = { viewModel.onTextFieldEntered(RegionInputEvent.RegionName(it)) },
            onImeKeyAction = handleSaveAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[RegionFields.REGION_GEOCODE]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = RegionFields.REGION_GEOCODE,
                        isFocused = focusState.isFocused
                    )
                },
            enabled = false,
            labelResId = com.oborodulin.jwsuite.presentation_geo.R.string.geocode_hint,
            helperResId = com.oborodulin.jwsuite.presentation_geo.R.string.geocode_helper,
            leadingPainterResId = R.drawable.ic_abc_36,
            keyboardOptions = remember {
                KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                )
            },
            inputWrapper = regionGeocode,
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[RegionFields.REGION_OSM_ID]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = RegionFields.REGION_OSM_ID,
                        isFocused = focusState.isFocused
                    )
                },
            enabled = false,
            labelResId = com.oborodulin.jwsuite.presentation_geo.R.string.osm_id_hint,
            helperResId = com.oborodulin.jwsuite.presentation_geo.R.string.osm_id_helper,
            leadingPainterResId = com.oborodulin.home.common.R.drawable.ic_123_36,
            keyboardOptions = remember {
                KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Next
                )
            },
            inputWrapper = regionOsmId,
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[RegionFields.REGION_LATITUDE]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = RegionFields.REGION_LATITUDE,
                        isFocused = focusState.isFocused
                    )
                },
            enabled = false,
            labelResId = com.oborodulin.jwsuite.presentation_geo.R.string.latitude_hint,
            leadingImageVector = Icons.Outlined.LocationOn,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
            },
            inputWrapper = latitude,
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[RegionFields.REGION_LONGITUDE]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = RegionFields.REGION_LONGITUDE,
                        isFocused = focusState.isFocused
                    )
                },
            enabled = false,
            labelResId = com.oborodulin.jwsuite.presentation_geo.R.string.longitude_hint,
            leadingImageVector = Icons.Outlined.LocationOn,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
            },
            inputWrapper = longitude,
            onImeKeyAction = viewModel::moveFocusImeAction
        )
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewRegionView() {
    JWSuiteTheme {
        Surface {
            RegionView(viewModel = RegionViewModelImpl.previewModel(LocalContext.current)) {}
        }
    }
}
