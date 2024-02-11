package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.role.list

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.ListViewModel
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_ACTION
import com.oborodulin.jwsuite.data_congregation.R
import com.oborodulin.jwsuite.domain.types.MemberRoleType
import com.oborodulin.jwsuite.domain.usecases.role.GetRolesUseCase
import com.oborodulin.jwsuite.domain.usecases.role.RoleUseCases
import com.oborodulin.jwsuite.presentation.ui.model.RolesListItem
import com.oborodulin.jwsuite.presentation_congregation.ui.model.converters.RolesListConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

private const val TAG = "Congregating.RolesListViewModelImpl"

@HiltViewModel
class RolesListViewModelImpl @Inject constructor(
    private val useCases: RoleUseCases,
    private val converter: RolesListConverter
) : RolesListViewModel,
    ListViewModel<List<RolesListItem>, UiState<List<RolesListItem>>, RolesListUiAction, UiSingleEvent>() {

    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: RolesListUiAction): Job {
        if (LOG_FLOW_ACTION) Timber.tag(TAG)
            .d("handleAction(RolesListUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is RolesListUiAction.Load -> loadRoles(action.memberId)
        }
        return job
    }

    private fun loadRoles(memberId: UUID? = null): Job {
        Timber.tag(TAG).d("loadRoles(...) called: memberId = %s", memberId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.getRolesUseCase.execute(GetRolesUseCase.Request(memberId))
                .map {
                    converter.convert(it)
                }
                .collect {
                    submitState(it)
                }
        }
        return job
    }

    override fun initFieldStatesByUiModel(uiModel: List<RolesListItem>): Job? = null

    companion object {
        fun previewModel(ctx: Context) =
            object : RolesListViewModel {
                override val uiStateFlow = MutableStateFlow(UiState.Success(previewList(ctx)))
                override val singleEventFlow =
                    Channel<UiSingleEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()
                override val uiStateErrorMsg = MutableStateFlow("")

                override fun redirectedErrorMessage() = null
                override val searchText = MutableStateFlow(TextFieldValue(""))
                override val isSearching = MutableStateFlow(false)
                override fun onSearchTextChange(text: TextFieldValue) {}
                override fun clearSearchText() {}

                override val areSingleSelected = MutableStateFlow(false)
                override fun singleSelectItem(selectedItem: ListItemModel) {}
                override fun singleSelectedItem() = null

                override fun handleActionJob(
                    action: () -> Unit,
                    afterAction: (CoroutineScope) -> Unit
                ) {
                }

                override fun submitAction(action: RolesListUiAction): Job? = null
            }

        fun previewList(ctx: Context) = listOf(
            RolesListItem(
                id = UUID.randomUUID(),
                roleType = MemberRoleType.ADMIN,
                roleName = ctx.resources.getString(R.string.def_role_name_admin)
            ),
            RolesListItem(
                id = UUID.randomUUID(),
                roleType = MemberRoleType.USER,
                roleName = ctx.resources.getString(R.string.def_role_name_user)
            ),
            RolesListItem(
                id = UUID.randomUUID(),
                roleType = MemberRoleType.TERRITORIES,
                roleName = ctx.resources.getString(R.string.def_role_name_territories)
            ),
            RolesListItem(
                id = UUID.randomUUID(),
                roleType = MemberRoleType.BILLS,
                roleName = ctx.resources.getString(R.string.def_role_name_bills)
            ),
            RolesListItem(
                id = UUID.randomUUID(),
                roleType = MemberRoleType.REPORTS,
                roleName = ctx.resources.getString(R.string.def_role_name_reports)
            )
        )
    }
}