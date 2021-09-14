package diego.garzon.domain.mappers

import diego.garzon.domain.model.RateDomainModel
import diego.garzon.ui.products_landing.RateDataModel

class RateMapper : Mapper<RateDataModel, RateDomainModel>() {
    override fun domainToViewModel(entryData: RateDomainModel): RateDataModel {
        return RateDataModel(
            entryData.fromCurrency,
            entryData.toCurrency,
            entryData.rate
        )
    }

    override fun viewModelToDomain(entryData: RateDataModel): RateDomainModel {
        return RateDomainModel(
            entryData.fromCurrency,
            entryData.toCurrency,
            entryData.rate,

        )
    }

}