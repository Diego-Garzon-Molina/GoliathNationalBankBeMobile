package diego.garzon.ui.products_landing

import android.os.Parcel
import android.os.Parcelable
import java.math.BigDecimal

data class RateDataModel(
    val fromCurrency: Currency?,
    val toCurrency: Currency?,
    val rate: BigDecimal?,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Currency::class.java.classLoader) as? Currency,
        parcel.readValue(Currency::class.java.classLoader) as? Currency,
        parcel.readValue(BigDecimal::class.java.classLoader) as? BigDecimal
    )

    enum class Currency {
        EUR,
        USD,
        CAD,
        AUD
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(rate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RateDataModel> {
        override fun createFromParcel(parcel: Parcel): RateDataModel {
            return RateDataModel(parcel)
        }

        override fun newArray(size: Int): Array<RateDataModel?> {
            return arrayOfNulls(size)
        }

        fun getCurrencyFromString(value: String?): Currency {
            Currency.values().forEach {
                if (it.name == value) {
                    return it
                }
            }
            return Currency.USD
        }
    }

}
