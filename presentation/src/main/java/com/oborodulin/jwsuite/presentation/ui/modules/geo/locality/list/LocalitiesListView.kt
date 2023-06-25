package com.oborodulin.jwsuite.presentation.ui.modules.geo.locality.list

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.oborodulin.home.accounting.R
import com.oborodulin.jwsuite.presentation.ui.congregating.model.CongregationListItem
import com.oborodulin.home.billing.ui.subtotals.PayerServiceSubtotalsListUiAction
import com.oborodulin.home.billing.ui.subtotals.PayerServiceSubtotalsListView
import com.oborodulin.home.billing.ui.subtotals.PayerServiceSubtotalsListViewModel
import com.oborodulin.home.billing.ui.subtotals.PayerServiceSubtotalsListViewModelImpl
import com.oborodulin.home.common.ui.ComponentUiAction
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.home.metering.ui.value.MeterValuesListUiAction
import com.oborodulin.home.metering.ui.value.MeterValuesListView
import com.oborodulin.home.metering.ui.value.MeterValuesListViewModel
import com.oborodulin.home.metering.ui.value.MeterValuesListViewModelImpl
import com.oborodulin.jwsuite.presentation.AppState
import com.oborodulin.jwsuite.presentation.navigation.inputs.CongregationInput
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import java.util.*

private const val TAG = "Accounting.ui.PayersListView"

@Composable
fun PayersListView(
    appState: AppState,
    payersListViewModel: LocalitiesListViewModelImpl = hiltViewModel(),
    meterValuesListViewModel: MeterValuesListViewModelImpl = hiltViewModel(),
    payerServiceSubtotalsListViewModel: PayerServiceSubtotalsListViewModelImpl = hiltViewModel(),
    navController: NavController,
    congregationInput: CongregationInput
) {
    Timber.tag(TAG).d("PayersListView(...) called: payerInput = %s", congregationInput)
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("PayersListView: LaunchedEffect() BEFORE collect ui state flow")
        payersListViewModel.submitAction(LocalitiesListUiAction.Load)
    }
    payersListViewModel.uiStateFlow.collectAsState().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        CommonScreen(state = state) {
            PayersAccounting(
                payers = it,
                appState = appState,
                localitiesListViewModel = payersListViewModel,
                payerServiceSubtotalsListViewModel = payerServiceSubtotalsListViewModel,
                meterValuesListViewModel = meterValuesListViewModel,
                navController = navController,
                congregationInput = congregationInput
            )
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("PayersListView: LaunchedEffect() AFTER collect ui state flow")
        payersListViewModel.singleEventFlow.collectLatest {
            Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
            when (it) {
                is LocalitiesListUiSingleEvent.OpenPayerScreen -> {
                    navController.navigate(it.navRoute)
                }
            }
        }
    }
}

@Composable
fun PayersList(
    payers: List<CongregationListItem>,
    congregationInput: CongregationInput,
    onFavorite: (CongregationListItem) -> Unit,
    onClick: (CongregationListItem) -> Unit,
    onEdit: (CongregationListItem) -> Unit,
    onDelete: (CongregationListItem) -> Unit
) {
    Timber.tag(TAG).d("PayersList(...) called: payerInput = %s", congregationInput)
    var selectedIndex by remember { mutableStateOf(-1) } // by
    if (payers.isNotEmpty()) {
        val listState = rememberLazyListState()
        LazyColumn(
            state = listState,
            modifier = Modifier
                .selectableGroup() // Optional, for accessibility purpose
                .padding(8.dp)
                .focusable(enabled = true)
        ) {
            items(payers.size) { index ->
                payers[index].let { payer ->
                    val isSelected =
                        ((selectedIndex == -1) and ((congregationInput.congregationId == payer.id) || payer.isFavorite)) || (selectedIndex == index)
                    PayerListItemComponent(
                        icon = com.oborodulin.jwsuite.presentation.R.drawable.outline_house_black_36,
                        item = payer,
                        itemActions = listOf(
                            ComponentUiAction.EditListItem { onEdit(payer) },
                            ComponentUiAction.DeleteListItem(
                                stringResource(
                                    R.string.dlg_confirm_del_payer,
                                    payer.fullName
                                )
                            ) { onDelete(payer) }),
                        selected = isSelected,
                        background = (if (isSelected) Color.LightGray else Color.Transparent),
                        onFavorite = { onFavorite(payer) },
                    ) {
                        //selectedIndex = if (selectedIndex != index) index else -1
                        if (selectedIndex != index) selectedIndex = index
                        onClick(payer)
                    }
                }
            }
        }
    }
    /*
            list.apply {
                val error = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend AS LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append AS LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh AS LoadState.Error
                    else -> null
                }

                val loading = when {
                    loadState.prepend is LoadState.Loading -> loadState.prepend AS LoadState.Loading
                    loadState.append is LoadState.Loading -> loadState.append AS LoadState.Loading
                    loadState.refresh is LoadState.Loading -> loadState.refresh AS LoadState.Loading
                    else -> null
                }

                if (loading != null) {
                    repeat((0..20).count()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .background(color = Color.DarkGray)
                            ) {
                                ShimmerAnimation()
                            }
                        }
                    }
                }

                if (error != null) {
                    //TODO: add error handler
                    item { SweetError(message = error.error.localizedMessage ?: "Error") }
                }
            }*/
    else {
        Text(
            text = stringResource(R.string.payer_list_empty_text),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun PayersAccounting(
    payers: List<CongregationListItem>,
    appState: AppState,
    localitiesListViewModel: LocalitiesListViewModel,
    payerServiceSubtotalsListViewModel: PayerServiceSubtotalsListViewModel,
    meterValuesListViewModel: MeterValuesListViewModel,
    navController: NavController,
    congregationInput: CongregationInput
) {
    Timber.tag(TAG).d("PayersAccounting(...) called: payerInput = %s", congregationInput)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Box(
            modifier = Modifier
                .padding(vertical = 4.dp)
                .clip(RoundedCornerShape(16.dp))
                //.background(MaterialTheme.colorScheme.background, shape = RoundedCornerShape(20.dp))
                .weight(3.3f)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            PayersList(payers,
                congregationInput = congregationInput,
                onFavorite = { payer ->
                    localitiesListViewModel.handleActionJob(action = {
                        localitiesListViewModel.submitAction(
                            LocalitiesListUiAction.FavoritePayer(payer.id)
                        )
                    },
                        afterAction = {
                            meterValuesListViewModel.submitAction(
                                MeterValuesListUiAction.Load(payer.id)
                            )
                        }
                    )
                },
                onClick = { payer ->
                    localitiesListViewModel.setPrimaryObjectData(
                        arrayListOf(
                            payer.id.toString(),
                            payer.fullName
                        )
                    )
                    appState.actionBarSubtitle.value = payer.address
                    with(payerServiceSubtotalsListViewModel) {
                        setPrimaryObjectData(arrayListOf(payer.id.toString()))
                        submitAction(PayerServiceSubtotalsListUiAction.Load(payer.id))
                    }
                    with(meterValuesListViewModel) {
                        clearInputFieldsStates()
                        setPrimaryObjectData(arrayListOf(payer.id.toString()))
                        submitAction(MeterValuesListUiAction.Load(payer.id))
                    }
                },
                onEdit = { payer ->
                    localitiesListViewModel.submitAction(
                        LocalitiesListUiAction.EditPayer(
                            payer.id
                        )
                    )
                }
            ) { payer ->
                localitiesListViewModel.handleActionJob(action = {
                    localitiesListViewModel.submitAction(LocalitiesListUiAction.DeletePayer(payer.id))
                },
                    afterAction = {
                        meterValuesListViewModel.submitAction(MeterValuesListUiAction.Init)
                    }
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clip(RoundedCornerShape(16.dp))
                .weight(3.4f)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            MeterValuesListView(
                viewModel = meterValuesListViewModel,
                navController = navController,
                payerInput = congregationInput
            )
        }
        Box(
            modifier = Modifier
                .padding(vertical = 4.dp)
                .clip(RoundedCornerShape(16.dp))
                .weight(3.3f)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            PayerServiceSubtotalsListView(
                viewModel = payerServiceSubtotalsListViewModel,
                navController = navController,
                payerInput = congregationInput
            )
            //Text(text = "Итого:")
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewPayersAccounting() {
    PayersList(
        payers = LocalitiesListViewModelImpl.previewList(LocalContext.current),
        congregationInput = CongregationInput(UUID.randomUUID()),
        onFavorite = {},
        onClick = {},
        onEdit = {},
        onDelete = {})
}
