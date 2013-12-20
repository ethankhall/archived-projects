package io.ehdev.timetracker.core.project.permissions

import io.ehdev.timetracker.core.user.User

class Permissions {
    def readAccess = []
    def writeAccess = []

    boolean canUserWrite(User user){
        return writeAccess.any{
            it.getId() == user.getId()
        }
    }

    boolean canUserRead(User user){
        return user in writeAccess || user in readAccess
    }
}
