package com.oborodulin.home.common.ui.components.search

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.state.MviViewModeled
import com.oborodulin.home.common.ui.state.UiAction
import com.oborodulin.home.common.ui.state.UiSingleEvent

@Composable
fun <T : Any, A : UiAction, E : UiSingleEvent> SearchViewModelComponent(
    viewModel: MviViewModeled<T, A, E>,
    @StringRes placeholderResId: Int? = null,
    isFocused: Boolean = true,
    modifier: Modifier = Modifier
) {
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()
    SearchComponent(
        fieldValue = searchText,
        placeholderResId = placeholderResId,
        modifier = modifier,
        isFocused = isFocused,
        onValueChange = viewModel::onSearchTextChange
    )
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewSearchViewModelComponent() {
    /*var textState by remember { mutableStateOf(TextFieldValue("Search query")) }
    SearchViewModelComponent(textState) { textState = it }*/
}