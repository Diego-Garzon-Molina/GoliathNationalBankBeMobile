package diego.garzon.data.services

import diego.garzon.data.models.RateDataModelResponse
import retrofit2.Call
import retrofit2.http.GET

interface GetRateListService {
    @GET("/rates")
    fun getRateList(
    ): Call<List<RateDataModelResponse>>

}


