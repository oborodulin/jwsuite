package com.oborodulin.jwsuite.data.sources.local
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.oborodulin.jwsuite.domain.repositories.GeoRegionsRepository
import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data.local.datastore.repositories.sources.LocalSessionManagerDataSource
import com.oborodulin.jwsuite.data_geo.local.db.dao.GeoRegionDao
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.repositories.sources.local.LocalGeoRegionDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

/**
 * Created by o.borodulin on 08.August.2022
 */
internal val KEY_USERNAME = intPreferencesKey("key_username")
internal val KEY_PASSWORD = intPreferencesKey("key_password")
@OptIn(ExperimentalCoroutinesApi::class)
class LocalSessionManagerDataSourceImpl @Inject constructor(private val dataStore: DataStore<Preferences>) :
    LocalSessionManagerDataSource {
    override fun isSigned() = dataStore.data.[KEY_USERNAME] != null

    override fun isLogged(): Flow<Boolean>
    override suspend fun signup(username: String, password: String)
    override suspend fun login(password: String)
    override suspend fun logout()
}
