package diego.garzon.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class RateDataModelResponse() {

    @SerializedName("from")
    @Expose
    val fromCurrency: String? = null

    @SerializedName("to")
    @Expose
    val toCurrency: String? = null

    @SerializedName("rate")
    @Expose
    val rate: String? = null
}

