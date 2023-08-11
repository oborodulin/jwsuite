package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.details

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryInput
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.TerritoryDetailsListItem
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import timber.log.Timber

private const val TAG = "Territoring.TerritoryDetailsView"

@Composable
fun TerritoryDetailsView(
    viewModel: TerritoryDetailsViewModelImpl = hiltViewModel(),
    territoryInput: TerritoryInput? = null
) {
    Timber.tag(TAG).d("TerritoryDetailsView(...) called: territoryInput = %s", territoryInput)
    LaunchedEffect(territoryInput?.territoryId) {
        Timber.tag(TAG).d("TerritoryDetailsView: LaunchedEffect() BEFORE collect ui state flow")
        territoryInput?.let { viewModel.submitAction(TerritoryDetailsUiAction.Load(it.territoryId)) }
    }
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()
    viewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        CommonScreen(state = state) {
            TerritoryDetails(details = it)
        }
    }
}

@Composable
fun TerritoryDetails(details: List<TerritoryDetailsListItem>) {
    Timber.tag(TAG).d("TerritoryDetails(...) called")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .padding(horizontal = 4.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            details.forEach { Text("${it.headline}${it.supportingText}") }
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewMembersList() {
    JWSuiteTheme {
        Surface {
            TerritoryDetails(
                details = TerritoryDetailsViewModelImpl.previewList(LocalContext.current)
            )
        }
    }
}
