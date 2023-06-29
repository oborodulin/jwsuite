package com.oborodulin.jwsuite.domain.usecases.group

data class GroupUseCases(
    val getGroupsUseCase: GetGroupsUseCase,
    val getGroupUseCase: GetGroupUseCase,
    val saveGroupUseCase: SaveGroupUseCase,
    val deleteGroupUseCase: DeleteGroupUseCase
)