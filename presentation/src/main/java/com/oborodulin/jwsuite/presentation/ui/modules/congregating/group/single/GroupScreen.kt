package com.oborodulin.jwsuite.presentation.ui.modules.congregating.group.single

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.jwsuite.presentation.AppState
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.RegionInput
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import kotlinx.coroutines.launch
import timber.log.Timber

private const val TAG = "Geo.ui.GroupScreen"

@Composable
fun GroupScreen(
    appState: AppState,
    viewModel: GroupViewModelImpl = hiltViewModel(),
    regionInput: RegionInput? = null
) {
    Timber.tag(TAG).d("RegionScreen(...) called: regionInput = %s", regionInput)
    LaunchedEffect(regionInput?.regionId) {
        Timber.tag(TAG).d("RegionScreen: LaunchedEffect() BEFORE collect ui state flow")
        viewModel.submitAction(GroupUiAction.Load(regionInput?.regionId))
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
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                }
            ) { it ->
                CommonScreen(paddingValues = it, state = state) {
                    val areInputsValid by viewModel.areInputsValid.collectAsStateWithLifecycle()
                    GroupView(viewModel)
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
                            viewModel.submitAction(GroupUiAction.Save)
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