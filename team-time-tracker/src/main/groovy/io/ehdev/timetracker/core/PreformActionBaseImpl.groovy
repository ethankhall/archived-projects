package io.ehdev.timetracker.core
import io.ehdev.timetracker.core.permissions.ExtendedPermissions
import io.ehdev.timetracker.core.user.UserImpl
import io.ehdev.timetracker.core.user.UserNotAuthorizedToAdminException
import io.ehdev.timetracker.core.user.UserNotAuthorizedToReadException
import io.ehdev.timetracker.core.user.UserNotAuthorizedToWriteException

abstract class PreformActionBaseImpl implements PreformActionBase {

    abstract List<ExtendedPermissions> getPermissions();

    public ExtendedPermissions findPermissionForUser(UserImpl user){
        ExtendedPermissions foundPermission = getPermissions().find {
            it.refUser.uuid == user.uuid
        }
        return foundPermission ?: ExtendedPermissions.EMPTY_PERMISSION
    }

    public preformWrite(UserImpl user, Closure closure){
        if(findPermissionForUser(user).canUserWrite()){
            return closure.call(this)
        } else {
            throw new UserNotAuthorizedToWriteException(user)
        }
    }

    public preformRead(UserImpl user, Closure closure){
        if(findPermissionForUser(user).canUserRead()){
            return closure.call(this)
        } else {
            throw new UserNotAuthorizedToReadException(user)
        }
    }

    public preformAdmin(UserImpl user, Closure closure){
        if(findPermissionForUser(user).canUserAdmin()){
            return closure.call(this)
        } else {
            throw new UserNotAuthorizedToAdminException(user)
        }
    }
}
