package com.oborodulin.jwsuite.presentation_territory.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.oborodulin.home.common.ui.components.dialog.alert.AlertDialogComponent
import com.oborodulin.home.common.ui.components.fab.MinFabItem
import com.oborodulin.home.common.ui.components.fab.MultiFabComponent
import com.oborodulin.home.common.ui.components.fab.MultiFloatingState
import com.oborodulin.home.common.util.toast
import com.oborodulin.jwsuite.domain.types.TerritoryCategoryType
import com.oborodulin.jwsuite.presentation_territory.R
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoriesListItem
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid.TerritoriesGridUiAction
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid.TerritoriesGridViewModel

private const val TAG = "Presentation.AtWorkProcessMultiFabComponent"

@Composable
fun AtWorkProcessMultiFabComponent(
    enabled: Boolean,
    onMultiFabStateChange: (MultiFloatingState) -> Unit,
    multiFloatingState: MultiFloatingState,
    checkedTerritories: List<TerritoriesListItem>,
    territoriesGridViewModel: TerritoriesGridViewModel
) {
    val ctx = LocalContext.current

    val minFabStreet = MinFabItem(
        labelResId = R.string.fab_territory_process_street_text,
        painterResId = com.oborodulin.jwsuite.presentation.R.drawable.ic_street_sign_24
    ) {
        ctx.toast("Street process")
    }
    val minFabHouse = MinFabItem(
        labelResId = R.string.fab_territory_process_house_text,
        imageVector = Icons.Outlined.Home
    ) {
        ctx.toast("House process")
    }
    val minFabEntrance = MinFabItem(
        labelResId = R.string.fab_territory_process_entrance_text,
        painterResId = com.oborodulin.jwsuite.presentation.R.drawable.ic_entrance_24
    ) {
        ctx.toast("Entrance process")
    }
    val minFabFloor = MinFabItem(
        labelResId = R.string.fab_territory_process_floor_text,
        painterResId = com.oborodulin.jwsuite.presentation.R.drawable.ic_floors_24
    ) {
        ctx.toast("Room process")
    }
    val minFabRoom = MinFabItem(
        labelResId = R.string.fab_territory_process_room_text,
        painterResId = com.oborodulin.jwsuite.presentation.R.drawable.ic_room_24
    ) {
        ctx.toast("Room process")
    }
    val isShowAlert = rememberSaveable { mutableStateOf(false) }
    val minFabs = mutableListOf<MinFabItem>()
    var onClick: (() -> Unit)? = null
    if (checkedTerritories.size == 1) {
        checkedTerritories[0].isPrivateSector?.let {
            if (it) {
                minFabs.add(minFabStreet)
            }
        }
        when (checkedTerritories[0].territoryCategory.territoryCategoryCode) {
            TerritoryCategoryType.HOUSES -> when (checkedTerritories[0].isPrivateSector) {
                true -> minFabs.add(minFabHouse)
                else -> minFabs.addAll(listOf(minFabHouse, minFabEntrance))
            }

            TerritoryCategoryType.ROOMS -> minFabs.add(minFabRoom)
            else -> {}
        }
    } else {
        onClick = { isShowAlert.value = true }
    }
    AlertDialogComponent(
        isShow = isShowAlert,
        titleResId = com.oborodulin.home.common.R.string.dlg_confirm_title,
        text = stringResource(R.string.dlg_confirm_process_territories)
    ) { territoriesGridViewModel.submitAction(TerritoriesGridUiAction.Process) }

    MultiFabComponent(
        modifier = Modifier.padding(
            bottom = 44.dp,
            end = 4.dp
        ),
        multiFloatingState = multiFloatingState,
        onMultiFabStateChange = onMultiFabStateChange,
        enabled = enabled,
        collapsedImageVector = Icons.Outlined.ThumbUp,
        collapsedTextResId = R.string.fab_territory_at_work_text,
        expandedImageVector = Icons.Default.Close,
        items = minFabs,
        onClick = onClick
    )
    /*
                MinFabItem(
                    labelResId = R.string.fab_territory_process_text,
                    painterResId = com.oborodulin.jwsuite.presentation.R.drawable.ic_territory_map_24
                ) {
                    ctx.toast("Territory process")
                }

     */
}
/*
@Preview(name = "Night Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewAtWorkMultiFabComponentEnabled() {
    HomeComposableTheme {
        Surface {
            AtWorkMultiFabComponent(enabled = true, onClick = {})
        }
    }
}

@Preview(name = "Night Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewAtWorkMultiFabComponentDisabled() {
    HomeComposableTheme {
        Surface {
            AtWorkMultiFabComponent(enabled = false, onClick = {})
        }
    }
}
*/