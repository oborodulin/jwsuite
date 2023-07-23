package com.oborodulin.jwsuite.presentation.ui.modules.congregating.member.single

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.oborodulin.home.common.ui.components.field.TextFieldComponent
import com.oborodulin.home.common.ui.components.field.util.InputFocusRequester
import com.oborodulin.home.common.ui.components.field.util.inputProcess
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.ui.modules.FavoriteCongregationViewModel
import com.oborodulin.jwsuite.presentation.ui.modules.FavoriteCongregationViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.congregation.list.CongregationsListViewModel
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.congregation.list.CongregationsListViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.congregation.single.CongregationComboBox
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.congregation.single.CongregationViewModel
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.congregation.single.CongregationViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.group.list.GroupsListViewModel
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.group.single.GroupViewModel
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.CongregationsListItem
import com.oborodulin.jwsuite.presentation.ui.modules.geo.locality.list.LocalitiesListViewModel
import com.oborodulin.jwsuite.presentation.ui.modules.geo.locality.list.LocalitiesListViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.geo.locality.single.LocalityViewModel
import com.oborodulin.jwsuite.presentation.ui.modules.geo.locality.single.LocalityViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.geo.region.list.RegionsListViewModel
import com.oborodulin.jwsuite.presentation.ui.modules.geo.region.list.RegionsListViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.geo.region.single.RegionViewModel
import com.oborodulin.jwsuite.presentation.ui.modules.geo.region.single.RegionViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.list.RegionDistrictsListViewModel
import com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.list.RegionDistrictsListViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.single.RegionDistrictInputEvent
import com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.single.RegionDistrictViewModel
import com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.single.RegionDistrictViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import timber.log.Timber

private const val TAG = "Congregating.MemberView"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MemberView(
    sharedViewModel: FavoriteCongregationViewModel<CongregationsListItem>,
    memberViewModel: MemberViewModel,
    congregationsListViewModel: CongregationsListViewModel,
    congregationViewModel: CongregationViewModel,
    groupsListViewModel: GroupsListViewModel,
    groupViewModel: GroupViewModel,
    localitiesListViewModel: LocalitiesListViewModel,
    localityViewModel: LocalityViewModel,
    regionsListViewModel: RegionsListViewModel,
    regionViewModel: RegionViewModel,
    regionDistrictsListViewModel: RegionDistrictsListViewModel,
    regionDistrictViewModel: RegionDistrictViewModel
) {
    Timber.tag(TAG).d("MemberView(...) called")
    val currentCongregation by sharedViewModel.sharedFlow.collectAsStateWithLifecycle(null)
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(memberViewModel.events, lifecycleOwner) {
        memberViewModel.events.flowWithLifecycle(
            lifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
    }

    Timber.tag(TAG).d("CollectAsStateWithLifecycle for all region fields")
    val congregation by memberViewModel.congregation.collectAsStateWithLifecycle()
    val group by memberViewModel.group.collectAsStateWithLifecycle()
    val memberNum by memberViewModel.memberNum.collectAsStateWithLifecycle()
    val memberName by memberViewModel.memberName.collectAsStateWithLifecycle()
    val surname by memberViewModel.surname.collectAsStateWithLifecycle()
    val patronymic by memberViewModel.patronymic.collectAsStateWithLifecycle()
    val phoneNumber by memberViewModel.phoneNumber.collectAsStateWithLifecycle()
    val memberType by memberViewModel.memberType.collectAsStateWithLifecycle()
    val dateOfBirth by memberViewModel.dateOfBirth.collectAsStateWithLifecycle()
    val dateOfBaptism by memberViewModel.dateOfBaptism.collectAsStateWithLifecycle()
    val inactiveDate by memberViewModel.inactiveDate.collectAsStateWithLifecycle()

    Timber.tag(TAG).d("Init Focus Requesters for all region fields")
    val focusRequesters: MutableMap<String, InputFocusRequester> = HashMap()
    enumValues<MemberFields>().forEach {
        focusRequesters[it.name] = InputFocusRequester(it, remember { FocusRequester() })
    }

    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("MemberView(...): LaunchedEffect()")
        events.collect { event ->
            Timber.tag(TAG).d("Collect input events flow: %s", event.javaClass.name)
            inputProcess(context, focusManager, keyboardController, event, focusRequesters)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .height(IntrinsicSize.Min)
            .clip(RoundedCornerShape(16.dp))
            .border(
                2.dp,
                MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(16.dp)
            )
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        currentCongregation?.let {
            memberViewModel.onTextFieldEntered(
                MemberInputEvent.Congregation(
                    it
                )
            )
        }
        CongregationComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[MemberFields.MEMBER_CONGREGATION.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    memberViewModel.onTextFieldFocusChanged(
                        focusedField = MemberFields.MEMBER_CONGREGATION,
                        isFocused = focusState.isFocused
                    )
                },
            enabled = false,
            listViewModel = congregationsListViewModel,
            singleViewModel = congregationViewModel,
            localitiesListViewModel = localitiesListViewModel,
            localityViewModel = localityViewModel,
            regionsListViewModel = regionsListViewModel,
            regionViewModel = regionViewModel,
            regionDistrictsListViewModel = regionDistrictsListViewModel,
            regionDistrictViewModel = regionDistrictViewModel,
            inputWrapper = congregation,
            onImeKeyAction = memberViewModel::moveFocusImeAction
        )
        GroupComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[MemberFields.MEMBER_GROUP.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    memberViewModel.onTextFieldFocusChanged(
                        focusedField = MemberFields.MEMBER_GROUP,
                        isFocused = focusState.isFocused
                    )
                },
            listViewModel = groupsListViewModel,
            singleViewModel = groupViewModel,
            inputWrapper = group,
            onValueChange = {
                memberViewModel.onTextFieldEntered(RegionDistrictInputEvent.Region(it))
            },
            onImeKeyAction = memberViewModel::moveFocusImeAction
        )
        TextFieldComponent(
            modifier = Modifier
                .focusRequester(focusRequesters[MemberFields.MEMBER_NUM.name]!!.focusRequester)
                .onFocusChanged { focusState ->
                    memberViewModel.onTextFieldFocusChanged(
                        focusedField = MemberFields.MEMBER_NUM,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.member_num_hint,
            leadingIcon = {
                Icon(
                    painterResource(com.oborodulin.home.common.R.drawable.ic_123_36),
                    null
                )
            },
            keyboardOptions = remember {
                KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            },
            inputWrapper = memberNum,
            onValueChange = { memberViewModel.onTextFieldEntered(MemberInputEvent.MemberNum(it)) },
            onImeKeyAction = memberViewModel::moveFocusImeAction
        )
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewGroupView() {
    val ctx = LocalContext.current
    JWSuiteTheme {
        Surface {
            MemberView(
                sharedViewModel = FavoriteCongregationViewModelImpl.previewModel,
                memberViewModel = MemberViewModelImpl.previewModel(ctx),
                congregationsListViewModel = CongregationsListViewModelImpl.previewModel(ctx),
                congregationViewModel = CongregationViewModelImpl.previewModel(ctx),
                localitiesListViewModel = LocalitiesListViewModelImpl.previewModel(ctx),
                localityViewModel = LocalityViewModelImpl.previewModel(ctx),
                regionsListViewModel = RegionsListViewModelImpl.previewModel(ctx),
                regionViewModel = RegionViewModelImpl.previewModel(ctx),
                regionDistrictsListViewModel = RegionDistrictsListViewModelImpl.previewModel(ctx),
                regionDistrictViewModel = RegionDistrictViewModelImpl.previewModel(ctx)
            )
        }
    }
}
