package com.oborodulin.jwsuite.domain.model

import com.oborodulin.jwsuite.domain.model.NetworkMovie

/**
 * Created by tfakioglu on 13.December.2021
 */
data class Movies(
    val results: List<NetworkMovie>,
    val currentPage: Int,
    val totalPages: Int,
)