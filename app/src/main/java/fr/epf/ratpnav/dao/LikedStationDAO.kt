package fr.epf.ratpnav.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import fr.epf.ratpnav.models.LikedStation

@Dao
interface LikedStationDAO {
    @Query("select * from liked")
    suspend fun getLiked():List<LikedStation>

    @Insert
    suspend fun addLike(like: LikedStation)

    @Delete
    suspend fun deleteLike(like: LikedStation)

    @Query("select count(:name) from liked where name =:name")
    suspend fun CheckLike(name:String): Int

    @Query("select count(*) from liked ")
    suspend fun CheckTable(): Int

    @Query("select * from liked where name =:name")
    suspend fun getLike(name:String): LikedStation

    @Query("delete from liked")
    suspend fun deleteAllLike()
}