package diego.garzon.data

import diego.garzon.data.models.RateDataModelResponse
import diego.garzon.domain.model.RateDomainModel
import diego.garzon.ui.products_landing.RateDataModel.CREATOR.getCurrencyFromString
import java.math.BigDecimal

class RateResponseMapper : DataMapper<RateDataModelResponse, RateDomainModel>() {
    override fun dataToDomain(entryData: RateDataModelResponse?): RateDomainModel {
        return RateDomainModel(
            getCurrencyFromString(entryData?.fromCurrency),
            getCurrencyFromString(entryData?.toCurrency),
            BigDecimal(entryData?.rate)
        )
    }

    override fun domainToData(entryData: RateDomainModel?): RateDataModelResponse? {
        return null
    }
}