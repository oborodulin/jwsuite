package com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.list

import android.content.res.Configuration
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.navigation.NavController
import com.oborodulin.home.common.ui.ComponentUiAction
import com.oborodulin.home.common.ui.components.items.ListItemComponent
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.RegionInput
import com.oborodulin.jwsuite.presentation.ui.model.RegionDistrictsListItem
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

private const val TAG = "Geo.ui.RegionDistrictsListView"

@Composable
fun RegionDistrictsListView(
    viewModel: RegionDistrictsListViewModelImpl = hiltViewModel(),
    navController: NavController,
    regionInput: RegionInput
) {
    Timber.tag(TAG).d("RegionDistrictsListView(...) called: regionInput = %s", regionInput)
    LaunchedEffect(regionInput) {
        Timber.tag(TAG).d("RegionDistrictsListView: LaunchedEffect() BEFORE collect ui state flow")
        viewModel.submitAction(RegionDistrictsListUiAction.Load(regionInput.regionId))
    }
    viewModel.uiStateFlow.collectAsState().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        CommonScreen(state = state) {
            RegionDistrictsList(
                regionDistricts = it,
                onEdit = { regionDistrict ->
                    viewModel.submitAction(
                        RegionDistrictsListUiAction.EditRegionDistrict(regionDistrict.id)
                    )
                },
                onDelete = { regionDistrict ->
                    viewModel.submitAction(
                        RegionDistrictsListUiAction.DeleteRegionDistrict(regionDistrict.id)
                    )
                }
            ) {}
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("RegionDistrictsListView: LaunchedEffect() AFTER collect ui state flow")
        viewModel.singleEventFlow.collectLatest {
            Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
            when (it) {
                is RegionDistrictsListUiSingleEvent.OpenRegionDistrictScreen -> {
                    navController.navigate(it.navRoute)
                }
            }
        }
    }
}

@Composable
fun RegionDistrictsList(
    regionDistricts: List<RegionDistrictsListItem>,
    onEdit: (RegionDistrictsListItem) -> Unit,
    onDelete: (RegionDistrictsListItem) -> Unit,
    onClick: (RegionDistrictsListItem) -> Unit
) {
    Timber.tag(TAG).d("RegionDistrictsList(...) called")
    var selectedIndex by remember { mutableIntStateOf(-1) } // by
    if (regionDistricts.isNotEmpty()) {
        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier
                .padding(8.dp)
                .focusable(enabled = true)
        ) {
            items(regionDistricts.size) { index ->
                regionDistricts[index].let { regionDistrict ->
                    val isSelected = (selectedIndex == index)
                    ListItemComponent(
                        item = regionDistrict,
                        itemActions = listOf(
                            ComponentUiAction.EditListItem { onEdit(regionDistrict) },
                            ComponentUiAction.DeleteListItem(
                                stringResource(
                                    R.string.dlg_confirm_del_region_district,
                                    regionDistrict.districtName
                                )
                            ) { onDelete(regionDistrict) }),
                        selected = isSelected,
                        background = (if (isSelected) Color.LightGray else Color.Transparent),
                    ) {
                        if (selectedIndex != index) selectedIndex = index
                        onClick(regionDistrict)
                    }
                }
            }
        }
    } else {
        Text(
            text = stringResource(R.string.region_districts_list_empty_text),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewRegionDistrictsList() {
    RegionDistrictsList(
        regionDistricts = RegionDistrictsListViewModelImpl.previewList(LocalContext.current),
        onEdit = {},
        onDelete = {},
        onClick = {})
}
