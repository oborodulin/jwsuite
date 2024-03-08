package com.oborodulin.jwsuite.presentation_geo.ui.geo.street.localitydistrict

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.oborodulin.home.common.ui.components.field.util.InputFocusRequester
import com.oborodulin.home.common.ui.components.field.util.inputProcess
import com.oborodulin.home.common.ui.components.list.SearchMultiCheckViewComponent
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_INPUT
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_geo.R
import com.oborodulin.jwsuite.presentation_geo.ui.geo.localitydistrict.single.LocalityDistrictUiAction
import com.oborodulin.jwsuite.presentation_geo.ui.geo.localitydistrict.single.LocalityDistrictView
import com.oborodulin.jwsuite.presentation_geo.ui.geo.localitydistrict.single.LocalityDistrictViewModelImpl
import com.oborodulin.jwsuite.presentation_geo.ui.geo.street.single.StreetComboBox
import com.oborodulin.jwsuite.presentation_geo.ui.model.StreetLocalityDistrictsUiModel
import com.oborodulin.jwsuite.presentation_geo.ui.model.toStreetsListItem
import timber.log.Timber
import java.util.EnumMap

private const val TAG = "Territoring.StreetLocalityDistrictView"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun StreetLocalityDistrictView(
    streetLocalityDistrictsUiModel: StreetLocalityDistrictsUiModel? = null,
    streetLocalityDistrictViewModel: StreetLocalityDistrictViewModel,
    localityDistrictViewModel: LocalityDistrictViewModelImpl = hiltViewModel()
) {
    Timber.tag(TAG).d("StreetLocalityDistrictView(...) called")
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(streetLocalityDistrictViewModel.events, lifecycleOwner) {
        streetLocalityDistrictViewModel.events.flowWithLifecycle(
            lifecycleOwner.lifecycle, Lifecycle.State.STARTED
        )
    }

    streetLocalityDistrictsUiModel?.let {
        streetLocalityDistrictViewModel.onTextFieldEntered(
            StreetLocalityDistrictInputEvent.Street(it.street.toStreetsListItem())
        )
    }
    Timber.tag(TAG).d("Street Locality District: CollectAsStateWithLifecycle for all fields")
    val street by streetLocalityDistrictViewModel.street.collectAsStateWithLifecycle()

    Timber.tag(TAG).d("Street Locality District: Init Focus Requesters for all fields")
    val focusRequesters =
        EnumMap<StreetLocalityDistrictFields, InputFocusRequester>(StreetLocalityDistrictFields::class.java)
    enumValues<StreetLocalityDistrictFields>().forEach {
        focusRequesters[it] = InputFocusRequester(it, remember { FocusRequester() })
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("StreetLocalityDistrictView -> LaunchedEffect()")
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
            .heightIn(min = 350.dp, max = 550.dp)
            //.height(IntrinsicSize.Min)
            .clip(RoundedCornerShape(16.dp))
            .border(
                2.dp,
                MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(16.dp)
            ),
        //.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        StreetComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[StreetLocalityDistrictFields.STREET_LOCALITY_DISTRICT_STREET]!!.focusRequester)
                .onFocusChanged { focusState ->
                    streetLocalityDistrictViewModel.onTextFieldFocusChanged(
                        focusedField = StreetLocalityDistrictFields.STREET_LOCALITY_DISTRICT_STREET,
                        isFocused = focusState.isFocused
                    )
                },
            enabled = false,
            inputWrapper = street
        )
        streetLocalityDistrictsUiModel?.let { streetLocalityDistricts ->
            SearchMultiCheckViewComponent(
                listViewModel = streetLocalityDistrictViewModel,
                loadListUiAction = StreetLocalityDistrictUiAction.Load(streetLocalityDistricts.street.id!!),
                items = streetLocalityDistricts.localityDistricts,
                singleViewModel = localityDistrictViewModel,
                loadUiAction = LocalityDistrictUiAction.Load(),
                confirmUiAction = LocalityDistrictUiAction.Save,
                emptyListTextResId = R.string.for_streets_locality_districts_list_empty_text,
                dialogView = { _, handleSaveAction -> LocalityDistrictView(handleSaveAction = handleSaveAction) }
            )
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewStreetLocalityDistrictView() {
    JWSuiteTheme {
        Surface {
            /*StreetLocalityDistrictView(
                localityViewModel = StreetLocalityDistrictViewModelImpl.previewModel(LocalContext.current),
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
