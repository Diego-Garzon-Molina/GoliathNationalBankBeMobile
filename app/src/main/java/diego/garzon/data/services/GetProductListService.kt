package diego.garzon.data.services

import diego.garzon.data.models.ProductDataModelResponse
import retrofit2.Call
import retrofit2.http.GET

interface GetProductListService {
    @GET("/transactions")
    fun getProducts(): Call<List<ProductDataModelResponse>>
}



