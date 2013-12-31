package io.ehdev.timetracker.core.user

class UserBuilder {

    public static UserImpl createNewUser(){
        return new UserImpl(uuid: UUID.randomUUID().toString())
    }
}
