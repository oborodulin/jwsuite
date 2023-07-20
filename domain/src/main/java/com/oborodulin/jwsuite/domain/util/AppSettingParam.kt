package com.oborodulin.jwsuite.domain.util

enum class AppSettingParam {
    LANG,
    CURRENCY_CODE,
    DAY_MU,
    MONTH_MU,
    YEAR_MU,
    PERSON_NUM_MU,
    TERRITORY_PROCESSING_PERIOD,        // 1 year
    TERRITORY_AT_HAND_PERIOD,           // 4 months
    TERRITORY_ROOMS_LIMIT,              // 50-75 rooms per territory
    TERRITORY_MAX_ROOMS,                // 50-75 rooms
    TERRITORY_IDLE_PERIOD;              // 3 months
}