package com.oborodulin.jwsuite.presentation_geo.ui.geo.street.microdistrict

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
import com.oborodulin.jwsuite.presentation_geo.ui.geo.microdistrict.single.MicrodistrictUiAction
import com.oborodulin.jwsuite.presentation_geo.ui.geo.microdistrict.single.MicrodistrictView
import com.oborodulin.jwsuite.presentation_geo.ui.geo.microdistrict.single.MicrodistrictViewModelImpl
import com.oborodulin.jwsuite.presentation_geo.ui.geo.street.single.StreetComboBox
import com.oborodulin.jwsuite.presentation_geo.ui.model.MicrodistrictsListItem
import com.oborodulin.jwsuite.presentation_geo.ui.model.StreetMicrodistrictUiModel
import com.oborodulin.jwsuite.presentation_geo.ui.model.toStreetsListItem
import timber.log.Timber
import java.util.EnumMap

private const val TAG = "Territoring.StreetMicrodistrictView"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun StreetMicrodistrictView(
    streetMicrodistrictUiModel: StreetMicrodistrictUiModel? = null,
    streetMicrodistrictViewModel: StreetMicrodistrictViewModel,
    microdistrictViewModel: MicrodistrictViewModelImpl = hiltViewModel()
) {
    Timber.tag(TAG).d("StreetMicrodistrictView(...) called")
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(streetMicrodistrictViewModel.events, lifecycleOwner) {
        streetMicrodistrictViewModel.events.flowWithLifecycle(
            lifecycleOwner.lifecycle, Lifecycle.State.STARTED
        )
    }

    streetMicrodistrictUiModel?.let {
        streetMicrodistrictViewModel.onTextFieldEntered(
            StreetMicrodistrictInputEvent.Street(it.street.toStreetsListItem())
        )
    }
    Timber.tag(TAG).d("Street Microdistrict: CollectAsStateWithLifecycle for all fields")
    val street by streetMicrodistrictViewModel.street.collectAsStateWithLifecycle()
    val searchText by streetMicrodistrictViewModel.searchText.collectAsStateWithLifecycle()

    Timber.tag(TAG).d("Street Microdistrict: Init Focus Requesters for all fields")
    val focusRequesters =
        EnumMap<StreetMicrodistrictFields, InputFocusRequester>(StreetMicrodistrictFields::class.java)
    enumValues<StreetMicrodistrictFields>().forEach {
        focusRequesters[it] = InputFocusRequester(it, remember { FocusRequester() })
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("StreetMicrodistrictView(...): LaunchedEffect()")
        events.collect { event ->
            Timber.tag(TAG).d("Collect input events flow: %s", event.javaClass.name)
            inputProcess(context, focusManager, keyboardController, event, focusRequesters)
        }
    }
    val isShowNewSingleDialog by microdistrictViewModel.showDialog.collectAsStateWithLifecycle()
    FullScreenDialog(
        isShow = isShowNewSingleDialog,
        viewModel = microdistrictViewModel,
        loadUiAction = MicrodistrictUiAction.Load(),
        confirmUiAction = MicrodistrictUiAction.Save,
        dialogView = { MicrodistrictView() },
        onValueChange = {
            streetMicrodistrictUiModel?.let {
                streetMicrodistrictViewModel.submitAction(StreetMicrodistrictUiAction.Load(it.street.id!!))
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
                .focusRequester(focusRequesters[StreetMicrodistrictFields.STREET_MICRODISTRICT_STREET]!!.focusRequester)
                .onFocusChanged { focusState ->
                    streetMicrodistrictViewModel.onTextFieldFocusChanged(
                        focusedField = StreetMicrodistrictFields.STREET_MICRODISTRICT_STREET,
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
                onValueChange = streetMicrodistrictViewModel::onSearchTextChange
            )
            AddIconButtonComponent { microdistrictViewModel.onOpenDialogClicked() }
        }
        Spacer(modifier = Modifier.width(width = 8.dp))
        streetMicrodistrictUiModel?.let {
            ForStreetMicrodistrictsList(
                searchedText = searchText.text,
                microdistricts = it.microdistricts,
                onChecked = { streetMicrodistrictViewModel.observeCheckedListItems() }
            )
        }
    }
}

@Composable
fun ForStreetMicrodistrictsList(
    searchedText: String = "",
    microdistricts: List<MicrodistrictsListItem>,
    onChecked: (Boolean) -> Unit,
    onClick: (MicrodistrictsListItem) -> Unit = {}
) {
    Timber.tag(TAG)
        .d("ForStreetMicrodistrictsList(...) called: size = %d", microdistricts.size)
    if (microdistricts.isNotEmpty()) {
        val listState =
            rememberLazyListState(initialFirstVisibleItemIndex = microdistricts.filter { it.selected }
                .getOrNull(0)?.let { microdistricts.indexOf(it) } ?: 0)
        var filteredItems: List<MicrodistrictsListItem>
        LazyColumn(
            state = listState,
            modifier = Modifier
                .padding(8.dp)
                .focusable(enabled = true)
        ) {
            filteredItems = if (searchedText.isEmpty()) {
                microdistricts
            } else {
                microdistricts.filter { it.doesMatchSearchQuery(searchedText) }
            }
            itemsIndexed(filteredItems, key = { _, item -> item.id }) { _, microdistrict ->
                ForStreetMicrodistrictsListItemComponent(
                    item = microdistrict,
                    //selected = microdistrict.selected,
                    onChecked = onChecked,
                    onClick = { onClick(microdistrict) }
                )
            }
        }
    } else {
        EmptyListTextComponent(R.string.for_streets_microdistricts_list_empty_text)
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewStreetMicrodistrictView() {
    JWSuiteTheme {
        Surface {
            /*StreetMicrodistrictView(
                localityViewModel = StreetMicrodistrictViewModelImpl.previewModel(LocalContext.current),
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