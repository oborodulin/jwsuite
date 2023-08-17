package com.oborodulin.jwsuite.data_congregation.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.domain.util.Constants.DB_TRUE

@DatabaseView(
    viewName = FavoriteCongregationView.VIEW_NAME,
    value = "SELECT * FROM ${CongregationView.VIEW_NAME} WHERE isFavorite = $DB_TRUE"
)
class FavoriteCongregationView(
    @Embedded val favorite: CongregationView
) {
    companion object {
        const val VIEW_NAME = "favorite_congregation_view"
    }
}