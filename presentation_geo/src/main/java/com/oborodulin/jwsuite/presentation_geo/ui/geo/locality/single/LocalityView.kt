package com.oborodulin.jwsuite.presentation_geo.ui.geo.locality.single

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
import com.oborodulin.home.common.ui.components.field.TextFieldComponent
import com.oborodulin.home.common.ui.components.field.util.InputFocusRequester
import com.oborodulin.home.common.ui.components.field.util.inputProcess
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_INPUT
import com.oborodulin.home.common.util.OnImeKeyAction
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_geo.ui.geo.country.single.CountryComboBox
import com.oborodulin.jwsuite.presentation_geo.ui.geo.region.single.RegionComboBox
import com.oborodulin.jwsuite.presentation_geo.ui.geo.regiondistrict.single.RegionDistrictComboBox
import timber.log.Timber
import java.util.EnumMap

private const val TAG = "Geo.LocalityView"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LocalityView(
    viewModel: LocalityViewModelImpl = hiltViewModel(),
    handleSaveAction: OnImeKeyAction
) {
    Timber.tag(TAG).d("LocalityView(...) called")
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(viewModel.events, lifecycleOwner) {
        viewModel.events.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }

    Timber.tag(TAG).d("Locality: CollectAsStateWithLifecycle for all fields")
    val country by viewModel.country.collectAsStateWithLifecycle()
    val region by viewModel.region.collectAsStateWithLifecycle()
    val regionDistrict by viewModel.regionDistrict.collectAsStateWithLifecycle()
    val localityCode by viewModel.localityCode.collectAsStateWithLifecycle()
    val localityShortName by viewModel.localityShortName.collectAsStateWithLifecycle()
    val localityType by viewModel.localityType.collectAsStateWithLifecycle()
    val localityName by viewModel.localityName.collectAsStateWithLifecycle()

    val localityTypes by viewModel.localityTypes.collectAsStateWithLifecycle()

    Timber.tag(TAG).d("Locality: Init Focus Requesters for all fields")
    val focusRequesters = EnumMap<LocalityFields, InputFocusRequester>(LocalityFields::class.java)
    enumValues<LocalityFields>().forEach {
        focusRequesters[it] = InputFocusRequester(it, remember { FocusRequester() })
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("LocalityView -> LaunchedEffect()")
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
                .focusRequester(focusRequesters[LocalityFields.LOCALITY_COUNTRY]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = LocalityFields.LOCALITY_COUNTRY,
                        isFocused = focusState.isFocused
                    )
                },
            //enabled = districtOsmId.value.isEmpty(),
            inputWrapper = country,
            onValueChange = { viewModel.onTextFieldEntered(LocalityInputEvent.Country(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        RegionComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[LocalityFields.LOCALITY_REGION]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = LocalityFields.LOCALITY_REGION,
                        isFocused = focusState.isFocused
                    )
                },
            countryId = country.item?.itemId,
            inputWrapper = region,
            onValueChange = { viewModel.onTextFieldEntered(LocalityInputEvent.Region(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        RegionDistrictComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[LocalityFields.LOCALITY_REGION_DISTRICT]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = LocalityFields.LOCALITY_REGION_DISTRICT,
                        isFocused = focusState.isFocused
                    )
                },
            regionId = region.item?.itemId,
            inputWrapper = regionDistrict,
            onValueChange = { viewModel.onTextFieldEntered(LocalityInputEvent.RegionDistrict(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        val pattern = remember { Regex("^\\d+\$") }
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[LocalityFields.LOCALITY_CODE]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = LocalityFields.LOCALITY_CODE,
                        isFocused = focusState.isFocused
                    )
                },
            placeholderResId = com.oborodulin.jwsuite.presentation_geo.R.string.locality_code_placeholder,
            labelResId = R.string.code_hint,
            helperResId = com.oborodulin.jwsuite.presentation_geo.R.string.locality_code_helper,
            leadingPainterResId = com.oborodulin.home.common.R.drawable.ic_123_36,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
            },
            inputWrapper = localityCode,
            onValueChange = {
                //https://stackoverflow.com/questions/73400126/restrict-only-numbers-in-textfield-in-jetpack-compose
                if (it.isEmpty() || it.matches(pattern)) {
                    viewModel.onTextFieldEntered(LocalityInputEvent.LocalityCode(it))
                }
            },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[LocalityFields.LOCALITY_SHORT_NAME]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = LocalityFields.LOCALITY_SHORT_NAME,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.short_name_hint,
            leadingPainterResId = R.drawable.ic_ab_36,
            keyboardOptions = remember {
                KeyboardOptions(
                    capitalization = KeyboardCapitalization.Characters,
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                )
            },
            inputWrapper = localityShortName,
            maxLength = 4,
            onValueChange = {
                viewModel.onTextFieldEntered(LocalityInputEvent.LocalityShortName(it))
            },
            onImeKeyAction = viewModel::moveFocusImeAction
            //onImeKeyAction = { } //viewModel.onContinueClick { onSubmit() }
        )
        ExposedDropdownMenuBoxComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[LocalityFields.LOCALITY_TYPE]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = LocalityFields.LOCALITY_TYPE,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.type_hint,
            leadingPainterResId = com.oborodulin.jwsuite.presentation_geo.R.drawable.ic_signpost_36,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
            },
            inputWrapper = localityType,
            values = localityTypes.values.toList(), // resolve Enums to Resource
            keys = localityTypes.keys.map { it.name }, // Enums
            onValueChange = { viewModel.onTextFieldEntered(LocalityInputEvent.LocalityType(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction,
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[LocalityFields.LOCALITY_NAME]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = LocalityFields.LOCALITY_NAME,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.name_hint,
            leadingPainterResId = R.drawable.ic_abc_36,
            keyboardOptions = remember {
                KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                )
            },
            //  visualTransformation = ::creditCardFilter,
            inputWrapper = localityName,
            onValueChange = { viewModel.onTextFieldEntered(LocalityInputEvent.LocalityName(it)) },
            onImeKeyAction = handleSaveAction
        )
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewLocalityView() {
    JWSuiteTheme {
        Surface {
            /*LocalityView(
                localityViewModel = LocalityViewModelImpl.previewModel(LocalContext.current),
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
