package io.ehdev.timetracker.core.user

class UserNotFoundException extends RuntimeException {

    UserNotFoundException(String type, String id){
        super("User of type($type) with id $id was not found.")
    }
}
