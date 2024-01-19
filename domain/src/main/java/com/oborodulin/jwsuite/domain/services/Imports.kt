package com.oborodulin.jwsuite.domain.services

import com.oborodulin.jwsuite.domain.services.csv.CsvConfig

// List of supported import functionality
// https://mobikul.com/room-database-export-import-to-csv/
sealed class Imports {
    data class CSV(val csvConfig: CsvConfig) : Imports()
}