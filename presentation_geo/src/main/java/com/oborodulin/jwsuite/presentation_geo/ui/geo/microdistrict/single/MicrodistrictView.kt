package com.oborodulin.jwsuite.presentation_geo.ui.geo.microdistrict.single

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.painterResource
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
import com.oborodulin.jwsuite.presentation_geo.R
import com.oborodulin.jwsuite.presentation_geo.ui.geo.locality.single.LocalityComboBox
import com.oborodulin.jwsuite.presentation_geo.ui.geo.localitydistrict.single.LocalityDistrictComboBox
import com.oborodulin.jwsuite.presentation_geo.ui.theme.JWSuiteTheme
import timber.log.Timber

private const val TAG = "Geo.MicrodistrictView"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MicrodistrictView(viewModel: MicrodistrictViewModelImpl = hiltViewModel()) {
    Timber.tag(TAG).d("MicrodistrictView(...) called")
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

    Timber.tag(TAG).d("CollectAsStateWithLifecycle for all Microdistrict fields")
    val locality by viewModel.locality.collectAsStateWithLifecycle()
    val localityDistrict by viewModel.localityDistrict.collectAsStateWithLifecycle()
    val microdistrictShortName by viewModel.microdistrictShortName.collectAsStateWithLifecycle()
    val microdistrictType by viewModel.microdistrictType.collectAsStateWithLifecycle()
    val microdistrictName by viewModel.microdistrictName.collectAsStateWithLifecycle()

    val microdistrictTypes by viewModel.microdistrictTypes.collectAsStateWithLifecycle()

    Timber.tag(TAG).d("Init Focus Requesters for all Microdistrict fields")
    val focusRequesters: MutableMap<String, InputFocusRequester> = HashMap()
    enumValues<MicrodistrictFields>().forEach {
        focusRequesters[it.name] = InputFocusRequester(it, remember { FocusRequester() })
    }

    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("MicrodistrictView(...): LaunchedEffect()")
        events.collect { event ->
            Timber.tag(TAG).d("Collect input events flow: %s", event.javaClass.name)
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
        LocalityComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[MicrodistrictFields.MICRODISTRICT_LOCALITY.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = MicrodistrictFields.MICRODISTRICT_LOCALITY,
                        isFocused = focusState.isFocused
                    )
                },
            inputWrapper = locality,
            onValueChange = { viewModel.onTextFieldEntered(MicrodistrictInputEvent.Locality(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        LocalityDistrictComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[MicrodistrictFields.MICRODISTRICT_LOCALITY_DISTRICT.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = MicrodistrictFields.MICRODISTRICT_LOCALITY_DISTRICT,
                        isFocused = focusState.isFocused
                    )
                },
            localityId = locality.item?.itemId,
            inputWrapper = localityDistrict,
            onValueChange = {
                viewModel.onTextFieldEntered(MicrodistrictInputEvent.LocalityDistrict(it))
            },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[MicrodistrictFields.MICRODISTRICT_SHORT_NAME.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = MicrodistrictFields.MICRODISTRICT_SHORT_NAME,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.short_name_hint,
            leadingIcon = { Icon(painterResource(R.drawable.ic_ab_36), null) },
            keyboardOptions = remember {
                KeyboardOptions(
                    capitalization = KeyboardCapitalization.Characters,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            },
            inputWrapper = microdistrictShortName,
            onValueChange = {
                viewModel.onTextFieldEntered(MicrodistrictInputEvent.MicrodistrictShortName(it))
            },
            onImeKeyAction = viewModel::moveFocusImeAction
            //onImeKeyAction = { } //viewModel.onContinueClick { onSubmit() }
        )
        ExposedDropdownMenuBoxComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[MicrodistrictFields.MICRODISTRICT_TYPE.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = MicrodistrictFields.MICRODISTRICT_TYPE,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.type_hint,
            leadingIcon = { Icon(painterResource(R.drawable.ic_signpost_36), null) },
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
            },
            inputWrapper = microdistrictType,
            values = microdistrictTypes.values.toList(), // resolve Enums to Resource
            keys = microdistrictTypes.keys.map { it.name }, // Enums
            onValueChange = {
                viewModel.onTextFieldEntered(MicrodistrictInputEvent.MicrodistrictType(it))
            },
            onImeKeyAction = viewModel::moveFocusImeAction,
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[MicrodistrictFields.MICRODISTRICT_NAME.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = MicrodistrictFields.MICRODISTRICT_NAME,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.name_hint,
            leadingIcon = { Icon(painterResource(R.drawable.ic_abc_36), null) },
            keyboardOptions = remember {
                KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                )
            },
            //  visualTransformation = ::creditCardFilter,
            inputWrapper = microdistrictName,
            onValueChange = {
                viewModel.onTextFieldEntered(MicrodistrictInputEvent.MicrodistrictName(it))
            },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewMicrodistrictView() {
    JWSuiteTheme {
        Surface {
            /*MicrodistrictView(
                localityViewModel = MicrodistrictViewModelImpl.previewModel(LocalContext.current),
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
