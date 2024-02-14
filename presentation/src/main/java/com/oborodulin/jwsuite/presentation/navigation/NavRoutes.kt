package com.oborodulin.jwsuite.presentation.navigation

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.oborodulin.home.common.util.LogLevel.LOG_NAVIGATION
import com.oborodulin.jwsuite.domain.types.MemberRoleType
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_CONGREGATING
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_CONGREGATION
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_DASHBOARDING
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_DASHBOARD_SETTINGS
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_ENTRANCE
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_FLOOR
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_GEO
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_GROUP
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_HAND_OUT_CONFIRMATION
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_HOME
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_HOUSE
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_HOUSING
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_LOCALITY
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_LOCALITY_DISTRICT
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_LOGIN
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_MEMBER
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_MEMBER_REPORT
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_MEMBER_ROLE
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_MICRODISTRICT
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_MINISTRING
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_PROCESS_CONFIRMATION
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_REGION
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_REGION_DISTRICT
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_REPORT_HOUSES
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_REPORT_ROOMS
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_REPORT_STREETS
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_ROOM
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_SIGNUP
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_STREET
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_STREET_LOCALITY_DISTRICT
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_STREET_MICRODISTRICT
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_TERRITORING
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_TERRITORY
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_TERRITORY_CATEGORY
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_TERRITORY_DETAILS
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_TERRITORY_ENTRANCE
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_TERRITORY_FLOOR
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_TERRITORY_HOUSE
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_TERRITORY_ROOM
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_TERRITORY_SETTINGS
import com.oborodulin.jwsuite.presentation.navigation.MainDestinations.ROUTE_TERRITORY_STREET
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.CongregationInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.EntranceInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.FloorInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.GroupInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.HouseInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.LocalityDistrictInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.LocalityInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.MemberInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.MemberReportInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.MemberRoleInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.MicrodistrictInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.RegionDistrictInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.RegionInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.RoomInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.StreetInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.StreetLocalityDistrictInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.StreetMicrodistrictInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryCategoryInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryEntranceInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryFloorInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryHouseInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryRoomInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryStreetInput
import timber.log.Timber
import java.util.UUID

// ic_launcher - https://www.flaticon.com/authors/manshagraphics
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
// ic_territory_house.png - https://www.freepik.com/
// ic_territory_entrance.png - https://www.flaticon.com/authors/muhammad-usman
// ic_territory_floor.png - https://www.flaticon.com/authors/mangsaabguru
// ic_territory_room.png - https://www.flaticon.com/authors/catkuro
// ic_intercom.png - https://www.freepik.com/
// ic_residential.png - https://www.flaticon.com/search/4?type=icon&search-group=all&word=human+house&color=black&shape=outline&type=icon
// ic_floor_rooms.png - https://www.flaticon.com/authors/andrejs-kirma
// ic_language.png - https://www.freepik.com/
// ic_geo.png - https://www.freepik.com/
// ic_role.png - <a href="https://www.flaticon.com/free-icons/setting" title="setting icons">Setting icons created by Tanah Basah - Flaticon</a>
//              https://www.flaticon.com/search?word=role&shape=outline
// ic_receive.png - <a href="https://www.flaticon.com/free-icons/download" title="download icons">Download icons created by Bharat Icons - Flaticon</a>
// ic_housing.png - https://www.flaticon.com/search/2?type=icon&word=houses
//                  <a href="https://www.flaticon.com/free-icons/real-estate-home" title="real estate home icons">Real estate home icons created by Eklip Studio - Flaticon</a>
// ic_member_report.png - https://www.flaticon.com/authors/lagot-design
//                  <a href="https://www.flaticon.com/free-icons/member" title="member icons">Member icons created by Lagot Design - Flaticon</a>
// ic_report_house.png - <a href="https://www.flaticon.com/free-icons/estate" title="estate icons">Estate icons created by Atif Arshad - Flaticon</a>
//                  https://www.flaticon.com/search/2?type=icon&search-group=all&word=house+report&type=icon
// ic_report_room.png - https://www.flaticon.com/authors/rsetiawan
//                  <a href="https://www.flaticon.com/free-icons/report" title="report icons">Report icons created by rsetiawan - Flaticon</a>
// ic_report_mark.png - https://www.flaticon.com/search?type=icon&search-group=all&word=report+mark&type=icon
//                  <a href="https://www.flaticon.com/free-icons/exclamation-mark" title="exclamation mark icons">Exclamation mark icons created by Anggara - Flaticon</a>
// ic_gender.png - https://www.flaticon.com/authors/icon-mela
//                  <a href="https://www.flaticon.com/free-icons/wc" title="wc icons">Wc icons created by Icon Mela - Flaticon</a>
// ic_add_house.png - https://www.flaticon.com/authors/edi-prast
//                  <a href="https://www.flaticon.com/free-icons/real-estate" title="real estate icons">Real estate icons created by Edi Prast - Flaticon</a>
// ic_receive.png - https://www.flaticon.com/authors/heisenberg-jr
//                  <a href="https://www.flaticon.com/free-icons/ui" title="ui icons">Ui icons created by heisenberg_jr - Flaticon</a>
// ic_backup.png - https://www.flaticon.com/authors/aquariid
//                  <a href="https://www.flaticon.com/free-icons/mysql" title="mysql icons">Mysql icons created by AquariiD - Flaticon</a>
// ic_restore.png - https://www.flaticon.com/authors/aquariid
//                  <a href="https://www.flaticon.com/free-icons/servers" title="servers icons">Servers icons created by AquariiD - Flaticon</a>
// ic_synchronizing.png - https://www.flaticon.com/authors/ayub-irawan
//                    <a href="https://www.flaticon.com/free-icons/sync" title="sync icons">Sync icons created by Ayub Irawan - Flaticon</a>

private const val TAG = "Presentation.NavRoutes"

// https://betterprogramming.pub/jetpack-compose-clean-navigation-94b386f7a076

// Geo:
private const val ARG_REGION_ID = "regionId"
private const val ARG_REGION_DISTRICT_ID = "regionDistrictId"
private const val ARG_LOCALITY_ID = "localityId"
private const val ARG_LOCALITY_DISTRICT_ID = "localityDistrictId"
private const val ARG_MICRODISTRICT_ID = "microdistrictId"
private const val ARG_STREET_ID = "streetId"
private const val ARG_STREET_DISTRICT_ID = "streetDistrictId"

// Congregation:
private const val ARG_CONGREGATION_ID = "congregationId"
private const val ARG_GROUP_ID = "groupId"
private const val ARG_MEMBER_ID = "memberId"
private const val ARG_MEMBER_ROLE_ID = "memberRoleId"

// Territory:
private const val ARG_TERRITORY_CATEGORY_ID = "territoryCategoryId"
private const val ARG_TERRITORY_ID = "territoryId"
private const val ARG_TERRITORY_STREET_ID = "territoryStreetId"
private const val ARG_HOUSE_ID = "houseId"
private const val ARG_ENTRANCE_ID = "entranceId"
private const val ARG_FLOOR_ID = "floorId"
private const val ARG_ROOM_ID = "roomId"

private const val ARG_MEMBER_REPORT_ID = "memberReportId"

/**
 * Created by oborodulin on 12.December.2021
 */
sealed class NavRoutes(
    val route: String,
    val iconImageVector: ImageVector? = null,
    @DrawableRes open val iconPainterResId: Int? = null,
    @StringRes open val titleResId: Int,
    val arguments: List<NamedNavArgument> = emptyList(),
    val userRoles: List<MemberRoleType> = emptyList()
) {
    // Bottom Navigation:
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
        titleResId = R.string.nav_item_congregating,
        userRoles = listOf(MemberRoleType.ADMIN)
    )

    data object Territoring : NavRoutes(
        route = ROUTE_TERRITORING,
        iconPainterResId = R.drawable.ic_territory_map_24,
        titleResId = R.string.nav_item_territoring,
        userRoles = listOf(MemberRoleType.TERRITORIES)
    )

    data object Ministring : NavRoutes(
        route = ROUTE_MINISTRING,
        iconPainterResId = R.drawable.ic_maps_home_work_24,
        titleResId = R.string.nav_item_ministring
    )

    // Auth Navigation:
    data object Signup : NavRoutes(
        route = ROUTE_SIGNUP,
        iconImageVector = Icons.Outlined.AccountBox,
        titleResId = R.string.nav_item_signup
    )

    data object Login : NavRoutes(
        route = ROUTE_LOGIN,
        iconImageVector = Icons.Outlined.Lock,
        titleResId = R.string.nav_item_login
    )

    // Service Navigation:
    data object Geo : NavRoutes(
        route = ROUTE_GEO,
        iconPainterResId = R.drawable.ic_geo_24,
        titleResId = R.string.nav_item_geo
    )

    data object Housing : NavRoutes(
        route = ROUTE_HOUSING,
        iconPainterResId = R.drawable.ic_housing_24,
        titleResId = R.string.nav_item_housing
    )

    // Settings:
    data object DashboardSettings : NavRoutes(
        route = ROUTE_DASHBOARD_SETTINGS,
        iconImageVector = Icons.Outlined.Settings,
        titleResId = R.string.nav_item_dashboard_settings
    )

    data object TerritorySettings : NavRoutes(
        route = ROUTE_TERRITORY_SETTINGS,
        iconImageVector = Icons.Outlined.Settings,
        titleResId = R.string.nav_item_territory_settings
    )

    // Main Navigation:
    // Geo:
    data object Region : NavRoutes(
        route = String.format(ROUTE_REGION, "$ARG_REGION_ID={$ARG_REGION_ID}"),
        iconPainterResId = R.drawable.ic_region_24,
        titleResId = R.string.nav_item_region,
        arguments = listOf(navArgument(ARG_REGION_ID) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {
        fun routeForRegion(regionInput: RegionInput? = null): String {
            val route = String.format(
                ROUTE_REGION, regionInput?.let { "$ARG_REGION_ID=${it.regionId}" }.orEmpty()
            )
            if (LOG_NAVIGATION) Timber.tag(TAG).d("Region -> routeForRegion: '%s'", route)
            return route
        }

        fun fromEntry(entry: NavBackStackEntry): RegionInput? {
            val regionInput =
                entry.arguments?.getString(ARG_REGION_ID)?.let { RegionInput(UUID.fromString(it)) }
            if (LOG_NAVIGATION) Timber.tag(TAG).d("Region -> fromEntry: '%s'", regionInput)
            return regionInput
        }
    }

    data object RegionDistrict : NavRoutes(
        route =
        String.format(ROUTE_REGION_DISTRICT, "$ARG_REGION_DISTRICT_ID={$ARG_REGION_DISTRICT_ID}"),
        iconPainterResId = R.drawable.ic_district_24,
        titleResId = R.string.nav_item_region_district,
        arguments = listOf(navArgument(ARG_REGION_DISTRICT_ID) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {
        fun routeForRegionDistrict(regionDistrictInput: RegionDistrictInput? = null): String {
            val route = String.format(
                ROUTE_REGION_DISTRICT,
                regionDistrictInput?.let { "$ARG_REGION_DISTRICT_ID=${it.regionDistrictId}" }
                    .orEmpty()
            )
            if (LOG_NAVIGATION) Timber.tag(TAG)
                .d("RegionDistrict -> routeForRegionDistrict: '%s'", route)
            return route
        }

        fun fromEntry(entry: NavBackStackEntry): RegionDistrictInput? {
            val regionDistrictInput =
                entry.arguments?.getString(ARG_REGION_DISTRICT_ID)?.let {
                    RegionDistrictInput(UUID.fromString(it))
                }
            if (LOG_NAVIGATION) Timber.tag(TAG)
                .d("RegionDistrict -> fromEntry: '%s'", regionDistrictInput)
            return regionDistrictInput
        }
    }

    data object Locality : NavRoutes(
        route = String.format(ROUTE_LOCALITY, "$ARG_LOCALITY_ID={$ARG_LOCALITY_ID}"),
        iconPainterResId = R.drawable.ic_location_city_24,
        titleResId = R.string.nav_item_locality,
        arguments = listOf(navArgument(ARG_LOCALITY_ID) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {
        fun routeForLocality(localityInput: LocalityInput? = null): String {
            val route = String.format(
                ROUTE_LOCALITY, localityInput?.let { "$ARG_LOCALITY_ID=${it.localityId}" }.orEmpty()
            )
            if (LOG_NAVIGATION) Timber.tag(TAG).d("Locality -> routeForLocality: '%s'", route)
            return route
        }

        fun fromEntry(entry: NavBackStackEntry): LocalityInput? {
            val localityInput = entry.arguments?.getString(ARG_LOCALITY_ID)?.let {
                LocalityInput(UUID.fromString(it))
            }
            if (LOG_NAVIGATION) Timber.tag(TAG).d("Locality -> fromEntry: '%s'", localityInput)
            return localityInput
        }
    }

    data object LocalityDistrict : NavRoutes(
        route = String.format(
            ROUTE_LOCALITY_DISTRICT, "$ARG_LOCALITY_DISTRICT_ID={$ARG_LOCALITY_DISTRICT_ID}"
        ),
        iconPainterResId = R.drawable.ic_locality_district_24,
        titleResId = R.string.nav_item_locality_district,
        arguments = listOf(navArgument(ARG_LOCALITY_DISTRICT_ID) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {
        fun routeForLocalityDistrict(localityDistrictInput: LocalityDistrictInput? = null): String {
            val route = String.format(
                ROUTE_LOCALITY_DISTRICT,
                localityDistrictInput?.let { "$ARG_LOCALITY_DISTRICT_ID=${it.localityDistrictId}" }
                    .orEmpty()
            )
            if (LOG_NAVIGATION) Timber.tag(TAG)
                .d("LocalityDistrict -> routeForLocalityDistrict: '%s'", route)
            return route
        }

        fun fromEntry(entry: NavBackStackEntry): LocalityDistrictInput? {
            val localityDistrictInput = entry.arguments?.getString(ARG_LOCALITY_DISTRICT_ID)?.let {
                LocalityDistrictInput(UUID.fromString(it))
            }
            if (LOG_NAVIGATION) Timber.tag(TAG)
                .d("LocalityDistrict -> fromEntry: '%s'", localityDistrictInput)
            return localityDistrictInput
        }
    }

    data object Microdistrict : NavRoutes(
        route = String.format(ROUTE_MICRODISTRICT, "$ARG_MICRODISTRICT_ID={$ARG_MICRODISTRICT_ID}"),
        iconPainterResId = R.drawable.ic_microdistrict_24,
        titleResId = R.string.nav_item_microdistrict,
        arguments = listOf(navArgument(ARG_MICRODISTRICT_ID) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {
        fun routeForMicrodistrict(microdistrictInput: MicrodistrictInput? = null): String {
            val route = String.format(
                ROUTE_MICRODISTRICT,
                microdistrictInput?.let { "$ARG_MICRODISTRICT_ID=${it.microdistrictId}" }.orEmpty()
            )
            if (LOG_NAVIGATION) Timber.tag(TAG)
                .d("Microdistrict -> routeForMicrodistrict: '%s'", route)
            return route
        }

        fun fromEntry(entry: NavBackStackEntry): MicrodistrictInput? {
            val microdistrictInput = entry.arguments?.getString(ARG_MICRODISTRICT_ID)?.let {
                MicrodistrictInput(UUID.fromString(it))
            }
            if (LOG_NAVIGATION) Timber.tag(TAG)
                .d("Microdistrict -> fromEntry: '%s'", microdistrictInput)
            return microdistrictInput
        }
    }

    data object Street : NavRoutes(
        route = String.format(ROUTE_STREET, "$ARG_STREET_ID={$ARG_STREET_ID}"),
        iconPainterResId = R.drawable.ic_street_sign_24,
        titleResId = R.string.nav_item_street,
        arguments = listOf(navArgument(ARG_STREET_ID) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {
        fun routeForStreet(streetInput: StreetInput? = null): String {
            val route = String.format(
                ROUTE_STREET, streetInput?.let { "$ARG_STREET_ID=${it.streetId}" }.orEmpty()
            )
            if (LOG_NAVIGATION) Timber.tag(TAG).d("Street -> routeForStreet: '%s'", route)
            return route
        }

        fun fromEntry(entry: NavBackStackEntry): StreetInput? {
            val streetInput = entry.arguments?.getString(ARG_STREET_ID)?.let {
                StreetInput(UUID.fromString(it))
            }
            if (LOG_NAVIGATION) Timber.tag(TAG).d("Street -> fromEntry: '%s'", streetInput)
            return streetInput
        }
    }

    data object StreetLocalityDistrict : NavRoutes(
        route = String.format(
            ROUTE_STREET_LOCALITY_DISTRICT,
            "{$ARG_STREET_ID}",
            "$ARG_STREET_DISTRICT_ID={$ARG_STREET_DISTRICT_ID}"
        ),
        iconPainterResId = R.drawable.ic_locality_district_24,
        titleResId = R.string.nav_item_street_locality_district,
        arguments = listOf(navArgument(ARG_STREET_ID) {
            type = NavType.StringType
            nullable = false
            //defaultValue = null
        }, navArgument(ARG_STREET_DISTRICT_ID) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {
        fun routeForStreetLocalityDistrict(streetLocalityDistrictInput: StreetLocalityDistrictInput? = null): String {
            val route = String.format(
                ROUTE_STREET_LOCALITY_DISTRICT,
                streetLocalityDistrictInput?.let { "${it.streetId}" }.orEmpty(),
                streetLocalityDistrictInput?.streetDistrictId?.let { "$ARG_STREET_DISTRICT_ID=${it}" }
                    .orEmpty()
            )
            Timber.tag(TAG)
                .d("StreetLocalityDistrict -> routeForStreetLocalityDistrict: '%s'", route)
            return route
        }

        fun fromEntry(entry: NavBackStackEntry): StreetLocalityDistrictInput {
            val streetLocalityDistrictInput = StreetLocalityDistrictInput(
                UUID.fromString(entry.arguments?.getString(ARG_STREET_ID).orEmpty()),
                entry.arguments?.getString(ARG_STREET_DISTRICT_ID)?.let { UUID.fromString(it) }
            )
            Timber.tag(TAG)
                .d("StreetLocalityDistrict -> fromEntry: '%s'", streetLocalityDistrictInput)
            return streetLocalityDistrictInput
        }
    }

    data object StreetMicrodistrict : NavRoutes(
        route = String.format(
            ROUTE_STREET_MICRODISTRICT,
            "{$ARG_STREET_ID}",
            "$ARG_STREET_DISTRICT_ID={$ARG_STREET_DISTRICT_ID}"
        ),
        iconPainterResId = R.drawable.ic_microdistrict_24,
        titleResId = R.string.nav_item_street_microdistrict,
        arguments = listOf(navArgument(ARG_STREET_ID) {
            type = NavType.StringType
            nullable = false
            //defaultValue = null
        }, navArgument(ARG_STREET_DISTRICT_ID) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {
        fun routeForStreetMicrodistrict(streetMicrodistrictInput: StreetMicrodistrictInput? = null): String {
            val route = String.format(
                ROUTE_STREET_MICRODISTRICT,
                streetMicrodistrictInput?.let { "${it.streetId}" }.orEmpty(),
                streetMicrodistrictInput?.streetDistrictId?.let { "$ARG_STREET_DISTRICT_ID=${it}" }
                    .orEmpty()
            )
            if (LOG_NAVIGATION) Timber.tag(TAG)
                .d("StreetMicrodistrict -> routeForStreetMicrodistrict: '%s'", route)
            return route
        }

        fun fromEntry(entry: NavBackStackEntry): StreetMicrodistrictInput {
            val streetMicrodistrictInput = StreetMicrodistrictInput(
                UUID.fromString(entry.arguments?.getString(ARG_STREET_ID).orEmpty()),
                entry.arguments?.getString(ARG_STREET_DISTRICT_ID)?.let { UUID.fromString(it) }
            )
            Timber.tag(TAG)
                .d("StreetMicrodistrict -> fromEntry: '%s'", streetMicrodistrictInput)
            return streetMicrodistrictInput
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
                congregationInput?.let { "$ARG_CONGREGATION_ID=${it.congregationId}" }.orEmpty()
            )
            if (LOG_NAVIGATION) Timber.tag(TAG)
                .d("Congregation -> routeForCongregation: '%s'", route)
            return route
        }

        fun fromEntry(entry: NavBackStackEntry): CongregationInput? {
            val congregationInput = entry.arguments?.getString(ARG_CONGREGATION_ID)
                ?.let { CongregationInput(UUID.fromString(it)) }
            if (LOG_NAVIGATION) Timber.tag(TAG)
                .d("Congregation -> fromEntry: '%s'", congregationInput)
            return congregationInput
        }
    }

    data object Group : NavRoutes(
        route = String.format(ROUTE_GROUP, "$ARG_GROUP_ID={$ARG_GROUP_ID}"),
        iconPainterResId = R.drawable.ic_group_24,
        titleResId = R.string.nav_item_group,
        arguments = listOf(navArgument(ARG_GROUP_ID) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {
        fun routeForGroup(groupInput: GroupInput? = null): String {
            val route = String.format(
                ROUTE_GROUP, groupInput?.let { "$ARG_GROUP_ID=${it.groupId}" }.orEmpty()
            )
            if (LOG_NAVIGATION) Timber.tag(TAG).d("Group -> routeForGroup: '%s'", route)
            return route
        }

        fun fromEntry(entry: NavBackStackEntry): GroupInput? {
            val groupInput =
                entry.arguments?.getString(ARG_GROUP_ID)?.let { GroupInput(UUID.fromString(it)) }
            if (LOG_NAVIGATION) Timber.tag(TAG).d("Group -> fromEntry: '%s'", groupInput)
            return groupInput
        }
    }

    data object Member : NavRoutes(
        route = String.format(ROUTE_MEMBER, "$ARG_MEMBER_ID={$ARG_MEMBER_ID}"),
        iconImageVector = Icons.Outlined.Person,
        titleResId = R.string.nav_item_member,
        arguments = listOf(navArgument(ARG_MEMBER_ID) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {
        fun routeForMember(memberInput: MemberInput? = null): String {
            val route = String.format(
                ROUTE_MEMBER, memberInput?.let { "$ARG_MEMBER_ID=${it.memberId}" }.orEmpty()
            )
            if (LOG_NAVIGATION) Timber.tag(TAG).d("Member -> routeForMember: '%s'", route)
            return route
        }

        fun fromEntry(entry: NavBackStackEntry): MemberInput? {
            val memberInput =
                entry.arguments?.getString(ARG_MEMBER_ID)?.let { MemberInput(UUID.fromString(it)) }
            if (LOG_NAVIGATION) Timber.tag(TAG).d("Member -> fromEntry: '%s'", memberInput)
            return memberInput
        }
    }

    data object MemberRole : NavRoutes(
        route = String.format(ROUTE_MEMBER_ROLE, "$ARG_MEMBER_ROLE_ID={$ARG_MEMBER_ROLE_ID}"),
        iconPainterResId = R.drawable.ic_role_24,
        titleResId = R.string.nav_item_member_role,
        arguments = listOf(navArgument(ARG_MEMBER_ROLE_ID) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {
        fun routeForMemberRole(memberRoleInput: MemberRoleInput? = null): String {
            val route = String.format(
                ROUTE_MEMBER_ROLE,
                memberRoleInput?.let { "$ARG_MEMBER_ROLE_ID=${it.memberRoleId}" }.orEmpty()
            )
            if (LOG_NAVIGATION) Timber.tag(TAG).d("MemberRole -> routeForMemberRole: '%s'", route)
            return route
        }

        fun fromEntry(entry: NavBackStackEntry): MemberRoleInput? {
            val memberRoleInput =
                entry.arguments?.getString(ARG_MEMBER_ROLE_ID)
                    ?.let { MemberRoleInput(UUID.fromString(it)) }
            if (LOG_NAVIGATION) Timber.tag(TAG).d("MemberRole -> fromEntry: '%s'", memberRoleInput)
            return memberRoleInput
        }
    }

    // Territory:
    data object TerritoryCategory : NavRoutes(
        route = String.format(
            ROUTE_TERRITORY_CATEGORY, "$ARG_TERRITORY_CATEGORY_ID={$ARG_TERRITORY_CATEGORY_ID}"
        ),
        iconPainterResId = R.drawable.ic_territory_category_24,
        titleResId = R.string.nav_item_territory_category,
        arguments = listOf(navArgument(ARG_TERRITORY_CATEGORY_ID) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {
        fun routeForTerritoryCategory(territoryCategoryInput: TerritoryCategoryInput? = null): String {
            val route = String.format(
                ROUTE_TERRITORY_CATEGORY,
                territoryCategoryInput?.let { "$ARG_TERRITORY_CATEGORY_ID=${it.territoryCategoryId}" }
                    .orEmpty()
            )
            if (LOG_NAVIGATION) Timber.tag(TAG)
                .d("TerritoryCategory -> routeForTerritoryCategory: '%s'", route)
            return route
        }

        fun fromEntry(entry: NavBackStackEntry): TerritoryCategoryInput? {
            val territoryCategoryInput =
                entry.arguments?.getString(ARG_TERRITORY_CATEGORY_ID)?.let {
                    TerritoryCategoryInput(UUID.fromString(it))
                }
            if (LOG_NAVIGATION) Timber.tag(TAG)
                .d("TerritoryCategory -> fromEntry: '%s'", territoryCategoryInput)
            return territoryCategoryInput
        }
    }

    data object Territory : NavRoutes(
        route = String.format(ROUTE_TERRITORY, "$ARG_TERRITORY_ID={$ARG_TERRITORY_ID}"),
        iconPainterResId = R.drawable.ic_territory_map_24,
        titleResId = R.string.nav_item_territory,
        arguments = listOf(navArgument(ARG_TERRITORY_ID) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {
        fun routeForTerritory(territoryInput: TerritoryInput? = null): String {
            val route = String.format(
                ROUTE_TERRITORY,
                territoryInput?.let { "$ARG_TERRITORY_ID=${it.territoryId}" }.orEmpty()
            )
            if (LOG_NAVIGATION) Timber.tag(TAG).d("Territory -> routeForTerritory: '%s'", route)
            return route
        }

        fun fromEntry(entry: NavBackStackEntry): TerritoryInput? {
            val territoryInput = entry.arguments?.getString(ARG_TERRITORY_ID)?.let {
                TerritoryInput(UUID.fromString(it))
            }
            if (LOG_NAVIGATION) Timber.tag(TAG).d("Territory -> fromEntry: '%s'", territoryInput)
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
            if (LOG_NAVIGATION) Timber.tag(TAG)
                .d("TerritoryDetails -> routeForTerritoryDetails: '%s'", route)
            return route
        }

        fun fromEntry(entry: NavBackStackEntry): TerritoryInput {
            val territoryInput = TerritoryInput(
                UUID.fromString(entry.arguments?.getString(ARG_TERRITORY_ID).orEmpty())
            )
            if (LOG_NAVIGATION) Timber.tag(TAG)
                .d("TerritoryDetails -> fromEntry: '%s'", territoryInput)
            return territoryInput
        }
    }

    data object TerritoryStreet : NavRoutes(
        route = String.format(
            ROUTE_TERRITORY_STREET, "{$ARG_TERRITORY_ID}",
            "$ARG_TERRITORY_STREET_ID={$ARG_TERRITORY_STREET_ID}"
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
            defaultValue = null
        })
    ) {
        fun routeForTerritoryStreet(territoryStreetInput: TerritoryStreetInput? = null): String {
            val route = String.format(
                ROUTE_TERRITORY_STREET,
                territoryStreetInput?.let { "${it.territoryId}" }.orEmpty(),
                territoryStreetInput?.territoryStreetId?.let { "$ARG_TERRITORY_STREET_ID=${it}" }
                    .orEmpty()
            )
            if (LOG_NAVIGATION) Timber.tag(TAG)
                .d("TerritoryStreet -> routeForTerritoryStreet: '%s'", route)
            return route
        }

        fun fromEntry(entry: NavBackStackEntry): TerritoryStreetInput {
            val territoryStreetInput = TerritoryStreetInput(
                UUID.fromString(entry.arguments?.getString(ARG_TERRITORY_ID).orEmpty()),
                entry.arguments?.getString(ARG_TERRITORY_STREET_ID)?.let { UUID.fromString(it) }
            )
            if (LOG_NAVIGATION) Timber.tag(TAG)
                .d("TerritoryStreet -> fromEntry: '%s'", territoryStreetInput)
            return territoryStreetInput
        }
    }

    data object TerritoryHouse : NavRoutes(
        route = String.format(
            ROUTE_TERRITORY_HOUSE, "{$ARG_TERRITORY_ID}", "$ARG_HOUSE_ID={$ARG_HOUSE_ID}"
        ),
        iconPainterResId = R.drawable.ic_territory_house_24,
        titleResId = R.string.nav_item_territory_house,
        arguments = listOf(navArgument(ARG_TERRITORY_ID) {
            type = NavType.StringType
            nullable = false
            //defaultValue = null
        }, navArgument(ARG_HOUSE_ID) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {
        fun routeForTerritoryHouse(territoryHouseInput: TerritoryHouseInput? = null): String {
            val route = String.format(
                ROUTE_TERRITORY_HOUSE,
                territoryHouseInput?.let { "${it.territoryId}" }.orEmpty(),
                territoryHouseInput?.houseId?.let { "$ARG_HOUSE_ID=${it}" }.orEmpty()
            )
            if (LOG_NAVIGATION) Timber.tag(TAG)
                .d("TerritoryHouse -> routeForTerritoryHouse: '%s'", route)
            return route
        }

        fun fromEntry(entry: NavBackStackEntry): TerritoryHouseInput {
            val territoryHouseInput = TerritoryHouseInput(
                UUID.fromString(entry.arguments?.getString(ARG_TERRITORY_ID).orEmpty()),
                entry.arguments?.getString(ARG_HOUSE_ID)?.let { UUID.fromString(it) }
            )
            if (LOG_NAVIGATION) Timber.tag(TAG)
                .d("TerritoryHouse -> fromEntry: '%s'", territoryHouseInput)
            return territoryHouseInput
        }
    }

    data object TerritoryEntrance : NavRoutes(
        route = String.format(
            ROUTE_TERRITORY_ENTRANCE, "{$ARG_TERRITORY_ID}", "$ARG_ENTRANCE_ID={$ARG_ENTRANCE_ID}"
        ),
        iconPainterResId = R.drawable.ic_territory_entrance_24,
        titleResId = R.string.nav_item_territory_entrance,
        arguments = listOf(navArgument(ARG_TERRITORY_ID) {
            type = NavType.StringType
            nullable = false
            //defaultValue = null
        }, navArgument(ARG_ENTRANCE_ID) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {
        fun routeForTerritoryEntrance(territoryEntranceInput: TerritoryEntranceInput? = null): String {
            val route = String.format(
                ROUTE_TERRITORY_ENTRANCE,
                territoryEntranceInput?.let { "${it.territoryId}" }.orEmpty(),
                territoryEntranceInput?.entranceId?.let { "$ARG_ENTRANCE_ID=${it}" }
                    .orEmpty()
            )
            if (LOG_NAVIGATION) Timber.tag(TAG)
                .d("TerritoryEntrance -> routeForTerritoryEntrance: '%s'", route)
            return route
        }

        fun fromEntry(entry: NavBackStackEntry): TerritoryEntranceInput {
            val territoryEntranceInput = TerritoryEntranceInput(
                UUID.fromString(entry.arguments?.getString(ARG_TERRITORY_ID).orEmpty()),
                entry.arguments?.getString(ARG_ENTRANCE_ID)?.let { UUID.fromString(it) }
            )
            if (LOG_NAVIGATION) Timber.tag(TAG)
                .d("TerritoryEntrance -> fromEntry: '%s'", territoryEntranceInput)
            return territoryEntranceInput
        }
    }

    data object TerritoryFloor : NavRoutes(
        route = String.format(
            ROUTE_TERRITORY_FLOOR, "{$ARG_TERRITORY_ID}", "$ARG_FLOOR_ID={$ARG_FLOOR_ID}"
        ),
        iconPainterResId = R.drawable.ic_territory_floor_24,
        titleResId = R.string.nav_item_territory_floor,
        arguments = listOf(navArgument(ARG_TERRITORY_ID) {
            type = NavType.StringType
            nullable = false
            //defaultValue = null
        }, navArgument(ARG_FLOOR_ID) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {
        fun routeForTerritoryFloor(territoryFloorInput: TerritoryFloorInput? = null): String {
            val route = String.format(
                ROUTE_TERRITORY_FLOOR,
                territoryFloorInput?.let { "${it.territoryId}" }.orEmpty(),
                territoryFloorInput?.floorId?.let { "$ARG_FLOOR_ID=${it}" }
                    .orEmpty()
            )
            if (LOG_NAVIGATION) Timber.tag(TAG)
                .d("TerritoryFloor -> routeForTerritoryFloor: '%s'", route)
            return route
        }

        fun fromEntry(entry: NavBackStackEntry): TerritoryFloorInput {
            val territoryFloorInput = TerritoryFloorInput(
                UUID.fromString(entry.arguments?.getString(ARG_TERRITORY_ID).orEmpty()),
                entry.arguments?.getString(ARG_FLOOR_ID)?.let { UUID.fromString(it) }
            )
            if (LOG_NAVIGATION) Timber.tag(TAG)
                .d("TerritoryFloor -> fromEntry: '%s'", territoryFloorInput)
            return territoryFloorInput
        }
    }

    data object TerritoryRoom : NavRoutes(
        route = String.format(
            ROUTE_TERRITORY_ROOM, "{$ARG_TERRITORY_ID}", "$ARG_ROOM_ID={$ARG_ROOM_ID}"
        ),
        iconPainterResId = R.drawable.ic_territory_room_24,
        titleResId = R.string.nav_item_territory_room,
        arguments = listOf(navArgument(ARG_TERRITORY_ID) {
            type = NavType.StringType
            nullable = false
            //defaultValue = null
        }, navArgument(ARG_ROOM_ID) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {
        fun routeForTerritoryRoom(territoryRoomInput: TerritoryRoomInput? = null): String {
            val route = String.format(
                ROUTE_TERRITORY_ROOM,
                territoryRoomInput?.let { "${it.territoryId}" }.orEmpty(),
                territoryRoomInput?.roomId?.let { "$ARG_ROOM_ID=${it}" }
                    .orEmpty()
            )
            if (LOG_NAVIGATION) Timber.tag(TAG)
                .d("TerritoryRoom -> routeForTerritoryRoom: '%s'", route)
            return route
        }

        fun fromEntry(entry: NavBackStackEntry): TerritoryRoomInput {
            val territoryId = entry.arguments?.getString(ARG_TERRITORY_ID).orEmpty()
            val roomId = entry.arguments?.getString(ARG_ROOM_ID)
            if (LOG_NAVIGATION) Timber.tag(TAG)
                .d(
                    "TerritoryRoom -> fromEntry: %s = '%s', %s = '%s'",
                    ARG_TERRITORY_ID, territoryId, ARG_ROOM_ID, roomId
                )
            val territoryRoomInput = TerritoryRoomInput(
                UUID.fromString(territoryId), roomId?.let { UUID.fromString(it) }
            )
            if (LOG_NAVIGATION) Timber.tag(TAG)
                .d("TerritoryRoom -> fromEntry: '%s'", territoryRoomInput)
            return territoryRoomInput
        }
    }

    // Housing:
    data object House : NavRoutes(
        route = String.format(ROUTE_HOUSE, "$ARG_HOUSE_ID={$ARG_HOUSE_ID}"),
        iconImageVector = Icons.Outlined.Home,
        titleResId = R.string.nav_item_house,
        arguments = listOf(navArgument(ARG_HOUSE_ID) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {
        fun routeForHouse(houseInput: HouseInput? = null): String {
            val route = String.format(
                ROUTE_HOUSE, houseInput?.let { "$ARG_HOUSE_ID=${it.houseId}" }.orEmpty()
            )
            if (LOG_NAVIGATION) Timber.tag(TAG).d("House -> routeForHouse: '%s'", route)
            return route
        }

        fun fromEntry(entry: NavBackStackEntry): HouseInput? {
            val houseInput = entry.arguments?.getString(ARG_HOUSE_ID)?.let {
                HouseInput(UUID.fromString(it))
            }
            if (LOG_NAVIGATION) Timber.tag(TAG).d("House -> fromEntry: '%s'", houseInput)
            return houseInput
        }
    }

    data object Entrance : NavRoutes(
        route = String.format(ROUTE_ENTRANCE, "$ARG_ENTRANCE_ID={$ARG_ENTRANCE_ID}"),
        iconPainterResId = R.drawable.ic_entrance_24,
        titleResId = R.string.nav_item_entrance,
        arguments = listOf(navArgument(ARG_ENTRANCE_ID) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {
        fun routeForEntrance(entranceInput: EntranceInput? = null): String {
            val route = when (entranceInput) {
                null -> baseRoute()
                else -> String.format(ROUTE_ENTRANCE, entranceInput.entranceId)
            }
            //val route = String.format(ROUTE_RATE, payerId)
            if (LOG_NAVIGATION) Timber.tag(TAG).d("Entrance -> routeForEntrance: '%s'", route)
            return route
        }

        fun fromEntry(entry: NavBackStackEntry): EntranceInput {
            val entranceInput = EntranceInput(
                UUID.fromString(entry.arguments?.getString(ARG_ENTRANCE_ID).orEmpty())
            )
            if (LOG_NAVIGATION) Timber.tag(TAG).d("Entrance -> fromEntry: '%s'", entranceInput)
            return entranceInput
        }
    }

    data object Floor : NavRoutes(
        route = String.format(ROUTE_FLOOR, "$ARG_FLOOR_ID={$ARG_FLOOR_ID}"),
        iconPainterResId = R.drawable.ic_floors_24,
        titleResId = R.string.nav_item_floor,
        arguments = listOf(navArgument(ARG_FLOOR_ID) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {
        fun routeForFloor(floorInput: FloorInput? = null): String {
            val route = when (floorInput) {
                null -> baseRoute()
                else -> String.format(ROUTE_FLOOR, floorInput.floorId)
            }
            //val route = String.format(ROUTE_RATE, payerId)
            if (LOG_NAVIGATION) Timber.tag(TAG).d("Floor -> routeForFloor: '%s'", route)
            return route
        }

        fun fromEntry(entry: NavBackStackEntry): FloorInput {
            val floorInput = FloorInput(
                UUID.fromString(entry.arguments?.getString(ARG_FLOOR_ID).orEmpty())
            )
            if (LOG_NAVIGATION) Timber.tag(TAG).d("Floor -> fromEntry: '%s'", floorInput)
            return floorInput
        }
    }

    data object Room : NavRoutes(
        route = String.format(ROUTE_ROOM, "$ARG_ROOM_ID={$ARG_ROOM_ID}"),
        iconPainterResId = R.drawable.ic_room_24,
        titleResId = R.string.nav_item_room,
        arguments = listOf(navArgument(ARG_ROOM_ID) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {
        fun routeForRoom(roomInput: RoomInput? = null): String {
            val route = when (roomInput) {
                null -> baseRoute()
                else -> String.format(ROUTE_ROOM, roomInput.roomId)
            }
            //val route = String.format(ROUTE_RATE, payerId)
            if (LOG_NAVIGATION) Timber.tag(TAG).d("Room -> routeForRoom: '%s'", route)
            return route
        }

        fun fromEntry(entry: NavBackStackEntry): RoomInput {
            val roomInput = RoomInput(
                UUID.fromString(entry.arguments?.getString(ARG_ROOM_ID).orEmpty())
            )
            if (LOG_NAVIGATION) Timber.tag(TAG).d("Room -> fromEntry: '%s'", roomInput)
            return roomInput
        }
    }

    data object HandOutConfirmation : NavRoutes(
        route = ROUTE_HAND_OUT_CONFIRMATION,
        iconPainterResId = R.drawable.ic_hand_map_24,
        titleResId = R.string.nav_item_territory_hand_out_confirmation
    ) {
        fun routeForHandOutConfirmation(): String {
            Timber.tag(TAG)
                .d("HandOutConfirmation -> routeForHandOutConfirmation: '%s'", this.route)
            return this.route
        }
    }

    data object ProcessConfirmation : NavRoutes(
        route = ROUTE_PROCESS_CONFIRMATION,
        iconImageVector = Icons.Outlined.ThumbUp,
        titleResId = R.string.nav_item_territory_at_work_confirmation
    ) {
        fun routeForProcessConfirmation(): String {
            if (LOG_NAVIGATION) Timber.tag(TAG)
                .d("ProcessConfirmation - routeProcessConfirmation: '%s'", this.route)
            return this.route
        }
    }

    data object MemberReport : NavRoutes(
        route = String.format(
            ROUTE_MEMBER_REPORT, "$ARG_MEMBER_REPORT_ID={$ARG_MEMBER_REPORT_ID}",
            "$ARG_TERRITORY_STREET_ID={$ARG_TERRITORY_STREET_ID}",
            "$ARG_HOUSE_ID={$ARG_HOUSE_ID}",
            "$ARG_ROOM_ID={$ARG_ROOM_ID}"
        ),
        iconPainterResId = R.drawable.ic_member_report_24,
        titleResId = R.string.nav_item_territory_member_report,
        arguments = listOf(navArgument(ARG_MEMBER_REPORT_ID) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        }, navArgument(ARG_TERRITORY_STREET_ID) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        }, navArgument(ARG_HOUSE_ID) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        }, navArgument(ARG_ROOM_ID) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {
        fun routeForMemberReport(memberReportInput: MemberReportInput): String {
            val route = String.format(
                ROUTE_MEMBER_REPORT,
                memberReportInput.territoryMemberReportId?.let { "$ARG_MEMBER_REPORT_ID=${it}" }
                    .orEmpty(),
                memberReportInput.territoryStreetId?.let { "$ARG_TERRITORY_STREET_ID=${it}" }
                    .orEmpty(),
                memberReportInput.houseId?.let { "$ARG_HOUSE_ID=${it}" }.orEmpty(),
                memberReportInput.roomId?.let { "$ARG_ROOM_ID=${it}" }.orEmpty(),
            )
            if (LOG_NAVIGATION) Timber.tag(TAG)
                .d("MemberReport -> routeForMemberReport: '%s'", route)
            return route
        }

        fun fromEntry(entry: NavBackStackEntry): MemberReportInput {
            val memberReportInput = MemberReportInput(
                territoryMemberReportId = entry.arguments?.getString(ARG_MEMBER_REPORT_ID)
                    ?.let { UUID.fromString(it) },
                territoryStreetId = entry.arguments?.getString(ARG_TERRITORY_STREET_ID)
                    ?.let { UUID.fromString(it) },
                houseId = entry.arguments?.getString(ARG_HOUSE_ID)?.let { UUID.fromString(it) },
                roomId = entry.arguments?.getString(ARG_ROOM_ID)?.let { UUID.fromString(it) }
            )
            if (LOG_NAVIGATION) Timber.tag(TAG)
                .d("MemberReport -> fromEntry: '%s'", memberReportInput)
            return memberReportInput
        }
    }

    data object ReportStreets : NavRoutes(
        route = String.format(ROUTE_REPORT_STREETS, "$ARG_TERRITORY_ID={$ARG_TERRITORY_ID}"),
        iconPainterResId = R.drawable.ic_territory_map_24,
        titleResId = R.string.nav_item_territory_report_streets,
        arguments = listOf(navArgument(ARG_TERRITORY_ID) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {
        fun routeForReportStreets(territoryInput: TerritoryInput? = null): String {
            val route = String.format(
                ROUTE_REPORT_STREETS,
                territoryInput?.let { "$ARG_TERRITORY_ID=${it.territoryId}" }.orEmpty()
            )
            if (LOG_NAVIGATION) Timber.tag(TAG)
                .d("ReportStreets -> routeForReportStreets: '%s'", route)
            return route
        }

        fun fromEntry(entry: NavBackStackEntry): TerritoryInput? {
            val territoryInput = entry.arguments?.getString(ARG_TERRITORY_ID)?.let {
                TerritoryInput(UUID.fromString(it))
            }
            if (LOG_NAVIGATION) Timber.tag(TAG)
                .d("ReportStreets -> fromEntry: '%s'", territoryInput)
            return territoryInput
        }
    }

    data object ReportHouses : NavRoutes(
        route = String.format(ROUTE_REPORT_HOUSES, "$ARG_TERRITORY_ID={$ARG_TERRITORY_ID}"),
        iconPainterResId = R.drawable.ic_report_house_24,
        titleResId = R.string.nav_item_territory_report_houses,
        arguments = listOf(navArgument(ARG_TERRITORY_ID) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {
        fun routeForReportHouses(territoryInput: TerritoryInput? = null): String {
            val route = String.format(
                ROUTE_REPORT_HOUSES,
                territoryInput?.let { "$ARG_TERRITORY_ID=${it.territoryId}" }.orEmpty()
            )
            if (LOG_NAVIGATION) Timber.tag(TAG)
                .d("ReportHouses -> routeForReportHouses: '%s'", route)
            return route
        }

        fun fromEntry(entry: NavBackStackEntry): TerritoryInput {
            val territoryInput = TerritoryInput(
                UUID.fromString(entry.arguments?.getString(ARG_TERRITORY_ID).orEmpty())
            )
            if (LOG_NAVIGATION) Timber.tag(TAG)
                .d("ReportHouses -> fromEntry: '%s'", territoryInput)
            return territoryInput
        }
    }

    data object ReportRooms : NavRoutes(
        route = String.format(ROUTE_REPORT_ROOMS, "$ARG_TERRITORY_ID={$ARG_TERRITORY_ID}"),
        iconPainterResId = R.drawable.ic_territory_map_24,
        titleResId = R.string.nav_item_territory_report_rooms,
        arguments = listOf(navArgument(ARG_TERRITORY_ID) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {
        fun routeForReportRooms(territoryInput: TerritoryInput? = null): String {
            val route = String.format(
                ROUTE_REPORT_ROOMS,
                territoryInput?.let { "$ARG_TERRITORY_ID=${it.territoryId}" }.orEmpty()
            )
            if (LOG_NAVIGATION) Timber.tag(TAG)
                .d("ReportRooms -> routeForReportRooms: '%s'", route)
            return route
        }

        fun fromEntry(entry: NavBackStackEntry): TerritoryInput {
            val territoryInput = TerritoryInput(
                UUID.fromString(entry.arguments?.getString(ARG_TERRITORY_ID).orEmpty())
            )
            if (LOG_NAVIGATION) Timber.tag(TAG)
                .d("ReportRooms -> fromEntry: '%s'", territoryInput)
            return territoryInput
        }
    }

    fun baseRoute() = this.route.substringBefore('/').substringBefore('?')

    companion object {
        fun authRoutes() = listOf(Signup, Login)
        fun bottomNavBarRoutes() = listOf(Dashboarding, Congregating, Territoring, Ministring)
        fun aggregationRoutes() = listOf(Geo, Housing)
        fun geoRoutes() =
            listOf(
                Geo,
                Region,
                RegionDistrict,
                Locality,
                LocalityDistrict,
                Microdistrict,
                Street,
                StreetLocalityDistrict,
                StreetMicrodistrict
            )

        fun congregationRoutes() = listOf(Congregation, Group, Member)
        fun territoryRoutes() = listOf(
            TerritoryCategory,
            Territory,
            TerritoryDetails,
            TerritoryStreet,
            TerritoryHouse,
            TerritoryEntrance,
            TerritoryFloor,
            TerritoryRoom,
            HandOutConfirmation,
            ProcessConfirmation
        )

        fun housingRoutes() = listOf(Housing, House, Entrance, Floor, Room)

        fun mainRouteByDestination(destination: String? = null) = when {
            destination == null -> Home.route  // navigate to DashboardingScreen()
            bottomNavBarRoutes().map { it.route }.contains(destination) -> Home.route
            congregationRoutes().map { it.route }.contains(destination) -> Graph.CONGREGATION
            geoRoutes().map { it.route }.contains(destination) -> Graph.GEO
            territoryRoutes().map { it.route }.contains(destination) -> Graph.TERRITORY
            housingRoutes().map { it.route }.contains(destination) -> Graph.HOUSING
            else -> Home.route
        }

        fun isShowBackButton(route: String?) = when (route) {
            Congregation.route -> true
            else -> false
        }

        // https://stackoverflow.com/questions/58351219/kotlin-get-declared-member-property-value
        // https://stackoverflow.com/questions/59743974/kotlin-reflection-get-instance-of-a-member-property
        fun titleByRoute(ctx: Context, route: String, vararg formatArgs: Any): String {
            val routes =
                bottomNavBarRoutes() + geoRoutes() + congregationRoutes() + territoryRoutes() + housingRoutes()
            return routes.find { it.route == route }?.titleResId?.let {
                ctx.getString(it, formatArgs)
            } ?: ""
            /*
            val subclasses: List<KClass<*>> = NavRoutes::class.sealedSubclasses
            val titleResId =
                subclasses.find {
                    it.members.find { member -> member.name == "route" }?.call() == route
                }?.members?.find { member -> member.name == "titleResId" }?.call() as? Int
            return titleResId?.let { context.getString(it) } ?: ""
             */
        }
    }
}
