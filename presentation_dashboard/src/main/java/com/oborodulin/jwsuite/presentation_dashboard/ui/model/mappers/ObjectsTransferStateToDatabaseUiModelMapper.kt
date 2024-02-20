package com.oborodulin.jwsuite.presentation_dashboard.ui.model.mappers

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.state.ObjectsTransferState
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.DatabaseUiModel

class ObjectsTransferStateToDatabaseUiModelMapper : Mapper<ObjectsTransferState, DatabaseUiModel> {
    override fun map(input: ObjectsTransferState) = DatabaseUiModel(
        entityDesc = input.objectDesc,
        progress = input.progress,
        isSuccess = input.isSuccess,
        isDone = input.isDone
    )
}