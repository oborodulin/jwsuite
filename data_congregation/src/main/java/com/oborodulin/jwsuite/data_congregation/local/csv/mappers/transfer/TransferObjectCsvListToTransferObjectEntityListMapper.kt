package com.oborodulin.jwsuite.data_congregation.local.csv.mappers.transfer

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_congregation.local.db.entities.TransferObjectEntity
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.TransferObjectCsv

class TransferObjectCsvListToTransferObjectEntityListMapper(mapper: TransferObjectCsvToTransferObjectEntityMapper) :
    ListMapperImpl<TransferObjectCsv, TransferObjectEntity>(mapper)