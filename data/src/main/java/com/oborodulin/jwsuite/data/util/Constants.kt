package com.oborodulin.jwsuite.data.util

/**
 * Created by tfakioglu on 13.December.2021
 */
object Constants {
    const val DATABASE_NAME = "jwsuite-database.sqlite"

    // GEO Prefix:
    // Regions:
    const val PX_REGION = "r_"
    const val PX_DISTRICT_REGION = "dr_"
    const val PX_LOCALITY_REGION = "lr_"
    const val PX_CONGREGATION_REGION = "cr_"
    const val PX_TERRITORY_REGION = "tr_"

    // Region Districts:
    const val PX_REGION_DISTRICT = "rd_"
    const val PX_LOCALITY_REGION_DISTRICT = "ld_"
    const val PX_CONGREGATION_REGION_DISTRICT = "cd_"

    // Localities:
    const val PX_LOCALITY = "l_"
    const val PX_CONGREGATION_LOCALITY = "cl_"
    const val PX_TERRITORY_LOCALITY = "tl_"

    // Congregation:
    const val PX_GROUP_CONGREGATION = "gc_"

    // Territories:
    const val PX_TERRITORY = "t_"

    // Group:
    const val PX_GROUP_REGION = PX_GROUP_CONGREGATION+PX_CONGREGATION_REGION
    const val PX_GROUP_REGION_DISTRICT = PX_GROUP_CONGREGATION+PX_CONGREGATION_REGION_DISTRICT
    const val PX_GROUP_LOCALITY = PX_GROUP_CONGREGATION+PX_CONGREGATION_LOCALITY

    const val DB_TRUE = 1
    const val DB_FALSE = 0
    const val DB_FRACT_SEC_TIME = "'%Y-%m-%dT%H:%M:%f'"
    const val DB_DAY_TIME = "'%Y-%m-%d'"
    const val DB_MON_TIME = "'%d-%02d-01T00:00:00.000'"
    const val TZ_TIME =
        "strftime('%Y-%m-%dT%H:%M:%S', datetime('now', 'localtime')) || printf('%+.2d:%.2d', round((julianday('now', 'localtime') - julianday('now')) * 24), abs(round((julianday('now', 'localtime') - julianday('now')) * 24 * 60) % 60))"

    const val FMT_IS_PER_PERSON_EXPR = "'%d %s x %.2f %s = %.2f %s'"
    const val FMT_METER_VAL_EXPR = "'%.2f %s x %.2f %s = %.2f %s'"
    const val FMT_DEBT_EXPR = "'%.2f %s'"
    const val FMT_RATE_DEBT_EXPR = "'%.2f %s = %.2f %s'"
    const val FMT_OPT_FACTOR_EXPR = "'%.2f %s x '"
    const val FMT_PAYMENT_PERIOD_EXPR = "'%02d.%d: '"
    const val FMT_HEATING_METER_EXPR = "'%.5f %s x '"

    // AppSettings:
    const val PRM_LANG_VAL = "'LANG'"
    const val PRM_CURRENCY_CODE_VAL = "'CURRENCY_CODE'"
    const val PRM_ALL_ITEMS_VAL = "'ALL_ITEMS'"
    const val PRM_DAY_MU_VAL = "'DAY_MU'"
    const val PRM_MONTH_MU_VAL = "'MONTH_MU'"
    const val PRM_PERSON_NUM_MU_VAL = "'PERSON_NUM_MU'"
    const val PRM_TERRITORY_BUSINESS_MARK_VAL = "'TERRITORY_BUSINESS_MARK'"
    const val PRM_TERRITORY_PROCESSING_PERIOD_VAL = "'TERRITORY_PROCESSING_PERIOD'"
    const val PRM_TERRITORY_AT_HAND_PERIOD_VAL = "'TERRITORY_AT_HAND_PERIOD'"
    const val PRM_TERRITORY_ROOMS_LIMIT_VAL = "'TERRITORY_ROOMS_LIMIT'"
    const val PRM_TERRITORY_MAX_ROOMS_VAL = "'TERRITORY_MAX_ROOMS'"
    const val PRM_TERRITORY_IDLE_PERIOD_VAL = "'TERRITORY_IDLE_PERIOD'"

    // TerritoryLocationType:
    const val TDT_ALL_VAL = "'ALL'"
    const val TDT_LOCALITY_VAL = "'LOCALITY'"
    const val TDT_LOCALITY_DISTRICT_VAL = "'LOCALITY_DISTRICT'"
    const val TDT_MICRO_DISTRICT_VAL = "'MICRO_DISTRICT'"
}