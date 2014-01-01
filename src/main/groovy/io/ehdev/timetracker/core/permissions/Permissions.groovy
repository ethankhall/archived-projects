package io.ehdev.timetracker.core.permissions

import io.ehdev.timetracker.core.user.UserImpl

interface Permissions {
    boolean canUserWrite(UserImpl user);
    boolean canUserRead(UserImpl user);
    boolean canUserAdmin(UserImpl user);
}
