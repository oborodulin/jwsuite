package com.oborodulin.jwsuite.domain.services

import com.oborodulin.jwsuite.domain.services.csv.CsvConfig

// List of supported export functionality
// https://chetangupta.net/db-to-csv/
sealed class Exports {
    data class CSV(val csvConfig: CsvConfig) : Exports()
}