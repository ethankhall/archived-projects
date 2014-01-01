package io.ehdev.timetracker.core.permissions

import io.ehdev.timetracker.core.user.User
import io.ehdev.timetracker.core.user.UserImpl

import javax.persistence.*

@Entity
@Table
class ExtendedPermissions implements Permissions {

    @Id
    @GeneratedValue
    Integer id

    @ManyToOne
    ExtendedPermissions parentPermissions = null

    @ManyToMany
    def List<UserImpl> adminAccess = []

    @ManyToMany
    def List<UserImpl> readAccess = []

    @ManyToMany
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
}
