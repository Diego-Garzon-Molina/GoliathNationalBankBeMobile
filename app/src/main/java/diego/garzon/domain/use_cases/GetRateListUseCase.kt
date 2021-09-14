package diego.garzon.domain.use_cases

import diego.garzon.domain.Either
import diego.garzon.domain.GNBIRepository
import diego.garzon.domain.Interactor
import diego.garzon.domain.mappers.RateMapper
import diego.garzon.ui.products_landing.RateDataModel

class GetRateListUseCase(private val repository: GNBIRepository) :
    Interactor<String, ArrayList<RateDataModel>> {

    override fun invoke(request: String): Either<Exception, ArrayList<RateDataModel>> {
        val response = repository.getRateList()
        return if (response.isLeft) {
            Either.left(response.leftValue)
        } else {
            Either.right(RateMapper().listDomainToViewModel(response.rightValue))
        }
    }

}