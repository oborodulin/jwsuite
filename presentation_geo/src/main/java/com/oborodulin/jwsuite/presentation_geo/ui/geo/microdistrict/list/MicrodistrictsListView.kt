package com.oborodulin.jwsuite.presentation_geo.ui.geo.microdistrict.list

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
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.LocalityDistrictInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.LocalityInput
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_geo.R
import com.oborodulin.jwsuite.presentation_geo.ui.model.MicrodistrictsListItem
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

private const val TAG = "Geo.MicrodistrictsListView"

@Composable
fun MicrodistrictsListView(
    viewModel: MicrodistrictsListViewModelImpl = hiltViewModel(),
    navController: NavController,
    localityInput: LocalityInput,
    localityDistrictInput: LocalityDistrictInput? = null
) {
    Timber.tag(TAG).d(
        "MicrodistrictsListView(...) called: localityInput = %s; localityDistrictInput = %s",
        localityInput,
        localityDistrictInput
    )
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("MicrodistrictsListView: LaunchedEffect() BEFORE collect ui state flow")
        viewModel.submitAction(
            MicrodistrictsListUiAction.Load(
                localityInput.localityId,
                localityDistrictInput?.localityDistrictId
            )
        )
    }
    viewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        CommonScreen(state = state) {
            MicrodistrictsList(
                microdistricts = it,
                onEdit = { locality ->
                    viewModel.submitAction(MicrodistrictsListUiAction.EditMicrodistrict(locality.id))
                },
                onDelete = { locality ->
                    viewModel.submitAction(MicrodistrictsListUiAction.DeleteMicrodistrict(locality.id))
                }
            ) {}
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("MicrodistrictsListView: LaunchedEffect() AFTER collect single Event Flow")
        viewModel.singleEventFlow.collectLatest {
            Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
            when (it) {
                is MicrodistrictsListUiSingleEvent.OpenMicrodistrictScreen -> {
                    navController.navigate(it.navRoute)
                }
            }
        }
    }
}

@Composable
fun MicrodistrictsList(
    microdistricts: List<MicrodistrictsListItem>,
    onEdit: (MicrodistrictsListItem) -> Unit,
    onDelete: (MicrodistrictsListItem) -> Unit,
    onClick: (MicrodistrictsListItem) -> Unit
) {
    Timber.tag(TAG).d("MicrodistrictsList(...) called")
    var selectedIndex by remember { mutableStateOf(-1) } // by
    if (microdistricts.isNotEmpty()) {
        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier
                .padding(8.dp)
                .focusable(enabled = true)
        ) {
            items(microdistricts.size) { index ->
                microdistricts[index].let { microdistrict ->
                    val isSelected = (selectedIndex == index)
                    ListItemComponent(
                        item = microdistrict,
                        itemActions = listOf(
                            ComponentUiAction.EditListItem { onEdit(microdistrict) },
                            ComponentUiAction.DeleteListItem(
                                stringResource(
                                    R.string.dlg_confirm_del_microdistrict,
                                    microdistrict.microdistrictFullName
                                )
                            ) { onDelete(microdistrict) }),
                        selected = isSelected,
                        background = (if (isSelected) Color.LightGray else Color.Transparent),
                        onClick = {
                            if (selectedIndex != index) selectedIndex = index
                            onClick(microdistrict)
                        }
                    )
                }
            }
        }
    } else {
        Text(
            text = stringResource(R.string.microdistricts_list_empty_text),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewMicrodistrictsList() {
    JWSuiteTheme {
        Surface {
            MicrodistrictsList(
                microdistricts = MicrodistrictsListViewModelImpl.previewList(LocalContext.current),
                onEdit = {},
                onDelete = {},
                onClick = {})
        }
    }
}
