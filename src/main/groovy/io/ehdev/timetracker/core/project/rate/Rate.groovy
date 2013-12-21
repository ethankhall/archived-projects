package io.ehdev.timetracker.core.project.rate

import io.ehdev.timetracker.core.entry.LineItemEntry
import io.ehdev.timetracker.core.project.ProjectImpl

abstract class Rate {
    BigDecimal getAmount(ProjectImpl project){
        return getAmount(project.getEntries())
    }
    abstract BigDecimal getAmount(List<LineItemEntry> entries)
}
