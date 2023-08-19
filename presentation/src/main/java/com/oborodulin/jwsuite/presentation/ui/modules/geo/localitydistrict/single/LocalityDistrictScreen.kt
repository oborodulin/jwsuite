package com.oborodulin.jwsuite.presentation.ui.modules.geo.localitydistrict.single

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
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.LocalityDistrictInput
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import kotlinx.coroutines.launch
import timber.log.Timber

private const val TAG = "Geo.LocalityDistrictScreen"

@Composable
fun LocalityDistrictScreen(
    appState: AppState,
    viewModel: LocalityDistrictViewModelImpl = hiltViewModel(),
    localityDistrictInput: LocalityDistrictInput? = null
) {
    Timber.tag(TAG).d("LocalityDistrictScreen(...) called: localityInput = %s", localityDistrictInput)
    LaunchedEffect(localityDistrictInput?.localityDistrictId) {
        Timber.tag(TAG).d("LocalityDistrictScreen: LaunchedEffect() BEFORE collect ui state flow")
        viewModel.submitAction(
            LocalityDistrictUiAction.Load(localityDistrictInput?.localityDistrictId)
        )
    }
    viewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        viewModel.dialogTitleResId.collectAsStateWithLifecycle().value?.let {
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
                    val areInputsValid by viewModel.areInputsValid.collectAsStateWithLifecycle()
                    LocalityDistrictView()
                    Spacer(Modifier.height(8.dp))
                    SaveButtonComponent(
                        enabled = areInputsValid,
                        onClick = {
                            viewModel.onContinueClick {
                                Timber.tag(TAG)
                                    .d("LocalityDistrictScreen(...): Start viewModelScope.launch")
                                viewModel.viewModelScope().launch {
                                    viewModel.actionsJobFlow.collect {
                                        Timber.tag(TAG).d(
                                            "LocalityDistrictScreen(...): Start actionsJobFlow.collect [job = %s]",
                                            it?.toString()
                                        )
                                        it?.join()
                                        appState.backToBottomBarScreen()
                                    }
                                }
                                viewModel.submitAction(LocalityDistrictUiAction.Save)
                                Timber.tag(TAG).d("LocalityDistrictScreen(...): onSubmit() executed")
                            }
                        }
                    )
                }
            }
        }
    }
}