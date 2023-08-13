package com.oborodulin.home.common.ui.components.search

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oborodulin.home.common.util.OnTextFieldValueChange
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchComponent(
    fieldValue: TextFieldValue,
    @StringRes placeholderResId: Int? = null,
    modifier: Modifier = Modifier,
    onValueChange: OnTextFieldValueChange
) {
//    val containerColor = FilledTextFieldTokens.ContainerColor.toColor()
    // https://stackoverflow.com/questions/69036917/text-field-text-goes-below-the-ime-in-lazycolum-jetpack-compose/69120348#69120348
    val relocation = remember { BringIntoViewRequester() }
    val scope = rememberCoroutineScope()
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
            .then(modifier),
        textStyle = TextStyle(fontSize = 18.sp),
        placeholder = placeholderResId?.let { { Text(text = stringResource(id = it)) } },
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "",
                modifier = Modifier.size(24.dp)
            )
        },
        trailingIcon = {
            if (fieldValue.text.isNotEmpty()) {
                // Remove text from TextField when you press the 'X' icon
                IconButton(onClick = { onValueChange(TextFieldValue("")) })
                {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        },
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
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewSearchComponent() {
    var textState by remember { mutableStateOf(TextFieldValue("Search query")) }
    SearchComponent(textState) { textState = it }
}