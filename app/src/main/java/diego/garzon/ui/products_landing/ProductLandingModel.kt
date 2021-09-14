package diego.garzon.ui.products_landing

import java.math.BigDecimal

data class ProductLandingModel(
    var listProducts: ArrayList<ProductTransactionsModel> = arrayListOf(),
    var listRates: ArrayList<RateDataModel> = arrayListOf(),
    var listProductsConverted: ArrayList<ProductTransactionsModel> = arrayListOf(),
) {

    data class ProductDataViewModel(
        val sku: String?,
        val amount: BigDecimal?,
        val currency: RateDataModel.Currency?
    )


}