package io.ehdev.timetracker.core.permissions
import io.ehdev.timetracker.core.Storable
import io.ehdev.timetracker.core.user.UserImpl
import org.apache.commons.lang3.builder.ReflectionToStringBuilder

import javax.persistence.*

@MappedSuperclass
abstract class ExtendedPermissions implements Permissions, Storable {

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


    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof ExtendedPermissions)) return false

        ExtendedPermissions that = (ExtendedPermissions) o

        if (adminAccess != that.adminAccess) return false
        if (readAccess != that.readAccess) return false
        if (writeAccess != that.writeAccess) return false
        if (id != that.id) return false
        if (refUser != that.refUser) return false

        return true
    }

    int hashCode() {
        int result
        result = (id != null ? id.hashCode() : 0)
        result = 31 * result + refUser.hashCode()
        result = 31 * result + (readAccess ? 1 : 0)
        result = 31 * result + (writeAccess ? 1 : 0)
        result = 31 * result + (adminAccess ? 1 : 0)
        return result
    }
    final static ExtendedPermissions EMPTY_PERMISSION = new UserProjectPermissions()
}
