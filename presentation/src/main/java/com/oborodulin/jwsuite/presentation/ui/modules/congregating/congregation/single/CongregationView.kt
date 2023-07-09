package com.oborodulin.jwsuite.presentation.ui.modules.congregating.congregation.single

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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.oborodulin.home.common.ui.components.dialog.FullScreenDialog
import com.oborodulin.home.common.ui.components.field.ComboBoxComponent
import com.oborodulin.home.common.ui.components.field.SwitchComponent
import com.oborodulin.home.common.ui.components.field.TextFieldComponent
import com.oborodulin.home.common.ui.components.field.util.InputFocusRequester
import com.oborodulin.home.common.ui.components.field.util.inputProcess
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.ui.modules.geo.locality.list.LocalitiesListUiAction
import com.oborodulin.jwsuite.presentation.ui.modules.geo.locality.list.LocalitiesListViewModel
import com.oborodulin.jwsuite.presentation.ui.modules.geo.locality.list.LocalitiesListViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.geo.locality.single.LocalityUiAction
import com.oborodulin.jwsuite.presentation.ui.modules.geo.locality.single.LocalityView
import com.oborodulin.jwsuite.presentation.ui.modules.geo.locality.single.LocalityViewModel
import com.oborodulin.jwsuite.presentation.ui.modules.geo.locality.single.LocalityViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.geo.region.list.RegionsListViewModel
import com.oborodulin.jwsuite.presentation.ui.modules.geo.region.list.RegionsListViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.geo.region.single.RegionViewModel
import com.oborodulin.jwsuite.presentation.ui.modules.geo.region.single.RegionViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.list.RegionDistrictsListViewModel
import com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.list.RegionDistrictsListViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.single.RegionDistrictViewModel
import com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.single.RegionDistrictViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import timber.log.Timber

private const val TAG = "Congregating.ui.CongregationView"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CongregationView(
    congregationViewModel: CongregationViewModel,
    localitiesListViewModel: LocalitiesListViewModel,
    localityViewModel: LocalityViewModel,
    regionsListViewModel: RegionsListViewModel,
    regionViewModel: RegionViewModel,
    regionDistrictsListViewModel: RegionDistrictsListViewModel,
    regionDistrictViewModel: RegionDistrictViewModel
) {
    Timber.tag(TAG).d("CongregationView(...) called")
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(congregationViewModel.events, lifecycleOwner) {
        congregationViewModel.events.flowWithLifecycle(
            lifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
    }

    Timber.tag(TAG).d("CollectAsStateWithLifecycle for all payer fields")
    val locality by congregationViewModel.locality.collectAsStateWithLifecycle()
    val congregationNum by congregationViewModel.congregationNum.collectAsStateWithLifecycle()
    val congregationName by congregationViewModel.congregationName.collectAsStateWithLifecycle()
    val territoryMark by congregationViewModel.territoryMark.collectAsStateWithLifecycle()
    val isFavorite by congregationViewModel.isFavorite.collectAsStateWithLifecycle()

    Timber.tag(TAG).d("Init Focus Requesters for all payer fields")
    val focusRequesters: MutableMap<String, InputFocusRequester> = HashMap()
    enumValues<CongregationFields>().forEach {
        focusRequesters[it.name] = InputFocusRequester(it, remember { FocusRequester() })
    }

    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("CongregationView(...): LaunchedEffect()")
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
            .border(2.dp, MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(16.dp))
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val isShowNewLocalityDialog = remember { mutableStateOf(false) }
        FullScreenDialog(
            isShow = isShowNewLocalityDialog,
            viewModel = localityViewModel,
            dialogView = {
                LocalityView(
                    localityViewModel,
                    regionsListViewModel,
                    regionViewModel,
                    regionDistrictsListViewModel,
                    regionDistrictViewModel
                )
            }
        ) { localityViewModel.submitAction(LocalityUiAction.Save) }

        ComboBoxComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[CongregationFields.CONGREGATION_LOCALITY.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    congregationViewModel.onTextFieldFocusChanged(
                        focusedField = CongregationFields.CONGREGATION_LOCALITY,
                        isFocused = focusState.isFocused
                    )
                },
            listViewModel = localitiesListViewModel,
            loadListUiAction = LocalitiesListUiAction.LoadAll,
            isShowSingleDialog = isShowNewLocalityDialog,
            labelResId = R.string.locality_hint,
            listTitleResId = R.string.dlg_title_select_locality,
            leadingIcon = { Icon(painterResource(R.drawable.ic_location_city_36), null) },
            inputWrapper = locality,
            onValueChange = {
                congregationViewModel.onTextFieldEntered(CongregationInputEvent.Locality(it))
            },
            onImeKeyAction = congregationViewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[CongregationFields.CONGREGATION_NUM.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    congregationViewModel.onTextFieldFocusChanged(
                        focusedField = CongregationFields.CONGREGATION_NUM,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.congregation_num_hint,
            leadingIcon = {
                Icon(painterResource(com.oborodulin.home.common.R.drawable.ic_123_36), null)
            },
            keyboardOptions = remember {
                KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            },
            inputWrapper = congregationNum,
            onValueChange = {
                congregationViewModel.onTextFieldEntered(
                    CongregationInputEvent.CongregationNum(it)
                )
            },
            onImeKeyAction = congregationViewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[CongregationFields.CONGREGATION_NAME.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    congregationViewModel.onTextFieldFocusChanged(
                        focusedField = CongregationFields.CONGREGATION_NAME,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.name_hint,
            leadingIcon = { Icon(painterResource(R.drawable.ic_abc_36), null) },
            keyboardOptions = remember {
                KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            },
            //  visualTransformation = ::creditCardFilter,
            inputWrapper = congregationName,
            onValueChange = { value ->
                congregationViewModel.onTextFieldEntered(
                    CongregationInputEvent.CongregationName(value)
                )
                CongregationInputEvent.TerritoryMark(
                    value.replace("-[.,]", " ").split(" ")
                        .joinToString("") { it.trim()[0].uppercase() })
            },
            onImeKeyAction = congregationViewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .height(90.dp)
                .focusRequester(focusRequesters[CongregationFields.TERRITORY_MARK.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    congregationViewModel.onTextFieldFocusChanged(
                        focusedField = CongregationFields.TERRITORY_MARK,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.territory_mark_hint,
            leadingIcon = { Icon(painterResource(R.drawable.ic_map_marker_36), null) },
            keyboardOptions = remember {
                KeyboardOptions(
                    capitalization = KeyboardCapitalization.Characters,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            },
            //  visualTransformation = ::creditCardFilter,
            inputWrapper = territoryMark,
            onValueChange = {
                congregationViewModel.onTextFieldEntered(CongregationInputEvent.TerritoryMark(it))
            },
            onImeKeyAction = congregationViewModel::moveFocusImeAction
        )
        SwitchComponent(
            modifier = Modifier
                .height(90.dp)
                .focusRequester(focusRequesters[CongregationFields.IS_FAVORITE.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    congregationViewModel.onTextFieldFocusChanged(
                        focusedField = CongregationFields.IS_FAVORITE,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.is_favorite_hint,
            inputWrapper = isFavorite,
            onCheckedChange = {
                congregationViewModel.onTextFieldEntered(CongregationInputEvent.IsFavorite(it))
            }
        )
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewCongregationView() {
    JWSuiteTheme {
        Surface {
            CongregationView(
                congregationViewModel = CongregationViewModelImpl.previewModel,
                localitiesListViewModel = LocalitiesListViewModelImpl.previewModel(LocalContext.current),
                localityViewModel = LocalityViewModelImpl.previewModel,
                regionsListViewModel = RegionsListViewModelImpl.previewModel(LocalContext.current),
                regionViewModel = RegionViewModelImpl.previewModel,
                regionDistrictsListViewModel = RegionDistrictsListViewModelImpl.previewModel(
                    LocalContext.current
                ),
                regionDistrictViewModel = RegionDistrictViewModelImpl.previewModel
            )
        }
    }
}
