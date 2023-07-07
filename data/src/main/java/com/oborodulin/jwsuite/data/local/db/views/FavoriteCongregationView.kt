package com.oborodulin.jwsuite.data.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data.util.Constants

@DatabaseView(
    viewName = FavoriteCongregationView.VIEW_NAME,
    value = "SELECT * FROM ${CongregationView.VIEW_NAME} WHERE isFavorite = ${Constants.DB_TRUE}"
)
class FavoriteCongregationView(
    @Embedded val favorite: CongregationView
) {
    companion object {
        const val VIEW_NAME = "favorite_congregation_view"
    }
}