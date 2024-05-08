package org.d3if0145.assesment2.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.d3if0145.assesment2.model.Parfum

@Dao
interface ParfumDao {

    @Insert
    suspend fun insert(parfum: Parfum)

    @Update
    suspend fun update(parfum: Parfum)

    @Query("SELECT * FROM parfum ORDER BY genderParfum ASC")
    fun getParfum(): Flow<List<Parfum>>

    @Query("SELECT * FROM parfum WHERE id = :id")
    suspend fun getParfumById(id: Long): Parfum?

    @Query("DELETE FROM parfum WHERE id = :id")
    suspend fun deleteById(id: Long)



}
