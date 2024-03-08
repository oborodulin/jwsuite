package com.oborodulin.home.common.util

object LogLevel {
    // Core:
    const val LOG_UI_COMPONENTS = true
    const val LOG_UI_STATE = true
    const val LOG_MVI = false
    const val LOG_MVI_SINGLE = true
    const val LOG_MVI_DIALOG = true
    const val LOG_MVI_LIST = true
    const val LOG_MVI_SHARED = true
    const val LOG_MVI_UI_STATE = true

    // Flows:
    const val LOG_FLOW_ACTION = true
    const val LOG_FLOW_EVENT = true
    const val LOG_FLOW_INPUT = true
    const val LOG_FLOW_JOB = true

    // Data:
    const val LOG_DATABASE = true
    const val LOG_DATASTORE = true
    const val LOG_WORKER = true
    const val LOG_DB_MAPPER = false

    // Secure:
    const val LOG_SECURE = false

    // Domain:
    const val LOG_DOMAIN_MODEL = false

    // UI:
    const val LOG_NAVIGATION = true
}