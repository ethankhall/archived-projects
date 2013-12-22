package io.ehdev.timetracker.core.company

import io.ehdev.timetracker.core.permissions.Permissions
import io.ehdev.timetracker.core.user.User

class Company {
    String name
    Permissions permissions
    List<User> admin
}
