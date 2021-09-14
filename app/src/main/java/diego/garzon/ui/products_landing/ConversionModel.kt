package diego.garzon.ui.products_landing

data class ConversionModel(
    val productTransactionsModelList: ArrayList<ProductTransactionsModel>,
    val desireCurrency: RateDataModel.Currency,
    val rates: ArrayList<RateDataModel>
)