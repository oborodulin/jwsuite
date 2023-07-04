package com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.single

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
import com.oborodulin.home.common.ui.components.field.TextFieldComponent
import com.oborodulin.home.common.ui.components.field.util.InputFocusRequester
import com.oborodulin.home.common.ui.components.field.util.inputProcess
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.ui.modules.geo.region.list.RegionsListViewModel
import com.oborodulin.jwsuite.presentation.ui.modules.geo.region.list.RegionsListViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.geo.region.single.RegionUiAction
import com.oborodulin.jwsuite.presentation.ui.modules.geo.region.single.RegionView
import com.oborodulin.jwsuite.presentation.ui.modules.geo.region.single.RegionViewModel
import com.oborodulin.jwsuite.presentation.ui.modules.geo.region.single.RegionViewModelImpl
import timber.log.Timber

private const val TAG = "Geo.ui.RegionDistrictView"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegionDistrictView(
    regionDistrictViewModel: RegionDistrictViewModel,
    regionsListViewModel: RegionsListViewModel,
    regionViewModel: RegionViewModel
) {
    Timber.tag(TAG).d("RegionDistrictView(...) called")
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(regionDistrictViewModel.events, lifecycleOwner) {
        regionDistrictViewModel.events.flowWithLifecycle(
            lifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
    }

    Timber.tag(TAG).d("CollectAsStateWithLifecycle for all regionDistrict fields")
    val region by regionDistrictViewModel.region.collectAsStateWithLifecycle()
    val districtShortName by regionDistrictViewModel.districtShortName.collectAsStateWithLifecycle()
    val districtName by regionDistrictViewModel.districtName.collectAsStateWithLifecycle()

    Timber.tag(TAG).d("Init Focus Requesters for all regionDistrict fields")
    val focusRequesters: MutableMap<String, InputFocusRequester> = HashMap()
    enumValues<RegionDistrictFields>().forEach {
        focusRequesters[it.name] = InputFocusRequester(it, remember { FocusRequester() })
    }

    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("RegionDistrictView(...): LaunchedEffect()")
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
        val isShowNewRegionDialog = remember { mutableStateOf(false) }
        FullScreenDialog(
            isShow = isShowNewRegionDialog,
            viewModel = regionViewModel,
            dialogView = { RegionView(regionViewModel) }
        ) { regionViewModel.submitAction(RegionUiAction.Save) }

        ComboBoxComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[RegionDistrictFields.REGION_DISTRICT_REGION.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    regionDistrictViewModel.onTextFieldFocusChanged(
                        focusedField = RegionDistrictFields.REGION_DISTRICT_REGION,
                        isFocused = focusState.isFocused
                    )
                },
            listViewModel = regionsListViewModel,
            isShowItemDialog = isShowNewRegionDialog,
            labelResId = R.string.locality_region_hint,
            listTitleResId = R.string.dlg_title_select_region,
            leadingIcon = { Icon(painterResource(R.drawable.ic_region_36), null) },
            inputWrapper = region,
            onValueChange = {
                regionDistrictViewModel.onTextFieldEntered(
                    RegionDistrictInputEvent.Region(ListItemModel(headline = it))
                )
            },
            onImeKeyAction = regionDistrictViewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[RegionDistrictFields.DISTRICT_SHORT_NAME.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    regionDistrictViewModel.onTextFieldFocusChanged(
                        focusedField = RegionDistrictFields.DISTRICT_SHORT_NAME,
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
            inputWrapper = districtShortName,
            onValueChange = {
                regionDistrictViewModel.onTextFieldEntered(
                    RegionDistrictInputEvent.DistrictShortName(
                        it
                    )
                )
            },
            onImeKeyAction = regionDistrictViewModel::moveFocusImeAction
            //onImeKeyAction = { } //viewModel.onContinueClick { onSubmit() }
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[RegionDistrictFields.DISTRICT_NAME.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    regionDistrictViewModel.onTextFieldFocusChanged(
                        focusedField = RegionDistrictFields.DISTRICT_NAME,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.name_hint,
            leadingIcon = { Icon(painterResource(R.drawable.ic_abc_36), null) },
            keyboardOptions = remember {
                KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
            },
            //  visualTransformation = ::creditCardFilter,
            inputWrapper = districtName,
            onValueChange = {
                regionDistrictViewModel.onTextFieldEntered(RegionDistrictInputEvent.DistrictName(it))
            },
            onImeKeyAction = regionDistrictViewModel::moveFocusImeAction
        )
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewRegionDistrictView() {
    RegionDistrictView(
        regionDistrictViewModel = RegionDistrictViewModelImpl.previewModel,
        regionsListViewModel = RegionsListViewModelImpl.previewModel(LocalContext.current),
        regionViewModel = RegionViewModelImpl.previewModel
    )
}
