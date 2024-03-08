package com.oborodulin.jwsuite.presentation_geo.ui.geo.regiondistrict.single

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
import com.oborodulin.home.common.ui.components.field.TextFieldComponent
import com.oborodulin.home.common.ui.components.field.util.InputFocusRequester
import com.oborodulin.home.common.ui.components.field.util.inputProcess
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_INPUT
import com.oborodulin.home.common.util.OnImeKeyAction
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_geo.ui.geo.country.single.CountryComboBox
import com.oborodulin.jwsuite.presentation_geo.ui.geo.region.single.RegionComboBox
import timber.log.Timber
import java.util.EnumMap

private const val TAG = "Geo.RegionDistrictView"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegionDistrictView(
    viewModel: RegionDistrictViewModelImpl = hiltViewModel(),
    handleSaveAction: OnImeKeyAction
) {
    Timber.tag(TAG).d("RegionDistrictView(...) called")
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(viewModel.events, lifecycleOwner) {
        viewModel.events.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }

    Timber.tag(TAG).d("Region District: CollectAsStateWithLifecycle for all fields")
    val country by viewModel.country.collectAsStateWithLifecycle()
    val region by viewModel.region.collectAsStateWithLifecycle()
    val districtShortName by viewModel.districtShortName.collectAsStateWithLifecycle()
    val districtName by viewModel.districtName.collectAsStateWithLifecycle()

    Timber.tag(TAG).d("Region District: Init Focus Requesters for all fields")
    val focusRequesters =
        EnumMap<RegionDistrictFields, InputFocusRequester>(RegionDistrictFields::class.java)
    enumValues<RegionDistrictFields>().forEach {
        focusRequesters[it] = InputFocusRequester(it, remember { FocusRequester() })
    }

    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("RegionDistrictView -> LaunchedEffect()")
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
                .focusRequester(focusRequesters[RegionDistrictFields.REGION_DISTRICT_COUNTRY]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = RegionDistrictFields.REGION_DISTRICT_COUNTRY,
                        isFocused = focusState.isFocused
                    )
                },
            //enabled = districtOsmId.value.isEmpty(),
            inputWrapper = country,
            onValueChange = { viewModel.onTextFieldEntered(RegionDistrictInputEvent.Country(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        RegionComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[RegionDistrictFields.REGION_DISTRICT_REGION]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = RegionDistrictFields.REGION_DISTRICT_REGION,
                        isFocused = focusState.isFocused
                    )
                },
            countryId = country.item?.itemId,
            inputWrapper = region,
            onValueChange = {
                viewModel.onTextFieldEntered(RegionDistrictInputEvent.Region(it))
            },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[RegionDistrictFields.DISTRICT_SHORT_NAME]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = RegionDistrictFields.DISTRICT_SHORT_NAME,
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
            inputWrapper = districtShortName,
            maxLength = 4,
            onValueChange = {
                viewModel.onTextFieldEntered(RegionDistrictInputEvent.DistrictShortName(it))
            },
            onImeKeyAction = viewModel::moveFocusImeAction
            //onImeKeyAction = { } //viewModel.onContinueClick { onSubmit() }
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[RegionDistrictFields.DISTRICT_NAME]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = RegionDistrictFields.DISTRICT_NAME,
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
            inputWrapper = districtName,
            onValueChange = { viewModel.onTextFieldEntered(RegionDistrictInputEvent.DistrictName(it)) },
            onImeKeyAction = handleSaveAction
        )
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewRegionDistrictView() {
    JWSuiteTheme {
        Surface {
            /*RegionDistrictView(
                regionDistrictViewModel = RegionDistrictViewModelImpl.previewModel(LocalContext.current),
                regionsListViewModel = RegionsListViewModelImpl.previewModel(LocalContext.current),
                regionViewModel = RegionViewModelImpl.previewModel(LocalContext.current)
            )*/
        }
    }
}
