package com.oborodulin.jwsuite.data.local.db.mappers.csv.transfer

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_congregation.local.db.entities.TransferObjectEntity

class TransferObjectEntityListToTransferObjectsListMapper(mapper: TransferObjectEntityToTransferObjectMapper) :
    ListMapperImpl<TransferObjectEntity, com.oborodulin.jwsuite.domain.services.csv.model.congregation.TransferObjectCsv>(mapper)