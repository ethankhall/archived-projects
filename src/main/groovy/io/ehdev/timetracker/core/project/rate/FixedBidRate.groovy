package io.ehdev.timetracker.core.project.rate
import com.fasterxml.jackson.annotation.JsonTypeName
import groovy.transform.TupleConstructor
import io.ehdev.timetracker.core.entry.LineItemEntry

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import java.math.RoundingMode

@Entity
@DiscriminatorValue(value="fixed")
@JsonTypeName("FIXED")
@TupleConstructor(excludes = 'id', includeSuperFields = true)
class FixedBidRate extends Rate {

    @Override
    BigDecimal getAmount(List<LineItemEntry> entries) {
        return rateValue.setScale(2, RoundingMode.HALF_EVEN)
    }

    static final FixedBidRate ZERO = new FixedBidRate(rateValue: 0)

}

