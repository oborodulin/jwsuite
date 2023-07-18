package com.oborodulin.jwsuite.presentation.navigation

object MainDestinations {
    //createExercise/{exerciseId}/{workoutId}?setNumber={setNumber}&repNumber={repNumber}
    const val ROUTE_HOME = "home"
    const val ROUTE_DASHBOARDING = "dashboarding"
    const val ROUTE_CONGREGATING = "congregating"
    const val ROUTE_TERRITORING = "territoring"
    const val ROUTE_MINISTRING = "ministring"

    //GEO:
    const val ROUTE_REGION = "region/%s"
    const val ROUTE_REGION_DISTRICT = "regionDistrict/%s"
    const val ROUTE_LOCALITY = "locality/%s"

    //Congregates:
    const val ROUTE_CONGREGATION = "congregation/%s"
    const val ROUTE_GROUP = "group/%s"
    const val ROUTE_MEMBER = "member/%s"

    //Territories:
    const val ROUTE_TERRITORY_CATEGORY = "territoryCategory/%s"
    const val ROUTE_TERRITORY = "territory/%s"
}