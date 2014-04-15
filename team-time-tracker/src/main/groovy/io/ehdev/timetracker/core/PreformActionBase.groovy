package io.ehdev.timetracker.core

import io.ehdev.timetracker.core.user.UserImpl

interface PreformActionBase {
    public preformWrite(UserImpl user, Closure closure);
    public preformRead(UserImpl user, Closure closure);
    public preformAdmin(UserImpl user, Closure closure);
}
