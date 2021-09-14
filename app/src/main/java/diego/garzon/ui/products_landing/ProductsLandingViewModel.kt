package diego.garzon.ui.products_landing

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import diego.garzon.domain.use_cases.GetConversionUseCase
import diego.garzon.domain.use_cases.GetProductsListUseCase
import diego.garzon.domain.use_cases.GetRateListUseCase
import diego.garzon.platform.InteractorExecutor
import diego.garzon.platform.Resource
import diego.garzon.platform.Status

class ProductsLandingViewModel(
    private val executor: InteractorExecutor,
    private val useCaseProducts: GetProductsListUseCase,
    private val useCaseRate: GetRateListUseCase,
    private val useCaseConversion: GetConversionUseCase
) : ViewModel() {
    var productsLanding = MutableLiveData<Resource<ProductLandingModel>>()

    fun load() {
        getRateInvoke()
        getProductInvoke()
    }

    fun refresh() {
        getRateInvoke()
        getProductInvoke()
    }

    fun convertProducts(desireCurrency: RateDataModel.Currency) {
        getConversionInvoke(desireCurrency)
    }

    private fun getRateInvoke() {
        if (productsLanding.value?.status != Status.LOADING) {
            productsLanding.value =
                Resource.loading(productsLanding.value?.data ?: ProductLandingModel())
        }
        executor(
            interactor = useCaseRate,
            request = "",
            onError = {
                productsLanding.value = Resource.error(it)
            },
            onSuccess = {
                productsLanding.value!!.data?.listRates = it
                if (productsLanding.value!!.data?.listProducts?.isEmpty() == false) {
                    convertProducts(RateDataModel.Currency.EUR)
                }
            }
        )
    }

    private fun getProductInvoke() {
        if (productsLanding.value?.status != Status.LOADING) {
            productsLanding.value =
                Resource.loading(productsLanding.value?.data ?: ProductLandingModel())
        }
        executor(
            interactor = useCaseProducts,
            request = "",
            onError = {
                productsLanding.value = Resource.error(it)
            },
            onSuccess = {
                productsLanding.value!!.data?.listProducts = it
                if (productsLanding.value!!.data?.listRates?.isEmpty() == false) {
                    convertProducts(RateDataModel.Currency.EUR)
                }
            }
        )
    }

    private fun getConversionInvoke(
        desireCurrency: RateDataModel.Currency
    ) {
        if (productsLanding.value?.status != Status.LOADING) {
            productsLanding.value =
                Resource.loading(productsLanding.value?.data ?: ProductLandingModel())
        }
        executor(
            interactor = useCaseConversion,
            request = ConversionModel(
                productsLanding.value?.data!!.listProducts,
                desireCurrency,
                productsLanding.value?.data!!.listRates
            ),
            onError = {
                productsLanding.value = Resource.error(it)
            },
            onSuccess = {
                productsLanding.value!!.data?.listProductsConverted = it
                productsLanding.value = Resource.success(productsLanding.value!!.data)
            }
        )
    }


}
