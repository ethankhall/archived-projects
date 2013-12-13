package io.ehdev.timetracker.core.entry

import org.joda.time.Duration

interface LineItemEntry {
    public Duration getDuration();
}
