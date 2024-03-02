package com.oborodulin.jwsuite.data.local.db.populate

import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.GroupEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.role.RoleEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.role.RoleTransferObjectEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.transfer.TransferObjectEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoCountryEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityDistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoMicrodistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionDistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.HouseEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryCategoryEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryMemberCrossRefEntity
import com.oborodulin.jwsuite.domain.types.MemberRoleType
import com.oborodulin.jwsuite.domain.types.TransferObjectType

interface Populable {
    // App Settings:
    suspend fun insertDefAppSettings()

    // GEO:
    suspend fun insertDefCountry(country: GeoCountryEntity): GeoCountryEntity
    suspend fun insertDefRegion(region: GeoRegionEntity): GeoRegionEntity
    suspend fun insertDefRegionDistrict(regionDistrict: GeoRegionDistrictEntity): GeoRegionDistrictEntity
    suspend fun insertDeftLocality(locality: GeoLocalityEntity): GeoLocalityEntity
    suspend fun insertDeftLocalityDistrict(localityDistrict: GeoLocalityDistrictEntity): GeoLocalityDistrictEntity
    suspend fun insertDefMicrodistrict(microdistrict: GeoMicrodistrictEntity): GeoMicrodistrictEntity
    suspend fun insertDefStreet(street: GeoStreetEntity): GeoStreetEntity
    suspend fun insertDefStreetDistrict(
        street: GeoStreetEntity,
        localityDistrict: GeoLocalityDistrictEntity,
        microdistrict: GeoMicrodistrictEntity? = null
    )

    suspend fun insertDefHouse(house: HouseEntity): HouseEntity

    // CONGREGATION:
    suspend fun insertDefMemberRole(roleType: MemberRoleType): RoleEntity
    suspend fun insertDefGroup(groupNum: Int, congregation: CongregationEntity): GroupEntity
    suspend fun insertDefMember(
        memberNumInGroup: Int,
        congregation: CongregationEntity,
        group: GroupEntity,
        roles: List<RoleEntity> = emptyList()
    ): MemberEntity

    suspend fun insertDefAdminMember(roles: List<RoleEntity> = emptyList()): MemberEntity

    // TERRITORIES:
    suspend fun insertDefTerritoryCategory(territoryCategory: TerritoryCategoryEntity): TerritoryCategoryEntity
    suspend fun insertDefTerritories(
        territoryCategory: TerritoryCategoryEntity,
        congregation: CongregationEntity,
        locality: GeoLocalityEntity, localityDistrict: GeoLocalityDistrictEntity,
        microdistrict: GeoMicrodistrictEntity
    ): MutableList<TerritoryEntity>

    suspend fun insertDefTerritoryMember(
        territory: TerritoryEntity,
        member: MemberEntity
    ): TerritoryMemberCrossRefEntity

    suspend fun deliveryDefTerritoryMember(
        territory: TerritoryEntity,
        territoryMember: TerritoryMemberCrossRefEntity
    )

    suspend fun insertDefTerritoryStreet(
        territory: TerritoryEntity, street: GeoStreetEntity,
        isEven: Boolean? = null, isPrivateSector: Boolean? = null
    )

    // TRANSFERS:
    suspend fun insertDefTransferObject(
        transferObjectType: TransferObjectType
    ): TransferObjectEntity

    suspend fun insertDefRoleTransferObject(
        role: RoleEntity, transferObject: TransferObjectEntity,
        isPersonalData: Boolean
    ): RoleTransferObjectEntity

    // Init database:
    suspend fun prePopulateDb()
    suspend fun init()
}