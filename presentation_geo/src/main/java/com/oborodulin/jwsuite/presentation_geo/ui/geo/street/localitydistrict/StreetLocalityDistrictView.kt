package com.oborodulin.jwsuite.presentation_geo.ui.geo.street.localitydistrict

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.oborodulin.home.common.ui.components.EmptyListTextComponent
import com.oborodulin.home.common.ui.components.buttons.AddIconButtonComponent
import com.oborodulin.home.common.ui.components.dialog.FullScreenDialog
import com.oborodulin.home.common.ui.components.field.util.InputFocusRequester
import com.oborodulin.home.common.ui.components.field.util.inputProcess
import com.oborodulin.home.common.ui.components.search.SearchComponent
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_geo.R
import com.oborodulin.jwsuite.presentation_geo.ui.geo.localitydistrict.single.LocalityDistrictUiAction
import com.oborodulin.jwsuite.presentation_geo.ui.geo.localitydistrict.single.LocalityDistrictView
import com.oborodulin.jwsuite.presentation_geo.ui.geo.localitydistrict.single.LocalityDistrictViewModelImpl
import com.oborodulin.jwsuite.presentation_geo.ui.geo.street.single.StreetComboBox
import com.oborodulin.jwsuite.presentation_geo.ui.model.LocalityDistrictsListItem
import com.oborodulin.jwsuite.presentation_geo.ui.model.StreetLocalityDistrictUiModel
import com.oborodulin.jwsuite.presentation_geo.ui.model.toStreetsListItem
import timber.log.Timber
import java.util.EnumMap

private const val TAG = "Territoring.StreetLocalityDistrictView"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun StreetLocalityDistrictView(
    streetLocalityDistrictUiModel: StreetLocalityDistrictUiModel? = null,
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

    streetLocalityDistrictUiModel?.let {
        streetLocalityDistrictViewModel.onTextFieldEntered(
            StreetLocalityDistrictInputEvent.Street(it.street.toStreetsListItem())
        )
    }
    Timber.tag(TAG).d("Street Locality District: CollectAsStateWithLifecycle for all fields")
    val street by streetLocalityDistrictViewModel.street.collectAsStateWithLifecycle()
    val searchText by streetLocalityDistrictViewModel.searchText.collectAsStateWithLifecycle()

    Timber.tag(TAG).d("Street Locality District: Init Focus Requesters for all fields")
    val focusRequesters =
        EnumMap<StreetLocalityDistrictFields, InputFocusRequester>(StreetLocalityDistrictFields::class.java)
    enumValues<StreetLocalityDistrictFields>().forEach {
        focusRequesters[it] = InputFocusRequester(it, remember { FocusRequester() })
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("StreetLocalityDistrictView(...): LaunchedEffect()")
        events.collect { event ->
            Timber.tag(TAG).d("Collect input events flow: %s", event.javaClass.name)
            inputProcess(context, focusManager, keyboardController, event, focusRequesters)
        }
    }
    val isShowNewSingleDialog by localityDistrictViewModel.showDialog.collectAsStateWithLifecycle()
    FullScreenDialog(
        isShow = isShowNewSingleDialog,
        viewModel = localityDistrictViewModel,
        loadUiAction = LocalityDistrictUiAction.Load(),
        confirmUiAction = LocalityDistrictUiAction.Save,
        dialogView = { LocalityDistrictView() },
        onValueChange = {
            streetLocalityDistrictUiModel?.let {
                streetLocalityDistrictViewModel.submitAction(StreetLocalityDistrictUiAction.Load(it.street.id!!))
            }
        },
        //onShowListDialog = onShowListDialog
    )
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
        Row(verticalAlignment = Alignment.CenterVertically) {
            SearchComponent(
                searchText,
                modifier = Modifier.weight(2.8f),
                onValueChange = streetLocalityDistrictViewModel::onSearchTextChange
            )
            AddIconButtonComponent { localityDistrictViewModel.onOpenDialogClicked() }
        }
        Spacer(modifier = Modifier.width(width = 8.dp))
        streetLocalityDistrictUiModel?.let {
            ForStreetLocalityDistrictsList(
                searchedText = searchText.text,
                localityDistricts = it.localityDistricts,
                onChecked = { streetLocalityDistrictViewModel.observeCheckedListItems() }
            )
        }
    }
}

@Composable
fun ForStreetLocalityDistrictsList(
    searchedText: String = "",
    localityDistricts: List<LocalityDistrictsListItem>,
    onChecked: (Boolean) -> Unit,
    onClick: (LocalityDistrictsListItem) -> Unit = {}
) {
    Timber.tag(TAG)
        .d("ForStreetLocalityDistrictsList(...) called: size = %d", localityDistricts.size)
    if (localityDistricts.isNotEmpty()) {
        val listState =
            rememberLazyListState(initialFirstVisibleItemIndex = localityDistricts.filter { it.selected }
                .getOrNull(0)?.let { localityDistricts.indexOf(it) } ?: 0)
        var filteredItems: List<LocalityDistrictsListItem>
        LazyColumn(
            state = listState,
            modifier = Modifier
                .padding(8.dp)
                .focusable(enabled = true)
        ) {
            filteredItems = if (searchedText.isEmpty()) {
                localityDistricts
            } else {
                localityDistricts.filter { it.doesMatchSearchQuery(searchedText) }
            }
            itemsIndexed(filteredItems, key = { _, item -> item.id }) { _, localityDistrict ->
                ForStreetLocalityDistrictsListItemComponent(
                    item = localityDistrict,
                    //selected = localityDistrict.selected,
                    onChecked = onChecked,
                    onClick = { onClick(localityDistrict) }
                )
            }
        }
    } else {
        EmptyListTextComponent(R.string.for_streets_locality_districts_list_empty_text)
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
