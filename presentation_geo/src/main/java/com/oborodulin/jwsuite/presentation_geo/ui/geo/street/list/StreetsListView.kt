package com.oborodulin.jwsuite.presentation_geo.ui.geo.street.list

import android.content.res.Configuration
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.oborodulin.home.common.ui.ComponentUiAction
import com.oborodulin.home.common.ui.components.EmptyListTextComponent
import com.oborodulin.home.common.ui.components.list.items.ListItemComponent
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.LocalityDistrictInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.LocalityInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.MicrodistrictInput
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_geo.R
import com.oborodulin.jwsuite.presentation_geo.ui.model.StreetsListItem
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

private const val TAG = "Geo.StreetsListView"

@Composable
fun StreetsListView(
    viewModel: StreetsListViewModelImpl = hiltViewModel(),
    navController: NavController,
    localityInput: LocalityInput? = null,
    localityDistrictInput: LocalityDistrictInput? = null,
    microdistrictInput: MicrodistrictInput? = null,
    isPrivateSector: Boolean = false
) {
    Timber.tag(TAG).d(
        "StreetsListView(...) called: isPrivateSector = %s; localityInput = %s; localityDistrictInput = %s; microdistrictInput = %s",
        isPrivateSector,
        localityInput,
        localityDistrictInput,
        microdistrictInput
    )
    LaunchedEffect(
        localityInput?.localityId,
        localityDistrictInput?.localityDistrictId,
        microdistrictInput?.microdistrictId,
        isPrivateSector
    ) {
        Timber.tag(TAG).d("StreetsListView: LaunchedEffect() BEFORE collect ui state flow")
        viewModel.submitAction(
            StreetsListUiAction.Load(
                localityInput?.localityId,
                localityDistrictInput?.localityDistrictId,
                microdistrictInput?.microdistrictId,
                isPrivateSector
            )
        )
    }
    viewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        CommonScreen(state = state) {
            StreetsList(
                streets = it,
                onEdit = { street ->
                    viewModel.submitAction(StreetsListUiAction.EditStreet(street.id))
                },
                onDelete = { street ->
                    viewModel.submitAction(StreetsListUiAction.DeleteStreet(street.id))
                }
            ) { street -> viewModel.singleSelectItem(street) }
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("StreetsListView: LaunchedEffect() AFTER collect single Event Flow")
        viewModel.singleEventFlow.collectLatest {
            Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
            when (it) {
                is StreetsListUiSingleEvent.OpenStreetScreen -> navController.navigate(it.navRoute)
            }
        }
    }
}

@Composable
fun StreetsList(
    streets: List<StreetsListItem>,
    onEdit: (StreetsListItem) -> Unit,
    onDelete: (StreetsListItem) -> Unit,
    onClick: (StreetsListItem) -> Unit
) {
    Timber.tag(TAG).d("StreetsList(...) called: size = %d", streets.size)
    if (streets.isNotEmpty()) {
        val listState =
            rememberLazyListState(initialFirstVisibleItemIndex = streets.filter { it.selected }
                .getOrNull(0)?.let { streets.indexOf(it) } ?: 0)
        LazyColumn(
            state = listState,
            modifier = Modifier
                .padding(8.dp)
                .focusable(enabled = true)
        ) {
            itemsIndexed(streets, key = { _, item -> item.id }) { _, street ->
                ListItemComponent(
                    item = street,
                    itemActions = listOf(
                        ComponentUiAction.EditListItem { onEdit(street) },
                        ComponentUiAction.DeleteListItem(
                            stringResource(
                                R.string.dlg_confirm_del_street,
                                street.streetFullName
                            )
                        ) { onDelete(street) }),
                    selected = street.selected,
                    onClick = { onClick(street) }
                )
            }
        }
    } else {
        EmptyListTextComponent(R.string.streets_list_empty_text)
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewStreetsList() {
    JWSuiteTheme {
        Surface {
            StreetsList(
                streets = StreetsListViewModelImpl.previewList(LocalContext.current),
                onEdit = {},
                onDelete = {},
                onClick = {}
            )
        }
    }
}
