package com.oborodulin.jwsuite.domain.usecases.territory.report

data class TerritoryReportUseCases(
    val getMemberReportsUseCase: GetMemberReportsUseCase,
    val getMemberReportUseCase: GetMemberReportUseCase,
    val getReportHousesUseCase: GetReportHousesUseCase,
    val getReportRoomsUseCase: GetReportRoomsUseCase,
    val processMemberReportUseCase: ProcessMemberReportUseCase,
    val cancelProcessMemberReportUseCase: CancelProcessMemberReportUseCase,
    val saveMemberReportUseCase: SaveMemberReportUseCase,
    val saveReportHouseUseCase: SaveReportHouseUseCase,
    val saveReportRoomUseCase: SaveReportRoomUseCase,
    val deleteMemberReportUseCase: DeleteMemberReportUseCase
)