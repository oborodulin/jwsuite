package com.oborodulin.jwsuite.data.local.db.dao

import androidx.room.Dao
import androidx.room.RawQuery
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Dao
interface DatabaseDao {
    // READS:
    // https://commonsware.com/AndroidArch/pages/chap-rxroom-006
    @RawQuery
    fun dataTables(supportSQLiteQuery: SupportSQLiteQuery): List<String>
    fun findDataTables(): Flow<List<String>> {
        // SQLiteException: no such table: sqlite_schema: https://github.com/mattn/go-sqlite3/issues/1003
        val query = """
    SELECT name AS tableName FROM sqlite_master 
    WHERE type = 'table' AND name NOT LIKE 'sqlite_%' AND name NOT LIKE 'android_%' AND name NOT LIKE '%master%'
    ORDER BY rootpage
    """
        return flowOf(dataTables(SimpleSQLiteQuery(query, arrayOf<String>())))
    }

    // API:
    @RawQuery
    suspend fun checkpoint(supportSQLiteQuery: SupportSQLiteQuery): Int
    suspend fun setCheckpoint(): Int {
        return checkpoint(SimpleSQLiteQuery("pragma wal_checkpoint(full)"))
    }
}