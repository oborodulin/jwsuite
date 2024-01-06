package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single

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
import androidx.compose.material.icons.outlined.CheckCircle
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
import com.oborodulin.home.common.ui.components.field.SwitchComponent
import com.oborodulin.home.common.ui.components.field.TextFieldComponent
import com.oborodulin.home.common.ui.components.field.util.InputFocusRequester
import com.oborodulin.home.common.ui.components.field.util.inputProcess
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.SharedViewModeled
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_INPUT
import com.oborodulin.home.common.util.OnImeKeyAction
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_congregation.ui.FavoriteCongregationViewModelImpl
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.congregation.single.CongregationComboBox
import com.oborodulin.jwsuite.presentation_geo.ui.geo.locality.single.LocalityComboBox
import com.oborodulin.jwsuite.presentation_geo.ui.geo.localitydistrict.single.LocalityDistrictComboBox
import com.oborodulin.jwsuite.presentation_geo.ui.geo.microdistrict.single.MicrodistrictComboBox
import com.oborodulin.jwsuite.presentation_territory.R
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorycategory.single.TerritoryCategoryComboBox
import timber.log.Timber
import java.util.EnumMap

private const val TAG = "Territoring.TerritoryView"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TerritoryView(
    sharedViewModel: SharedViewModeled<ListItemModel?>?,
    viewModel: TerritoryViewModel,//Impl = hiltViewModel()
    handleSaveAction: OnImeKeyAction
) {
    Timber.tag(TAG).d("TerritoryView(...) called")
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(viewModel.events, lifecycleOwner) {
        viewModel.events.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }

    Timber.tag(TAG).d("Territory: CollectAsStateWithLifecycle for all fields")
    val congregation by viewModel.congregation.collectAsStateWithLifecycle()
    val category by viewModel.category.collectAsStateWithLifecycle()
    val locality by viewModel.locality.collectAsStateWithLifecycle()
    val localityDistrict by viewModel.localityDistrict.collectAsStateWithLifecycle()
    val microdistrict by viewModel.microdistrict.collectAsStateWithLifecycle()
    val territoryNum by viewModel.territoryNum.collectAsStateWithLifecycle()
    val isBusiness by viewModel.isBusiness.collectAsStateWithLifecycle()
    val isGroupMinistry by viewModel.isGroupMinistry.collectAsStateWithLifecycle()
    val isActive by viewModel.isActive.collectAsStateWithLifecycle()
    val territoryDesc by viewModel.territoryDesc.collectAsStateWithLifecycle()

    Timber.tag(TAG).d("Territory: Init Focus Requesters for all fields")
    val focusRequesters = EnumMap<TerritoryFields, InputFocusRequester>(TerritoryFields::class.java)
    enumValues<TerritoryFields>().forEach {
        focusRequesters[it] = InputFocusRequester(it, remember { FocusRequester() })
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("TerritoryView -> LaunchedEffect()")
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
        CongregationComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[TerritoryFields.TERRITORY_CONGREGATION]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = TerritoryFields.TERRITORY_CONGREGATION,
                        isFocused = focusState.isFocused
                    )
                },
            enabled = false,
            sharedViewModel = sharedViewModel,
            inputWrapper = congregation,
            onValueChange = { viewModel.onTextFieldEntered(TerritoryInputEvent.Congregation(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TerritoryCategoryComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[TerritoryFields.TERRITORY_CATEGORY]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = TerritoryFields.TERRITORY_CATEGORY,
                        isFocused = focusState.isFocused
                    )
                },
            inputWrapper = category,
            onValueChange = { categoryItem ->
                Timber.tag(TAG).d("onValueChange: categoryItem.itemId = %s", categoryItem.itemId)
                viewModel.onTextFieldEntered(TerritoryInputEvent.Category(categoryItem))
                categoryItem.itemId?.let {
                    viewModel.onInsert {
                        viewModel.submitAction(
                            TerritoryUiAction.GetNextTerritoryNum(congregation.item?.itemId!!, it)
                        )
                    }
                }
            },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        LocalityComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[TerritoryFields.TERRITORY_LOCALITY]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = TerritoryFields.TERRITORY_LOCALITY,
                        isFocused = focusState.isFocused
                    )
                },
            inputWrapper = locality,
            onValueChange = { viewModel.onTextFieldEntered(TerritoryInputEvent.Locality(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        LocalityDistrictComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[TerritoryFields.TERRITORY_LOCALITY_DISTRICT]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = TerritoryFields.TERRITORY_LOCALITY_DISTRICT,
                        isFocused = focusState.isFocused
                    )
                },
            localityId = locality.item?.itemId,
            inputWrapper = localityDistrict,
            onValueChange = { viewModel.onTextFieldEntered(TerritoryInputEvent.LocalityDistrict(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        MicrodistrictComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[TerritoryFields.TERRITORY_MICRODISTRICT]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = TerritoryFields.TERRITORY_MICRODISTRICT,
                        isFocused = focusState.isFocused
                    )
                },
            localityId = locality.item?.itemId,
            localityDistrictId = localityDistrict.item?.itemId,
            inputWrapper = microdistrict,
            onValueChange = { viewModel.onTextFieldEntered(TerritoryInputEvent.Microdistrict(it)) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[TerritoryFields.TERRITORY_NUM]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = TerritoryFields.TERRITORY_NUM,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.territory_num_hint,
            leadingPainterResId = com.oborodulin.home.common.R.drawable.ic_123_36,
            keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
            },
            inputWrapper = territoryNum,
            onValueChange = { viewModel.onTextFieldEntered(TerritoryInputEvent.TerritoryNum(it.toIntOrNull())) },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        SwitchComponent(
            switchModifier = Modifier
                .height(90.dp)
                .focusRequester(focusRequesters[TerritoryFields.TERRITORY_IS_BUSINESS]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = TerritoryFields.TERRITORY_IS_BUSINESS,
                        isFocused = focusState.isFocused
                    )
                },
            painterResId = R.drawable.ic_business_center_36,
            labelResId = R.string.territory_is_business_hint,
            inputWrapper = isBusiness,
            onCheckedChange = { viewModel.onTextFieldEntered(TerritoryInputEvent.IsBusiness(it)) }
        )
        SwitchComponent(
            switchModifier = Modifier
                .height(90.dp)
                .focusRequester(focusRequesters[TerritoryFields.TERRITORY_IS_GROUP_MINISTRY]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = TerritoryFields.TERRITORY_IS_GROUP_MINISTRY,
                        isFocused = focusState.isFocused
                    )
                },
            painterResId = com.oborodulin.jwsuite.presentation_congregation.R.drawable.ic_group_36,
            labelResId = R.string.territory_is_group_ministry_hint,
            inputWrapper = isGroupMinistry,
            onCheckedChange = { viewModel.onTextFieldEntered(TerritoryInputEvent.IsGroupMinistry(it)) }
        )
        SwitchComponent(
            switchModifier = Modifier
                .height(90.dp)
                .focusRequester(focusRequesters[TerritoryFields.TERRITORY_IS_ACTIVE]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = TerritoryFields.TERRITORY_IS_ACTIVE,
                        isFocused = focusState.isFocused
                    )
                },
            imageVector = Icons.Outlined.CheckCircle,
            labelResId = R.string.territory_is_active_hint,
            inputWrapper = isActive,
            onCheckedChange = { viewModel.onTextFieldEntered(TerritoryInputEvent.IsActive(it)) }
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[TerritoryFields.TERRITORY_DESC]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = TerritoryFields.TERRITORY_DESC,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.territory_desc_hint,
            leadingPainterResId = R.drawable.ic_text_snippet_36,
            keyboardOptions = remember {
                KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                )
            },
            inputWrapper = territoryDesc,
            onValueChange = { viewModel.onTextFieldEntered(TerritoryInputEvent.TerritoryDesc(it)) },
            onImeKeyAction = handleSaveAction
        )
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewGroupView() {
    val ctx = LocalContext.current
    JWSuiteTheme {
        Surface {
            TerritoryView(
                sharedViewModel = FavoriteCongregationViewModelImpl.previewModel,
                viewModel = TerritoryViewModelImpl.previewModel(ctx)
            ) {}
        }
    }
}
