package io.ehdev.timetracker.services.external.user
import io.ehdev.timetracker.core.user.User
import io.ehdev.timetracker.core.user.UserImpl

class ExternalUser implements User {

    String name
    String email

    ExternalUser(){
    }

    public static ExternalUser convertUser(User user){
        return new ExternalUser(name: user.name, email: user.email)
    }

    public UserImpl convertToUser(){
        return new UserImpl(name: name, email: email)
    }
}
