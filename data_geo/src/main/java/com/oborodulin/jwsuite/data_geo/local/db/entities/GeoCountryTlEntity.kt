package com.oborodulin.jwsuite.data_geo.local.db.entities

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oborodulin.home.common.data.UUIDSerializer
import com.oborodulin.home.common.data.entities.BaseEntity
import com.oborodulin.jwsuite.data_geo.R
import kotlinx.serialization.Serializable
import java.util.Locale
import java.util.UUID

@Entity(
    tableName = GeoCountryTlEntity.TABLE_NAME,
    indices = [Index(value = ["countriesId", "countryLocCode", "countryTlCode"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = GeoCountryEntity::class,
        parentColumns = arrayOf("countryId"),
        childColumns = arrayOf("countriesId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )]
)
@Serializable
data class GeoCountryTlEntity(
    @Serializable(with = UUIDSerializer::class)
    @PrimaryKey val countryTlId: UUID = UUID.randomUUID(),
    val countryLocCode: String = Locale.getDefault().language,
    val countryTlCode: String? = null,
    val countryName: String,
    @Serializable(with = UUIDSerializer::class)
    @ColumnInfo(index = true) val countriesId: UUID
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "geo_countries_tl"

        fun defaultCountryTl(
            countryTlId: UUID = UUID.randomUUID(), countryId: UUID, countryTlCode: String? = null,
            countryName: String
        ) = GeoCountryTlEntity(
            countryTlId = countryTlId, countryTlCode = countryTlCode, countryName = countryName,
            countriesId = countryId
        )

        private fun defCountryTl(ctx: Context, countryId: UUID) = defaultCountryTl(
            countryName = ctx.resources.getString(R.string.def_country_name), countryId = countryId
        )

        private fun ukraineCountryTl(ctx: Context, countryId: UUID) = defaultCountryTl(
            countryName = ctx.resources.getString(R.string.def_country_ukraine_name),
            countryId = countryId
        )

        private fun russiaCountryTl(ctx: Context, countryId: UUID) = defaultCountryTl(
            countryName = ctx.resources.getString(R.string.def_country_russia_name),
            countryId = countryId
        )

        fun countryTl(ctx: Context, countryCode: String, countryId: UUID) =
            when (countryCode) {
                ctx.resources.getString(R.string.def_country_code) -> defCountryTl(
                    ctx,
                    countryId
                )

                ctx.resources.getString(R.string.def_country_ukraine_code) -> ukraineCountryTl(
                    ctx,
                    countryId
                )

                ctx.resources.getString(R.string.def_country_russia_code) -> russiaCountryTl(
                    ctx,
                    countryId
                )

                else -> defaultCountryTl(countryId = UUID.randomUUID(), countryName = "")
            }
    }

    override fun id() = this.countryTlId

    override fun key(): Int {
        var result = countriesId.hashCode()
        result = result * 31 + countryLocCode.hashCode()
        return result
    }
}


