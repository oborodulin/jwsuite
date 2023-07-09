package com.oborodulin.jwsuite.presentation.ui.modules.congregating.congregation.single

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.jwsuite.presentation.AppState
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.CongregationInput
import com.oborodulin.jwsuite.presentation.ui.modules.geo.locality.list.LocalitiesListViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.geo.locality.single.LocalityViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.geo.region.list.RegionsListViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.geo.region.single.RegionViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.list.RegionDistrictsListViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.single.RegionDistrictViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import kotlinx.coroutines.launch
import timber.log.Timber

private const val TAG = "Congregating.ui.CongregationScreen"

@Composable
fun CongregationScreen(
    appState: AppState,
    congregationViewModel: CongregationViewModelImpl = hiltViewModel(),
    localitiesListViewModel: LocalitiesListViewModelImpl = hiltViewModel(),
    localityViewModel: LocalityViewModelImpl = hiltViewModel(),
    regionsListViewModel: RegionsListViewModelImpl = hiltViewModel(),
    regionViewModel: RegionViewModelImpl = hiltViewModel(),
    regionDistrictsListViewModel: RegionDistrictsListViewModelImpl = hiltViewModel(),
    regionDistrictViewModel: RegionDistrictViewModelImpl = hiltViewModel(),
    congregationInput: CongregationInput? = null
) {
    Timber.tag(TAG).d("CongregationScreen(...) called: congregationInput = %s", congregationInput)
    LaunchedEffect(congregationInput?.congregationId) {
        Timber.tag(TAG).d("CongregationScreen: LaunchedEffect() BEFORE collect ui state flow")
        when (congregationInput) {
            null -> {
                congregationViewModel.dialogTitleResId = R.string.congregation_new_subheader
                congregationViewModel.submitAction(CongregationUiAction.Create)
            }

            else -> {
                congregationViewModel.dialogTitleResId = R.string.congregation_subheader
                congregationViewModel.submitAction(CongregationUiAction.Load(congregationInput.congregationId))
            }
        }
    }
    congregationViewModel.uiStateFlow.collectAsState().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        JWSuiteTheme {
            ScaffoldComponent(
                appState = appState,
                topBarTitleId = congregationViewModel.dialogTitleResId,
                topBarNavigationIcon = {
                    IconButton(onClick = { appState.backToBottomBarScreen() }) {
                        Icon(Icons.Outlined.Close, null)
                    }
                }
            ) {
                CommonScreen(paddingValues = it, state = state) {
                    val areInputsValid by congregationViewModel.areInputsValid.collectAsStateWithLifecycle()
                    CongregationView(
                        congregationViewModel,
                        localitiesListViewModel,
                        localityViewModel,
                        regionsListViewModel,
                        regionViewModel,
                        regionDistrictsListViewModel,
                        regionDistrictViewModel
                    )
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = {
                        congregationViewModel.onContinueClick {
                            Timber.tag(TAG)
                                .d("CongregationScreen(...): Start viewModelScope.launch")
                            congregationViewModel.viewModelScope().launch {
                                congregationViewModel.actionsJobFlow.collect { job ->
                                    Timber.tag(TAG).d(
                                        "CongregationScreen(...): Start actionsJobFlow.collect [job = %s]",
                                        job?.toString()
                                    )
                                    job?.join()
                                    appState.backToBottomBarScreen()
                                }
                            }
                            congregationViewModel.submitAction(CongregationUiAction.Save)
                            Timber.tag(TAG).d("CongregationScreen(...): onSubmit() executed")
                        }
                    }, enabled = areInputsValid) {
                        Text(text = stringResource(com.oborodulin.home.common.R.string.btn_save_lbl))
                    }
                }
            }
        }
    }
}
