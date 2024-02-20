package com.oborodulin.jwsuite.data_congregation.local.db.entities.transfer

import android.content.Context
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oborodulin.home.common.data.UUIDSerializer
import com.oborodulin.home.common.data.entities.BaseEntity
import com.oborodulin.jwsuite.data_congregation.R
import com.oborodulin.jwsuite.domain.types.TransferObjectType
import kotlinx.serialization.Serializable
import java.util.UUID

@Entity(
    tableName = TransferObjectEntity.TABLE_NAME,
    indices = [Index(value = ["transferObjectType"], unique = true)]
)
@Serializable
data class TransferObjectEntity(
    @Serializable(with = UUIDSerializer::class)
    @PrimaryKey val transferObjectId: UUID = UUID.randomUUID(),
    val transferObjectType: TransferObjectType = TransferObjectType.ALL,
    val transferObjectName: String
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "transfer_objects"

        fun defaultTransferObject(
            transferObjectId: UUID = UUID.randomUUID(),
            transferObjectType: TransferObjectType = TransferObjectType.ALL,
            transferObjectName: String
        ) = TransferObjectEntity(
            transferObjectId = transferObjectId, transferObjectType = transferObjectType,
            transferObjectName = transferObjectName
        )

        fun allTransferObject(ctx: Context) = defaultTransferObject(
            transferObjectType = TransferObjectType.ALL,
            transferObjectName = ctx.resources.getString(R.string.def_trans_obj_name_all)
        )

        fun membersTransferObject(ctx: Context) = defaultTransferObject(
            transferObjectType = TransferObjectType.MEMBERS,
            transferObjectName = ctx.resources.getString(R.string.def_trans_obj_name_members)
        )

        fun territoriesTransferObject(ctx: Context) = defaultTransferObject(
            transferObjectType = TransferObjectType.TERRITORIES,
            transferObjectName = ctx.resources.getString(R.string.def_trans_obj_name_territories)
        )

        fun territoryReportTransferObject(ctx: Context) = defaultTransferObject(
            transferObjectType = TransferObjectType.TERRITORY_REPORT,
            transferObjectName = ctx.resources.getString(R.string.def_trans_obj_name_territory_report)
        )

        fun billsTransferObject(ctx: Context) = defaultTransferObject(
            transferObjectType = TransferObjectType.BILLS,
            transferObjectName = ctx.resources.getString(R.string.def_trans_obj_name_bills)
        )

        fun reportsTransferObject(ctx: Context) = defaultTransferObject(
            transferObjectType = TransferObjectType.REPORTS,
            transferObjectName = ctx.resources.getString(R.string.def_trans_obj_name_reports)
        )
    }

    override fun id() = this.transferObjectId
    override fun key() = this.transferObjectType.hashCode()
    override fun toString(): String {
        val str = StringBuffer()
        str.append("Transfer Object Entity '").append(transferObjectType).append("' ")
            .append(transferObjectName)
            .append(" transferObjectId = ").append(transferObjectId)
        return str.toString()
    }
}