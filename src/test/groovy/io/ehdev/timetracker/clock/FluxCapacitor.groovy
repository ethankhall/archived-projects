package io.ehdev.timetracker.clock

import org.joda.time.DateTime

class FluxCapacitor implements Clock {

    DateTime currentTime
    int stepAmount

    public FluxCapacitor(DateTime now){
        this(now, 0)
    }

    public FluxCapacitor(DateTime now, int stepAmount){
        currentTime = now
        this.stepAmount = stepAmount
    }

    @Override
    DateTime getNow() {
        def returnClock = currentTime
        currentTime = currentTime.plusSeconds(stepAmount)
        return returnClock
    }
}
