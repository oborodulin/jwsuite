package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorycategory.single

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
import com.oborodulin.home.common.util.OnImeKeyAction
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_territory.R
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryCategoriesListItem
import com.oborodulin.jwsuite.presentation_territory.ui.model.toTerritoryCategoriesListItem
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorycategory.list.TerritoryCategoriesListUiAction
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorycategory.list.TerritoryCategoriesListViewModelImpl
import timber.log.Timber

private const val TAG = "Territoring.TerritoryCategoryComboBox"

@Composable
fun TerritoryCategoryComboBox(
    modifier: Modifier = Modifier,
    listViewModel: TerritoryCategoriesListViewModelImpl = hiltViewModel(),
    singleViewModel: TerritoryCategoryViewModelImpl = hiltViewModel(),
    inputWrapper: InputListItemWrapper<TerritoryCategoriesListItem>,
    onValueChange: (TerritoryCategoriesListItem) -> Unit,
    onImeKeyAction: OnImeKeyAction
) {
    Timber.tag(TAG).d("TerritoryCategoryComboBox(...) called")
    var isShowListDialog by rememberSaveable { mutableStateOf(false) }
    val onShowListDialog = { isShowListDialog = true }
    val onDismissListDialog = { isShowListDialog = false }
    val isShowNewSingleDialog by singleViewModel.showDialog.collectAsStateWithLifecycle()
    FullScreenDialog(
        isShow = isShowNewSingleDialog,
        viewModel = singleViewModel,
        loadUiAction = TerritoryCategoryUiAction.Load(),
        confirmUiAction = TerritoryCategoryUiAction.Save,
        dialogView = { _, handleConfirmAction ->
            TerritoryCategoryView(
                viewModel = singleViewModel,
                handleSaveAction = handleConfirmAction
            )
        },
        onValueChange = { onValueChange(it.toTerritoryCategoriesListItem()) }
        //onShowListDialog = onShowListDialog
    )
    ComboBoxComponent(
        modifier = modifier,
        listViewModel = listViewModel,
        loadListUiAction = TerritoryCategoriesListUiAction.Load,
        isShowListDialog = isShowListDialog,
        onShowListDialog = onShowListDialog,
        onDismissListDialog = onDismissListDialog,
        onShowSingleDialog = { singleViewModel.onOpenDialogClicked() },
        labelResId = R.string.territory_category_hint,
        listTitleResId = R.string.dlg_title_select_territory_category,
        leadingPainterResId = R.drawable.ic_territory_category_36,
        inputWrapper = inputWrapper,
        onValueChange = { onValueChange(it.toTerritoryCategoriesListItem()) },
        onImeKeyAction = onImeKeyAction
    )
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewTerritoryCategoryComboBox() {
    JWSuiteTheme {
        Surface {
            /*TerritoryCategoryComboBox(
                listViewModel = TerritoryCategoriesListViewModelImpl.previewModel(LocalContext.current),
                singleViewModel = TerritoryCategoryViewModelImpl.previewModel(LocalContext.current),
                inputWrapper = InputListItemWrapper(),
                onValueChange = {},
                onImeKeyAction = {}
            )*/
        }
    }
}
