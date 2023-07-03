package com.oborodulin.jwsuite.presentation.ui.modules.geo.region.single

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
import com.oborodulin.jwsuite.presentation.navigation.inputs.CongregationInput
import kotlinx.coroutines.launch
import timber.log.Timber

private const val TAG = "Geo.ui.RegionScreen"

@Composable
fun RegionScreen(
    appState: AppState,
    viewModel: RegionViewModelImpl = hiltViewModel(),
    regionInput: CongregationInput? = null
) {
    Timber.tag(TAG).d("RegionScreen(...) called: regionInput = %s", regionInput)
    LaunchedEffect(regionInput?.congregationId) {
        Timber.tag(TAG).d("RegionScreen: LaunchedEffect() BEFORE collect ui state flow")
        when (regionInput) {
            null -> {
                viewModel.dialogTitleResId = R.string.region_new_subheader
                viewModel.submitAction(RegionUiAction.Create)
            }

            else -> {
                viewModel.dialogTitleResId = R.string.region_subheader
                viewModel.submitAction(RegionUiAction.Load(regionInput.congregationId))
            }
        }
    }
    viewModel.uiStateFlow.collectAsState().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        HomeComposableTheme { //(darkTheme = true)
            ScaffoldComponent(
                appState = appState,
                //scaffoldState = appState.regionScaffoldState,
                topBarTitleId = viewModel.dialogTitleResId,
                topBarNavigationIcon = {
                    IconButton(onClick = { appState.backToBottomBarScreen() }) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                }
            ) { it ->
                CommonScreen(paddingValues = it, state = state) {
                    val areInputsValid by viewModel.areInputsValid.collectAsStateWithLifecycle()
                    RegionView(viewModel)
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = {
                        viewModel.onContinueClick {
                            Timber.tag(TAG).d("RegionScreen(...): Start viewModelScope.launch")
                            viewModel.viewModelScope().launch {
                                viewModel.actionsJobFlow.collect {
                                    Timber.tag(TAG).d(
                                        "RegionScreen(...): Start actionsJobFlow.collect [job = %s]",
                                        it?.toString()
                                    )
                                    it?.join()
                                    appState.backToBottomBarScreen()
                                }
                            }
                            viewModel.submitAction(RegionUiAction.Save)
                            Timber.tag(TAG).d("RegionScreen(...): onSubmit() executed")
                        }
                    }, enabled = areInputsValid) {
                        Text(text = stringResource(com.oborodulin.home.common.R.string.btn_save_lbl))
                    }
                }
            }
        }
    }
}