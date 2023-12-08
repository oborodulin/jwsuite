package com.oborodulin.home.common.ui.components.search

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oborodulin.home.common.R
import com.oborodulin.home.common.util.OnTextFieldValueChange
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchComponent(
    fieldValue: TextFieldValue,
    @StringRes placeholderResId: Int? = null,
    modifier: Modifier = Modifier,
    isFocused: Boolean = false,
    onValueChange: OnTextFieldValueChange
) {
//    val containerColor = FilledTextFieldTokens.ContainerColor.toColor()
    // https://stackoverflow.com/questions/69036917/text-field-text-goes-below-the-ime-in-lazycolum-jetpack-compose/69120348#69120348
    val relocation = remember { BringIntoViewRequester() }
    val scope = rememberCoroutineScope()
    // https://stackoverflow.com/questions/64181930/request-focus-on-textfield-in-jetpack-compose
    // https://stackoverflow.com/questions/72833025/jetpack-compose-textfield-doesnt-lose-focus-on-click-outside-of-it
    // initialize focus reference to be able to request focus programmatically
    val focusRequester = FocusRequester()
    // https://medium.com/@kamal.lakhani56/search-bar-animation-jetpack-compose-c865e4605c6a
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val keyboardActions =
        KeyboardActions(onSearch = { focusManager.clearFocus();keyboardController?.hide() })
    TextField(
        value = fieldValue,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(32.dp)
            .padding(vertical = 4.dp)
            .bringIntoViewRequester(relocation)
            .onFocusEvent {
                if (it.isFocused) scope.launch { delay(300); relocation.bringIntoView() }
            }
            .focusRequester(focusRequester)
            .then(modifier),
        textStyle = TextStyle(fontSize = 18.sp),
        placeholder = {
            Row {
                Icon(
                    Icons.Default.Search,
                    contentDescription = stringResource(R.string.search_cnt_desc)
                )
                placeholderResId?.let {
                    Text(
                        text = stringResource(it),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        //color = MaterialTheme.colorScheme.background,
                        //style = MaterialTheme.typography.titleLarge //h6
                    )
                }
            }
        },
        /*leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "",
                modifier = Modifier.size(24.dp)
            )
        },*/
        trailingIcon = {
            if (fieldValue.text.isNotEmpty()) {
                // Remove text from TextField when you press the 'X' icon
                IconButton(onClick = { onValueChange(TextFieldValue("")) })
                {
                    Icon(
                        Icons.Outlined.Clear,
                        contentDescription = stringResource(R.string.search_clear_cnt_desc),
                        //tint = Color.White
                        //modifier = Modifier.size(24.dp)
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions = keyboardActions,
        singleLine = true,
        shape = RectangleShape, // The TextFiled has rounded corners top left and right by default
        /*
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    focusedContainerColor = containerColor,
                    unfocusedContainerColor = containerColor,
                    disabledContainerColor = containerColor,
                    cursorColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    focusedLeadingIconColor = Color.White,
                    focusedTrailingIconColor = Color.White,
                    //focusedBackgroundColor = colorResource(id = R.color.colorPrimary),
                )
         */
    )
    // https://stackoverflow.com/questions/64181930/request-focus-on-textfield-in-jetpack-compose
    LaunchedEffect(Unit) {
        if (isFocused) focusRequester.requestFocus()
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewSearchComponent() {
    var textState by remember { mutableStateOf(TextFieldValue("Search query")) }
    SearchComponent(textState) { textState = it }
}