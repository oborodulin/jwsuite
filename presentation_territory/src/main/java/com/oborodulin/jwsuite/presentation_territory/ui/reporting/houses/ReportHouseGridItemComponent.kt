package com.oborodulin.jwsuite.presentation_territory.ui.reporting.houses

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oborodulin.home.common.ui.components.dialog.alert.DeleteConfirmDialogComponent
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_territory.R
import com.oborodulin.jwsuite.presentation_territory.ui.components.AtWorkCancelProcessConfirmDialogComponent
import com.oborodulin.jwsuite.presentation_territory.ui.components.AtWorkProcessConfirmDialogComponent
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryReportHousesListItem
import timber.log.Timber

private const val TAG = "Reporting.ReportHouseGridItemComponent"

@Composable
fun ReportHouseGridItemComponent(
    reportHouse: TerritoryReportHousesListItem,
    onAddMemberReport: (TerritoryReportHousesListItem) -> Unit = {},
    onEditMemberReport: (TerritoryReportHousesListItem) -> Unit = {},
    onDeleteMemberReport: (TerritoryReportHousesListItem) -> Unit = {},
    onProcessHouse: (TerritoryReportHousesListItem) -> Unit = {},
    onCancelProcessHouse: (TerritoryReportHousesListItem) -> Unit = {},
    onClick: (TerritoryReportHousesListItem) -> Unit = {}
) {
    Timber.tag(TAG).d("ReportHouseGridItemComponent(...) called")
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(2.9f)
                    .padding(horizontal = 2.dp)
                    .clickable { onClick(reportHouse) }
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontSize = 20.sp)) {
                            append(reportHouse.houseFullNum)
                        }
                        reportHouse.territoryShortMark?.let {
                            withStyle(style = SpanStyle(fontSize = 20.sp)) { append(":") }
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            ) { append(it) }
                        }
                        reportHouse.personInfo?.let {
                            withStyle(style = SpanStyle(fontSize = 14.sp)) { append("\n$it") }
                        }
                        reportHouse.languageInfo?.let {
                            withStyle(style = SpanStyle(fontSize = 14.sp)) { append("\n$it") }
                        }
                    },
                    modifier = Modifier
                        .padding(start = 2.dp),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Start
                )
                Divider(Modifier.fillMaxWidth(), thickness = 2.dp)
                Text(
                    modifier = Modifier.padding(2.dp),
                    text = reportHouse.streetFullName,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Start,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.3f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {
                val isShowDeleteAlert = rememberSaveable { mutableStateOf(false) }
                val isShowProcessAlert = rememberSaveable { mutableStateOf(false) }
                val isShowCancelProcessAlert = rememberSaveable { mutableStateOf(false) }
                reportHouse.territoryMemberReportId?.let {
                    Image(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onEditMemberReport(reportHouse) },
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = ""
                    )
                    Spacer(Modifier.height(18.dp))
                    DeleteConfirmDialogComponent(
                        isShow = isShowDeleteAlert,
                        text = stringResource(
                            R.string.dlg_confirm_del_territory_house_report,
                            reportHouse.houseFullNum
                        )
                    ) { onDeleteMemberReport(reportHouse) }
                    Image(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { isShowDeleteAlert.value = true },
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = ""
                    )
                    reportHouse.isProcessed?.let {
                        Spacer(Modifier.height(18.dp))
                        if (reportHouse.isProcessed) {
                            AtWorkCancelProcessConfirmDialogComponent(
                                isShow = isShowCancelProcessAlert,
                                text = stringResource(
                                    R.string.dlg_confirm_cancel_process_territory_house,
                                    reportHouse.houseFullNum
                                )
                            ) { onCancelProcessHouse(reportHouse) }
                            Image(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable { isShowCancelProcessAlert.value = true },
                                imageVector = Icons.Outlined.Clear,
                                contentDescription = ""
                            )
                        } else {
                            AtWorkProcessConfirmDialogComponent(
                                isShow = isShowProcessAlert,
                                text = stringResource(
                                    R.string.dlg_confirm_process_territory_house,
                                    reportHouse.houseFullNum
                                )
                            ) { onProcessHouse(reportHouse) }
                            Image(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable { isShowProcessAlert.value = true },
                                imageVector = Icons.Outlined.Done,
                                contentDescription = ""
                            )
                        }
                    }
                } ?: Image(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { onAddMemberReport(reportHouse) },
                    imageVector = Icons.Outlined.Add,
                    contentDescription = ""
                )
            }
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewReportHouseGridItemComponent() {
    JWSuiteTheme {
        Surface {
            ReportHouseGridItemComponent(
                reportHouse = ReportHousesViewModelImpl.previewList(LocalContext.current)[0],
                onAddMemberReport = {},
                onClick = {}
            )
        }
    }
}