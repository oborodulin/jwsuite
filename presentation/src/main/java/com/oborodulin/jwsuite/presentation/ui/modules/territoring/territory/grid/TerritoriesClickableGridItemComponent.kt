package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.grid

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oborodulin.home.common.util.OnListItemEvent
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.TerritoriesListItem
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation.util.Constants
import timber.log.Timber

private const val TAG = "Territoring.TerritoriesClickableGridItemComponent"

@Composable
fun TerritoriesClickableGridItemComponent(
    territory: TerritoriesListItem,
    cellSize: Dp = Constants.CELL_SIZE,
    onChecked: (Boolean) -> Unit = {},
    onClick: (TerritoriesListItem) -> Unit = {}
) {
    Timber.tag(TAG).d("TerritoriesClickableGridItemComponent(...) called")
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable { onClick(territory) },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val territoryMarks = territory.cardNum.split("-")
                if (territoryMarks.isNotEmpty()) {
                    // CongregationMark + TerritoryCategoryMark -
                    Text(
                        modifier = Modifier
                            .padding(start = 2.dp)
                            .alignByBaseline(),
                        text = territoryMarks[0],
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Start,
                    )
                }
                if (territoryMarks.size > 1) {
                    // TerritoryNum + TerritoryBusinessMark?
                    Text(
                        modifier = Modifier
                            .padding(end = 2.dp)
                            .alignByBaseline(),
                        text = territoryMarks[1],
                        fontSize = 20.sp,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.End,
                    )
                }
                var checkedState by rememberSaveable { mutableStateOf(territory.isChecked) }
                Checkbox(
                    modifier = Modifier
                        .padding(0.dp)
                        //.align(Alignment.CenterVertically),
                        .alignByBaseline(),
                    checked = checkedState,
                    onCheckedChange = {
                        checkedState = it
                        territory.isChecked = checkedState
                        onChecked(it)
                    }
                )
            }
            Divider(Modifier.width(cellSize.times(0.9f)), thickness = 2.dp)
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                territory.isPrivateSector?.let {
                    if (it) {
                        Image(
                            modifier = Modifier.clip(RoundedCornerShape(4.dp)),
                            painter = painterResource(R.drawable.ic_cottage_18),
                            contentDescription = stringResource(R.string.ter_private_sector_cnt_desc)
                        )
                    }
                }
                if (territory.isInPerimeter) {
                    Image(
                        modifier = Modifier.clip(RoundedCornerShape(4.dp)),
                        painter = painterResource(R.drawable.ic_aspect_ratio_18),
                        contentDescription = stringResource(R.string.ter_in_perimeter_cnt_desc)
                    )
                }
                if (territory.isBusiness) {
                    Image(
                        modifier = Modifier.clip(RoundedCornerShape(4.dp)),
                        painter = painterResource(R.drawable.ic_business_center_18),
                        contentDescription = stringResource(R.string.ter_business_cnt_desc)
                    )
                }
                if (territory.isGroupMinistry) {
                    Image(
                        modifier = Modifier.clip(RoundedCornerShape(4.dp)),
                        painter = painterResource(R.drawable.ic_group_18),
                        contentDescription = stringResource(R.string.ter_group_ministry_cnt_desc)
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { onClick(territory) },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    modifier = Modifier
                        .padding(4.dp)
                        .align(Alignment.CenterHorizontally),
                    text = territory.cardLocation,
                    fontSize = 12.sp,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.End,
                    overflow = TextOverflow.Ellipsis,
                )
                territory.member?.memberShortName?.let {
                    Text(
                        modifier = Modifier.padding(2.dp),
                        text = it,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Start,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                territory.territoryDesc?.let {
                    Divider(
                        Modifier
                            .width(cellSize.times(0.9f))
                            .align(Alignment.CenterHorizontally), thickness = 2.dp
                    )
                    Text(
                        modifier = Modifier.padding(2.dp),
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Start,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewTerritoriesClickableGridItemComponent() {
    JWSuiteTheme {
        Surface {
            TerritoriesClickableGridItemComponent(
                territory = TerritoriesGridViewModelImpl.previewList(LocalContext.current)[0],
                cellSize = 110.dp,
                onChecked = {},
                onClick = {}
            )
        }
    }
}