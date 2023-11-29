package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.role.single

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
import com.oborodulin.home.common.ui.components.field.ComboBoxComponent
import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.util.OnImeKeyAction
import com.oborodulin.home.common.util.OnListItemEvent
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_congregation.R
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.role.list.RolesListUiAction
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.role.list.RolesListViewModelImpl
import timber.log.Timber
import java.util.UUID

private const val TAG = "Congregating.RoleComboBox"

@Composable
fun RoleComboBox(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    memberId: UUID,
    listViewModel: RolesListViewModelImpl = hiltViewModel(),
    inputWrapper: InputListItemWrapper<ListItemModel>,
    onValueChange: OnListItemEvent,
    onImeKeyAction: OnImeKeyAction
) {
    Timber.tag(TAG).d("RoleComboBox(...) called: memberId = %s", memberId)
    var isShowListDialog by rememberSaveable { mutableStateOf(false) }
    val onShowListDialog = { isShowListDialog = true }
    val onDismissListDialog = { isShowListDialog = false }
    ComboBoxComponent(
        modifier = modifier,
        enabled = enabled,
        listViewModel = listViewModel,
        loadListUiAction = RolesListUiAction.Load(memberId),
        isShowListDialog = isShowListDialog,
        onShowListDialog = onShowListDialog,
        onDismissListDialog = onDismissListDialog,
        onShowSingleDialog = {},
        labelResId = R.string.member_role_hint,
        listTitleResId = R.string.dlg_title_select_role,
        leadingPainterResId = R.drawable.ic_role_36,
        inputWrapper = inputWrapper,
        onValueChange = onValueChange,
        onImeKeyAction = onImeKeyAction
    )
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewRoleComboBox() {
    JWSuiteTheme {
        Surface {
            /*RoleComboBox(
                localityId = UUID.randomUUID(),
                listViewModel = LocalityDistrictsListViewModelImpl.previewModel(LocalContext.current),
                singleViewModel = LocalityDistrictViewModelImpl.previewModel(LocalContext.current),
                localitysListViewModel = LocalitysListViewModelImpl.previewModel(LocalContext.current),
                localityViewModel = LocalityViewModelImpl.previewModel(LocalContext.current),
                inputWrapper = InputListItemWrapper(),
                onValueChange = {},
                onImeKeyAction = {}
            )*/
        }
    }
}
