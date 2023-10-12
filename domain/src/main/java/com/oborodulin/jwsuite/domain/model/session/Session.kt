package com.oborodulin.jwsuite.domain.model.session

import com.oborodulin.jwsuite.domain.model.congregation.Role
import kotlinx.serialization.Serializable

@Serializable
data class Session(
    val isSigned: Boolean = false,
    val isLogged: Boolean = false,
    val roles: List<Role> = emptyList(),
    val startDestination: String? = null
)
