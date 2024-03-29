package com.oborodulin.jwsuite.domain.util

/**
 * Created by o.borodulin on 06.October.2023
 */
object Constants {
    // Internal Storage constants:
    const val BACKUP_PATH = "/backup"
    const val TRANSFER_PATH = "/transfer"
    const val RECEIVE_PATH = "$TRANSFER_PATH/receive"

    // Database constants:
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

    // OSM constants:
    const val OSM_TIMEOUT = 600

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

    // MemberType:
    const val MT_PREACHER_VAL = "'PREACHER'"
    const val MT_FULL_TIME_VAL = "'FULL_TIME'"
    const val MT_SERVICE_VAL = "'SERVICE'"
    const val MT_IN_ACTIVE_VAL = "'IN_ACTIVE'"

    // MemberRoleType:
    const val MR_ADMIN_VAL = "'ADMIN'"
    const val MR_USER_VAL = "'USER'"
    const val MR_TERRITORIES_VAL = "'TERRITORIES'"
    const val MR_BILLS_VAL = "'BILLS'"
    const val MR_REPORTS_VAL = "'REPORTS'"

    // TransferObjectType:
    const val TOT_ALL_VAL = "'ALL'"
    const val TOT_MEMBERS_VAL = "'MEMBERS'"
    const val TOT_TERRITORIES_VAL = "'TERRITORIES'"
    const val TOT_BILLS_VAL = "'BILLS'"

    // TerritoryLocationType:
    const val TDT_ALL_VAL = "'ALL'"
    const val TDT_LOCALITY_VAL = "'LOCALITY'"
    const val TDT_LOCALITY_DISTRICT_VAL = "'LOCALITY_DISTRICT'"
    const val TDT_MICRO_DISTRICT_VAL = "'MICRO_DISTRICT'"
}