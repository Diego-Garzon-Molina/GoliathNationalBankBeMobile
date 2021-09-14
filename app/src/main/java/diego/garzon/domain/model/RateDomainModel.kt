package diego.garzon.domain.model

import diego.garzon.ui.products_landing.RateDataModel
import java.math.BigDecimal

data class RateDomainModel(
    val fromCurrency: RateDataModel.Currency?,
    val toCurrency: RateDataModel.Currency?,
    val rate: BigDecimal?,
)
