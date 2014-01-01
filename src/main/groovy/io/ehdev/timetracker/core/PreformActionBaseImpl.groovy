package io.ehdev.timetracker.core
import io.ehdev.timetracker.core.permissions.Permissions
import io.ehdev.timetracker.core.user.UserImpl
import io.ehdev.timetracker.core.user.UserNotAuthorizedToAdminException
import io.ehdev.timetracker.core.user.UserNotAuthorizedToReadException
import io.ehdev.timetracker.core.user.UserNotAuthorizedToWriteException

abstract class PreformActionBaseImpl implements PreformActionBase {

    abstract Permissions getPermissions()

    public preformWrite(UserImpl user, Closure closure){
        if(permissions.canUserWrite(user)){
            return closure.call(this)
        } else {
            throw new UserNotAuthorizedToWriteException()
        }
    }

    public preformRead(UserImpl user, Closure closure){
        if(permissions.canUserRead(user)){
            return closure.call(this)
        } else {
            throw new UserNotAuthorizedToReadException()
        }
    }

    public preformAdmin(UserImpl user, Closure closure){
        if(permissions.canUserAdmin(user)){
            return closure.call(this)
        } else {
            throw new UserNotAuthorizedToAdminException()
        }
    }
}
