package fr.epf.ratpnav.API

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


//Est ce que Call  avec l'utilisation de callbacks est une bonne méthode pour réaliser des opérations asynchrones ?
interface APIService {
    @GET("lines/{type}")
    suspend fun getDestination(
        @Path("type") type:String
    ): GetDestinationResult

    @GET("stations/{type}/{code}")
    suspend fun getStations(
        @Path("type") type:String,
        @Path("code") code:String
    ): GetStationResult

    @GET("traffic/{type}")
    suspend fun getInfoForEachLine(
        @Path("type") type:String
    ): GetTrafficResult

    @GET("traffic/{type}")
    open fun getInfoForEachLineAsync(@Path("type") type:String): Call<GetTrafficResult>

    @GET("schedules/{type}/{line}/{station}/{way}")
    suspend fun getScheduleByLine(
        @Path("type") type:String,
        @Path("line") line:String,
        @Path("station") station:String,
        @Path("way") way:String
    ): GetScheduleByLineResult

    @GET("schedules/{type}/{line}/{station}/{way}")
    open fun getScheduleByLineAsync(
        @Path("type") type:String,
        @Path("line") line:String,
        @Path("station") station:String,
        @Path("way") way:String
    ):Call<GetScheduleByLineResult>
}

data class GetDestinationResult(val result: Destination = Destination())
data class Destination(val metros:List<MetroR> = emptyList())
data class MetroR(val code:String="",val name:String="",val directions:String="",val id:Int=0)

data class GetStationResult(val result: Stations = Stations())
data class Stations(val stations:List<Station> = emptyList())
data class Station(val name:String="",val slug:String="")

data class GetTrafficResult(val result: MetroTraffic = MetroTraffic())
data class MetroTraffic(val metros:List<MetroInfo> = emptyList())
data class MetroInfo(val line:String="",val slug:String="",val title:String="",val message:String="")

data class GetScheduleByLineResult(val result: Schedules = Schedules())
data class Schedules(val schedules:List<Schedule> = emptyList())
data class Schedule(val code:String="",val message:String="",val destination:String="")

