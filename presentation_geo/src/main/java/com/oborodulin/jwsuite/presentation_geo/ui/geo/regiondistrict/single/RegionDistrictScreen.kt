package com.oborodulin.jwsuite.presentation_geo.ui.geo.regiondistrict.single

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.components.buttons.SaveButtonComponent
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.jwsuite.presentation.AppState
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.RegionDistrictInput
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import kotlinx.coroutines.launch
import timber.log.Timber

private const val TAG = "Geo.RegionDistrictScreen"

@Composable
fun RegionDistrictScreen(
    appState: AppState,
    regionDistrictViewModel: RegionDistrictViewModelImpl = hiltViewModel(),
    regionDistrictInput: RegionDistrictInput? = null
) {
    Timber.tag(TAG).d("RegionDistrictScreen(...) called: localityInput = %s", regionDistrictInput)
    LaunchedEffect(regionDistrictInput?.regionDistrictId) {
        Timber.tag(TAG).d("RegionDistrictScreen: LaunchedEffect() BEFORE collect ui state flow")
        regionDistrictViewModel.submitAction(
            RegionDistrictUiAction.Load(regionDistrictInput?.regionDistrictId)
        )
    }
    regionDistrictViewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        regionDistrictViewModel.dialogTitleResId.collectAsStateWithLifecycle().value?.let {
            appState.actionBarSubtitle.value = stringResource(it)
        }
        JWSuiteTheme {
            ScaffoldComponent(
                appState = appState,
                topBarNavigationIcon = {
                    IconButton(onClick = { appState.backToBottomBarScreen() }) {
                        Icon(Icons.Outlined.ArrowBack, null)
                    }
                }
            ) { it ->
                CommonScreen(paddingValues = it, state = state) {
                    val areInputsValid by regionDistrictViewModel.areInputsValid.collectAsStateWithLifecycle()
                    RegionDistrictView()
                    Spacer(Modifier.height(8.dp))
                    SaveButtonComponent(
                        enabled = areInputsValid,
                        onClick = {
                            regionDistrictViewModel.onContinueClick {
                                Timber.tag(TAG)
                                    .d("RegionDistrictScreen(...): Start viewModelScope.launch")
                                regionDistrictViewModel.viewModelScope().launch {
                                    regionDistrictViewModel.actionsJobFlow.collect {
                                        Timber.tag(TAG).d(
                                            "RegionDistrictScreen(...): Start actionsJobFlow.collect [job = %s]",
                                            it?.toString()
                                        )
                                        it?.join()
                                        appState.backToBottomBarScreen()
                                    }
                                }
                                regionDistrictViewModel.submitAction(RegionDistrictUiAction.Save)
                                Timber.tag(TAG).d("RegionDistrictScreen(...): onSubmit() executed")
                            }
                        }
                    )
                }
            }
        }
    }
}