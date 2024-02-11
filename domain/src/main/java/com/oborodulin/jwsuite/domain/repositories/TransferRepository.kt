package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.services.csv.CsvTransferableRepo
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.TransferObjectCsv
import kotlinx.coroutines.flow.Flow

interface TransferRepository : CsvTransferableRepo {

    // -------------------------------------- CSV Transfer --------------------------------------
    fun extractTransferObjects(): Flow<List<TransferObjectCsv>>
    fun loadTransferObjects(transferObjects: List<TransferObjectCsv>): Flow<Int>
}