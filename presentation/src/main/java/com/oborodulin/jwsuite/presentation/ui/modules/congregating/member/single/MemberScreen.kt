package com.oborodulin.jwsuite.presentation.ui.modules.congregating.member.single

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
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.GroupInput
import com.oborodulin.jwsuite.presentation.ui.modules.FavoriteCongregationViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.congregation.list.CongregationsListViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.congregation.single.CongregationViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.geo.locality.list.LocalitiesListViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.geo.locality.single.LocalityViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.geo.region.list.RegionsListViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.geo.region.single.RegionViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.list.RegionDistrictsListViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.single.RegionDistrictViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import kotlinx.coroutines.launch
import timber.log.Timber

private const val TAG = "Congregating.GroupScreen"

@Composable
fun GroupScreen(
    appState: AppState,
    sharedViewModel: FavoriteCongregationViewModelImpl = hiltViewModel(),
    groupViewModel: MemberViewModelImpl = hiltViewModel(),
    congregationsListViewModel: CongregationsListViewModelImpl = hiltViewModel(),
    congregationViewModel: CongregationViewModelImpl = hiltViewModel(),
    localitiesListViewModel: LocalitiesListViewModelImpl = hiltViewModel(),
    localityViewModel: LocalityViewModelImpl = hiltViewModel(),
    regionsListViewModel: RegionsListViewModelImpl = hiltViewModel(),
    regionViewModel: RegionViewModelImpl = hiltViewModel(),
    regionDistrictsListViewModel: RegionDistrictsListViewModelImpl = hiltViewModel(),
    regionDistrictViewModel: RegionDistrictViewModelImpl = hiltViewModel(),
    groupInput: GroupInput? = null
) {
    Timber.tag(TAG).d("GroupScreen(...) called: groupInput = %s", groupInput)
    LaunchedEffect(groupInput?.groupId) {
        Timber.tag(TAG).d("GroupScreen: LaunchedEffect() BEFORE collect ui state flow")
        groupViewModel.submitAction(MemberUiAction.Load(groupInput?.groupId))
    }
    groupViewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        groupViewModel.dialogTitleResId.collectAsStateWithLifecycle().value?.let {
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
                    val areInputsValid by groupViewModel.areInputsValid.collectAsStateWithLifecycle()
                    MemberView(
                        sharedViewModel,
                        groupViewModel,
                        congregationsListViewModel,
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
                        groupViewModel.onContinueClick {
                            Timber.tag(TAG).d("GroupScreen(...): Start viewModelScope.launch")
                            groupViewModel.viewModelScope().launch {
                                groupViewModel.actionsJobFlow.collect {
                                    Timber.tag(TAG).d(
                                        "GroupScreen(...): Start actionsJobFlow.collect [job = %s]",
                                        it?.toString()
                                    )
                                    it?.join()
                                    appState.backToBottomBarScreen()
                                }
                            }
                            groupViewModel.submitAction(MemberUiAction.Save)
                            Timber.tag(TAG).d("GroupScreen(...): onSubmit() executed")
                        }
                    }, enabled = areInputsValid) {
                        Text(text = stringResource(com.oborodulin.home.common.R.string.btn_save_lbl))
                    }
                }
            }
        }
    }
}