package diego.garzon.data.repositories

import diego.garzon.data.ProductResponseMapper
import diego.garzon.data.services.GetRateListService
import diego.garzon.data.services.GetProductListService
import diego.garzon.domain.Either
import diego.garzon.data.RateResponseMapper
import diego.garzon.domain.GNBIRepository
import diego.garzon.domain.model.ProductDomainModel
import diego.garzon.domain.model.RateDomainModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class GNBRepository :
    GNBIRepository {
    private val getProductListService: GetProductListService
    private val getRateListService: GetRateListService

    override fun getProductList(): Either<Exception, ArrayList<ProductDomainModel>> {
        return try {
            val response = getProductListService.getProducts().execute()
            if (response.isSuccessful) {
                Either.right(ProductResponseMapper().listDataToDomain(response.body()))
            } else {
                Either.left(Exception("An error has occurred with the server"))
            }
        } catch (exception: IOException) {
            Either.left(Exception("The connection has failed"))
        }
    }

    override fun getRateList(): Either<Exception, ArrayList<RateDomainModel>> {
        return try {
            val response = getRateListService.getRateList().execute()
            if (response.isSuccessful) {
                Either.right(RateResponseMapper().listDataToDomain(response.body()))
            } else {
                Either.left(Exception("An error has occurred with the server"))
            }
        } catch (exception: IOException) {
            Either.left(Exception("The connection has failed"))
        }
    }


    companion object {
        private const val GNB_SERVICE_BASE_URL = "https://quiet-stone-2094.herokuapp.com"
    }

    init {
        val interceptor = Interceptor { chain ->
            val builder = chain.request().newBuilder()
            builder.header("Content-Type", "application/json")
            builder.header("Accept", "application/json")
            return@Interceptor chain.proceed(builder.build())
        }
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        getProductListService = Retrofit.Builder()
            .baseUrl(GNB_SERVICE_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GetProductListService::class.java)
        getRateListService = Retrofit.Builder()
            .baseUrl(GNB_SERVICE_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GetRateListService::class.java)
    }
}