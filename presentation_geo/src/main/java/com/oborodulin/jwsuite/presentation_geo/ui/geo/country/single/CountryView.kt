package com.oborodulin.jwsuite.presentation_geo.ui.geo.country.single

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
import com.oborodulin.home.common.ui.components.field.TextFieldComponent
import com.oborodulin.home.common.ui.components.field.util.InputFocusRequester
import com.oborodulin.home.common.ui.components.field.util.inputProcess
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_INPUT
import com.oborodulin.home.common.util.OnImeKeyAction
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import timber.log.Timber
import java.util.EnumMap

private const val TAG = "Geo.CountryView"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CountryView(viewModel: CountryViewModel, handleSaveAction: OnImeKeyAction) {
    Timber.tag(TAG).d("CountryView(...) called")
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(viewModel.events, lifecycleOwner) {
        viewModel.events.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }

    Timber.tag(TAG).d("Country: CollectAsStateWithLifecycle for all fields")
    val countryCode by viewModel.countryCode.collectAsStateWithLifecycle()
    val countryName by viewModel.countryName.collectAsStateWithLifecycle()
    val countryGeocode by viewModel.countryGeocode.collectAsStateWithLifecycle()
    val countryOsmId by viewModel.countryOsmId.collectAsStateWithLifecycle()
    val latitude by viewModel.latitude.collectAsStateWithLifecycle()
    val longitude by viewModel.longitude.collectAsStateWithLifecycle()

    Timber.tag(TAG).d("Country: Init Focus Requesters for all fields")
    val focusRequesters = EnumMap<CountryFields, InputFocusRequester>(CountryFields::class.java)
    enumValues<CountryFields>().forEach {
        focusRequesters[it] = InputFocusRequester(it, remember { FocusRequester() })
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("CountryView -> LaunchedEffect()")
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
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[CountryFields.COUNTRY_CODE]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = CountryFields.COUNTRY_CODE,
                        isFocused = focusState.isFocused
                    )
                },
            maxLength = 2,
            labelResId = R.string.code_hint,
            helperResId = com.oborodulin.jwsuite.presentation_geo.R.string.country_code_helper,
            leadingPainterResId = com.oborodulin.home.common.R.drawable.ic_123_36,
            keyboardOptions = remember {
                KeyboardOptions(
                    capitalization = KeyboardCapitalization.Characters,
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                )
            },
            inputWrapper = countryCode,
            onValueChange = { viewModel.onTextFieldEntered(CountryInputEvent.CountryCode(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[CountryFields.COUNTRY_NAME]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = CountryFields.COUNTRY_NAME,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.name_hint,
            helperResId = com.oborodulin.jwsuite.presentation_geo.R.string.country_name_helper,
            leadingPainterResId = R.drawable.ic_abc_36,
            keyboardOptions = remember {
                KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                )
            },
            //  visualTransformation = ::creditCardFilter,
            inputWrapper = countryName,
            onValueChange = { viewModel.onTextFieldEntered(CountryInputEvent.CountryName(it)) },
            onImeKeyAction = handleSaveAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[CountryFields.COUNTRY_GEOCODE]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = CountryFields.COUNTRY_GEOCODE,
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
            inputWrapper = countryGeocode,
            onValueChange = {},
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[CountryFields.COUNTRY_OSM_ID]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = CountryFields.COUNTRY_OSM_ID,
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
            inputWrapper = countryOsmId,
            onValueChange = {},
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[CountryFields.COUNTRY_LATITUDE]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = CountryFields.COUNTRY_LATITUDE,
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
            onValueChange = {},
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[CountryFields.COUNTRY_LONGITUDE]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = CountryFields.COUNTRY_LONGITUDE,
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
            onValueChange = {},
            onImeKeyAction = viewModel::moveFocusImeAction
        )
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewCountryView() {
    JWSuiteTheme {
        Surface {
            CountryView(viewModel = CountryViewModelImpl.previewModel(LocalContext.current)) {}
        }
    }
}
