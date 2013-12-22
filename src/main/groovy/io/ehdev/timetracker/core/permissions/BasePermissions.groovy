package io.ehdev.timetracker.core.permissions

import io.ehdev.timetracker.core.user.User

class BasePermissions implements Permissions {

    Permissions parentPermissions = null

    def List<User> readAccess = []
    def List<User> writeAccess = []

    boolean canUserWrite(User user){
        if(parentPermissions?.canUserWrite(user)){
            return true
        }
        return user in writeAccess || canUserAdmin(user)
    }

    boolean canUserRead(User user){
        if(parentPermissions?.canUserRead(user)){
            return true
        }
        return canUserWrite(user) || user in readAccess
    }

    @Override
    boolean canUserAdmin(User user) {
        if(parentPermissions?.canUserAdmin(user)){
            return true
        }
        return false
    }
}
