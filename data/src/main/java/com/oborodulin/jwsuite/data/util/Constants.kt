package com.oborodulin.jwsuite.data.util

/**
 * Created by tfakioglu on 13.December.2021
 */
object Constants {
    const val DATABASE_NAME = "home-database.sqlite"

    // GEO:
    // Regions:
    const val PX_REGION = "r_"
    const val PX_DISTRICT_REGION = "dr_"
    const val PX_LOCALITY_REGION = "lr_"
    const val PX_CONGREGATION_REGION = "cd_"

    // Region Districts:
    const val PX_REGION_DISTRICT = "rd_"
    const val PX_LOCALITY = "l_"
    const val PX_LOCALITY_DISTRICT = "ld_"
    const val PX_MICRODISTRICT = "md_"

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

    // TerritoryDistrictType:
    const val TDT_LOCALITY_VAL = "'LOCALITY'"
    const val TDT_LOCALITY_DISTRICT_VAL = "'LOCALITY_DISTRICT'"
    const val TDT_MICRO_DISTRICT_VAL = "'MICRO_DISTRICT'"

    /*
        const val SQL_PREV_METERS_VALUES_SUBQUERY = """
    SELECT v.metersId, MAX(datetime(v.valueDate)) maxValueDate
        FROM meter_values v JOIN meters m ON m.meterId = v.metersId
            JOIN payers_services AS ps ON ps.payerServiceId = m.payersServicesId
            JOIN payers AS p ON p.payerId = ps.payersId
        WHERE datetime(v.valueDate) <= CASE WHEN datetime('now') > datetime('now', 'start of month', '+' || (ifnull(p.paymentDay, ${DEF_PAYMENT_DAY}) - 1) || ' days')
                THEN datetime('now', 'start of month', '+' || (ifnull(p.paymentDay, ${DEF_PAYMENT_DAY}) - 1) || ' days')
                ELSE datetime('now', '-1 months', 'start of month', '+' || (ifnull(p.paymentDay, ${DEF_PAYMENT_DAY}) - 1) || ' days') END
    GROUP BY v.metersId
    """
    //.trimIndent()
    //.replace("\n\\s+".toRegex(), "")

        const val TMDB_BASE_URL = "https://api.themoviedb.org/3/"

        private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"
        private const val IMAGE_SIZE_W185 = "w185"
        private const val IMAGE_SIZE_W780 = "w780"

        const val CAST_AVATAR_URL = IMAGE_BASE_URL + IMAGE_SIZE_W185
        const val CAST_IMDB_URL = "https://www.imdb.com/name/"
        const val POSTER_URL = IMAGE_BASE_URL + IMAGE_SIZE_W185
        const val BACKDROP_URL = IMAGE_BASE_URL + IMAGE_SIZE_W780

     */
}