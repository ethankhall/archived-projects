package io.ehdev.timetracker.core.project.permissions

import io.ehdev.timetracker.core.user.User

class Permissions {

    def parentPermissions = null

    def List<User> readAccess = []
    def List<User> writeAccess = []

    boolean canUserWrite(User user){
        if(parentPermissions?.canUserWrite(user)){
            return true
        }
        return user in writeAccess
    }

    boolean canUserRead(User user){
        if(parentPermissions?.canUserRead(user)){
            return true
        }
        return user in writeAccess || user in readAccess
    }
}
