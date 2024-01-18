package com.oborodulin.jwsuite.domain.usecases.db

import android.content.Context
import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.domain.repositories.AppSettingsRepository
import com.oborodulin.jwsuite.domain.services.ExportService
import com.oborodulin.jwsuite.domain.services.Exports
import com.oborodulin.jwsuite.domain.services.csv.CsvConfig
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

// https://chetangupta.net/db-to-csv/
class CsvExportUseCase(
    configuration: Configuration,
    private val ctx: Context,
    private val exportService: ExportService,
    private val appSettingsRepository: AppSettingsRepository
) : UseCase<CsvExportUseCase.Request, CsvExportUseCase.Response>(configuration) {
    override fun process(request: Request) = flow {
        val appSettings = appSettingsRepository.extractAppSettings().first()
        // call export function from Export serivce
        // apply config + type of export
        exportService.export(
            type = Exports.CSV(CsvConfig(ctx = ctx, prefix = "")),
            content = appSettings               // send transformed data of exportable type
        ).catch {
            // handle error here
            throw UseCaseException.ExportException(it)
        }.map {
            emit(Response(it))
        }/*.collect { _ ->
            // do anything on success
            //_exportCsvState.value = ViewState.Success(emptyList())
        }*/
    }

    data object Request : UseCase.Request
    data class Response(val isSuccess: Boolean) : UseCase.Response
}