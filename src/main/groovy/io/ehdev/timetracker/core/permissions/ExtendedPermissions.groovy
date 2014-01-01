package io.ehdev.timetracker.core.permissions
import io.ehdev.timetracker.core.user.UserImpl
import org.apache.commons.lang3.builder.EqualsBuilder
import org.apache.commons.lang3.builder.HashCodeBuilder
import org.apache.commons.lang3.builder.ReflectionToStringBuilder
import org.hibernate.annotations.LazyCollection
import org.hibernate.annotations.LazyCollectionOption

import javax.persistence.*

@Entity
@Table(name = 'permissions')
class ExtendedPermissions implements Permissions {

    @Id
    @GeneratedValue
    Integer id

    @ManyToOne(cascade = [CascadeType.ALL])
    ExtendedPermissions parentPermissions = null

    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    def List<UserImpl> adminAccess = []

    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    def List<UserImpl> readAccess = []

    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    def List<UserImpl> writeAccess = []

    boolean canUserWrite(UserImpl user){
        if(parentPermissions?.canUserWrite(user)){
            return true
        }
        return user in writeAccess || canUserAdmin(user)
    }

    boolean canUserRead(UserImpl user){
        if(parentPermissions?.canUserRead(user)){
            return true
        }
        return canUserWrite(user) || user in readAccess
    }

    boolean canUserAdmin(UserImpl user){
        if(parentPermissions?.canUserAdmin(user)){
            return true
        }
        return user in adminAccess
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
