package io.ehdev.timetracker.core.permissions

interface Permissions {
    boolean canUserWrite();
    boolean canUserRead();
    boolean canUserAdmin();
}
