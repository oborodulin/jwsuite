package com.oborodulin.jwsuite.domain.types

enum class AppSettingParam {
    LANG,
    CURRENCY_CODE,
    ALL_ITEMS,
    DAY_MU,
    MONTH_MU,
    YEAR_MU,
    PERSON_NUM_MU,
    TERRITORY_BUSINESS_MARK,
    TERRITORY_PROCESSING_PERIOD,        // 12 months
    TERRITORY_AT_HAND_PERIOD,           // 4 months
    TERRITORY_ROOMS_LIMIT,              // 50-75 rooms per territory
    TERRITORY_MAX_ROOMS,                // 144 rooms
    TERRITORY_IDLE_PERIOD;              // 3 months
}