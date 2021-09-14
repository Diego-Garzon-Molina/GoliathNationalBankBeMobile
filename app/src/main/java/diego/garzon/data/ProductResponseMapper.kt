package diego.garzon.data

import diego.garzon.data.models.ProductDataModelResponse
import diego.garzon.domain.model.ProductDomainModel
import java.math.BigDecimal

class ProductResponseMapper : DataMapper<ProductDataModelResponse, ProductDomainModel>() {
    override fun dataToDomain(entryData: ProductDataModelResponse?): ProductDomainModel {
        return ProductDomainModel(
            entryData?.sku,
            BigDecimal(entryData?.amount),
            entryData?.currency
        )
    }

    override fun domainToData(entryData: ProductDomainModel?): ProductDataModelResponse? {
        return null
    }
}