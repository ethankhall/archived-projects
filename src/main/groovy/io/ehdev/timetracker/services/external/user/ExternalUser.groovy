package io.ehdev.timetracker.services.external.user
import io.ehdev.timetracker.core.user.User
import io.ehdev.timetracker.core.user.UserImpl
import org.apache.commons.lang3.builder.EqualsBuilder
import org.apache.commons.lang3.builder.HashCodeBuilder
import org.apache.commons.lang3.builder.ReflectionToStringBuilder

class ExternalUser implements User {

    String name
    String email
    String uuid

    ExternalUser(){
    }

    ExternalUser(UserImpl user){
        name = user.name
        email = user.email
        uuid = user.uuid
    }

    public static ExternalUser convertUser(UserImpl user){
        return new ExternalUser(user)
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
