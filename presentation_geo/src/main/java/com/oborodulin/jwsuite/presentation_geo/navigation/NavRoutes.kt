package com.oborodulin.jwsuite.presentation_geo.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.oborodulin.jwsuite.presentation_geo.R
import com.oborodulin.jwsuite.presentation_geo.navigation.MainDestinations.ROUTE_LOCALITY
import com.oborodulin.jwsuite.presentation_geo.navigation.MainDestinations.ROUTE_LOCALITY_DISTRICT
import com.oborodulin.jwsuite.presentation_geo.navigation.MainDestinations.ROUTE_MICRODISTRICT
import com.oborodulin.jwsuite.presentation_geo.navigation.MainDestinations.ROUTE_REGION
import com.oborodulin.jwsuite.presentation_geo.navigation.MainDestinations.ROUTE_REGION_DISTRICT
import com.oborodulin.jwsuite.presentation_geo.navigation.MainDestinations.ROUTE_STREET
import com.oborodulin.jwsuite.presentation_geo.navigation.NavigationInput.LocalityDistrictInput
import com.oborodulin.jwsuite.presentation_geo.navigation.NavigationInput.LocalityInput
import com.oborodulin.jwsuite.presentation_geo.navigation.NavigationInput.MicrodistrictInput
import com.oborodulin.jwsuite.presentation_geo.navigation.NavigationInput.RegionDistrictInput
import com.oborodulin.jwsuite.presentation_geo.navigation.NavigationInput.RegionInput
import com.oborodulin.jwsuite.presentation_geo.navigation.NavigationInput.StreetInput
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

private const val ARG_REGION_ID = "regionId"
private const val ARG_REGION_DISTRICT_ID = "regionDistrictId"
private const val ARG_LOCALITY_ID = "localityId"
private const val ARG_LOCALITY_DISTRICT_ID = "localityDistrictId"
private const val ARG_MICRODISTRICT_ID = "microdistrictId"
private const val ARG_STREET_ID = "streetId"

/**
 * Created by oborodulin on 12.December.2021
 */
sealed class NavRoutes constructor(
    val route: String,
    @DrawableRes open val iconResId: Int,
    @StringRes open val titleResId: Int,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    data object Region : NavRoutes(
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

    data object RegionDistrict : NavRoutes(
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

    data object Locality : NavRoutes(
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

    data object LocalityDistrict : NavRoutes(
        String.format(ROUTE_LOCALITY_DISTRICT, "{$ARG_LOCALITY_DISTRICT_ID}"),
        R.drawable.ic_locality_district_24,
        R.string.nav_item_locality_district,
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
        String.format(ROUTE_MICRODISTRICT, "{$ARG_MICRODISTRICT_ID}"),
        R.drawable.ic_microdistrict_24,
        R.string.nav_item_microdistrict,
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
        String.format(ROUTE_STREET, "{$ARG_STREET_ID}"),
        R.drawable.ic_street_sign_24,
        R.string.nav_item_street,
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

    fun baseRoute() = this.route.substringBefore('/')
}
