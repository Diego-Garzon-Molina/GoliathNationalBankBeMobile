package diego.garzon.domain.use_cases

import diego.garzon.domain.Either
import diego.garzon.domain.Interactor
import diego.garzon.ui.products_landing.ConversionModel
import diego.garzon.ui.products_landing.ProductTransactionsModel
import diego.garzon.ui.products_landing.RateDataModel
import diego.garzon.ui.products_landing.TransactionModel
import java.math.BigDecimal
import kotlin.collections.ArrayList

class GetConversionUseCase :
    Interactor<ConversionModel, ArrayList<ProductTransactionsModel>> {
    private lateinit var listRateDataModel: ArrayList<RateDataModel>

    override fun invoke(request: ConversionModel): Either<Exception, ArrayList<ProductTransactionsModel>> {
        listRateDataModel = request.rates
        val response = arrayListOf<ProductTransactionsModel>()
        request.productTransactionsModelList.forEach { product ->
            response.add(conversion(request.desireCurrency, product))
        }
        return if (response.isEmpty()) {
            Either.left(java.lang.Exception())
        } else {
            Either.right(response)
        }
    }

    private fun conversion(
        destinyCurrency: RateDataModel.Currency?,
        product: ProductTransactionsModel
    ): ProductTransactionsModel {
        val transactionsConverted = arrayListOf<TransactionModel>()
        val result = ProductTransactionsModel(product.sku, transactionsConverted)
        product.transactions?.forEach { transaction ->
            calculateRates(transaction, destinyCurrency, transactionsConverted)
        }
        var totalAmount = BigDecimal.ZERO
        transactionsConverted.forEach { transaction ->
            totalAmount = transaction.amount?.add(
                totalAmount
            )
        }
        result.currentCurrency = transactionsConverted.first().currency!!
        result.totalAmount = totalAmount
        return result
    }

    private fun calculateRates(
        transactionsModel: TransactionModel,
        destinyCurrency: RateDataModel.Currency?,
        transactionsConverted: ArrayList<TransactionModel>
    ) {
        var ratesToUse = arrayListOf<BigDecimal>()
        if (transactionsModel.currency != destinyCurrency) {
            ratesToUse = foundCorrectRate(transactionsModel.currency, transactionsModel.currency, destinyCurrency, ratesToUse)
        } else {
            ratesToUse.add(BigDecimal.ONE)
        }
        var amountConverted = transactionsModel.amount
        ratesToUse.forEach { rate -> amountConverted = amountConverted?.times(rate) }
        transactionsConverted.add(TransactionModel(amountConverted, destinyCurrency))
    }

    private fun foundCorrectRate(
        originalCurrency: RateDataModel.Currency?,
        auxCurrency: RateDataModel.Currency?,
        destinyCurrency: RateDataModel.Currency?,
        ratesToUse: ArrayList<BigDecimal>
    ): ArrayList<BigDecimal> {
        var newRateCurrency: RateDataModel? = null
        listRateDataModel.forEach {
            if (it.fromCurrency == auxCurrency && it.toCurrency == destinyCurrency) {
                ratesToUse.add(it.rate!!)
                return ratesToUse
            }
        }
        listRateDataModel.first { it.fromCurrency == auxCurrency }.also {
            ratesToUse.add(it.rate!!)
            foundCorrectRate(originalCurrency, it.toCurrency, destinyCurrency, ratesToUse)
            var newRate = BigDecimal.ONE
            ratesToUse.forEach { rate -> newRate = newRate.times(rate) }
            newRateCurrency = RateDataModel(originalCurrency, destinyCurrency, newRate)
        }
        if (newRateCurrency != null && !listRateDataModel.contains(newRateCurrency))
            listRateDataModel.add(newRateCurrency!!)
        return ratesToUse
    }

}