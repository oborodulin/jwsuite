package com.oborodulin.jwsuite.presentation.navigation

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_CONGREGATING
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_CONGREGATION
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_DASHBOARDING
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_GROUP
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_HAND_OUT_TERRITORIES_CONFIRMATION
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_HOME
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_LOCALITY
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_MEMBER
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_MINISTRING
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_REGION
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_REGION_DISTRICT
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_TERRITORING
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_TERRITORY
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_TERRITORY_CATEGORY
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.CongregationInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.GroupInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.LocalityInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.MemberInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.RegionDistrictInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.RegionInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryCategoryInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryInput
import timber.log.Timber
import java.util.UUID

// ic_street_pin - https://www.flaticon.com/authors/srip
// ic_district - https://www.flaticon.com/authors/orvipixel
// ic_map_pins - https://www.flaticon.com/authors/itim2101
// ic_territory_map - https://www.freepik.com/
// ic_meeting - https://www.freepik.com/
// ic_room - https://www.flaticon.com/authors/andrejs-kirma
// ic_floor - https://www.flaticon.com/authors/icon-hubs
// ic_floors - https://www.flaticon.com/authors/bsd

private const val TAG = "Presentation.NavRoutes"

// Geo:
private const val ARG_REGION_ID = "regionId"
private const val ARG_REGION_DISTRICT_ID = "regionDistrictId"
private const val ARG_LOCALITY_ID = "localityId"

// Congregation:
private const val ARG_CONGREGATION_ID = "congregationId"
private const val ARG_GROUP_ID = "groupId"
private const val ARG_MEMBER_ID = "memberId"

// Territory:
private const val ARG_TERRITORY_CATEGORY_ID = "territoryCategoryId"
private const val ARG_TERRITORY_ID = "territoryId"

/**
 * Created by oborodulin on 12.December.2021
 */
sealed class NavRoutes constructor(
    val route: String,
    @DrawableRes open val iconResId: Int,
    @StringRes open val titleResId: Int,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    object Home : NavRoutes(ROUTE_HOME, R.drawable.ic_dashboard_24, R.string.nav_item_dashboarding)

    object Dashboarding :
        NavRoutes(ROUTE_DASHBOARDING, R.drawable.ic_dashboard_24, R.string.nav_item_dashboarding)

    object Congregating :
        NavRoutes(ROUTE_CONGREGATING, R.drawable.ic_congregation_24, R.string.nav_item_congregating)

    object Territoring :
        NavRoutes(ROUTE_TERRITORING, R.drawable.ic_territory_map_24, R.string.nav_item_territoring)

    object Ministring :
        NavRoutes(ROUTE_MINISTRING, R.drawable.ic_maps_home_work_24, R.string.nav_item_ministring)

    // Geo:
    object Region : NavRoutes(
        String.format(ROUTE_REGION, "{$ARG_REGION_ID}"),
        R.drawable.ic_region_24,
        R.string.nav_item_region,
        arguments = listOf(navArgument(ARG_REGION_ID) {
            type = NavType.StringType
            nullable = true
            //defaultValue = null
        })
    ) {
        fun routeForRegion(regionInput: RegionInput? = null): String {
            val route = when (regionInput) {
                null -> baseRoute()
                else -> String.format(ROUTE_REGION, regionInput.regionId)
            }
            //val route = String.format(ROUTE_PAYER, payerId)
            Timber.tag(TAG).d("Region - routeForRegion(...): '%s'", route)
            return route
        }

        fun fromEntry(entry: NavBackStackEntry): RegionInput {
            val regionInput =
                RegionInput(UUID.fromString(entry.arguments?.getString(ARG_REGION_ID).orEmpty()))
            Timber.tag(TAG).d("Region - fromEntry(...): '%s'", regionInput)
            return regionInput
        }
    }

    object RegionDistrict : NavRoutes(
        String.format(ROUTE_REGION_DISTRICT, "{$ARG_REGION_DISTRICT_ID}"),
        R.drawable.ic_district_24,
        R.string.nav_item_region_district,
        arguments = listOf(navArgument(ARG_REGION_DISTRICT_ID) {
            type = NavType.StringType
            nullable = true
            //defaultValue = null
        })
    ) {
        fun routeForRegionDistrict(regionDistrictInput: RegionDistrictInput? = null): String {
            val route = when (regionDistrictInput) {
                null -> baseRoute()
                else -> String.format(ROUTE_REGION_DISTRICT, regionDistrictInput.regionDistrictId)
            }
            //val route = String.format(ROUTE_PAYER, payerId)
            Timber.tag(TAG).d("RegionDistrict - routeForRegionDistrict(...): '%s'", route)
            return route
        }

        fun fromEntry(entry: NavBackStackEntry): RegionDistrictInput {
            val regionDistrictInput = RegionDistrictInput(
                UUID.fromString(entry.arguments?.getString(ARG_REGION_DISTRICT_ID).orEmpty())
            )
            Timber.tag(TAG).d("RegionDistrict - fromEntry(...): '%s'", regionDistrictInput)
            return regionDistrictInput
        }
    }

    object Locality : NavRoutes(
        String.format(ROUTE_LOCALITY, "{$ARG_LOCALITY_ID}"),
        R.drawable.ic_location_city_24,
        R.string.nav_item_locality,
        arguments = listOf(navArgument(ARG_LOCALITY_ID) {
            type = NavType.StringType
            nullable = true
            //defaultValue = null
        })
    ) {
        fun routeForLocality(localityInput: LocalityInput? = null): String {
            val route = when (localityInput) {
                null -> baseRoute()
                else -> String.format(ROUTE_LOCALITY, localityInput.localityId)
            }
            //val route = String.format(ROUTE_PAYER, payerId)
            Timber.tag(TAG).d("Locality - routeForLocality(...): '%s'", route)
            return route
        }

        fun fromEntry(entry: NavBackStackEntry): LocalityInput {
            val localityInput = LocalityInput(
                UUID.fromString(entry.arguments?.getString(ARG_LOCALITY_ID).orEmpty())
            )
            Timber.tag(TAG).d("Locality - fromEntry(...): '%s'", localityInput)
            return localityInput
        }
    }

    // Congregation:
    object Congregation : NavRoutes(
        String.format(ROUTE_CONGREGATION, "{$ARG_CONGREGATION_ID}"),
        R.drawable.ic_congregation_24,
        R.string.nav_item_congregation,
        arguments = listOf(navArgument(ARG_CONGREGATION_ID) {
            type = NavType.StringType
            nullable = true
            //defaultValue = null
        })
    ) {
        fun routeForCongregation(congregationInput: CongregationInput? = null): String {
            val route = when (congregationInput) {
                null -> baseRoute()
                else -> String.format(ROUTE_CONGREGATION, congregationInput.congregationId)
            }
            //val route = String.format(ROUTE_PAYER, payerId)
            Timber.tag(TAG).d("Congregation - routeForCongregation(...): '%s'", route)
            return route
        }

        fun fromEntry(entry: NavBackStackEntry): CongregationInput {
            val congregationInput = CongregationInput(
                UUID.fromString(entry.arguments?.getString(ARG_CONGREGATION_ID).orEmpty())
            )
            Timber.tag(TAG).d("Congregation - fromEntry(...): '%s'", congregationInput)
            return congregationInput
        }
    }

    object Group : NavRoutes(
        String.format(ROUTE_GROUP, "{$ARG_GROUP_ID}"),
        R.drawable.ic_group_24,
        R.string.nav_item_group,
        arguments = listOf(navArgument(ARG_GROUP_ID) {
            type = NavType.StringType
            nullable = true
            //defaultValue = null
        })
    ) {
        fun routeForGroup(groupInput: GroupInput? = null): String {
            val route = when (groupInput) {
                null -> baseRoute()
                else -> String.format(ROUTE_GROUP, groupInput.groupId)
            }
            //val route = String.format(ROUTE_SERVICE, payerId)
            Timber.tag(TAG).d("Group - routeForGroup(...): '%s'", route)
            return route
        }

        fun fromEntry(entry: NavBackStackEntry): GroupInput {
            val groupInput =
                GroupInput(UUID.fromString(entry.arguments?.getString(ARG_GROUP_ID).orEmpty()))
            Timber.tag(TAG).d("Group - fromEntry(...): '%s'", groupInput)
            return groupInput
        }
    }

    object Member : NavRoutes(
        String.format(ROUTE_MEMBER, "{$ARG_MEMBER_ID}"),
        R.drawable.ic_person_24,
        R.string.nav_item_member,
        arguments = listOf(navArgument(ARG_MEMBER_ID) {
            type = NavType.StringType
            nullable = true
            //defaultValue = null
        })
    ) {
        fun routeForMember(memberInput: MemberInput? = null): String {
            val route = when (memberInput) {
                null -> baseRoute()
                else -> String.format(ROUTE_MEMBER, memberInput.memberId)
            }
            //val route = String.format(ROUTE_PAYER_SERVICE, payerId)
            Timber.tag(TAG).d("Member - routeForMember(...): '%s'", route)
            return route
        }

        fun fromEntry(entry: NavBackStackEntry): MemberInput {
            val memberInput = MemberInput(
                UUID.fromString(entry.arguments?.getString(ARG_MEMBER_ID).orEmpty())
            )
            Timber.tag(TAG).d("Member - fromEntry(...): '%s'", memberInput)
            return memberInput
        }
    }

    object TerritoryCategory : NavRoutes(
        String.format(ROUTE_TERRITORY_CATEGORY, "{$ARG_TERRITORY_CATEGORY_ID}"),
        R.drawable.ic_location_pin_24,
        R.string.nav_item_territory_category,
        arguments = listOf(navArgument(ARG_TERRITORY_CATEGORY_ID) {
            type = NavType.StringType
            nullable = true
            //defaultValue = null
        })
    ) {
        fun routeForTerritoryCategory(territoryCategoryInput: TerritoryCategoryInput? = null): String {
            val route = when (territoryCategoryInput) {
                null -> baseRoute()
                else -> String.format(
                    ROUTE_TERRITORY_CATEGORY,
                    territoryCategoryInput.territoryCategoryId
                )
            }
            //val route = String.format(ROUTE_RATE, payerId)
            Timber.tag(TAG).d("TerritoryCategory - routeForTerritoryCategory(...): '%s'", route)
            return route
        }

        fun fromEntry(entry: NavBackStackEntry): TerritoryCategoryInput {
            val territoryInput = TerritoryCategoryInput(
                UUID.fromString(entry.arguments?.getString(ARG_TERRITORY_CATEGORY_ID).orEmpty())
            )
            Timber.tag(TAG).d("TerritoryCategory - fromEntry(...): '%s'", territoryInput)
            return territoryInput
        }
    }

    object Territory : NavRoutes(
        String.format(ROUTE_TERRITORY, "{$ARG_TERRITORY_ID}"),
        R.drawable.ic_map_marker_24,
        R.string.nav_item_territory,
        arguments = listOf(navArgument(ARG_TERRITORY_ID) {
            type = NavType.StringType
            nullable = true
            //defaultValue = null
        })
    ) {
        fun routeForTerritory(territoryInput: TerritoryInput? = null): String {
            val route = when (territoryInput) {
                null -> baseRoute()
                else -> String.format(ROUTE_TERRITORY, territoryInput.territoryId)
            }
            //val route = String.format(ROUTE_RATE, payerId)
            Timber.tag(TAG).d("Territory - routeForTerritory(...): '%s'", route)
            return route
        }

        fun fromEntry(entry: NavBackStackEntry): TerritoryInput {
            val territoryInput = TerritoryInput(
                UUID.fromString(entry.arguments?.getString(ARG_TERRITORY_ID).orEmpty())
            )
            Timber.tag(TAG).d("Territory - fromEntry(...): '%s'", territoryInput)
            return territoryInput
        }
    }

    object HandOutTerritoriesConfirmation : NavRoutes(
        ROUTE_HAND_OUT_TERRITORIES_CONFIRMATION,
        R.drawable.ic_hand_map_24,
        R.string.nav_item_territory_hand_out_confirmation
    ) {
        fun routeForHandOutTerritoriesConfirmation(): String {
            Timber.tag(TAG).d(
                "HandOutTerritoriesConfirmation - routeForHandOutTerritoriesConfirmation(...): '%s'",
                this.route
            )
            return this.route
        }
    }

    fun baseRoute() = this.route.substringBefore('/')

    companion object {
        fun bottomNavBarRoutes() = listOf(Dashboarding, Congregating, Territoring, Ministring)

        fun isShowBackButton(route: String?) = when (route) {
            Congregation.route -> true
            else -> false
        }

        fun titleByRoute(context: Context, route: String): String {
            return when (route) {
                Dashboarding.route -> context.getString(Dashboarding.titleResId)
                Congregating.route -> context.getString(Congregating.titleResId)
                Territoring.route -> context.getString(Territoring.titleResId)
                Ministring.route -> context.getString(Ministring.titleResId)
                Congregation.route -> context.getString(Congregation.titleResId)
                else -> ""
            }
        }
    }
}
