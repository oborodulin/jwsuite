package com.oborodulin.jwsuite.presentation.ui.modules.geo.locality.single

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.LocalityInput
import com.oborodulin.jwsuite.presentation.ui.modules.geo.region.list.RegionsListViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.geo.region.single.RegionViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.list.RegionDistrictsListViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.single.RegionDistrictViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import kotlinx.coroutines.launch
import timber.log.Timber

private const val TAG = "Geo.ui.LocalityScreen"

@Composable
fun LocalityScreen(
    appState: AppState,
    localityViewModel: LocalityViewModelImpl = hiltViewModel(),
    regionsListViewModel: RegionsListViewModelImpl = hiltViewModel(),
    regionViewModel: RegionViewModelImpl = hiltViewModel(),
    regionDistrictsListViewModel: RegionDistrictsListViewModelImpl = hiltViewModel(),
    regionDistrictViewModel: RegionDistrictViewModelImpl = hiltViewModel(),
    localityInput: LocalityInput? = null
) {
    Timber.tag(TAG).d("LocalityScreen(...) called: localityInput = %s", localityInput)
    LaunchedEffect(localityInput?.localityId) {
        Timber.tag(TAG).d("LocalityScreen: LaunchedEffect() BEFORE collect ui state flow")
        when (localityInput) {
            null -> {
                localityViewModel.dialogTitleResId = R.string.locality_new_subheader
                localityViewModel.submitAction(LocalityUiAction.Create)
            }

            else -> {
                localityViewModel.dialogTitleResId = R.string.locality_subheader
                localityViewModel.submitAction(LocalityUiAction.Load(localityInput.localityId))
            }
        }
    }
    localityViewModel.uiStateFlow.collectAsState().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        JWSuiteTheme { //(darkTheme = true)
            ScaffoldComponent(
                appState = appState,
                //scaffoldState = appState.localityScaffoldState,
                topBarTitleId = localityViewModel.dialogTitleResId,
                topBarNavigationIcon = {
                    IconButton(onClick = { appState.backToBottomBarScreen() }) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                }
            ) { it ->
                CommonScreen(paddingValues = it, state = state) {
                    val areInputsValid by localityViewModel.areInputsValid.collectAsStateWithLifecycle()
                    LocalityView(
                        localityViewModel,
                        regionsListViewModel, regionViewModel,
                        regionDistrictsListViewModel, regionDistrictViewModel
                    )
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = {
                        localityViewModel.onContinueClick {
                            Timber.tag(TAG).d("LocalityScreen(...): Start viewModelScope.launch")
                            localityViewModel.viewModelScope().launch {
                                localityViewModel.actionsJobFlow.collect {
                                    Timber.tag(TAG).d(
                                        "LocalityScreen(...): Start actionsJobFlow.collect [job = %s]",
                                        it?.toString()
                                    )
                                    it?.join()
                                    appState.backToBottomBarScreen()
                                }
                            }
                            localityViewModel.submitAction(LocalityUiAction.Save)
                            Timber.tag(TAG).d("LocalityScreen(...): onSubmit() executed")
                        }
                    }, enabled = areInputsValid) {
                        Text(text = stringResource(com.oborodulin.home.common.R.string.btn_save_lbl))
                    }
                }
            }
        }
    }
}