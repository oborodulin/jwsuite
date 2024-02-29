package com.oborodulin.jwsuite.presentation_geo.ui.geo.country.list

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.ListViewModel
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_ACTION
import com.oborodulin.jwsuite.data_geo.R
import com.oborodulin.jwsuite.domain.usecases.geocountry.CountryUseCases
import com.oborodulin.jwsuite.domain.usecases.geocountry.DeleteCountryUseCase
import com.oborodulin.jwsuite.domain.usecases.geocountry.GetCountriesUseCase
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.CountryInput
import com.oborodulin.jwsuite.presentation_geo.ui.model.CountriesListItem
import com.oborodulin.jwsuite.presentation_geo.ui.model.converters.CountriesListConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

private const val TAG = "Geo.CountriesListViewModelImpl"

@HiltViewModel
class CountriesListViewModelImpl @Inject constructor(
    private val useCases: CountryUseCases,
    private val converter: CountriesListConverter
) : CountriesListViewModel,
    ListViewModel<List<CountriesListItem>, UiState<List<CountriesListItem>>, CountriesListUiAction, CountriesListUiSingleEvent>() {
    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: CountriesListUiAction): Job {
        if (LOG_FLOW_ACTION) Timber.tag(TAG)
            .d("handleAction(CountriesListUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is CountriesListUiAction.Load -> loadCountries(action.isRemoteFetch)

            is CountriesListUiAction.EditCountry -> {
                submitSingleEvent(
                    CountriesListUiSingleEvent.OpenCountryScreen(
                        NavRoutes.Country.routeForCountry(CountryInput(action.countryId))
                    )
                )
            }

            is CountriesListUiAction.DeleteCountry -> deleteCountry(action.countryId)
        }
        return job
    }

    private fun loadCountries(isRemoteFetch: Boolean = false): Job {
        Timber.tag(TAG).d("loadCountries(...) called: isRemoteFetch = %s", isRemoteFetch)
        val job = viewModelScope.launch(errorHandler) {
            useCases.getCountriesUseCase.execute(GetCountriesUseCase.Request(isRemoteFetch)).map {
                converter.convert(it)
            }.collect {
                submitState(it)
            }
        }
        return job
    }

    private fun deleteCountry(countryId: UUID): Job {
        Timber.tag(TAG).d("deleteCountry(...) called: countryId = %s", countryId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.deleteCountryUseCase.execute(DeleteCountryUseCase.Request(countryId))
                .collect {}
        }
        return job
    }

    override fun initFieldStatesByUiModel(uiModel: List<CountriesListItem>): Job? = null

    companion object {
        fun previewModel(ctx: Context) =
            object : CountriesListViewModel {
                override val uiStateFlow = MutableStateFlow(UiState.Success(previewList(ctx)))
                override val singleEventFlow = Channel<CountriesListUiSingleEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()
                override val uiStateErrorMsg = MutableStateFlow("")

                override fun redirectedErrorMessage() = null
                override val searchText = MutableStateFlow(TextFieldValue(""))
                override val isSearching = MutableStateFlow(false)
                override fun onSearchTextChange(text: TextFieldValue) {}
                override fun clearSearchText() {}

                override val areSingleSelected = MutableStateFlow(false)
                override fun singleSelectItem(selectedItem: ListItemModel) {}
                override fun singleSelectedItem() = null

                override fun handleActionJob(
                    action: () -> Unit,
                    afterAction: (CoroutineScope) -> Unit
                ) {
                }

                override fun submitAction(action: CountriesListUiAction): Job? = null
            }

        fun previewList(ctx: Context) = listOf(
            CountriesListItem(
                id = UUID.randomUUID(),
                countryCode = ctx.resources.getString(R.string.def_country_ukraine_code),
                countryName = ctx.resources.getString(R.string.def_country_ukraine_name),
                osmInfo = "Ukraine[75.2145698, 98.5545665]"
            ),
            CountriesListItem(
                id = UUID.randomUUID(),
                countryCode = ctx.resources.getString(R.string.def_country_russia_code),
                countryName = ctx.resources.getString(R.string.def_country_russia_name),
                osmInfo = "Russia[78.2145698, 95.5545665]"
            )
        )
    }
}