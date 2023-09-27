package com.oborodulin.jwsuite.presentation.navigation

object MainDestinations {
    //createExercise/{exerciseId}/{workoutId}?setNumber={setNumber}&repNumber={repNumber}
    const val ROUTE_HOME = "home"
    const val ROUTE_DASHBOARDING = "dashboarding"
    const val ROUTE_CONGREGATING = "congregating"
    const val ROUTE_TERRITORING = "territoring"
    const val ROUTE_MINISTRING = "ministring"

    const val ROUTE_GEO = "geo"
    const val ROUTE_HOUSES = "houses"

    //GEO:
    const val ROUTE_REGION = "region?%s"
    const val ROUTE_REGION_DISTRICT = "regionDistrict?%s"
    const val ROUTE_LOCALITY = "locality?%s"
    const val ROUTE_LOCALITY_DISTRICT = "localityDistrict?%s"
    const val ROUTE_MICRODISTRICT = "microdistrict?%s"
    const val ROUTE_STREET = "street?%s"
    const val ROUTE_STREET_LOCALITY_DISTRICT = "streetLocalityDistrict/%s"
    const val ROUTE_STREET_MICRODISTRICT = "streetMicrodistrict/%s"

    //Congregates:
    const val ROUTE_CONGREGATION = "congregation?%s"
    const val ROUTE_GROUP = "group?%s"
    const val ROUTE_MEMBER = "member?%s"

    //Territories:
    const val ROUTE_TERRITORY_CATEGORY = "territoryCategory?%s"
    const val ROUTE_HAND_OUT_TERRITORIES_CONFIRMATION = "handOutTerritoriesConfirmation"
    const val ROUTE_AT_WORK_TERRITORIES_CONFIRMATION = "atWorkTerritoriesConfirmation"
    const val ROUTE_TERRITORY = "territory?%s"
    const val ROUTE_TERRITORY_DETAILS = "territoryDetails/%s"
    const val ROUTE_TERRITORY_STREET = "territoryStreet/%s?%s"
    const val ROUTE_TERRITORY_HOUSE = "territoryHouse/%s?%s"
    const val ROUTE_TERRITORY_ENTRANCE = "territoryEntrance/%s?%s"
    const val ROUTE_TERRITORY_FLOOR = "territoryFloor/%s?%s"
    const val ROUTE_TERRITORY_ROOM = "territoryRoom/%s?%s"
    const val ROUTE_HOUSE = "house?%s"
    const val ROUTE_ENTRANCE = "entrance?%s"
    const val ROUTE_FLOOR = "floor?%s"
    const val ROUTE_ROOM = "room?%s"
}