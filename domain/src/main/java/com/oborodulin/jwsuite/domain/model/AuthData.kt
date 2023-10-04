package com.oborodulin.jwsuite.domain.model

import com.oborodulin.home.common.data.OffsetDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Serializable
data class AuthData(
    val username: String? = null,
    val password: String? = null,
    val databasePassphrase: String = "",
    @Serializable(with = OffsetDateTimeSerializer::class)
    val lastLoginTime: OffsetDateTime = OffsetDateTime.now(),
    val isLogged: Boolean = false,
    val roles: List<Role> = emptyList(),
    val currentNavRoute: String? = null
)
