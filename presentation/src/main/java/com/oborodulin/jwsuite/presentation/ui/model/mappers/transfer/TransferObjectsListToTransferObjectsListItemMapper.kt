package com.oborodulin.jwsuite.presentation.ui.model.mappers.transfer

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.domain.model.congregation.TransferObject
import com.oborodulin.jwsuite.presentation.ui.model.TransferObjectsListItem

class TransferObjectsListToTransferObjectsListItemMapper(mapper: TransferObjectToTransferObjectsListItemMapper) :
    ListMapperImpl<TransferObject, TransferObjectsListItem>(mapper)