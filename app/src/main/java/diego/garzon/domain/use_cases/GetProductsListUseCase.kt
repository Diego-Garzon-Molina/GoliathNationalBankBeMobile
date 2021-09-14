package diego.garzon.domain.use_cases

import diego.garzon.domain.Either
import diego.garzon.domain.Interactor
import diego.garzon.domain.GNBIRepository
import diego.garzon.domain.mappers.ProductMapper
import diego.garzon.ui.products_landing.ProductLandingModel.ProductDataViewModel
import diego.garzon.ui.products_landing.ProductTransactionsModel
import diego.garzon.ui.products_landing.TransactionModel

class GetProductsListUseCase(private val repository: GNBIRepository) :
    Interactor<String, ArrayList<ProductTransactionsModel>> {

    override fun invoke(request: String): Either<Exception, ArrayList<ProductTransactionsModel>> {
        val response = repository.getProductList()
        return if (response.isLeft) {
            Either.left(response.leftValue)
        } else {
            Either.right(shortTransactionForEachProduct(ProductMapper().listDomainToViewModel(response.rightValue)))
        }
    }

    private fun shortTransactionForEachProduct(transactionList: ArrayList<ProductDataViewModel>): ArrayList<ProductTransactionsModel> {
        val listProductTransactionsModel = arrayListOf<ProductTransactionsModel>()
        transactionList.forEach { original ->
            val skuInList = listProductTransactionsModel.filter { it.sku == original.sku }
            if (skuInList.isNotEmpty()) {
                skuInList.first().transactions?.add(
                    TransactionModel(
                        original.amount,
                        original.currency
                    )
                )
            } else {
                listProductTransactionsModel.add(
                    ProductTransactionsModel(
                        original.sku,
                        arrayListOf(
                            TransactionModel(
                                original.amount,
                                original.currency
                            )
                        )
                    )
                )
            }
        }
        return listProductTransactionsModel
    }
}