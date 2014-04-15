package io.ehdev.timetracker.core.user

class UserNotAuthorizedToAdminException extends UserNotAuthorizedException{
    UserNotAuthorizedToAdminException() {
    }

    UserNotAuthorizedToAdminException(User user) {
        super(user)
    }
}
