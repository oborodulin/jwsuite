package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.congregation.single

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
import androidx.compose.material.icons.outlined.Favorite
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
import com.oborodulin.home.common.ui.components.field.SwitchComponent
import com.oborodulin.home.common.ui.components.field.TextFieldComponent
import com.oborodulin.home.common.ui.components.field.util.InputFocusRequester
import com.oborodulin.home.common.ui.components.field.util.inputProcess
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_INPUT
import com.oborodulin.home.common.util.OnImeKeyAction
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_congregation.R
import com.oborodulin.jwsuite.presentation_geo.ui.geo.locality.single.LocalityComboBox
import timber.log.Timber
import java.util.EnumMap

private const val TAG = "Congregating.CongregationView"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CongregationView(
    viewModel: CongregationViewModelImpl = hiltViewModel(), handleSaveAction: OnImeKeyAction
) {
    Timber.tag(TAG).d("CongregationView(...) called")
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(viewModel.events, lifecycleOwner) {
        viewModel.events.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }

    Timber.tag(TAG).d("Congregation: CollectAsStateWithLifecycle for all fields")
    val locality by viewModel.locality.collectAsStateWithLifecycle()
    val congregationNum by viewModel.congregationNum.collectAsStateWithLifecycle()
    val congregationName by viewModel.congregationName.collectAsStateWithLifecycle()
    val territoryMark by viewModel.territoryMark.collectAsStateWithLifecycle()
    val isFavorite by viewModel.isFavorite.collectAsStateWithLifecycle()

    Timber.tag(TAG).d("Congregation: Init Focus Requesters for all fields")
    val focusRequesters =
        EnumMap<CongregationFields, InputFocusRequester>(CongregationFields::class.java)
    enumValues<CongregationFields>().forEach {
        focusRequesters[it] = InputFocusRequester(it, remember { FocusRequester() })
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("CongregationView -> LaunchedEffect()")
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
            .border(2.dp, MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(16.dp))
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LocalityComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[CongregationFields.CONGREGATION_LOCALITY]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = CongregationFields.CONGREGATION_LOCALITY,
                        isFocused = focusState.isFocused
                    )
                },
            inputWrapper = locality,
            onValueChange = { viewModel.onTextFieldEntered(CongregationInputEvent.Locality(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[CongregationFields.CONGREGATION_NUM]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = CongregationFields.CONGREGATION_NUM,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.congregation_num_hint,
            leadingPainterResId = com.oborodulin.home.common.R.drawable.ic_123_36,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
            },
            inputWrapper = congregationNum,
            onValueChange = {
                viewModel.onTextFieldEntered(CongregationInputEvent.CongregationNum(it))
            },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[CongregationFields.CONGREGATION_NAME]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = CongregationFields.CONGREGATION_NAME,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = com.oborodulin.jwsuite.presentation.R.string.name_hint,
            leadingPainterResId = com.oborodulin.jwsuite.presentation.R.drawable.ic_abc_36,
            keyboardOptions = remember {
                KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                )
            },
            //  visualTransformation = ::creditCardFilter,
            inputWrapper = congregationName,
            onValueChange = { value ->
                viewModel.onTextFieldEntered(CongregationInputEvent.CongregationName(value))
                viewModel.onInsert {
                    val lastWord = value.replace("-[.,]", " ").split(" ").last()
                    //.joinToString("") { it.trim()[0].uppercase() }
                    viewModel.onTextFieldEntered(
                        CongregationInputEvent.TerritoryMark(
                            lastWord.getOrNull(0)?.uppercase() ?: ""
                        )
                    )
                }
            },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .height(90.dp)
                .focusRequester(focusRequesters[CongregationFields.TERRITORY_MARK]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = CongregationFields.TERRITORY_MARK,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.congregation_territory_mark_hint,
            leadingPainterResId = R.drawable.ic_map_marker_36,
            keyboardOptions = remember {
                KeyboardOptions(
                    capitalization = KeyboardCapitalization.Characters,
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                )
            },
            //  visualTransformation = ::creditCardFilter,
            inputWrapper = territoryMark,
            maxLength = 1,
            onValueChange = { viewModel.onTextFieldEntered(CongregationInputEvent.TerritoryMark(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        SwitchComponent(
            switchModifier = Modifier
                .height(90.dp)
                .focusRequester(focusRequesters[CongregationFields.IS_FAVORITE]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = CongregationFields.IS_FAVORITE,
                        isFocused = focusState.isFocused
                    )
                },
            imageVector = Icons.Outlined.Favorite,
            labelResId = R.string.congregation_is_favorite_hint,
            inputWrapper = isFavorite,
            onCheckedChange = { viewModel.onTextFieldEntered(CongregationInputEvent.IsFavorite(it)) }
        )
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewCongregationView() {
    JWSuiteTheme {
        Surface {
            /*CongregationView(
                congregationViewModel = CongregationViewModelImpl.previewModel(LocalContext.current),
                localitiesListViewModel = LocalitiesListViewModelImpl.previewModel(LocalContext.current),
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
