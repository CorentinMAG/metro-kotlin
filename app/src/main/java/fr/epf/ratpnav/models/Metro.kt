package fr.epf.ratpnav.models

import android.os.Parcelable
import androidx.room.*
import fr.epf.ratpnav.API.MetroInfo
import fr.epf.ratpnav.API.Schedules
import kotlinx.android.parcel.Parcelize

@Entity(tableName="metros",indices = arrayOf(Index(value = ["id"],
    unique = true)))
class MetroT(@PrimaryKey(autoGenerate = true) val id_metro:Int,
             val code:String,
             val name:String,
             val direction:String,
             val id:Int
)

@Entity(tableName = "stations",foreignKeys = arrayOf(
    ForeignKey(entity = MetroT::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("fk"),
    onDelete = ForeignKey.CASCADE)
))
class StationT(@PrimaryKey(autoGenerate = true) val id_station:Int,
               val fk:Int,
               val name:String,
               val slug:String
)


class FullMetro(val metro: MetroT, val info: MetroInfo)



class MetroSchedules(val correspondances: Correspondances, val schedules: Schedules)

class Correspondances(val station: StationT, val correspondance: List<String> = emptyList())

@Entity(tableName = "liked")
class LikedStation(@PrimaryKey(autoGenerate = true) val id:Int,
                   val name:String
)

@Parcelize
class Favoris(val station:String,val correspondances:List<String> = emptyList()):Parcelable