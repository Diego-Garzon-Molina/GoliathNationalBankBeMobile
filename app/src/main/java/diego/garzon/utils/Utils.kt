package diego.garzon.utils

import java.math.BigDecimal

fun BigDecimal.bankersRounding(): BigDecimal =
    this.setScale(2, BigDecimal.ROUND_HALF_EVEN)

