package com.oborodulin.jwsuite.data_congregation.local.db.mappers.transfer

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_congregation.local.db.entities.TransferObjectEntity
import com.oborodulin.jwsuite.domain.model.congregation.TransferObject

class TransferObjectListToTransferObjectsListMapper(mapper: TransferObjectEntityToTransferObjectMapper) :
    ListMapperImpl<TransferObjectEntity, TransferObject>(mapper)