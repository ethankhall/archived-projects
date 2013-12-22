package io.ehdev.timetracker.core.entry

class BadStartTimeException extends RuntimeException{
    BadStartTimeException(){
        super("Start time was not set")
    }
}
