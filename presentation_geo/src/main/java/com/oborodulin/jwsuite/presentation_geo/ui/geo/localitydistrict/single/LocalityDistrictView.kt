package com.oborodulin.jwsuite.presentation_geo.ui.geo.localitydistrict.single

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
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_geo.ui.geo.locality.single.LocalityComboBox
import timber.log.Timber
import java.util.EnumMap

private const val TAG = "Geo.LocalityDistrictView"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LocalityDistrictView(localityDistrictViewModel: LocalityDistrictViewModelImpl = hiltViewModel()) {
    Timber.tag(TAG).d("LocalityDistrictView(...) called")
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(localityDistrictViewModel.events, lifecycleOwner) {
        localityDistrictViewModel.events.flowWithLifecycle(
            lifecycleOwner.lifecycle, Lifecycle.State.STARTED
        )
    }

    Timber.tag(TAG).d("Locality District: CollectAsStateWithLifecycle for all fields")
    val locality by localityDistrictViewModel.locality.collectAsStateWithLifecycle()
    val districtShortName by localityDistrictViewModel.districtShortName.collectAsStateWithLifecycle()
    val districtName by localityDistrictViewModel.districtName.collectAsStateWithLifecycle()

    Timber.tag(TAG).d("Locality District: Init Focus Requesters for all fields")
    val focusRequesters = EnumMap<LocalityDistrictFields, InputFocusRequester>(LocalityDistrictFields::class.java)
    enumValues<LocalityDistrictFields>().forEach {
        focusRequesters[it] = InputFocusRequester(it, remember { FocusRequester() })
    }

    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("LocalityDistrictView(...): LaunchedEffect()")
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
                .focusRequester(focusRequesters[LocalityDistrictFields.LOCALITY_DISTRICT_LOCALITY]!!.focusRequester)
                .onFocusChanged { focusState ->
                    localityDistrictViewModel.onTextFieldFocusChanged(
                        focusedField = LocalityDistrictFields.LOCALITY_DISTRICT_LOCALITY,
                        isFocused = focusState.isFocused
                    )
                },
            inputWrapper = locality,
            onValueChange = {
                localityDistrictViewModel.onTextFieldEntered(LocalityDistrictInputEvent.Locality(it))
            },
            onImeKeyAction = localityDistrictViewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[LocalityDistrictFields.LOCALITY_DISTRICT_SHORT_NAME]!!.focusRequester)
                .onFocusChanged { focusState ->
                    localityDistrictViewModel.onTextFieldFocusChanged(
                        focusedField = LocalityDistrictFields.LOCALITY_DISTRICT_SHORT_NAME,
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
            maxLength = 3,
            onValueChange = {
                localityDistrictViewModel.onTextFieldEntered(
                    LocalityDistrictInputEvent.DistrictShortName(it)
                )
            },
            onImeKeyAction = localityDistrictViewModel::moveFocusImeAction
            //onImeKeyAction = { } //viewModel.onContinueClick { onSubmit() }
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[LocalityDistrictFields.LOCALITY_DISTRICT_NAME]!!.focusRequester)
                .onFocusChanged { focusState ->
                    localityDistrictViewModel.onTextFieldFocusChanged(
                        focusedField = LocalityDistrictFields.LOCALITY_DISTRICT_NAME,
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
            onValueChange = {
                localityDistrictViewModel.onTextFieldEntered(
                    LocalityDistrictInputEvent.DistrictName(it)
                )
            },
            onImeKeyAction = localityDistrictViewModel::moveFocusImeAction
        )
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewLocalityDistrictView() {
    JWSuiteTheme {
        Surface {
            /*LocalityDistrictView(
                localityDistrictViewModel = LocalityDistrictViewModelImpl.previewModel(LocalContext.current),
                localitysListViewModel = LocalitysListViewModelImpl.previewModel(LocalContext.current),
                localityViewModel = LocalityViewModelImpl.previewModel(LocalContext.current)
            )*/
        }
    }
}
