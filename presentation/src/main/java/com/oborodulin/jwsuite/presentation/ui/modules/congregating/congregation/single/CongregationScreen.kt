package com.oborodulin.jwsuite.presentation.ui.modules.congregating.congregation.single

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.home.common.ui.theme.HomeComposableTheme
import com.oborodulin.jwsuite.presentation.AppState
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.navigation.inputs.CongregationInput
import com.oborodulin.jwsuite.presentation.ui.modules.geo.locality.list.LocalitiesListViewModelImpl
import timber.log.Timber

private const val TAG = "Congregating.ui.CongregationScreen"

@Composable
fun CongregationScreen(
    appState: AppState,
    congregationViewModel: CongregationViewModelImpl = hiltViewModel(),
    localitiesListViewModel: LocalitiesListViewModelImpl = hiltViewModel(),
    congregationInput: CongregationInput? = null
) {
    Timber.tag(TAG).d("CongregationScreen(...) called: congregationInput = %s", congregationInput)
    LaunchedEffect(congregationInput?.congregationId) {
        Timber.tag(TAG).d("CongregationScreen: LaunchedEffect() BEFORE collect ui state flow")
        when (congregationInput) {
            null -> congregationViewModel.submitAction(CongregationUiAction.Create)
            else -> congregationViewModel.submitAction(CongregationUiAction.Load(congregationInput.congregationId))
        }
    }
    val topBarTitleId = when (congregationInput) {
        null -> R.string.congregation_new_subheader
        else -> R.string.congregation_subheader
    }
    congregationViewModel.uiStateFlow.collectAsState().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        HomeComposableTheme {
            ScaffoldComponent(
                appState = appState,
                topBarTitleId = topBarTitleId,
                topBarNavigationIcon = {
                    IconButton(onClick = { appState.backToBottomBarScreen() }) {
                        Icon(Icons.Outlined.Close, null)
                    }
                }
            ) {
                CommonScreen(paddingValues = it, state = state) {
                    CongregationView(appState, congregationViewModel, localitiesListViewModel) {
                        congregationViewModel.submitAction(CongregationUiAction.Save)
                    }
                }
            }
        }
    }
}
