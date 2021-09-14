package diego.garzon.ui.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import diego.garzon.domain.use_cases.GetConversionUseCase
import diego.garzon.platform.InteractorExecutor
import diego.garzon.platform.Resource
import diego.garzon.ui.products_landing.ConversionModel
import diego.garzon.ui.products_landing.ProductTransactionsModel
import diego.garzon.ui.products_landing.RateDataModel

class ProductViewModel(
    private val executor: InteractorExecutor,
    private val useCaseConversion: GetConversionUseCase
) : ViewModel() {
    var product = MutableLiveData<Resource<ProductTransactionsModel>>()
    lateinit var rates : ArrayList<RateDataModel>

    fun convertProducts(desireCurrency: RateDataModel.Currency, productToConvert: ProductTransactionsModel) {
        getConversionInvoke(desireCurrency, arrayListOf(productToConvert))
    }

    private fun getConversionInvoke(
        desireCurrency: RateDataModel.Currency,
        productToConvert: ArrayList<ProductTransactionsModel>
    ) {
            product.value =
                Resource.loading(null)
        executor(
            interactor = useCaseConversion,
            request = ConversionModel(
                productToConvert,
                desireCurrency,
                rates
            ),
            onError = {
                product.value = Resource.error(it)
            },
            onSuccess = {
                product.value = Resource.success(it.first())
            }
        )
    }

}