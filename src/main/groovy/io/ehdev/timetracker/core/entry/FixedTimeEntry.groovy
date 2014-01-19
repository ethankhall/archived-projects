package io.ehdev.timetracker.core.entry

import com.fasterxml.jackson.annotation.JsonTypeName
import groovy.transform.TupleConstructor
import org.joda.time.DateTime
import org.joda.time.Duration

import javax.persistence.Column
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue(value="fixed_time_entry")
@JsonTypeName("HOURLY")
@TupleConstructor(excludes = 'id', includeSuperFields = true)
class FixedTimeEntry extends LineItemEntry {

    @Column
    DateTime startTime

    @Column
    DateTime endTime

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
        validateData()
    }

    public void setTimes(DateTime startTime, DateTime endTime){
        this.startTime = startTime
        this.endTime = endTime
        validateData()
    }

    void validateData() {
        checkThatThereIsAStartTime()
        checkThatTimesAreInGoodOrder()
    }
}
