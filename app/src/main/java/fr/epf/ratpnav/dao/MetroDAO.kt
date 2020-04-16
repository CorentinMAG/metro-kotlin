package fr.epf.ratpnav.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import fr.epf.ratpnav.models.MetroT


@Dao
interface MetroDAO {
    @Query("select * from metros")
    suspend fun getMetro():List<MetroT>

    @Insert
    suspend fun addMetro(metro: MetroT)

    @Delete
    suspend fun deleteMetro(metro: MetroT)

    @Query("select * from metros where id_metro= :idMetro")
    suspend fun getMetro(idMetro:Int): MetroT

    @Query("delete from metros")
    suspend fun deleteAll()
}