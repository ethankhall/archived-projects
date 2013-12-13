package io.ehdev.timetracker.core.entry

import org.joda.time.Duration
import org.joda.time.DateTime

class FixedTimeEntry extends BaseEntry {

    final private def DateTime startTime
    final private def DateTime endTime

    FixedTimeEntry(Map properties){
        startTime = properties['startTime']
        endTime = properties['endTime']
        if(!startTime || !endTime)
            throw new RuntimeException("Start or End time was not set")

        if(startTime > endTime)
            throw new RuntimeException("Start time was before end time")
    }

    public Duration getDuration(){
        return new Duration(startTime, endTime)
    }

    public DateTime getStartTime(){
        return startTime
    }

    public DateTime getEndTime() {
        return endTime;
    }
}
