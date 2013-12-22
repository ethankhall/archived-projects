package io.ehdev.timetracker.core.company

import io.ehdev.timetracker.core.permissions.BasePermissions
import io.ehdev.timetracker.core.user.User

class Company {
    String name
    BasePermissions permissions
    List<User> admin
}
