package io.ehdev.timetracker.core.project.discount

import com.fasterxml.jackson.annotation.JsonTypeName

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import java.math.RoundingMode

@Entity
@DiscriminatorValue(value="fixed")
@JsonTypeName("FIXED")
class FixedValueDiscount extends Discount{

    FixedValueDiscount(BigDecimal discountRate){
        discountValue = discountRate
    }

    FixedValueDiscount() { }

    @Override
    BigDecimal getDiscountAmount(BigDecimal preRate){
        return discountValue.min(preRate).setScale(2, RoundingMode.HALF_EVEN)
    }

}
