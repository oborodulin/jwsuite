package com.oborodulin.jwsuite.presentation.di

import com.oborodulin.home.accounting.domain.usecases.*
import com.oborodulin.home.accounting.ui.model.converters.FavoritePayerConverter
import com.oborodulin.home.accounting.ui.model.converters.PayerConverter
import com.oborodulin.home.accounting.ui.model.converters.PayersListConverter
import com.oborodulin.home.accounting.ui.model.mappers.*
import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.domain.repositories.PayersRepository
import com.oborodulin.home.domain.usecases.*
import com.oborodulin.home.metering.domain.repositories.MetersRepository
import com.oborodulin.home.servicing.domain.repositories.RatesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CongregatingModule {
    // MAPPERS:
    @Singleton
    @Provides
    fun providePayerToPayerModelMapper(): PayerToPayerUiMapper = PayerToPayerUiMapper()

    @Singleton
    @Provides
    fun providePayerModelToPayerMapper(): PayerUiToPayerMapper = PayerUiToPayerMapper()

    @Singleton
    @Provides
    fun providePayerToPayerListItemModelMapper(): PayerToPayerListItemMapper =
        PayerToPayerListItemMapper()

    @Singleton
    @Provides
    fun providePayerListToPayerListItemModelMapper(mapper: PayerToPayerListItemMapper): PayerListToPayerListItemMapper =
        PayerListToPayerListItemMapper(mapper = mapper)

    // CONVERTERS:
    @Singleton
    @Provides
    fun providePayersListConverter(mapper: PayerListToPayerListItemMapper): PayersListConverter =
        PayersListConverter(mapper = mapper)

    @Singleton
    @Provides
    fun providePayerConverter(mapper: PayerToPayerUiMapper): PayerConverter =
        PayerConverter(mapper = mapper)

    @Singleton
    @Provides
    fun provideFavoritePayerConverter(mapper: PayerToPayerUiMapper): FavoritePayerConverter =
        FavoritePayerConverter(mapper = mapper)

    // USE CASES:
    @Singleton
    @Provides
    fun provideAccountingUseCases(
        configuration: UseCase.Configuration,
        payersRepository: PayersRepository,
        metersRepository: MetersRepository,
        getFavoritePayerUseCase: GetFavoritePayerUseCase
    ): AccountingUseCases = AccountingUseCases(getFavoritePayerUseCase = getFavoritePayerUseCase)

    @Singleton
    @Provides
    fun providePayerUseCases(
        configuration: UseCase.Configuration,
        payersRepository: PayersRepository, ratesRepository: RatesRepository,
        getPayerUseCase: GetPayerUseCase, savePayerUseCase: SavePayerUseCase,
        deletePayerUseCase: DeletePayerUseCase, favoritePayerUseCase: FavoritePayerUseCase
    ): PayerUseCases = PayerUseCases(
        getPayerUseCase = getPayerUseCase,
        getPayersUseCase = GetPayersUseCase(
            configuration, payersRepository, ratesRepository
        ),
        savePayerUseCase = savePayerUseCase,
        deletePayerUseCase = deletePayerUseCase,
        favoritePayerUseCase = favoritePayerUseCase
    )
}