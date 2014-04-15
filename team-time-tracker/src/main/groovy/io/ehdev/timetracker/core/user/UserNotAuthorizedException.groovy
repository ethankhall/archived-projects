package io.ehdev.timetracker.core.user

class UserNotAuthorizedException extends RuntimeException {
    UserNotAuthorizedException() {
        super()
    }

    UserNotAuthorizedException(User user){
        super("User(${user.getUuid()}) not authorized")
    }
}
