package com.oborodulin.jwsuite.domain.types

// resource: domain/building_types.xml
enum class BuildingType {
    // Accommodation
    APARTMENTS,
    HOUSE,
    DORMITORY,
    HOTEL,

    // Commercial
    RETAIL,
    SUPERMARKET,
    OFFICE,
    INDUSTRIAL, //non-residential

    // Civic/amenity
    PUBLIC,
    HOSPITAL,
    KINDERGARTEN,
    SCHOOL,
    UNIVERSITY
}