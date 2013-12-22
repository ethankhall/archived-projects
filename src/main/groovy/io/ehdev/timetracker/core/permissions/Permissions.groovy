package io.ehdev.timetracker.core.permissions

import io.ehdev.timetracker.core.user.User

interface Permissions {
    boolean canUserWrite(User user);
    boolean canUserRead(User user);
    boolean canUserAdmin(User user);
}
