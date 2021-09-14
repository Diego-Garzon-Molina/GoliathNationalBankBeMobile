package diego.garzon.ui.products_landing

import android.os.Parcel
import android.os.Parcelable
import java.math.BigDecimal

data class ProductTransactionsModel(
    val sku: String?,
    val transactions: ArrayList<TransactionModel>?,
    var totalAmount: BigDecimal = BigDecimal.ZERO,
    var currentCurrency: RateDataModel.Currency = RateDataModel.Currency.EUR
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readArray(TransactionModel::class.java.classLoader) as? ArrayList<TransactionModel>?,
        parcel.readValue(BigDecimal::class.java.classLoader) as BigDecimal,
        parcel.readValue(RateDataModel.Currency::class.java.classLoader) as RateDataModel.Currency
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(sku)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductTransactionsModel> {
        override fun createFromParcel(parcel: Parcel): ProductTransactionsModel {
            return ProductTransactionsModel(parcel)
        }

        override fun newArray(size: Int): Array<ProductTransactionsModel?> {
            return arrayOfNulls(size)
        }
    }

}

data class TransactionModel(
    val amount: BigDecimal?,
    val currency: RateDataModel.Currency?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(BigDecimal::class.java.classLoader) as? BigDecimal,
        parcel.readValue(RateDataModel.Currency::class.java.classLoader) as? RateDataModel.Currency
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(amount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TransactionModel> {
        override fun createFromParcel(parcel: Parcel): TransactionModel {
            return TransactionModel(parcel)
        }

        override fun newArray(size: Int): Array<TransactionModel?> {
            return arrayOfNulls(size)
        }
    }
}