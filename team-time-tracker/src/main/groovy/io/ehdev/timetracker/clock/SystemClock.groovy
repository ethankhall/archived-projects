package io.ehdev.timetracker.clock
import org.joda.time.DateTime

class SystemClock implements Clock{

    @Override
    DateTime getNow() {
        return DateTime.now()
    }
}
