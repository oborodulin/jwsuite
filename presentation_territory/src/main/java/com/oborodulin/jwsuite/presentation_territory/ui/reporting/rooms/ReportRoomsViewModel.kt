package com.oborodulin.jwsuite.presentation_territory.ui.reporting.rooms

import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.SingleViewModeled
import com.oborodulin.jwsuite.presentation_territory.ui.model.HousesListItem
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryReportRoomsListItem
import kotlinx.coroutines.flow.StateFlow

interface ReportRoomsViewModel :
    SingleViewModeled<List<TerritoryReportRoomsListItem>, ReportRoomsUiAction, ReportRoomsUiSingleEvent, ReportRoomsFields> {
    val house: StateFlow<InputListItemWrapper<HousesListItem>>
    fun singleSelectedItem(): ListItemModel?
}