package diego.garzon.domain

import diego.garzon.domain.model.ProductDomainModel
import diego.garzon.domain.model.RateDomainModel

interface GNBIRepository {
    fun getProductList(): Either<Exception, ArrayList<ProductDomainModel>>
    fun getRateList(): Either<Exception, ArrayList<RateDomainModel>>
}