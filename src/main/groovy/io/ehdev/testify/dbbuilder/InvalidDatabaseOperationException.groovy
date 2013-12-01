package io.ehdev.testify.dbbuilder

class InvalidDatabaseOperationException extends RuntimeException{

    InvalidDatabaseOperationException() {
    }

    InvalidDatabaseOperationException(Throwable throwable){
        super(throwable)
    }
}
