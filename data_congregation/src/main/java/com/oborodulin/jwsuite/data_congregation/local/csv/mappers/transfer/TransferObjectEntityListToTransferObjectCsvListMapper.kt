package com.oborodulin.jwsuite.data_congregation.local.csv.mappers.transfer

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_congregation.local.db.entities.TransferObjectEntity
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.TransferObjectCsv

class TransferObjectEntityListToTransferObjectCsvListMapper(mapper: TransferObjectEntityToTransferObjectCsvMapper) :
    ListMapperImpl<TransferObjectEntity, TransferObjectCsv>(mapper)