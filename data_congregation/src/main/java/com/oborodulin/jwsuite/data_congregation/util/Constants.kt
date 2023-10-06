package com.oborodulin.jwsuite.data_congregation.util

/**
 * Created by o.borodulin on 06.October.2023
 */
object Constants {
    // GEO Prefix:
    // Regions:
    const val PX_CONGREGATION_REGION = "cr_"

    // Region Districts:
    const val PX_CONGREGATION_REGION_DISTRICT = "crd_"

    // Localities:
    const val PX_CONGREGATION_LOCALITY = "cl_"

    // Locality Districts:

    // Microdistricts:

    // Congregation:
    const val PX_GROUP_CONGREGATION = "gc_"

    // Group:
    const val PX_GROUP_REGION = PX_GROUP_CONGREGATION + PX_CONGREGATION_REGION
    const val PX_GROUP_REGION_DISTRICT = PX_GROUP_CONGREGATION + PX_CONGREGATION_REGION_DISTRICT
    const val PX_GROUP_LOCALITY = PX_GROUP_CONGREGATION + PX_CONGREGATION_LOCALITY
}