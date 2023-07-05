package com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.single

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
import com.oborodulin.home.common.ui.theme.HomeComposableTheme
import com.oborodulin.jwsuite.presentation.AppState
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.CongregationInput
import kotlinx.coroutines.launch
import timber.log.Timber

private const val TAG = "Geo.ui.LocalityScreen"

@Composable
fun LocalityScreen(
    appState: AppState,
    viewModel: RegionDistrictViewModelImpl = hiltViewModel(),
    localityInput: CongregationInput? = null
) {
    Timber.tag(TAG).d("LocalityScreen(...) called: localityInput = %s", localityInput)
    LaunchedEffect(localityInput?.congregationId) {
        Timber.tag(TAG).d("LocalityScreen: LaunchedEffect() BEFORE collect ui state flow")
        when (localityInput) {
            null -> {
                viewModel.dialogTitleResId = R.string.locality_new_subheader
                viewModel.submitAction(RegionDistrictUiAction.Create)
            }

            else -> {
                viewModel.dialogTitleResId = R.string.locality_subheader
                viewModel.submitAction(RegionDistrictUiAction.Load(localityInput.congregationId))
            }
        }
    }
    viewModel.uiStateFlow.collectAsState().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        HomeComposableTheme {
            ScaffoldComponent(
                appState = appState,
                topBarTitleId = viewModel.dialogTitleResId,
                topBarNavigationIcon = {
                    IconButton(onClick = { appState.backToBottomBarScreen() }) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                }
            ) { it ->
                CommonScreen(paddingValues = it, state = state) {
                    val areInputsValid by viewModel.areInputsValid.collectAsStateWithLifecycle()
                    LocalityView(viewModel)
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = {
                        viewModel.onContinueClick {
                            Timber.tag(TAG).d("LocalityScreen(...): Start viewModelScope.launch")
                            viewModel.viewModelScope().launch {
                                viewModel.actionsJobFlow.collect {
                                    Timber.tag(TAG).d(
                                        "LocalityScreen(...): Start actionsJobFlow.collect [job = %s]",
                                        it?.toString()
                                    )
                                    it?.join()
                                    appState.backToBottomBarScreen()
                                }
                            }
                            viewModel.submitAction(RegionDistrictUiAction.Save)
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