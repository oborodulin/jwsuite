package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid.atwork

import androidx.compose.foundation.layout.Column
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.ui.AppState
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid.TerritoriesGridUiAction
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid.TerritoriesGridViewModel
import timber.log.Timber

private const val TAG = "Territoring.AtWorkTerritoriesConfirmationScreen"

@Composable
fun AtWorkTerritoriesConfirmationScreen(
    appState: AppState,
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    viewModel: TerritoriesGridViewModel//Impl = hiltViewModel()
) {
    Timber.tag(TAG).d("AtWorkTerritoriesConfirmationScreen(...) called")
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        Timber.tag(TAG)
            .d("AtWorkTerritoriesConfirmationScreen: LaunchedEffect() BEFORE collect ui state flow")
        viewModel.submitAction(TerritoriesGridUiAction.ProcessConfirmation)
    }
    viewModel.dialogTitleResId.collectAsStateWithLifecycle().value?.let { dialogTitleResId ->
        Timber.tag(TAG).d("Collect ui state flow")
        appState.actionBarSubtitle.value = stringResource(dialogTitleResId)
        val areInputsValid by viewModel.areAtWorkProcessInputsValid.collectAsStateWithLifecycle()
        JWSuiteTheme { //(darkTheme = true)
            ScaffoldComponent(
                appState = appState,
                topBarNavigationIcon = {
                    IconButton(onClick = { appState.backToBottomBarScreen() }) {
                        Icon(Icons.Outlined.ArrowBack, null)
                    }
                }
            ) { paddingValues ->
                Column(
                    modifier = Modifier.padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AtWorkTerritoriesConfirmationView(
                        sharedViewModel = appState.sharedViewModel.value, viewModel = viewModel
                    )
                }
            }
        }
    }
}