package fr.epf.ratpnav.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import fr.epf.ratpnav.models.StationT

@Dao
interface StationDAO {
    @Query("select * from stations")
    suspend fun getStations():List<StationT>

    @Insert
    suspend fun addStation(station: StationT)

    @Delete
    suspend fun deleteStation(station: StationT)

    @Query("select * from stations where id_station= :idStation")
    suspend fun getStation(idStation:Int): StationT

    @Query("delete from stations")
    suspend fun deleteAll()

    @Query("select * from stations where fk=:idMetro")
    suspend fun getStationsName(idMetro:Int):List<StationT>

    @Query("select metros.name from stations inner join metros where metros.id = stations.fk and stations.name=:StationName" )
    suspend fun getAllMetroFromStation(StationName:String):List<String>

}