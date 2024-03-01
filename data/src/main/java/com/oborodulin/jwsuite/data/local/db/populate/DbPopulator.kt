package com.oborodulin.jwsuite.data.local.db.populate

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import androidx.sqlite.db.SupportSQLiteDatabase
import com.oborodulin.home.common.util.Mapper
import com.oborodulin.jwsuite.data.local.db.JwSuiteDatabase
import com.oborodulin.jwsuite.data_appsetting.local.db.entities.AppSettingEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.GroupEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberCongregationCrossRefEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberMovementEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberRoleEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.role.RoleEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.role.RoleTransferObjectEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.transfer.TransferObjectEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoCountryEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoCountryTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityDistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityDistrictTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoMicrodistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoMicrodistrictTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionDistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionDistrictTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetDistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetTlEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.CongregationTerritoryCrossRefEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.HouseEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryCategoryEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryMemberCrossRefEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryStreetEntity
import com.oborodulin.jwsuite.domain.types.MemberRoleType
import com.oborodulin.jwsuite.domain.types.MemberType
import com.oborodulin.jwsuite.domain.types.TransferObjectType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import timber.log.Timber
import java.time.OffsetDateTime

private const val TAG = "Data.DbPopulator"

class DbPopulator(
    private val db: SupportSQLiteDatabase,
    private val ctx: Context,
    private val jsonLogger: Json? = null
) : Populable {
    override suspend fun insertDefAppSettings() {
        // Lang
        val lang = AppSettingEntity.langParam()
        db.insert(
            AppSettingEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
            Mapper.toContentValues(lang)
        )
        // Currency Code
        val currencyCode = AppSettingEntity.currencyCodeParam()
        db.insert(
            AppSettingEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
            Mapper.toContentValues(currencyCode)
        )
        // All Items
        val allItems = AppSettingEntity.allItemsParam(ctx)
        db.insert(
            AppSettingEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
            Mapper.toContentValues(allItems)
        )
        // Day Mu
        val dayMu = AppSettingEntity.dayMuParam(ctx)
        db.insert(
            AppSettingEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
            Mapper.toContentValues(dayMu)
        )
        // Month Mu
        val monthMu = AppSettingEntity.monthMuParam(ctx)
        db.insert(
            AppSettingEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
            Mapper.toContentValues(monthMu)
        )
        // Year Mu
        val yearMu = AppSettingEntity.yearMuParam(ctx)
        db.insert(
            AppSettingEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
            Mapper.toContentValues(monthMu)
        )
        // Person Num MU
        val personNumMu = AppSettingEntity.personNumMuParam(ctx)
        db.insert(
            AppSettingEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
            Mapper.toContentValues(personNumMu)
        )
        // Territory Business Mark
        val territoryBusinessMark = AppSettingEntity.territoryBusinessMarkParam(ctx)
        db.insert(
            AppSettingEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
            Mapper.toContentValues(territoryBusinessMark)
        )
        // Territory Processing Period
        val territoryProcessingPeriod = AppSettingEntity.territoryProcessingPeriodParam(ctx)
        db.insert(
            AppSettingEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
            Mapper.toContentValues(territoryProcessingPeriod)
        )
        // Territory At Hand Period
        val territoryAtHandPeriod = AppSettingEntity.territoryAtHandPeriodParam(ctx)
        db.insert(
            AppSettingEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
            Mapper.toContentValues(territoryAtHandPeriod)
        )
        // Territory Rooms Limit
        val territoryRoomsLimit = AppSettingEntity.territoryRoomsLimitParam(ctx)
        db.insert(
            AppSettingEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
            Mapper.toContentValues(territoryRoomsLimit)
        )
        // Territory Max Rooms
        val territoryMaxRoomsParam = AppSettingEntity.territoryMaxRoomsParam(ctx)
        db.insert(
            AppSettingEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
            Mapper.toContentValues(territoryMaxRoomsParam)
        )
        // Territory Idle Period
        val territoryIdlePeriod = AppSettingEntity.territoryIdlePeriodParam(ctx)
        db.insert(
            AppSettingEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
            Mapper.toContentValues(territoryIdlePeriod)
        )
        Timber.tag(TAG).i("Default app parameters imported")
        jsonLogger?.let {
            Timber.tag(TAG)
                .i(
                    ": {\"params\": {\"lang\": {%s}, \"currencyCode\": {%s}, \"allItems\": {%s}, \"dayMu\": {%s}, \"monthMu\": {%s}, \"yearMu\": {%s}, \"personNumMu\": {%s}, \"territoryBusinessMark\": {%s}, \"territoryProcessingPeriod\": {%s}, \"territoryAtHandPeriod\": {%s}, \"territoryRoomsLimit\": {%s}, \"territoryMaxRoomsParam\": {%s}, \"territoryIdlePeriod\": {%s}}",
                    it.encodeToString(lang),
                    it.encodeToString(currencyCode),
                    it.encodeToString(allItems),
                    it.encodeToString(dayMu),
                    it.encodeToString(monthMu),
                    it.encodeToString(yearMu),
                    it.encodeToString(personNumMu),
                    it.encodeToString(territoryBusinessMark),
                    it.encodeToString(territoryProcessingPeriod),
                    it.encodeToString(territoryAtHandPeriod),
                    it.encodeToString(territoryRoomsLimit),
                    it.encodeToString(territoryMaxRoomsParam),
                    it.encodeToString(territoryIdlePeriod)
                )
        }
    }

    // GEO:
    override suspend fun insertDefCountry(country: GeoCountryEntity): GeoCountryEntity {
        val textContent =
            GeoCountryTlEntity.countryTl(ctx, country.countryCode, country.countryId)
        db.insert(
            GeoCountryEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
            Mapper.toContentValues(country)
        )
        db.insert(
            GeoCountryTlEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
            Mapper.toContentValues(textContent)
        )
        Timber.tag(TAG).i("GEO: Default country imported")
        jsonLogger?.let {
            Timber.tag(TAG).i(
                ": {\"country\": {%s}, \"tl\": {%s}}",
                it.encodeToString(country),
                it.encodeToString(textContent)
            )
        }
        return country
    }

    override suspend fun insertDefRegion(region: GeoRegionEntity): GeoRegionEntity {
        val textContent =
            GeoRegionTlEntity.regionTl(ctx, region.regionCode, region.regionId)
        db.insert(
            GeoRegionEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
            Mapper.toContentValues(region)
        )
        db.insert(
            GeoRegionTlEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
            Mapper.toContentValues(textContent)
        )
        Timber.tag(TAG).i("GEO: Default region imported")
        jsonLogger?.let {
            Timber.tag(TAG).i(
                ": {\"region\": {%s}, \"tl\": {%s}}",
                it.encodeToString(region),
                it.encodeToString(textContent)
            )
        }
        return region
    }

    override suspend fun insertDefRegionDistrict(regionDistrict: GeoRegionDistrictEntity): GeoRegionDistrictEntity {
        val textContent =
            GeoRegionDistrictTlEntity.regionDistrictTl(
                ctx,
                regionDistrict.regDistrictShortName, regionDistrict.regionDistrictId
            )
        db.insert(
            GeoRegionDistrictEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
            Mapper.toContentValues(regionDistrict)
        )
        db.insert(
            GeoRegionDistrictTlEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
            Mapper.toContentValues(textContent)
        )
        Timber.tag(TAG).i("GEO: Default region District imported")
        jsonLogger?.let {
            Timber.tag(TAG).i(
                ": {\"regionDistrict\": {%s}, \"tl\": {%s}}",
                it.encodeToString(regionDistrict), it.encodeToString(textContent)
            )
        }
        return regionDistrict
    }

    override suspend fun insertDeftLocality(locality: GeoLocalityEntity):
            GeoLocalityEntity {
        val textContent =
            GeoLocalityTlEntity.localityTl(ctx, locality.localityCode, locality.localityId)
        db.insert(
            GeoLocalityEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
            Mapper.toContentValues(locality)
        )
        db.insert(
            GeoLocalityTlEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
            Mapper.toContentValues(textContent)
        )
        Timber.tag(TAG).i("GEO: Default locality imported")
        jsonLogger?.let {
            Timber.tag(TAG).i(
                ": {\"locality\": {%s}, \"tl\": {%s}}",
                it.encodeToString(locality), it.encodeToString(textContent)
            )
        }
        return locality
    }

    override suspend fun insertDeftLocalityDistrict(
        localityDistrict: GeoLocalityDistrictEntity
    ):
            GeoLocalityDistrictEntity {
        val textContent =
            GeoLocalityDistrictTlEntity.localityDistrictTl(
                ctx,
                localityDistrict.locDistrictShortName, localityDistrict.localityDistrictId
            )
        db.insert(
            GeoLocalityDistrictEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
            Mapper.toContentValues(localityDistrict)
        )
        db.insert(
            GeoLocalityDistrictTlEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
            Mapper.toContentValues(textContent)
        )
        Timber.tag(TAG).i("GEO: Default locality district imported")
        jsonLogger?.let {
            Timber.tag(TAG).i(
                ": {\"localityDistrict\": {%s}, \"tl\": {%s}}",
                it.encodeToString(localityDistrict), it.encodeToString(textContent)
            )
        }
        return localityDistrict
    }

    override suspend fun insertDefMicrodistrict(
        microdistrict: GeoMicrodistrictEntity
    ): GeoMicrodistrictEntity {
        val textContent = GeoMicrodistrictTlEntity.microdistrictTl(
            ctx,
            microdistrict.microdistrictShortName, microdistrict.microdistrictId
        )
        db.insert(
            GeoMicrodistrictEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
            Mapper.toContentValues(microdistrict)
        )
        db.insert(
            GeoMicrodistrictTlEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
            Mapper.toContentValues(textContent)
        )
        Timber.tag(TAG).i("GEO: Default locality microdistrict imported")
        jsonLogger?.let {
            Timber.tag(TAG).i(
                ": {\"microdistrict\": {%s}, \"tl\": {%s}}",
                it.encodeToString(microdistrict), it.encodeToString(textContent)
            )
        }
        return microdistrict
    }

    override suspend fun insertDefStreet(street: GeoStreetEntity): GeoStreetEntity {
        val textContent = GeoStreetTlEntity.streetTl(
            ctx, street.streetHashCode, street.streetId
        )
        db.insert(
            GeoStreetEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
            Mapper.toContentValues(street)
        )
        db.insert(
            GeoStreetTlEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
            Mapper.toContentValues(textContent)
        )
        Timber.tag(TAG).i("GEO: Default street imported")
        jsonLogger?.let {
            Timber.tag(TAG).i(
                ": {\"street\": {%s}, \"tl\": {%s}}",
                it.encodeToString(street), it.encodeToString(textContent)
            )
        }
        return street
    }

    override suspend fun insertDefStreetDistrict(
        street: GeoStreetEntity,
        localityDistrict: GeoLocalityDistrictEntity,
        microdistrict: GeoMicrodistrictEntity?
    ) {
        val streetDistrict = GeoStreetDistrictEntity.defaultDistrictStreet(
            streetId = street.streetId,
            localityDistrictId = localityDistrict.localityDistrictId,
            microdistrictId = microdistrict?.microdistrictId
        )
        db.insert(
            GeoStreetDistrictEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
            Mapper.toContentValues(streetDistrict)
        )
        Timber.tag(TAG).i("GEO: Default street district imported")
        jsonLogger?.let { Timber.tag(TAG).i(": {%s}", it.encodeToString(streetDistrict)) }
    }

    override suspend fun insertDefHouse(house: HouseEntity): HouseEntity {
        db.insert(
            HouseEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
            Mapper.toContentValues(house)
        )
        Timber.tag(TAG).i("GEO: Default house imported")
        jsonLogger?.let { Timber.tag(TAG).i(": {%s}", it.encodeToString(house)) }
        return house
    }

    // CONGREGATION:
    override suspend fun insertDefMemberRole(roleType: MemberRoleType):
            RoleEntity {
        val role = when (roleType) {
            MemberRoleType.ADMIN -> RoleEntity.adminRole(ctx)
            MemberRoleType.USER -> RoleEntity.userRole(ctx)
            MemberRoleType.TERRITORIES -> RoleEntity.territoriesRole(ctx)
            MemberRoleType.BILLS -> RoleEntity.billsRole(ctx)
            MemberRoleType.REPORTS -> RoleEntity.reportsRole(ctx)
        }
        db.insert(
            RoleEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
            Mapper.toContentValues(role)
        )
        Timber.tag(TAG).i("Default member role imported")
        jsonLogger?.let {
            Timber.tag(TAG).i(": {\"role\": {%s}}", it.encodeToString(role))
        }
        return role
    }

    override suspend fun insertDefGroup(
        groupNum: Int, congregation: CongregationEntity
    ): GroupEntity {
        val group = when (groupNum) {
            1 -> GroupEntity.group1(ctx, congregation.congregationId)
            2 -> GroupEntity.group2(ctx, congregation.congregationId)
            3 -> GroupEntity.group3(ctx, congregation.congregationId)
            4 -> GroupEntity.group4(ctx, congregation.congregationId)
            5 -> GroupEntity.group5(ctx, congregation.congregationId)
            6 -> GroupEntity.group6(ctx, congregation.congregationId)
            7 -> GroupEntity.group7(ctx, congregation.congregationId)
            8 -> GroupEntity.group8(ctx, congregation.congregationId)
            9 -> GroupEntity.group9(ctx, congregation.congregationId)
            10 -> GroupEntity.group10(ctx, congregation.congregationId)
            11 -> GroupEntity.group11(ctx, congregation.congregationId)
            12 -> GroupEntity.group12(ctx, congregation.congregationId)
            else -> null
        }
        group?.let {
            db.insert(
                GroupEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(it)
            )
            Timber.tag(TAG).i("CONGREGATION: Default group imported")
            jsonLogger?.let { logger ->
                Timber.tag(TAG).i(": {\"group\": {%s}}", logger.encodeToString(it))
            }
        }
        return group!!
    }

    override suspend fun insertDefMember(
        memberNumInGroup: Int,
        congregation: CongregationEntity,
        group: GroupEntity,
        roles: List<RoleEntity>
    ): MemberEntity {
        val member = when (congregation.congregationNum) {
            "1" -> when (group.groupNum) {
                1 -> when (memberNumInGroup) {
                    1 -> MemberEntity.ivanovMember11(ctx, group.groupId)
                    2 -> MemberEntity.petrovMember12(ctx, group.groupId)
                    else -> null
                }

                2 -> when (memberNumInGroup) {
                    1 -> MemberEntity.sidorovMember21(ctx, group.groupId)
                    else -> null
                }

                else -> null
            }

            "2" -> when (group.groupNum) {
                1 -> when (memberNumInGroup) {
                    1 -> MemberEntity.tarasovaMember11(ctx, group.groupId)
                    2 -> MemberEntity.shevchukMember12(ctx, group.groupId)
                    else -> null
                }

                2 -> when (memberNumInGroup) {
                    1 -> MemberEntity.matveychukMember21(ctx, group.groupId)
                    else -> null
                }

                else -> null
            }

            else -> null
        }
        member?.let {
            db.insert(
                MemberEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(it)
            )
            val memberCongregation = MemberCongregationCrossRefEntity.defaultCongregationMember(
                congregationId = congregation.congregationId, memberId = it.memberId
            )
            db.insert(
                MemberCongregationCrossRefEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(memberCongregation)
            )
            val memberMovement = MemberMovementEntity.defaultMemberMovement(
                memberId = it.memberId, memberType = MemberType.PREACHER
            )
            db.insert(
                MemberMovementEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(memberMovement)
            )
            Timber.tag(TAG).i("CONGREGATION: Default member imported")
            jsonLogger?.let { logger ->
                Timber.tag(TAG).i(
                    ": {\"member\": {%s}, \"memberCongregation\": {%s}, \"memberMovement\": {%s}, \"memberRoles\": [",
                    logger.encodeToString(member),
                    logger.encodeToString(memberCongregation),
                    logger.encodeToString(memberMovement)
                )
            }
            roles.forEach { role ->
                val memberRole = MemberRoleEntity.defaultMemberRole(
                    memberId = it.memberId, roleId = role.roleId
                )
                db.insert(
                    MemberRoleEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                    Mapper.toContentValues(memberRole)
                )
                jsonLogger?.let { logger ->
                    Timber.tag(TAG).i("{%s},", logger.encodeToString(memberRole))
                }
            }
            Timber.tag(TAG).i("]}")
        }
        return member!!
    }

    override suspend fun insertDefAdminMember(
        congregation: CongregationEntity,
        roles: List<RoleEntity>
    ): MemberEntity {
        val member = MemberEntity.adminMember(ctx)
        db.insert(
            MemberEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
            Mapper.toContentValues(member)
        )
        val memberCongregation = MemberCongregationCrossRefEntity.defaultCongregationMember(
            congregationId = congregation.congregationId, memberId = member.memberId
        )
        db.insert(
            MemberCongregationCrossRefEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
            Mapper.toContentValues(memberCongregation)
        )
        val memberMovement = MemberMovementEntity.defaultMemberMovement(
            memberId = member.memberId, memberType = MemberType.SERVICE
        )
        db.insert(
            MemberMovementEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
            Mapper.toContentValues(memberMovement)
        )
        Timber.tag(TAG).i("CONGREGATION: Default Administrator imported")
        jsonLogger?.let { logger ->
            Timber.tag(TAG).i(
                ": {\"member\": {%s}, \"memberCongregation\": {%s}, \"memberMovement\": {%s}, \"memberRoles\": [",
                logger.encodeToString(member),
                logger.encodeToString(memberCongregation),
                logger.encodeToString(memberMovement)
            )
        }
        roles.forEach { role ->
            val memberRole = MemberRoleEntity.defaultMemberRole(
                memberId = member.memberId, roleId = role.roleId
            )
            db.insert(
                MemberRoleEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(memberRole)
            )
            jsonLogger?.let { logger ->
                Timber.tag(TAG).i("{%s},", logger.encodeToString(memberRole))
            }
        }
        Timber.tag(TAG).i("]}")
        return member
    }

    // TERRITORIES:
    override suspend fun insertDefTerritoryCategory(
        territoryCategory: TerritoryCategoryEntity
    ): TerritoryCategoryEntity {
        db.insert(
            TerritoryCategoryEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
            Mapper.toContentValues(territoryCategory)
        )
        Timber.tag(TAG).i("TERRITORY: Default territory category imported")
        jsonLogger?.let { Timber.tag(TAG).i(": {%s}", it.encodeToString(territoryCategory)) }
        return territoryCategory
    }

    override suspend fun insertDefTerritories(
        territoryCategory: TerritoryCategoryEntity,
        congregation: CongregationEntity,
        locality: GeoLocalityEntity, localityDistrict: GeoLocalityDistrictEntity,
        microdistrict: GeoMicrodistrictEntity
    ): MutableList<TerritoryEntity> {
        val territories = mutableListOf<TerritoryEntity>()
        for (num in 1..144) {
            val territory = TerritoryEntity.defaultTerritory(
                territoryCategoryId = territoryCategory.territoryCategoryId,
                congregationId = congregation.congregationId,
                localityId = when (num % 2) {
                    0 -> congregation.cLocalitiesId
                    else -> locality.localityId
                },
                localityDistrictId = when (num % 4) {
                    0 -> localityDistrict.localityDistrictId
                    else -> null
                },
                microdistrictId = when (num % 6) {
                    0 -> microdistrict.microdistrictId
                    else -> null
                },
                isBusiness = num % 8 == 0,
                //isInPerimeter = num % 10 == 0,
                isGroupMinistry = num % 20 == 0,
                territoryNum = num
            )
            db.insert(
                TerritoryEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(territory)
            )
            val congregationTerritory =
                CongregationTerritoryCrossRefEntity.defaultCongregationTerritory(
                    congregationId = congregation.congregationId,
                    territoryId = territory.territoryId
                )
            db.insert(
                CongregationTerritoryCrossRefEntity.TABLE_NAME,
                SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(congregationTerritory)
            )
            territories.add(territory)
        }
        Timber.tag(TAG).i("TERRITORIES: Default territories imported")
        jsonLogger?.let {
            Timber.tag(TAG).i(
                ": {\"territories\": {\"count\": %s, \"congregation\": {%s}}}",
                territories.size, it.encodeToString(congregation)
            )
        }
        return territories
    }

    override suspend fun insertDefTerritoryMember(
        territory: TerritoryEntity, member: MemberEntity
    ): TerritoryMemberCrossRefEntity {
        val territoryMember = TerritoryMemberCrossRefEntity.defaultTerritoryMember(
            territoryId = territory.territoryId, memberId = member.memberId,
        )
        db.insert(
            TerritoryMemberCrossRefEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
            Mapper.toContentValues(territoryMember)
        )
        val handOutTerritory = territory.copy(isProcessed = false)
        db.update(
            TerritoryEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
            Mapper.toContentValues(handOutTerritory), "territoryId = ?",
            Array(1) { handOutTerritory.territoryId.toString() }
        )
        Timber.tag(TAG).i("TERRITORY: Default territory member imported")
        jsonLogger?.let { Timber.tag(TAG).i(": {%s}", it.encodeToString(territoryMember)) }
        Timber.tag(TAG).i("TERRITORY: Default hand out territory imported")
        jsonLogger?.let { Timber.tag(TAG).i(": {%s}", it.encodeToString(handOutTerritory)) }
        return territoryMember
    }

    override suspend fun deliveryDefTerritoryMember(
        territory: TerritoryEntity,
        territoryMember: TerritoryMemberCrossRefEntity
    ) {
        val deliveryTerritoryMember = territoryMember.copy(deliveryDate = OffsetDateTime.now())
        db.update(
            TerritoryMemberCrossRefEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
            Mapper.toContentValues(deliveryTerritoryMember), "territoryMemberId = ?",
            Array(1) { territoryMember.territoryMemberId.toString() }
        )
        val idleTerritory = territory.copy(isProcessed = true)
        db.update(
            TerritoryEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
            Mapper.toContentValues(idleTerritory), "territoryId = ?",
            Array(1) { idleTerritory.territoryId.toString() }
        )
        Timber.tag(TAG).i("TERRITORY: Delivery imported territory member")
        jsonLogger?.let {
            Timber.tag(TAG).i(": {%s}", it.encodeToString(deliveryTerritoryMember))
        }
        Timber.tag(TAG).i("TERRITORY: Delivery imported territory")
        jsonLogger?.let { Timber.tag(TAG).i(": {%s}", it.encodeToString(idleTerritory)) }
    }

    override suspend fun insertDefTerritoryStreet(
        territory: TerritoryEntity, street: GeoStreetEntity,
        isEven: Boolean?, isPrivateSector: Boolean?
    ) {
        val territoryStreet = TerritoryStreetEntity.privateSectorTerritoryStreet(
            territoryId = territory.territoryId, streetId = street.streetId, isEven = isEven
        )
        db.insert(
            TerritoryStreetEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
            Mapper.toContentValues(territoryStreet)
        )
        Timber.tag(TAG).i("TERRITORY: Default territory street imported")
        jsonLogger?.let { Timber.tag(TAG).i(": {%s}", it.encodeToString(territoryStreet)) }
    }

    // TRANSFERS:
    override suspend fun insertDefTransferObject(
        transferObjectType: TransferObjectType
    ): TransferObjectEntity {
        val transferObject = when (transferObjectType) {
            TransferObjectType.ALL -> TransferObjectEntity.allTransferObject(ctx)
            TransferObjectType.MEMBERS -> TransferObjectEntity.membersTransferObject(ctx)
            TransferObjectType.TERRITORIES -> TransferObjectEntity.territoriesTransferObject(
                ctx
            )

            TransferObjectType.TERRITORY_REPORT -> TransferObjectEntity.territoryReportTransferObject(
                ctx
            )

            TransferObjectType.BILLS -> TransferObjectEntity.billsTransferObject(ctx)
            TransferObjectType.REPORTS -> TransferObjectEntity.reportsTransferObject(ctx)
        }
        db.insert(
            TransferObjectEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
            Mapper.toContentValues(transferObject)
        )
        Timber.tag(TAG).i("Default transfer object imported")
        jsonLogger?.let {
            Timber.tag(TAG).i(": {\"transferObject\": {%s}}", it.encodeToString(transferObject))
        }
        return transferObject
    }

    override suspend fun insertDefRoleTransferObject(
        role: RoleEntity, transferObject: TransferObjectEntity,
        isPersonalData: Boolean
    ): RoleTransferObjectEntity {
        val roleTransferObject = RoleTransferObjectEntity.defaultRoleTransferObject(
            roleId = role.roleId, transferObjectId = transferObject.transferObjectId,
            isPersonalData = isPersonalData
        )
        db.insert(
            RoleTransferObjectEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
            Mapper.toContentValues(roleTransferObject)
        )
        Timber.tag(TAG).i("TRANSFERS: Default Role Transfer Object imported")
        jsonLogger?.let { logger ->
            Timber.tag(TAG).i(
                ": {\"roleTransferObject\": {%s}}", logger.encodeToString(roleTransferObject)
            )
        }
        return roleTransferObject
    }

    // Init database:
    override suspend fun prePopulateDb() {
        Timber.tag(TAG).i("prePopulateDb() called")
        db.beginTransaction()
        try {
            // Default settings:
            insertDefAppSettings()
            // ==============================
            // GEO:
            // Default country:
            val russia = insertDefCountry(GeoCountryEntity.russiaCountry(ctx))

            // Default regions:
            val donRegion = insertDefRegion(GeoRegionEntity.donetskRegion(ctx, russia.countryId))
            val lugRegion = insertDefRegion(GeoRegionEntity.luganskRegion(ctx, russia.countryId))

            // DON districts:
            val maryinskyDistrict = insertDefRegionDistrict(
                GeoRegionDistrictEntity.maryinskyRegionDistrict(ctx, donRegion.regionId)
            )

            val donetskyDistrict = insertDefRegionDistrict(
                GeoRegionDistrictEntity.donetskyRegionDistrict(ctx, donRegion.regionId)
            )

            // DON localities:
            val donetsk = insertDeftLocality(
                GeoLocalityEntity.donetskLocality(ctx, donRegion.regionId)
            )
            val makeevka = insertDeftLocality(
                GeoLocalityEntity.makeevkaLocality(ctx, donRegion.regionId)
            )
            val marinka = insertDeftLocality(
                GeoLocalityEntity.marinkaLocality(
                    ctx, donRegion.regionId, maryinskyDistrict.regionDistrictId
                )
            )
            val mospino = insertDeftLocality(
                GeoLocalityEntity.mospinoLocality(
                    ctx, donRegion.regionId, donetskyDistrict.regionDistrictId
                )
            )
            // LUG localities:
            val lugansk = insertDeftLocality(
                GeoLocalityEntity.luganskLocality(ctx, lugRegion.regionId)
            )

            // Donetsk districts:
            val budyonovsky = insertDeftLocalityDistrict(
                GeoLocalityDistrictEntity.bdnLocalityDistrict(ctx, donetsk.localityId)
            )
            val kievsky = insertDeftLocalityDistrict(
                GeoLocalityDistrictEntity.kvkLocalityDistrict(ctx, donetsk.localityId)
            )
            val kuybyshevsky = insertDeftLocalityDistrict(
                GeoLocalityDistrictEntity.kbshLocalityDistrict(ctx, donetsk.localityId)
            )
            val voroshilovsky = insertDeftLocalityDistrict(
                GeoLocalityDistrictEntity.vrshLocalityDistrict(ctx, donetsk.localityId)
            )

            // Donetsk microdistricts:
            val donskoy = insertDefMicrodistrict(
                GeoMicrodistrictEntity.donskoyMicrodistrict(
                    ctx, budyonovsky.ldLocalitiesId, budyonovsky.localityDistrictId
                )
            )
            val cvetochny = insertDefMicrodistrict(
                GeoMicrodistrictEntity.cvetochnyMicrodistrict(
                    ctx, budyonovsky.ldLocalitiesId, budyonovsky.localityDistrictId
                )
            )

            // Donetsk streets:
            val strelkovojDivizii = insertDefStreet(
                GeoStreetEntity.strelkovojDiviziiStreet(ctx, donetsk.localityId)
            )
            insertDefStreetDistrict(strelkovojDivizii, budyonovsky, donskoy)
            insertDefStreetDistrict(strelkovojDivizii, budyonovsky, cvetochny)
            val nezavisimosti = insertDefStreet(
                GeoStreetEntity.nezavisimostiStreet(ctx, donetsk.localityId)
            )
            insertDefStreetDistrict(nezavisimosti, budyonovsky, donskoy)
            val baratynskogo = insertDefStreet(
                GeoStreetEntity.baratynskogoStreet(ctx, donetsk.localityId)
            )
            insertDefStreetDistrict(baratynskogo, budyonovsky)
            val patorgynskogo = insertDefStreet(
                GeoStreetEntity.patorgynskogoStreet(ctx, donetsk.localityId)
            )
            insertDefStreetDistrict(patorgynskogo, budyonovsky)
            val belogorodskaya = insertDefStreet(
                GeoStreetEntity.belogorodskayaStreet(ctx, donetsk.localityId)
            )
            insertDefStreetDistrict(belogorodskaya, budyonovsky)

            // ==============================
            // Houses strelkovoj divizii:
            val sd7b = insertDefHouse(
                HouseEntity.defaultHouse(
                    streetId = strelkovojDivizii.streetId,
                    localityDistrictId = budyonovsky.localityDistrictId,
                    houseNum = 7, houseLetter = "б", buildingNum = 1,
                )
            )
            val sd9a = insertDefHouse(
                HouseEntity.defaultHouse(
                    streetId = strelkovojDivizii.streetId,
                    localityDistrictId = budyonovsky.localityDistrictId,
                    microdistrictId = donskoy.microdistrictId,
                    houseNum = 9, houseLetter = "а", isBusiness = true
                )
            )
            val sd21 = insertDefHouse(
                HouseEntity.defaultHouse(
                    streetId = strelkovojDivizii.streetId,
                    localityDistrictId = budyonovsky.localityDistrictId,
                    microdistrictId = cvetochny.microdistrictId,
                    houseNum = 21
                )
            )
            // Houses nezavisimosti:
            val n8 = insertDefHouse(
                HouseEntity.defaultHouse(
                    streetId = nezavisimosti.streetId,
                    localityDistrictId = budyonovsky.localityDistrictId,
                    microdistrictId = donskoy.microdistrictId,
                    houseNum = 8
                )
            )
            val n14 = insertDefHouse(
                HouseEntity.defaultHouse(
                    streetId = strelkovojDivizii.streetId,
                    localityDistrictId = budyonovsky.localityDistrictId,
                    microdistrictId = donskoy.microdistrictId,
                    houseNum = 14
                )
            )
            val n29v = insertDefHouse(
                HouseEntity.defaultHouse(
                    streetId = strelkovojDivizii.streetId,
                    localityDistrictId = budyonovsky.localityDistrictId,
                    microdistrictId = cvetochny.microdistrictId,
                    houseNum = 29, houseLetter = "в", isBusiness = true
                )
            )

            // Mospino streets:
            val novomospino = insertDefStreet(
                GeoStreetEntity.novomospinoStreet(ctx, mospino.localityId)
            )
            val gertsena =
                insertDefStreet(GeoStreetEntity.gertsenaStreet(ctx, mospino.localityId))


            // ==============================
            // CONGREGATION:
            // Default member roles:
            val adminRole = insertDefMemberRole(MemberRoleType.ADMIN)
            val userRole = insertDefMemberRole(MemberRoleType.USER)
            val territoriesRole = insertDefMemberRole(MemberRoleType.TERRITORIES)
            val billsRole = insertDefMemberRole(MemberRoleType.BILLS)
            val reportsRole = insertDefMemberRole(MemberRoleType.REPORTS)

            // Default congregations:
            // 1
            val congregation1 = CongregationEntity.favoriteCongregation(
                ctx, donetsk.localityId
            )
            db.insert(
                CongregationEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(congregation1)
            )
            Timber.tag(TAG).i("CONGREGATION: Default 1 Congregation imported")
            jsonLogger?.let { Timber.tag(TAG).i(": {%s}", it.encodeToString(congregation1)) }

            // Default Administrator:
            val adminMember = insertDefAdminMember(congregation1, listOf(adminRole))

            // 2
            val congregation2 = CongregationEntity.secondCongregation(
                ctx, donetsk.localityId
            )
            db.insert(
                CongregationEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(congregation2)
            )
            Timber.tag(TAG).i("CONGREGATION: Default 2 Congregation imported")
            jsonLogger?.let { Timber.tag(TAG).i(": {%s}", it.encodeToString(congregation2)) }

            // Default Groups:
            val group11 = insertDefGroup(1, congregation1)
            val group12 = insertDefGroup(2, congregation1)
            val group13 = insertDefGroup(3, congregation1)
            val group14 = insertDefGroup(4, congregation1)
            val group15 = insertDefGroup(5, congregation1)
            val group16 = insertDefGroup(6, congregation1)
            val group17 = insertDefGroup(7, congregation1)

            // Group members:
            val ivanov = insertDefMember(1, congregation1, group11, listOf(adminRole))
            val petrov = insertDefMember(2, congregation1, group11, listOf(userRole))
            val sidorov = insertDefMember(1, congregation1, group12, listOf(territoriesRole))

            // ==============================
            // TERRITORY:
            // Categories:
            val houseTerritoryCategory = insertDefTerritoryCategory(
                TerritoryCategoryEntity.houseTerritoryCategory(ctx)
            )
            val floorTerritoryCategory = insertDefTerritoryCategory(
                TerritoryCategoryEntity.floorTerritoryCategory(ctx)
            )
            val roomTerritoryCategory = insertDefTerritoryCategory(
                TerritoryCategoryEntity.roomTerritoryCategory(ctx)
            )
            // Room territories:
            val roomTerrs1 = insertDefTerritories(
                roomTerritoryCategory, congregation1, mospino, budyonovsky, donskoy
            )

            // House territories (override sector):
            val houseTerrs1 = insertDefTerritories(
                houseTerritoryCategory, congregation1, mospino, budyonovsky, cvetochny
            )
            insertDefTerritoryStreet(
                houseTerrs1.first { it.territoryNum == 1 }, baratynskogo
            )
            insertDefTerritoryStreet(
                houseTerrs1.first { it.territoryNum == 1 }, patorgynskogo, isEven = false
            )
            insertDefTerritoryStreet(
                houseTerrs1.first { it.territoryNum == 2 }, belogorodskaya, isEven = true
            )

            // Territory members:
            // ivanov
            insertDefTerritoryMember(roomTerrs1.first { it.territoryNum == 1 }, ivanov)
            // receive & delivery
            val roomTerrNum2 = roomTerrs1.first { it.territoryNum == 2 }
            insertDefTerritoryMember(roomTerrNum2, ivanov)
            val territoryMember = insertDefTerritoryMember(roomTerrNum2, ivanov)
            deliveryDefTerritoryMember(roomTerrNum2, territoryMember)

            insertDefTerritoryMember(roomTerrs1.first { it.territoryNum == 3 }, ivanov)
            insertDefTerritoryMember(houseTerrs1.first { it.territoryNum == 1 }, ivanov)
            // petrov
            insertDefTerritoryMember(roomTerrs1.first { it.territoryNum == 4 }, petrov)
            // sidorov
            insertDefTerritoryMember(houseTerrs1.first { it.territoryNum == 2 }, sidorov)

            // ==============================
            // TRANSFERS:
            // Default transfer objects:
            val allTransferObject = insertDefTransferObject(TransferObjectType.ALL)
            val membersTransferObject = insertDefTransferObject(TransferObjectType.MEMBERS)
            val territoriesTransferObject =
                insertDefTransferObject(TransferObjectType.TERRITORIES)
            val territoryReportTransferObject =
                insertDefTransferObject(TransferObjectType.TERRITORY_REPORT)
            val billsTransferObject = insertDefTransferObject(TransferObjectType.BILLS)
            val reportsTransferObject = insertDefTransferObject(TransferObjectType.REPORTS)

            insertDefRoleTransferObject(adminRole, allTransferObject, false)
            insertDefRoleTransferObject(reportsRole, reportsTransferObject, false)
            insertDefRoleTransferObject(territoriesRole, territoriesTransferObject, false)
            insertDefRoleTransferObject(billsRole, billsTransferObject, true)
            insertDefRoleTransferObject(userRole, territoriesTransferObject, true)
            insertDefRoleTransferObject(userRole, territoryReportTransferObject, true)

            // ==============================
            db.setTransactionSuccessful()
            Timber.tag(TAG).i("prePopulateDb(...) successful ended")
            /*
                        val isImport: Boolean = true
                        if (isImport) {
                        instance?.payerDao()?.add(payerEntity)
                        }

             */
        } catch (e: SQLiteException) {
            Timber.tag(TAG).e(e)
        } finally {
            db.endTransaction()
            JwSuiteDatabase.isImportExecute = false
        }
    }

    override suspend fun init() {
        // Default settings:
        insertDefAppSettings()
    }
}