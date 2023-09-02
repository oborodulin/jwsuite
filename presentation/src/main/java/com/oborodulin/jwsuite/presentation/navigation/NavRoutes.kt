package com.oborodulin.jwsuite.presentation.navigation

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_CONGREGATING
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_CONGREGATION
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_DASHBOARDING
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_ENTRANCE
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_FLOOR
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_GROUP
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_HAND_OUT_TERRITORIES_CONFIRMATION
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_HOME
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_HOUSE
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_LOCALITY
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_LOCALITY_DISTRICT
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_MEMBER
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_MICRODISTRICT
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_MINISTRING
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_REGION
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_REGION_DISTRICT
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_ROOM
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_STREET
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_TERRITORING
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_TERRITORY
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_TERRITORY_CATEGORY
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_TERRITORY_DETAILS
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_TERRITORY_STREET
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.CongregationInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.EntranceInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.FloorInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.GroupInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.HouseInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.LocalityDistrictInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.LocalityInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.MemberInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.MicrodistrictInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.RegionDistrictInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.RegionInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.RoomInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.StreetInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryCategoryInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryStreetInput
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
// ic_hand_map.png - https://www.flaticon.com/free-icon/hand_6242467?term=hand+map&page=1&position=9&origin=search&related_id=6242467
// ic_open_door.png - https://www.flaticon.com/authors/dinosoftlabs
// ic_room.png - https://www.flaticon.com/authors/dinosoftlabs
// ic_street.png - https://www.freepik.com/
// ic_street_sign.png - https://www.flaticon.com/authors/xnimrodx
// ic_locality_district.png - https://www.flaticon.com/authors/andrejs-kirma
// ic_cityscape.png - https://www.flaticon.com/authors/andrejs-kirma
// ic_microdistrict.png - https://www.flaticon.com/authors/andrejs-kirma
// ic_map_marker.png - https://www.flaticon.com/authors/dreamstale
// ic_territory_street.png - https://www.freepik.com/
// ic_private_sector.png - https://www.flaticon.com/authors/noomtah
// ic_street_side.png - https://www.flaticon.com/authors/freepik
// ic_territory_category.png - https://www.flaticon.com/authors/anggara
// ic_territory_details.png - https://www.flaticon.com/authors/anggara

private const val TAG = "Presentation.NavRoutes"

// Geo:
private const val ARG_REGION_ID = "regionId"
private const val ARG_REGION_DISTRICT_ID = "regionDistrictId"
private const val ARG_LOCALITY_ID = "localityId"
private const val ARG_LOCALITY_DISTRICT_ID = "localityDistrictId"
private const val ARG_MICRODISTRICT_ID = "microdistrictId"
private const val ARG_STREET_ID = "streetId"

// Congregation:
private const val ARG_CONGREGATION_ID = "congregationId"
private const val ARG_GROUP_ID = "groupId"
private const val ARG_MEMBER_ID = "memberId"

// Territory:
private const val ARG_TERRITORY_CATEGORY_ID = "territoryCategoryId"
private const val ARG_TERRITORY_ID = "territoryId"
private const val ARG_TERRITORY_STREET_ID = "territoryStreetId"
private const val ARG_HOUSE_ID = "houseId"
private const val ARG_ENTRANCE_ID = "entranceId"
private const val ARG_FLOOR_ID = "floorId"
private const val ARG_ROOM_ID = "roomId"

/**
 * Created by oborodulin on 12.December.2021
 */
sealed class NavRoutes constructor(
    val route: String,
    val iconImageVector: ImageVector? = null,
    @DrawableRes open val iconPainterResId: Int? = null,
    @StringRes open val titleResId: Int,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    data object Home : NavRoutes(
        route = ROUTE_HOME,
        iconPainterResId = R.drawable.ic_dashboard_24,
        titleResId = R.string.nav_item_dashboarding
    )

    data object Dashboarding : NavRoutes(
        route = ROUTE_DASHBOARDING,
        iconPainterResId = R.drawable.ic_dashboard_24,
        titleResId = R.string.nav_item_dashboarding
    )

    data object Congregating : NavRoutes(
        route = ROUTE_CONGREGATING,
        iconPainterResId = R.drawable.ic_congregation_24,
        titleResId = R.string.nav_item_congregating
    )

    data object Territoring : NavRoutes(
        route = ROUTE_TERRITORING,
        iconPainterResId = R.drawable.ic_territory_map_24,
        titleResId = R.string.nav_item_territoring
    )

    data object Ministring : NavRoutes(
        route = ROUTE_MINISTRING,
        iconPainterResId = R.drawable.ic_maps_home_work_24,
        titleResId = R.string.nav_item_ministring
    )

    // Geo:
    data object Region : NavRoutes(
        route = String.format(ROUTE_REGION, "{$ARG_REGION_ID}"),
        iconPainterResId = R.drawable.ic_region_24,
        titleResId = R.string.nav_item_region,
        arguments = listOf(navArgument(ARG_REGION_ID) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
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

        fun fromEntry(entry: NavBackStackEntry): RegionInput? {
            val regionInput =
                entry.arguments?.getString(ARG_REGION_ID)?.let { RegionInput(UUID.fromString(it)) }
            Timber.tag(TAG).d("Region - fromEntry(...): '%s'", regionInput)
            return regionInput
        }
    }

    data object RegionDistrict : NavRoutes(
        route =
        String.format(ROUTE_REGION_DISTRICT, "{$ARG_REGION_DISTRICT_ID}"),
        iconPainterResId = R.drawable.ic_district_24,
        titleResId = R.string.nav_item_region_district,
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

    data object Locality : NavRoutes(
        route = String.format(ROUTE_LOCALITY, "{$ARG_LOCALITY_ID}"),
        iconPainterResId = R.drawable.ic_location_city_24,
        titleResId = R.string.nav_item_locality,
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

    data object LocalityDistrict : NavRoutes(
        route = String.format(ROUTE_LOCALITY_DISTRICT, "{$ARG_LOCALITY_DISTRICT_ID}"),
        iconPainterResId = R.drawable.ic_locality_district_24,
        titleResId = R.string.nav_item_locality_district,
        arguments = listOf(navArgument(ARG_LOCALITY_DISTRICT_ID) {
            type = NavType.StringType
            nullable = true
            //defaultValue = null
        })
    ) {
        fun routeForLocalityDistrict(localityDistrictInput: LocalityDistrictInput? = null): String {
            val route = when (localityDistrictInput) {
                null -> baseRoute()
                else -> String.format(
                    ROUTE_LOCALITY_DISTRICT, localityDistrictInput.localityDistrictId
                )
            }
            //val route = String.format(ROUTE_PAYER, payerId)
            Timber.tag(TAG).d("LocalityDistrict - routeForLocalityDistrict(...): '%s'", route)
            return route
        }

        fun fromEntry(entry: NavBackStackEntry): LocalityDistrictInput {
            val localityDistrictInput = LocalityDistrictInput(
                UUID.fromString(entry.arguments?.getString(ARG_LOCALITY_DISTRICT_ID).orEmpty())
            )
            Timber.tag(TAG).d("LocalityDistrict - fromEntry(...): '%s'", localityDistrictInput)
            return localityDistrictInput
        }
    }

    data object Microdistrict : NavRoutes(
        route = String.format(ROUTE_MICRODISTRICT, "{$ARG_MICRODISTRICT_ID}"),
        iconPainterResId = R.drawable.ic_microdistrict_24,
        titleResId = R.string.nav_item_microdistrict,
        arguments = listOf(navArgument(ARG_MICRODISTRICT_ID) {
            type = NavType.StringType
            nullable = true
            //defaultValue = null
        })
    ) {
        fun routeForMicrodistrict(microdistrictInput: MicrodistrictInput? = null): String {
            val route = when (microdistrictInput) {
                null -> baseRoute()
                else -> String.format(ROUTE_MICRODISTRICT, microdistrictInput.microdistrictId)
            }
            //val route = String.format(ROUTE_PAYER, payerId)
            Timber.tag(TAG).d("Microdistrict - routeForMicrodistrict(...): '%s'", route)
            return route
        }

        fun fromEntry(entry: NavBackStackEntry): MicrodistrictInput {
            val microdistrictInput = MicrodistrictInput(
                UUID.fromString(entry.arguments?.getString(ARG_MICRODISTRICT_ID).orEmpty())
            )
            Timber.tag(TAG).d("Microdistrict - fromEntry(...): '%s'", microdistrictInput)
            return microdistrictInput
        }
    }

    data object Street : NavRoutes(
        route = String.format(ROUTE_STREET, "{$ARG_STREET_ID}"),
        iconPainterResId = R.drawable.ic_street_sign_24,
        titleResId = R.string.nav_item_street,
        arguments = listOf(navArgument(ARG_STREET_ID) {
            type = NavType.StringType
            nullable = true
            //defaultValue = null
        })
    ) {
        fun routeForStreet(streetInput: StreetInput? = null): String {
            val route = when (streetInput) {
                null -> baseRoute()
                else -> String.format(ROUTE_STREET, streetInput.streetId)
            }
            //val route = String.format(ROUTE_PAYER, payerId)
            Timber.tag(TAG).d("Street - routeForStreet(...): '%s'", route)
            return route
        }

        fun fromEntry(entry: NavBackStackEntry): StreetInput {
            val streetInput = StreetInput(
                UUID.fromString(entry.arguments?.getString(ARG_STREET_ID).orEmpty())
            )
            Timber.tag(TAG).d("Street - fromEntry(...): '%s'", streetInput)
            return streetInput
        }
    }

    // Congregation:
    data object Congregation : NavRoutes(
        route = String.format(ROUTE_CONGREGATION, "$ARG_CONGREGATION_ID={$ARG_CONGREGATION_ID}"),
        iconPainterResId = R.drawable.ic_congregation_24,
        titleResId = R.string.nav_item_congregation,
        arguments = listOf(navArgument(ARG_CONGREGATION_ID) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {
        fun routeForCongregation(congregationInput: CongregationInput? = null): String {
            val route = String.format(
                ROUTE_CONGREGATION,
                congregationInput?.let { "$ARG_CONGREGATION_ID=${it.congregationId}" } ?: ""
            )
            /*    when (congregationInput) {
                null -> baseRoute()
                else -> String.format(ROUTE_CONGREGATION, congregationInput.congregationId)
            }

             */
            //val route = String.format(ROUTE_PAYER, payerId)
            Timber.tag(TAG).d("Congregation - routeForCongregation(...): '%s'", route)
            return route
        }

        fun fromEntry(entry: NavBackStackEntry): CongregationInput? {
            val congregationInput = entry.arguments?.getString(ARG_CONGREGATION_ID)
                ?.let { CongregationInput(UUID.fromString(it)) }
            Timber.tag(TAG).d("Congregation - fromEntry(...): '%s'", congregationInput)
            return congregationInput
        }
    }

    data object Group : NavRoutes(
        route = String.format(ROUTE_GROUP, "{$ARG_GROUP_ID}"),
        iconPainterResId = R.drawable.ic_group_24,
        titleResId = R.string.nav_item_group,
        arguments = listOf(navArgument(ARG_GROUP_ID) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
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

        fun fromEntry(entry: NavBackStackEntry): GroupInput? {
            val groupInput =
                entry.arguments?.getString(ARG_GROUP_ID)?.let { GroupInput(UUID.fromString(it)) }
            Timber.tag(TAG).d("Group - fromEntry(...): '%s'", groupInput)
            return groupInput
        }
    }

    data object Member : NavRoutes(
        route = String.format(ROUTE_MEMBER, "{$ARG_MEMBER_ID}"),
        iconImageVector = Icons.Outlined.Person,
        titleResId = R.string.nav_item_member,
        arguments = listOf(navArgument(ARG_MEMBER_ID) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
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

        fun fromEntry(entry: NavBackStackEntry): MemberInput? {
            val memberInput =
                entry.arguments?.getString(ARG_MEMBER_ID)?.let { MemberInput(UUID.fromString(it)) }
            Timber.tag(TAG).d("Member - fromEntry(...): '%s'", memberInput)
            return memberInput
        }
    }

    data object TerritoryCategory : NavRoutes(
        route = String.format(ROUTE_TERRITORY_CATEGORY, "{$ARG_TERRITORY_CATEGORY_ID}"),
        iconPainterResId = R.drawable.ic_territory_category_24,
        titleResId = R.string.nav_item_territory_category,
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
            val territoryCategoryInput = TerritoryCategoryInput(
                UUID.fromString(entry.arguments?.getString(ARG_TERRITORY_CATEGORY_ID).orEmpty())
            )
            Timber.tag(TAG).d("TerritoryCategory - fromEntry(...): '%s'", territoryCategoryInput)
            return territoryCategoryInput
        }
    }

    data object Territory : NavRoutes(
        route = String.format(ROUTE_TERRITORY, "{$ARG_TERRITORY_ID}"),
        iconPainterResId = R.drawable.ic_territory_map_24,
        titleResId = R.string.nav_item_territory,
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

    data object TerritoryDetails : NavRoutes(
        route = String.format(ROUTE_TERRITORY_DETAILS, "{$ARG_TERRITORY_ID}"),
        iconPainterResId = R.drawable.ic_territory_details_24,
        titleResId = R.string.nav_item_territory_details,
        arguments = listOf(navArgument(ARG_TERRITORY_ID) {
            type = NavType.StringType
            nullable = true
            //defaultValue = null
        })
    ) {
        fun routeForTerritoryDetails(territoryInput: TerritoryInput? = null): String {
            val route = when (territoryInput) {
                null -> baseRoute()
                else -> String.format(ROUTE_TERRITORY_DETAILS, territoryInput.territoryId)
            }
            //val route = String.format(ROUTE_RATE, payerId)
            Timber.tag(TAG).d("TerritoryDetails - routeForTerritoryDetails(...): '%s'", route)
            return route
        }

        fun fromEntry(entry: NavBackStackEntry): TerritoryInput {
            val territoryInput = TerritoryInput(
                UUID.fromString(entry.arguments?.getString(ARG_TERRITORY_ID).orEmpty())
            )
            Timber.tag(TAG).d("TerritoryDetails - fromEntry(...): '%s'", territoryInput)
            return territoryInput
        }
    }

    data object TerritoryStreet : NavRoutes(
        route = String.format(
            ROUTE_TERRITORY_STREET,
            "{$ARG_TERRITORY_ID}",
            "{$ARG_TERRITORY_STREET_ID}"
        ),
        iconPainterResId = R.drawable.ic_territory_street_24,
        titleResId = R.string.nav_item_territory_street,
        arguments = listOf(navArgument(ARG_TERRITORY_ID) {
            type = NavType.StringType
            nullable = false
            //defaultValue = null
        }, navArgument(ARG_TERRITORY_STREET_ID) {
            type = NavType.StringType
            nullable = true
            //defaultValue = null
        })
    ) {
        fun routeForTerritoryStreet(territoryStreetInput: TerritoryStreetInput? = null): String {
            val route = territoryStreetInput?.let {
                String.format(
                    ROUTE_TERRITORY_STREET,
                    territoryStreetInput.territoryId, territoryStreetInput.territoryStreetId
                )
            } ?: baseRoute()
            //val route = String.format(ROUTE_RATE, payerId)
            Timber.tag(TAG).d("TerritoryStreet - routeForTerritoryStreet(...): '%s'", route)
            return route
        }

        fun fromEntry(entry: NavBackStackEntry): TerritoryStreetInput {
            val territoryStreetInput = TerritoryStreetInput(
                UUID.fromString(entry.arguments?.getString(ARG_TERRITORY_ID).orEmpty()),
                entry.arguments?.getString(ARG_TERRITORY_STREET_ID)?.let { UUID.fromString(it) }
            )
            Timber.tag(TAG).d("TerritoryStreet - fromEntry(...): '%s'", territoryStreetInput)
            return territoryStreetInput
        }
    }

    data object House : NavRoutes(
        route = String.format(ROUTE_HOUSE, "{$ARG_HOUSE_ID}"),
        iconImageVector = Icons.Outlined.Home,
        titleResId = R.string.nav_item_house,
        arguments = listOf(navArgument(ARG_HOUSE_ID) {
            type = NavType.StringType
            nullable = true
            //defaultValue = null
        })
    ) {
        fun routeForHouse(houseInput: HouseInput? = null): String {
            val route = when (houseInput) {
                null -> baseRoute()
                else -> String.format(ROUTE_HOUSE, houseInput.houseId)
            }
            //val route = String.format(ROUTE_RATE, payerId)
            Timber.tag(TAG).d("House - routeForHouse(...): '%s'", route)
            return route
        }

        fun fromEntry(entry: NavBackStackEntry): HouseInput {
            val houseInput = HouseInput(
                UUID.fromString(entry.arguments?.getString(ARG_HOUSE_ID).orEmpty())
            )
            Timber.tag(TAG).d("House - fromEntry(...): '%s'", houseInput)
            return houseInput
        }
    }

    data object Entrance : NavRoutes(
        route = String.format(ROUTE_ENTRANCE, "{$ARG_ENTRANCE_ID}"),
        iconPainterResId = R.drawable.ic_entrance_24,
        titleResId = R.string.nav_item_entrance,
        arguments = listOf(navArgument(ARG_ENTRANCE_ID) {
            type = NavType.StringType
            nullable = true
            //defaultValue = null
        })
    ) {
        fun routeForEntrance(entranceInput: EntranceInput? = null): String {
            val route = when (entranceInput) {
                null -> baseRoute()
                else -> String.format(ROUTE_ENTRANCE, entranceInput.entranceId)
            }
            //val route = String.format(ROUTE_RATE, payerId)
            Timber.tag(TAG).d("Entrance - routeForEntrance(...): '%s'", route)
            return route
        }

        fun fromEntry(entry: NavBackStackEntry): EntranceInput {
            val entranceInput = EntranceInput(
                UUID.fromString(entry.arguments?.getString(ARG_ENTRANCE_ID).orEmpty())
            )
            Timber.tag(TAG).d("Entrance - fromEntry(...): '%s'", entranceInput)
            return entranceInput
        }
    }

    data object Floor : NavRoutes(
        route = String.format(ROUTE_FLOOR, "{$ARG_FLOOR_ID}"),
        iconPainterResId = R.drawable.ic_floors_24,
        titleResId = R.string.nav_item_floor,
        arguments = listOf(navArgument(ARG_FLOOR_ID) {
            type = NavType.StringType
            nullable = true
            //defaultValue = null
        })
    ) {
        fun routeForFloor(floorInput: FloorInput? = null): String {
            val route = when (floorInput) {
                null -> baseRoute()
                else -> String.format(ROUTE_FLOOR, floorInput.floorId)
            }
            //val route = String.format(ROUTE_RATE, payerId)
            Timber.tag(TAG).d("Floor - routeForFloor(...): '%s'", route)
            return route
        }

        fun fromEntry(entry: NavBackStackEntry): FloorInput {
            val floorInput = FloorInput(
                UUID.fromString(entry.arguments?.getString(ARG_FLOOR_ID).orEmpty())
            )
            Timber.tag(TAG).d("Floor - fromEntry(...): '%s'", floorInput)
            return floorInput
        }
    }

    data object Room : NavRoutes(
        route = String.format(ROUTE_ROOM, "{$ARG_ROOM_ID}"),
        iconPainterResId = R.drawable.ic_room_24,
        titleResId = R.string.nav_item_room,
        arguments = listOf(navArgument(ARG_ROOM_ID) {
            type = NavType.StringType
            nullable = true
            //defaultValue = null
        })
    ) {
        fun routeForRoom(roomInput: RoomInput? = null): String {
            val route = when (roomInput) {
                null -> baseRoute()
                else -> String.format(ROUTE_ROOM, roomInput.roomId)
            }
            //val route = String.format(ROUTE_RATE, payerId)
            Timber.tag(TAG).d("Room - routeForRoom(...): '%s'", route)
            return route
        }

        fun fromEntry(entry: NavBackStackEntry): RoomInput {
            val roomInput = RoomInput(
                UUID.fromString(entry.arguments?.getString(ARG_ROOM_ID).orEmpty())
            )
            Timber.tag(TAG).d("Room - fromEntry(...): '%s'", roomInput)
            return roomInput
        }
    }

    data object HandOutTerritoriesConfirmation : NavRoutes(
        route = ROUTE_HAND_OUT_TERRITORIES_CONFIRMATION,
        iconPainterResId = R.drawable.ic_hand_map_24,
        titleResId = R.string.nav_item_territory_hand_out_confirmation
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
