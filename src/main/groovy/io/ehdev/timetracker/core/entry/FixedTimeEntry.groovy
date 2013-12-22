package io.ehdev.timetracker.core.entry

import org.joda.time.Duration
import org.joda.time.DateTime

class FixedTimeEntry extends BaseEntry {

    private def DateTime startTime
    private def DateTime endTime

    FixedTimeEntry(Map properties){
        startTime = properties['startTime']
        endTime = properties['endTime']

        checkThatThereIsAStartTime()
        checkThatTimesAreInGoodOrder()
    }

    public void checkThatThereIsAStartTime() {
        if (!startTime)
            throw new BadStartTimeException()
    }

    public void checkThatTimesAreInGoodOrder() {
        if (endTime && startTime > endTime)
            throw new StartAndEntTimeAreOutOfOrderException()
    }

    public Duration getDuration(){
        def calEndTime = endTime ?: DateTime.now()
        return new Duration(startTime, calEndTime)
    }

    public DateTime getStartTime(){
        return startTime
    }

    public DateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(DateTime endTime){
        this.endTime = endTime
        checkThatTimesAreInGoodOrder()
    }

    public void setStartTime(DateTime startTime){
        this.startTime = startTime
        checkThatTimesAreInGoodOrder()
        checkThatThereIsAStartTime()
    }

    public void setTimes(DateTime startTime, DateTime endTime){
        this.startTime = startTime
        this.endTime = endTime
        checkThatTimesAreInGoodOrder()
        checkThatThereIsAStartTime()
    }
}
