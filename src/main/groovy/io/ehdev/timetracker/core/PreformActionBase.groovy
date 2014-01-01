package io.ehdev.timetracker.core

import io.ehdev.timetracker.core.user.User

interface PreformActionBase {
    public preformWrite(User user, Closure closure);
    public preformRead(User user, Closure closure);
    public preformAdmin(User user, Closure closure);
}
