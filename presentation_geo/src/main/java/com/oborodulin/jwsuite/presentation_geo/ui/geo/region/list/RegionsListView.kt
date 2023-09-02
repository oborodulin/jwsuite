package com.oborodulin.jwsuite.presentation_geo.ui.geo.region.list

import android.content.res.Configuration
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.oborodulin.home.common.ui.ComponentUiAction
import com.oborodulin.home.common.ui.components.items.ListItemComponent
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.RegionInput
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_geo.R
import com.oborodulin.jwsuite.presentation_geo.ui.model.RegionsListItem
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

private const val TAG = "Geo.RegionsListView"

@Composable
fun RegionsListView(
    viewModel: RegionsListViewModelImpl = hiltViewModel(),
    navController: NavController,
    regionInput: RegionInput? = null
) {
    Timber.tag(TAG).d("RegionsListView(...) called: regionInput = %s", regionInput)
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("RegionsListView: LaunchedEffect() BEFORE collect ui state flow")
        viewModel.submitAction(RegionsListUiAction.Load)
    }
    viewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        CommonScreen(state = state) {
            RegionsList(
                regions = it,
                onEdit = { region ->
                    viewModel.submitAction(RegionsListUiAction.EditRegion(region.id))
                },
                onDelete = { region ->
                    viewModel.submitAction(RegionsListUiAction.DeleteRegion(region.id))
                }
            ) {}
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("RegionsListView: LaunchedEffect() AFTER collect ui state flow")
        viewModel.singleEventFlow.collectLatest {
            Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
            when (it) {
                is RegionsListUiSingleEvent.OpenRegionScreen -> {
                    navController.navigate(it.navRoute)
                }
            }
        }
    }
}

@Composable
fun RegionsList(
    regions: List<RegionsListItem>,
    onEdit: (RegionsListItem) -> Unit,
    onDelete: (RegionsListItem) -> Unit,
    onClick: (RegionsListItem) -> Unit
) {
    Timber.tag(TAG).d("RegionsList(...) called")
    var selectedIndex by remember { mutableStateOf(-1) } // by
    if (regions.isNotEmpty()) {
        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier
                .padding(8.dp)
                .focusable(enabled = true)
        ) {
            items(regions.size) { index ->
                regions[index].let { region ->
                    val isSelected = (selectedIndex == index)
                    ListItemComponent(
                        item = region,
                        itemActions = listOf(
                            ComponentUiAction.EditListItem { onEdit(region) },
                            ComponentUiAction.DeleteListItem(
                                stringResource(
                                    R.string.dlg_confirm_del_region,
                                    region.regionName
                                )
                            ) { onDelete(region) }),
                        selected = isSelected,
                        background = if (isSelected) Color.LightGray else Color.Transparent,
                        onClick = {
                            if (selectedIndex != index) selectedIndex = index
                            onClick(region)
                        }
                    )
                }
            }
        }
    } else {
        Text(
            text = stringResource(R.string.regions_list_empty_text),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewRegionsList() {
    JWSuiteTheme {
        Surface {
            RegionsList(
                regions = RegionsListViewModelImpl.previewList(LocalContext.current),
                onEdit = {},
                onDelete = {},
                onClick = {}
            )
        }
    }
}