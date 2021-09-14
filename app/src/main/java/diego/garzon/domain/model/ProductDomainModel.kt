package diego.garzon.domain.model

import java.math.BigDecimal

data class ProductDomainModel(
    val sku: String?,
    val amount: BigDecimal?,
    val currency: String?,
)