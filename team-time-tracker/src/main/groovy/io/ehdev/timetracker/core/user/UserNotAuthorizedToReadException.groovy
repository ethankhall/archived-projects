package io.ehdev.timetracker.core.user

class UserNotAuthorizedToReadException extends UserNotAuthorizedException{
    UserNotAuthorizedToReadException() {
    }

    UserNotAuthorizedToReadException(User user) {
        super(user)
    }
}
