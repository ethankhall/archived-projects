package io.ehdev.timetracker.core.project

import io.ehdev.timetracker.core.user.User

interface Project {

    public writeData(User user, Closure closure);
    public readData(User user, Closure closure);
    public String getName();
}
