package io.ehdev.timetracker.core.entry

class StartAndEntTimeAreOutOfOrderException extends RuntimeException{
    StartAndEntTimeAreOutOfOrderException(){
        super("Start time was before end time")
    }
}
