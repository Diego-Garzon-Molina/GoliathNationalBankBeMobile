package diego.garzon.domain.mappers

import diego.garzon.domain.model.ProductDomainModel
import diego.garzon.ui.products_landing.ProductLandingModel.ProductDataViewModel
import diego.garzon.ui.products_landing.RateDataModel

class ProductMapper : Mapper<ProductDataViewModel, ProductDomainModel>() {
    override fun domainToViewModel(entryData: ProductDomainModel): ProductDataViewModel {
        return ProductDataViewModel(entryData.sku, entryData.amount,
            RateDataModel.getCurrencyFromString(entryData.currency))
    }

    override fun viewModelToDomain(entryData: ProductDataViewModel): ProductDomainModel {
        return ProductDomainModel(entryData.sku, entryData.amount, entryData.currency?.name)
    }
}