package fr.epf.ratpnav.database

import androidx.room.Database
import androidx.room.RoomDatabase
import fr.epf.ratpnav.dao.LikedStationDAO
import fr.epf.ratpnav.dao.MetroDAO
import fr.epf.ratpnav.dao.StationDAO
import fr.epf.ratpnav.models.LikedStation
import fr.epf.ratpnav.models.MetroT
import fr.epf.ratpnav.models.StationT

@Database(entities= arrayOf(
    MetroT::class,
    StationT::class,
    LikedStation::class),version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getMetroDao(): MetroDAO
    abstract fun getStationDao(): StationDAO
    abstract fun getLikedDao(): LikedStationDAO
}
