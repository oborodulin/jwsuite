package com.oborodulin.home.common.ui.components.search

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.state.MviViewModeled
import com.oborodulin.home.common.ui.state.UiAction
import com.oborodulin.home.common.ui.state.UiSingleEvent

// https://www.youtube.com/watch?v=90gokceSYdM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : Any, A : UiAction, E : UiSingleEvent> SearchBarComponent(
    viewModel: MviViewModeled<T, A, E>,
    @StringRes placeholderResId: Int? = null,
    modifier: Modifier = Modifier
) {
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()
    var searchActive by rememberSaveable { mutableStateOf(false) }
    var searchItems = remember { mutableStateListOf("") }
    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            //.padding(4.dp)
            .then(modifier),
        query = searchText.text,
        onQueryChange = { viewModel.onSearchTextChange(TextFieldValue(it, TextRange(it.length))) },
        onSearch = { searchItems.add(searchText.text); searchActive = false },
        active = searchActive,
        onActiveChange = { searchActive = it },
        placeholder = placeholderResId?.let { { Text(stringResource(it)) } },
        leadingIcon = {
            Icon(
                Icons.Outlined.Search,
                contentDescription = "Search Icon"
            )
        },
        trailingIcon = {
            if (searchActive) {
                Icon(
                    modifier = Modifier.clickable {
                        if (searchText.text.isNotEmpty()) {
                            viewModel.onSearchTextChange(TextFieldValue("", TextRange(0)))
                        } else {
                            searchActive = false
                        }
                    },
                    imageVector = Icons.Outlined.Close,
                    contentDescription = "Close Icon"
                )
            }
        }) {}
    /*searchItems.forEach {
        Row(modifier = Modifier.padding(all = 14.dp)) {
            Icon(
                modifier = Modifier.padding(end = 10.dp),
                imageVector = Icons.Outlined.History,
                contentDescription = "History Icon"
            )
            Text(it)
        }
    }
}*/
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewSearchBarComponent() {
    /*var textState by remember { mutableStateOf(TextFieldValue("Search query")) }
    SearchBarComponent(textState) { textState = it }*/
}