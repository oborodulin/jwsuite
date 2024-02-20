package com.oborodulin.jwsuite.domain.model.state

import com.oborodulin.home.common.domain.model.DomainModel

data class ObjectsTransferState(
    val objectName: String = "",
    val objectDesc: String = "",
    val totalObjectItems: Int = 0,
    val totalObjects: Int = 0,
    val currentObjectNum: Int = 0,
    val isSuccess: Boolean = false,
) : DomainModel() {
    val progress = currentObjectNum.toFloat() / totalObjects
    val isDone = currentObjectNum == totalObjects
}