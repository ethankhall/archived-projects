package io.ehdev.timetracker.services.external.user
import io.ehdev.timetracker.core.user.User
import io.ehdev.timetracker.core.user.UserImpl
import org.apache.commons.lang3.builder.EqualsBuilder
import org.apache.commons.lang3.builder.HashCodeBuilder
import org.apache.commons.lang3.builder.ReflectionToStringBuilder

class ExternalUser implements User {

    String name
    String email
    String UUID

    ExternalUser(){
    }

    public static ExternalUser convertUser(UserImpl user){
        return new ExternalUser(name: user.name, email: user.email, UUID: user.uuid)
    }

    public UserImpl convertToUser(){
        return new UserImpl(name: name, email: email)
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public boolean equals(Object object) {
        return EqualsBuilder.reflectionEquals(this, object)
    }

    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this)
    }
}
