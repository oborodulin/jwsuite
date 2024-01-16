package com.oborodulin.jwsuite.domain.services.csv.model.congregation

import com.oborodulin.jwsuite.domain.services.Exportable
import com.opencsv.bean.CsvBindByName
import java.time.OffsetDateTime
import java.util.UUID

data class MemberCsv(
    @CsvBindByName val memberId: UUID,
    @CsvBindByName val memberNum: String? = null,
    @CsvBindByName val memberName: String? = null,
    @CsvBindByName val surname: String? = null,
    @CsvBindByName val patronymic: String? = null,
    @CsvBindByName val pseudonym: String,
    @CsvBindByName val phoneNumber: String? = null,
    @CsvBindByName val dateOfBirth: OffsetDateTime? = null,
    @CsvBindByName val dateOfBaptism: OffsetDateTime? = null,
    @CsvBindByName val loginExpiredDate: OffsetDateTime? = null,
    @CsvBindByName val mGroupsId: UUID? = null
) : Exportable