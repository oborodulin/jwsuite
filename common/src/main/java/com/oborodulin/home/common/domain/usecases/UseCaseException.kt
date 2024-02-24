package com.oborodulin.home.common.domain.usecases

sealed class UseCaseException(cause: Throwable) : Throwable(cause) {
    class AppSettingSaveException(cause: Throwable) : UseCaseException(cause)
    class AppSettingsSaveException(cause: Throwable) : UseCaseException(cause)

    // GEO:
    class GeoCountrySaveException(cause: Throwable) : UseCaseException(cause)
    class GeoRegionSaveException(cause: Throwable) : UseCaseException(cause)
    class GeoRegionDistrictSaveException(cause: Throwable) : UseCaseException(cause)
    class GeoLocalitySaveException(cause: Throwable) : UseCaseException(cause)
    class GeoLocalityDistrictSaveException(cause: Throwable) : UseCaseException(cause)
    class GeoMicrodistrictSaveException(cause: Throwable) : UseCaseException(cause)
    class GeoStreetSaveException(cause: Throwable) : UseCaseException(cause)
    class GeoStreetLocalityDistrictsSaveException(cause: Throwable) : UseCaseException(cause)
    class GeoStreetMicrodistrictsSaveException(cause: Throwable) : UseCaseException(cause)

    // Congregation:
    class CongregationSaveException(cause: Throwable) : UseCaseException(cause)
    class GroupSaveException(cause: Throwable) : UseCaseException(cause)
    class MemberSaveException(cause: Throwable) : UseCaseException(cause)
    class MemberRoleSaveException(cause: Throwable) : UseCaseException(cause)

    // Territory:
    class TerritoryCategorySaveException(cause: Throwable) : UseCaseException(cause)
    class TerritorySaveException(cause: Throwable) : UseCaseException(cause)
    class TerritoryStreetSaveException(cause: Throwable) : UseCaseException(cause)
    class TerritoryHouseSaveException(cause: Throwable) : UseCaseException(cause)
    class TerritoryEntranceSaveException(cause: Throwable) : UseCaseException(cause)
    class TerritoryFloorSaveException(cause: Throwable) : UseCaseException(cause)
    class TerritoryRoomSaveException(cause: Throwable) : UseCaseException(cause)
    class HandOutTerritoriesException(cause: Throwable) : UseCaseException(cause)
    class ProcessTerritoriesException(cause: Throwable) : UseCaseException(cause)
    class HouseSaveException(cause: Throwable) : UseCaseException(cause)
    class EntranceSaveException(cause: Throwable) : UseCaseException(cause)
    class FloorSaveException(cause: Throwable) : UseCaseException(cause)
    class RoomSaveException(cause: Throwable) : UseCaseException(cause)
    class MemberReportSaveException(cause: Throwable) : UseCaseException(cause)
    class ReportHouseSaveException(cause: Throwable) : UseCaseException(cause)
    class ReportRoomSaveException(cause: Throwable) : UseCaseException(cause)

    // Services
    class ExportException(cause: Throwable) : UseCaseException(cause)
    class ImportException(cause: Throwable) : UseCaseException(cause)
    class DataSendException(cause: Throwable) : UseCaseException(cause)
    class DataReceiveException(cause: Throwable) : UseCaseException(cause)

    class UnknownException(cause: Throwable) : UseCaseException(cause)

    companion object {
        fun createFromThrowable(throwable: Throwable): UseCaseException {
            return if (throwable is UseCaseException) throwable else UnknownException(throwable)
        }
    }
}