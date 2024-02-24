package com.oborodulin.jwsuite.domain.usecases.geocountry

data class CountryUseCases(
    val getCountriesUseCase: GetCountriesUseCase,
    val getCountryUseCase: GetCountryUseCase,
    val saveCountryUseCase: SaveCountryUseCase,
    val deleteCountryUseCase: DeleteCountryUseCase
)