package io.ehdev.timetracker.core
import io.ehdev.timetracker.core.permissions.Permissions
import io.ehdev.timetracker.core.user.User
import io.ehdev.timetracker.core.user.UserNotAuthorizedToAdminException
import io.ehdev.timetracker.core.user.UserNotAuthorizedToReadException
import io.ehdev.timetracker.core.user.UserNotAuthorizedToWriteException

abstract class PreformActionBaseImpl implements PreformActionBase{

    abstract Permissions getPermissions()

    public preformWrite(User user, Closure closure){
        if(permissions.canUserWrite(user)){
            return closure.call(this)
        } else {
            throw new UserNotAuthorizedToWriteException()
        }
    }

    public preformRead(User user, Closure closure){
        if(permissions.canUserRead(user)){
            return closure.call(this)
        } else {
            throw new UserNotAuthorizedToReadException()
        }
    }

    public preformAdmin(User user, Closure closure){
        if(permissions.canUserAdmin(user)){
            return closure.call(this)
        } else {
            throw new UserNotAuthorizedToAdminException()
        }
    }
}
