package com.oborodulin.jwsuite.presentation.navigation

// https://betterprogramming.pub/jetpack-compose-clean-navigation-94b386f7a076
object MainDestinations {
    //createExercise/{exerciseId}/{workoutId}/?setNumber={setNumber}&repNumber={repNumber}
    //option arg /?navArg={navArg}
    const val ROUTE_HOME = "home"
    const val ROUTE_DASHBOARDING = "dashboarding"
    const val ROUTE_CONGREGATING = "congregating"
    const val ROUTE_TERRITORING = "territoring"
    const val ROUTE_MINISTRING = "ministring"

    const val ROUTE_SIGNUP = "signup"
    const val ROUTE_LOGIN = "login"
    const val ROUTE_GEO = "geo"
    const val ROUTE_HOUSING = "housing"

    //Settings:
    const val ROUTE_DASHBOARD_SETTINGS = "dashboardSettings"
    const val ROUTE_TERRITORY_SETTINGS = "territorySettings"

    //Data Management:
    const val ROUTE_DATA_MANAGEMENT = "dataManagement"

    //Database:
    const val ROUTE_DATABASE_SEND = "databaseSend"

    //GEO:
    const val ROUTE_COUNTRY = "country/?%s"
    const val ROUTE_REGION = "region/?%s"
    const val ROUTE_REGION_DISTRICT = "regionDistrict/?%s"
    const val ROUTE_LOCALITY = "locality/?%s"
    const val ROUTE_LOCALITY_DISTRICT = "localityDistrict/?%s"
    const val ROUTE_MICRODISTRICT = "microdistrict/?%s"
    const val ROUTE_STREET = "street/?%s"
    const val ROUTE_STREET_LOCALITY_DISTRICT = "streetLocalityDistrict/%s/?%s"
    const val ROUTE_STREET_MICRODISTRICT = "streetMicrodistrict/%s/?%s"

    //Congregates:
    const val ROUTE_CONGREGATION = "congregation/?%s"
    const val ROUTE_GROUP = "group/?%s"
    const val ROUTE_MEMBER = "member/?%s"
    const val ROUTE_MEMBER_ROLE = "memberRole/?%s"

    //Territories:
    const val ROUTE_TERRITORY_CATEGORY = "territoryCategory/?%s"
    const val ROUTE_HAND_OUT_CONFIRMATION = "handOutConfirmation"
    const val ROUTE_PROCESS_CONFIRMATION = "processConfirmation"
    const val ROUTE_TERRITORY = "territory/?%s"
    const val ROUTE_TERRITORY_DETAILS = "territoryDetails/%s"
    const val ROUTE_TERRITORY_STREET = "territoryStreet/%s/?%s"
    const val ROUTE_TERRITORY_HOUSE = "territoryHouse/%s/?%s"
    const val ROUTE_TERRITORY_ENTRANCE = "territoryEntrance/%s/?%s"
    const val ROUTE_TERRITORY_FLOOR = "territoryFloor/%s/?%s"
    const val ROUTE_TERRITORY_ROOM = "territoryRoom/%s/?%s"
    const val ROUTE_HOUSE = "house/?%s"
    const val ROUTE_ENTRANCE = "entrance/?%s"
    const val ROUTE_FLOOR = "floor/?%s"
    const val ROUTE_ROOM = "room/?%s"

    // Partial Process (Territory Report):
    const val ROUTE_MEMBER_REPORT = "memberReport/?%s&%s&%s&%s"
    const val ROUTE_REPORT_STREETS = "reportStreets/?%s"
    const val ROUTE_REPORT_HOUSES = "reportHouses/?%s"
    const val ROUTE_REPORT_ROOMS = "reportRooms/?%s"
}