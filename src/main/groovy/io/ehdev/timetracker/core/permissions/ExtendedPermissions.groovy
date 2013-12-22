package io.ehdev.timetracker.core.permissions

import io.ehdev.timetracker.core.user.User

class ExtendedPermissions extends BasePermissions {


    def List<User> adminAccess = []

    boolean canUserAdmin(User user){
        return user in adminAccess
    }
}
