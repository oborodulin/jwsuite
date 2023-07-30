package com.oborodulin.home.common.ui.model

// https://www.youtube.com/watch?v=CfL6Dl2_dAE
interface Searchable {
    fun doesMatchSearchQuery(query: String): Boolean
}