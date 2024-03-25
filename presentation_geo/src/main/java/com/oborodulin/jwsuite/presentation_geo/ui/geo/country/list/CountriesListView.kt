package com.oborodulin.jwsuite.presentation_geo.ui.geo.country.list

import android.content.res.Configuration
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.oborodulin.home.common.ui.components.list.EditableListViewComponent
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.home.common.util.LogLevel.LOG_UI_STATE
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_geo.R
import com.oborodulin.home.common.ui.components.buttons.FetchButtonComponent
import com.oborodulin.jwsuite.presentation_geo.ui.geo.region.list.RegionsListUiAction
import com.oborodulin.jwsuite.presentation_geo.ui.geo.region.list.RegionsListViewModelImpl
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

private const val TAG = "Geo.CountriesListView"

@Composable
fun CountriesListView(
    countriesListViewModel: CountriesListViewModelImpl = hiltViewModel(),
    regionsListViewModel: RegionsListViewModelImpl = hiltViewModel(),
    navController: NavController,
    countryInput: NavigationInput.CountryInput? = null
) {
    Timber.tag(TAG).d("CountriesListView(...) called: countryInput = %s", countryInput)
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("CountriesListView -> LaunchedEffect()")
        countriesListViewModel.submitAction(CountriesListUiAction.Load())
    }
    val searchText by countriesListViewModel.searchText.collectAsStateWithLifecycle()
    countriesListViewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        if (LOG_UI_STATE) {
            Timber.tag(TAG).d("Collect ui state flow: %s", state)
        }
        CommonScreen(state = state) {
            EditableListViewComponent(
                items = it,
                searchedText = searchText.text,
                dlgConfirmDelResId = R.string.dlg_confirm_del_country,
                emptyListResId = R.string.countries_list_empty_text,
                isEmptyListTextOutput = true,
                fetchListControl = {
                    FetchButtonComponent(enabled = true) {
                        countriesListViewModel.submitAction(CountriesListUiAction.Load(true))
                    }
                },
                onEdit = { country ->
                    countriesListViewModel.submitAction(CountriesListUiAction.EditCountry(country.itemId!!))
                },
                onDelete = { region ->
                    countriesListViewModel.submitAction(CountriesListUiAction.DeleteCountry(region.itemId!!))
                }
            ) { region ->
                countriesListViewModel.singleSelectItem(region)
                regionsListViewModel.submitAction(RegionsListUiAction.Load(region.itemId!!))
            }
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("CountriesListView -> LaunchedEffect() -> collect single Event Flow")
        countriesListViewModel.singleEventFlow.collectLatest {
            Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
            when (it) {
                is CountriesListUiSingleEvent.OpenCountryScreen -> {
                    navController.navigate(it.navRoute)
                }
            }
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewRegionsList() {
    JWSuiteTheme {
        Surface {
/*            RegionsList(
                regions = CountriesListViewModelImpl.previewList(LocalContext.current),
                onEdit = {},
                onDelete = {},
                onClick = {}
            )*/
        }
    }
}
