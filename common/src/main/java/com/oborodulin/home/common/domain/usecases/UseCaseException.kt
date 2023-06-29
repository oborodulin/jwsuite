package com.oborodulin.home.common.domain.usecases

sealed class UseCaseException(cause: Throwable) : Throwable(cause) {

    class GeoRegionSaveException(cause: Throwable) : UseCaseException(cause)
    class GeoRegionDistrictSaveException(cause: Throwable) : UseCaseException(cause)
    class GeoLocalitySaveException(cause: Throwable) : UseCaseException(cause)
    class CongregationSaveException(cause: Throwable) : UseCaseException(cause)
    class GroupSaveException(cause: Throwable) : UseCaseException(cause)
    class MemberSaveException(cause: Throwable) : UseCaseException(cause)

    class UnknownException(cause: Throwable) : UseCaseException(cause)

    companion object {
        fun createFromThrowable(throwable: Throwable): UseCaseException {
            return if (throwable is UseCaseException) throwable else UnknownException(throwable)
        }
    }
}