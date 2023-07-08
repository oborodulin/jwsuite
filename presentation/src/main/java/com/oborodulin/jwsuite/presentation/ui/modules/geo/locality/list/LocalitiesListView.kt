package com.oborodulin.jwsuite.presentation.ui.modules.geo.locality.list

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
import androidx.navigation.NavController
import com.oborodulin.home.common.ui.ComponentUiAction
import com.oborodulin.home.common.ui.components.items.ListItemComponent
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.RegionDistrictInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.RegionInput
import com.oborodulin.jwsuite.presentation.ui.model.LocalitiesListItem
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

private const val TAG = "Geo.ui.LocalitiesListView"

@Composable
fun LocalitiesListView(
    viewModel: LocalitiesListViewModelImpl = hiltViewModel(),
    navController: NavController,
    regionInput: RegionInput,
    regionDistrictInput: RegionDistrictInput? = null
) {
    Timber.tag(TAG).d(
        "LocalitiesListView(...) called: regionInput = %s, regionDistrictInput = %s",
        regionInput,
        regionDistrictInput
    )
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("LocalitiesListView: LaunchedEffect() BEFORE collect ui state flow")
        viewModel.submitAction(
            LocalitiesListUiAction.Load(regionInput.regionId, regionDistrictInput?.regionDistrictId)
        )
    }
    viewModel.uiStateFlow.collectAsState().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        CommonScreen(state = state) {
            LocalitiesList(
                localities = it,
                onEdit = { locality ->
                    viewModel.submitAction(LocalitiesListUiAction.EditLocality(locality.id))
                },
                onDelete = { locality ->
                    viewModel.submitAction(LocalitiesListUiAction.DeleteLocality(locality.id))
                }
            ) {}
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("LocalitiesListView: LaunchedEffect() AFTER collect ui state flow")
        viewModel.singleEventFlow.collectLatest {
            Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
            when (it) {
                is LocalitiesListUiSingleEvent.OpenLocalityScreen -> {
                    navController.navigate(it.navRoute)
                }
            }
        }
    }
}

@Composable
fun LocalitiesList(
    localities: List<LocalitiesListItem>,
    onEdit: (LocalitiesListItem) -> Unit,
    onDelete: (LocalitiesListItem) -> Unit,
    onClick: (LocalitiesListItem) -> Unit
) {
    Timber.tag(TAG).d("LocalitiesList(...) called")
    var selectedIndex by remember { mutableStateOf(-1) } // by
    if (localities.isNotEmpty()) {
        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier
                .padding(8.dp)
                .focusable(enabled = true)
        ) {
            items(localities.size) { index ->
                localities[index].let { locality ->
                    val isSelected = (selectedIndex == index)
                    ListItemComponent(
                        item = locality,
                        itemActions = listOf(
                            ComponentUiAction.EditListItem { onEdit(locality) },
                            ComponentUiAction.DeleteListItem(
                                stringResource(
                                    R.string.dlg_confirm_del_locality,
                                    locality.localityName
                                )
                            ) { onDelete(locality) }),
                        selected = isSelected,
                        background = (if (isSelected) Color.LightGray else Color.Transparent),
                    ) {
                        if (selectedIndex != index) selectedIndex = index
                        onClick(locality)
                    }
                }
            }
        }
    } else {
        Text(
            text = stringResource(R.string.localities_list_empty_text),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewLocalitiesAccounting() {
    LocalitiesList(
        localities = LocalitiesListViewModelImpl.previewList(LocalContext.current),
        onEdit = {},
        onDelete = {},
        onClick = {})
}
