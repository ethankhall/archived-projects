package io.ehdev.timetracker.core.permissions

enum InternalPermission {
    NONE(false, false, false),
    READ(true, false, false),
    WRITE(false, true, false),
    ADMIN(false, false, true)

    final boolean readFlag
    final boolean writeFlag
    final boolean adminFlag

    InternalPermission(read, write, admin){
        readFlag = read
        writeFlag = write
        adminFlag = admin
    }
}
