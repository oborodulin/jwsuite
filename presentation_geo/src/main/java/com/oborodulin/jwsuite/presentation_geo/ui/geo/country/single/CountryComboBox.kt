package com.oborodulin.jwsuite.presentation_geo.ui.geo.country.single

import android.content.res.Configuration
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.components.dialog.FullScreenDialog
import com.oborodulin.home.common.ui.components.field.ComboBoxComponent
import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.util.OnImeKeyAction
import com.oborodulin.home.common.util.OnListItemEvent
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_geo.R
import com.oborodulin.jwsuite.presentation_geo.ui.geo.country.list.CountriesListUiAction
import com.oborodulin.jwsuite.presentation_geo.ui.geo.country.list.CountriesListViewModelImpl
import timber.log.Timber

private const val TAG = "Geo.CountryComboBox"

@Composable
fun CountryComboBox(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    listViewModel: CountriesListViewModelImpl = hiltViewModel(),
    singleViewModel: CountryViewModelImpl = hiltViewModel(),
    inputWrapper: InputListItemWrapper<ListItemModel>,
    onValueChange: OnListItemEvent,
    onImeKeyAction: OnImeKeyAction
) {
    Timber.tag(TAG).d("CountryComboBox(...) called")
    var isShowListDialog by rememberSaveable { mutableStateOf(false) }
    val onShowListDialog = { isShowListDialog = true }
    val onDismissListDialog = { isShowListDialog = false }
    val isShowNewSingleDialog by singleViewModel.showDialog.collectAsStateWithLifecycle()
    FullScreenDialog(
        isShow = isShowNewSingleDialog,
        viewModel = singleViewModel,
        loadUiAction = CountryUiAction.Load(),
        confirmUiAction = CountryUiAction.Save,
        dialogView = { _, handleConfirmAction ->
            CountryView(viewModel = singleViewModel, handleSaveAction = handleConfirmAction)
        },
        onValueChange = onValueChange,
        //onShowListDialog = onShowListDialog
    )
    ComboBoxComponent(
        modifier = modifier,
        enabled = enabled,
        listViewModel = listViewModel,
        loadListUiAction = CountriesListUiAction.Load(),
        isShowListDialog = isShowListDialog,
        onShowListDialog = onShowListDialog,
        onDismissListDialog = onDismissListDialog,
        onShowSingleDialog = { singleViewModel.onOpenDialogClicked() },
        labelResId = R.string.country_hint,
        listTitleResId = R.string.dlg_title_select_country,
        leadingPainterResId = R.drawable.ic_country_36,
        inputWrapper = inputWrapper,
        onValueChange = onValueChange,
        onImeKeyAction = onImeKeyAction
    )
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewCountryComboBox() {
    JWSuiteTheme {
        Surface {
/*            CountryComboBox(
                listViewModel = CountriesListViewModelImpl.previewModel(LocalContext.current),
                singleViewModel = CountryViewModelImpl.previewModel(LocalContext.current),
                inputWrapper = InputListItemWrapper(),
                onValueChange = {},
                onImeKeyAction = {}
            )*/
        }
    }
}