package diego.garzon.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class ProductDataModelResponse(){
    @SerializedName("sku")
    @Expose
    val sku: String? = null

    @SerializedName("amount")
    @Expose
    val amount: String? = null

    @SerializedName("currency")
    @Expose
    val currency: String? = null
}
