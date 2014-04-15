package io.ehdev.timetracker.core.user

class UserNotAuthorizedToWriteException extends UserNotAuthorizedException {
    UserNotAuthorizedToWriteException() {
    }

    UserNotAuthorizedToWriteException(User user) {
        super(user)
    }
}
