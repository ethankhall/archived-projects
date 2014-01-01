package io.ehdev.timetracker.core.user

class UserBuilder {

    static private int tokenCount = 0

    synchronized public static UserImpl createNewUser(){
        def authToken = "GEN: ${tokenCount++}"
        return new UserImpl(uuid: UUID.randomUUID().toString(), authToken: authToken)
    }

    synchronized public static UserImpl createNewUser(def name, def email){
        def authToken = "GEN: ${tokenCount++}"
        return new UserImpl(uuid: UUID.randomUUID().toString(), authToken: authToken, name: name, email: email)
    }
}
