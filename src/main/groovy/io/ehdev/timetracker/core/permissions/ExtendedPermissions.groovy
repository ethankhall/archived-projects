package io.ehdev.timetracker.core.permissions
import io.ehdev.timetracker.core.user.UserImpl
import org.apache.commons.lang3.builder.EqualsBuilder
import org.apache.commons.lang3.builder.HashCodeBuilder
import org.apache.commons.lang3.builder.ReflectionToStringBuilder

import javax.persistence.*

@MappedSuperclass
abstract class ExtendedPermissions implements Permissions {

    @Id
    @GeneratedValue
    Integer id

    @ManyToOne(cascade = CascadeType.ALL)
    UserImpl refUser

    boolean readAccess = false
    boolean writeAccess = false
    boolean adminAccess = false

    boolean canUserWrite(){
        return writeAccess || canUserAdmin()
    }

    boolean canUserRead(){
        return readAccess || canUserWrite()
    }

    boolean canUserAdmin(){
        adminAccess
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

    final static ExtendedPermissions EMPTY_PERMISSION = new UserProjectPermissions()
}
