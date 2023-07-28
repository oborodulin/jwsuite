package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territorycategory.single

import android.content.res.Configuration
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.components.dialog.FullScreenDialog
import com.oborodulin.home.common.ui.components.field.ComboBoxComponent
import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.util.OnImeKeyAction
import com.oborodulin.home.common.util.OnListItemEvent
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.territorycategory.list.TerritoryCategoriesListUiAction
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.territorycategory.list.TerritoryCategoriesListViewModel
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.territorycategory.list.TerritoryCategoriesListViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import timber.log.Timber

private const val TAG = "Territoring.TerritoryCategoryComboBox"

@Composable
fun TerritoryCategoryComboBox(
    modifier: Modifier = Modifier,
    listViewModel: TerritoryCategoriesListViewModel,
    singleViewModel: TerritoryCategoryViewModel,
    inputWrapper: InputListItemWrapper<ListItemModel>,
    onValueChange: OnListItemEvent,
    onImeKeyAction: OnImeKeyAction
) {
    Timber.tag(TAG).d("TerritoryCategoryComboBox(...) called")
    var isShowListDialog by remember { mutableStateOf(false) }
    val onShowListDialog = { isShowListDialog = true }
    val onDismissListDialog = { isShowListDialog = false }
    val isShowNewSingleDialog by singleViewModel.showDialog.collectAsStateWithLifecycle()
    FullScreenDialog(
        isShow = isShowNewSingleDialog,
        viewModel = singleViewModel,
        loadUiAction = TerritoryCategoryUiAction.Load(),
        confirmUiAction = TerritoryCategoryUiAction.Save,
        dialogView = { TerritoryCategoryView(singleViewModel) },
        onValueChange = onValueChange,
        //onShowListDialog = onShowListDialog
    )
    ComboBoxComponent(
        modifier = modifier,
        listViewModel = listViewModel,
        loadListUiAction = TerritoryCategoriesListUiAction.Load,
        searchedItem = "",
        isShowListDialog = isShowListDialog,
        onShowListDialog = onShowListDialog,
        onDismissListDialog = onDismissListDialog,
        onShowSingleDialog = { singleViewModel.onOpenDialogClicked() },
        labelResId = R.string.territory_category_hint,
        listTitleResId = R.string.dlg_title_select_territory_category,
        leadingIcon = { Icon(painterResource(R.drawable.ic_location_pin_36), null) },
        inputWrapper = inputWrapper,
        onValueChange = onValueChange,
        onImeKeyAction = onImeKeyAction
    )
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewTerritoryCategoryComboBox() {
    JWSuiteTheme {
        Surface {
            TerritoryCategoryComboBox(
                listViewModel = TerritoryCategoriesListViewModelImpl.previewModel(LocalContext.current),
                singleViewModel = TerritoryCategoryViewModelImpl.previewModel(LocalContext.current),
                inputWrapper = InputListItemWrapper(),
                onValueChange = {},
                onImeKeyAction = {}
            )
        }
    }
}
