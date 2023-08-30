package com.oborodulin.jwsuite.presentation_geo.ui.geo.microdistrict.single

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.components.buttons.SaveButtonComponent
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.jwsuite.presentation_geo.AppState
import com.oborodulin.jwsuite.presentation_geo.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation_geo.navigation.NavigationInput.MicrodistrictInput
import com.oborodulin.jwsuite.presentation_geo.ui.theme.JWSuiteTheme
import kotlinx.coroutines.launch
import timber.log.Timber

private const val TAG = "Geo.MicrodistrictScreen"

@Composable
fun MicrodistrictScreen(
    appState: AppState,
    viewModel: MicrodistrictViewModelImpl = hiltViewModel(),
    microdistrictInput: MicrodistrictInput? = null
) {
    Timber.tag(TAG)
        .d("MicrodistrictScreen(...) called: microdistrictInput = %s", microdistrictInput)
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(microdistrictInput?.microdistrictId) {
        Timber.tag(TAG).d("MicrodistrictScreen: LaunchedEffect() BEFORE collect ui state flow")
        viewModel.submitAction(MicrodistrictUiAction.Load(microdistrictInput?.microdistrictId))
    }
    viewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        viewModel.dialogTitleResId.collectAsStateWithLifecycle().value?.let {
            appState.actionBarSubtitle.value = stringResource(it)
        }
        JWSuiteTheme { //(darkTheme = true)
            ScaffoldComponent(
                appState = appState,
                topBarNavigationIcon = {
                    IconButton(onClick = { appState.backToBottomBarScreen() }) {
                        Icon(Icons.Outlined.ArrowBack, null)
                    }
                }
            ) { paddingValues ->
                CommonScreen(paddingValues = paddingValues, state = state) {
                    val areInputsValid by viewModel.areInputsValid.collectAsStateWithLifecycle()
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        MicrodistrictView()
                        Spacer(Modifier.height(8.dp))
                        SaveButtonComponent(
                            enabled = areInputsValid,
                            onClick = {
                                Timber.tag(TAG)
                                    .d("MicrodistrictScreen(...): Save Button onClick...")
                                // checks all errors
                                viewModel.onContinueClick {
                                    // if success, then save and backToBottomBarScreen
                                    // https://stackoverflow.com/questions/72987545/how-to-navigate-to-another-screen-after-call-a-viemodelscope-method-in-viewmodel
                                    coroutineScope.launch {
                                        viewModel.submitAction(MicrodistrictUiAction.Save)
                                            .join()
                                        appState.backToBottomBarScreen()
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}