package com.oborodulin.home.common.util

object LogLevel {
    // Core:
    const val LOG_UI_COMPONENTS = false
    const val LOG_MVI = false
    const val LOG_MVI_SINGLE = false
    const val LOG_MVI_DIALOG = false
    const val LOG_MVI_LIST = false
    const val LOG_MVI_SHARED = false
    const val LOG_MVI_UI_STATE = false

    // Flows:
    const val LOG_FLOW_ACTION = true
    const val LOG_FLOW_EVENT = true
    const val LOG_FLOW_INPUT = true
    const val LOG_FLOW_JOB = true

    // Data:
    const val LOG_DATABASE = true
    const val LOG_DATASTORE = true
    const val LOG_WORKER = true

    // Data mappers:
    const val LOG_DB_MAPPER_TERRITORY = false

    // Secure:
    const val LOG_SECURE = false

    // Domain:
    const val LOG_DOMAIN_MODEL = false

    // UI:
    const val LOG_NAVIGATION = false

    // Modules:
    const val LOG_AUTH = true
    const val LOG_SETTING = true
    const val LOG_CONGREGATION = true
    const val LOG_GEO = true
    const val LOG_HOUSING = true
    const val LOG_TERRITORY = true
}