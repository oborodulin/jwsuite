package com.oborodulin.home.common.ui.components.datatable

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oborodulin.home.common.ui.theme.HomeComposableTheme
import com.oborodulin.home.common.ui.theme.Typography

// https://stackoverflow.com/questions/68143308/how-do-i-create-a-table-in-jetpack-compose

private fun calcWeights(columns: List<String>, rows: List<List<String>>): List<Float> {
    val weights = MutableList(columns.size) { 0 }
    val fullList = rows.toMutableList()
    fullList.add(columns)
    fullList.forEach { list ->
        list.forEachIndexed { columnIndex, value ->
            weights[columnIndex] = weights[columnIndex].coerceAtLeast(value.length)
        }
    }
    return weights.map { it.toFloat() }
}

@Composable
fun SimpleDataTableComponent(columnHeaders: List<String>, rows: List<List<String>>) {
    val weights = remember { mutableStateOf(calcWeights(columnHeaders, rows)) }
    Column(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
    ) {
        /* HEADER */
        Row(modifier = Modifier.fillMaxWidth()) {
            columnHeaders.forEachIndexed { rowIndex, cell ->
                val weight = weights.value[rowIndex]
                SimpleCell(text = cell, weight = weight, isHeader = true)
            }
        }
        /* ROWS  */
        LazyColumn(modifier = Modifier) {
            itemsIndexed(rows) { rowIndex, row ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    row.forEachIndexed { columnIndex, cell ->
                        val weight = weights.value[columnIndex]
                        SimpleCell(text = cell, weight = weight)
                    }
                }
            }
        }
    }
}

@Composable
private fun SimpleCell(text: String, weight: Float = 1f, isHeader: Boolean = false) {
    val textStyle = MaterialTheme.typography.bodyLarge
    val fontWidth = textStyle.fontSize.value / 1.8f // depends of font used(
    val width = (fontWidth * weight).coerceAtMost(500f)
    val textColor = MaterialTheme.colorScheme.onBackground
    Text(
        text = text,
        maxLines = 1,
        softWrap = false,
        overflow = TextOverflow.Ellipsis,
        color = textColor,
        style = if (isHeader) Typography.titleSmall else Typography.bodyLarge,
        textAlign = if (isHeader) TextAlign.Center else TextAlign.Start,
        modifier = Modifier
            .border(0.dp, textColor.copy(alpha = 0.5f))
            .fillMaxWidth()
            .width(width.dp + 4.dp.times(2))// + Size.margins.times(2))
            .padding(horizontal = 4.dp, vertical = 2.dp)
    )
}

@Preview(name = "Night Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewSimpleDataTableComponent() {
    val rows =
        listOf(listOf("Espresso", "$3"), listOf("Double Espresso", "$4"), listOf("Americano", "$4"))
    HomeComposableTheme {
        Surface {
            Box(modifier = Modifier.fillMaxSize()) {
                SimpleDataTableComponent(columnHeaders = listOf("Name", "Price"), rows = rows)
            }
        }
    }
}