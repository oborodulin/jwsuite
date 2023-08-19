package com.oborodulin.jwsuite.presentation.ui.modules.geo.localitydistrict.list

import android.content.res.Configuration
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Alignment
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
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.LocalityInput
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.LocalityDistrictsListItem
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

private const val TAG = "Geo.LocalityDistrictsListView"

@Composable
fun LocalityDistrictsListView(
    viewModel: LocalityDistrictsListViewModelImpl = hiltViewModel(),
    navController: NavController,
    localityInput: LocalityInput
) {
    Timber.tag(TAG).d("LocalityDistrictsListView(...) called: localityInput = %s", localityInput)
    LaunchedEffect(localityInput) {
        Timber.tag(TAG)
            .d("LocalityDistrictsListView: LaunchedEffect() BEFORE collect ui state flow")
        viewModel.submitAction(LocalityDistrictsListUiAction.Load(localityInput.localityId))
    }
    viewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        CommonScreen(state = state) {
            LocalityDistrictsList(
                localityDistricts = it,
                onEdit = { localityDistrict ->
                    viewModel.submitAction(
                        LocalityDistrictsListUiAction.EditLocalityDistrict(localityDistrict.id)
                    )
                },
                onDelete = { localityDistrict ->
                    viewModel.submitAction(
                        LocalityDistrictsListUiAction.DeleteLocalityDistrict(localityDistrict.id)
                    )
                }
            ) {}
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("LocalityDistrictsListView: LaunchedEffect() AFTER collect ui state flow")
        viewModel.singleEventFlow.collectLatest {
            Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
            when (it) {
                is LocalityDistrictsListUiSingleEvent.OpenLocalityDistrictScreen -> {
                    navController.navigate(it.navRoute)
                }
            }
        }
    }
}

@Composable
fun LocalityDistrictsList(
    localityDistricts: List<LocalityDistrictsListItem>,
    onEdit: (LocalityDistrictsListItem) -> Unit,
    onDelete: (LocalityDistrictsListItem) -> Unit,
    onClick: (LocalityDistrictsListItem) -> Unit
) {
    Timber.tag(TAG).d("LocalityDistrictsList(...) called")
    var selectedIndex by remember { mutableStateOf(-1) } // by
    if (localityDistricts.isNotEmpty()) {
        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier
                .padding(8.dp)
                .focusable(enabled = true)
        ) {
            items(localityDistricts.size) { index ->
                localityDistricts[index].let { localityDistrict ->
                    val isSelected = (selectedIndex == index)
                    ListItemComponent(
                        item = localityDistrict,
                        itemActions = listOf(
                            ComponentUiAction.EditListItem { onEdit(localityDistrict) },
                            ComponentUiAction.DeleteListItem(
                                stringResource(
                                    R.string.dlg_confirm_del_locality_district,
                                    localityDistrict.districtName
                                )
                            ) { onDelete(localityDistrict) }),
                        selected = isSelected,
                        background = (if (isSelected) Color.LightGray else Color.Transparent),
                        onClick = {
                            if (selectedIndex != index) selectedIndex = index
                            onClick(localityDistrict)
                        }
                    )
                }
            }
        }
    } else {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .padding(4.dp)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(R.string.locality_districts_list_empty_text),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewLocalityDistrictsList() {
    JWSuiteTheme {
        Surface {
            LocalityDistrictsList(
                localityDistricts = LocalityDistrictsListViewModelImpl.previewList(LocalContext.current),
                onEdit = {},
                onDelete = {},
                onClick = {})
        }
    }
}
