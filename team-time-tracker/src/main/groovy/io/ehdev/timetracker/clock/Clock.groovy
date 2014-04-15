package io.ehdev.timetracker.clock

import org.joda.time.DateTime

public interface Clock {
    DateTime getNow();
}