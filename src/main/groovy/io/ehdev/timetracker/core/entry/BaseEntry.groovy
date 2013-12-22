package io.ehdev.timetracker.core.entry

import io.ehdev.timetracker.core.project.Project
import io.ehdev.timetracker.core.user.User

abstract class BaseEntry implements LineItemEntry{
    final String descrption
    final String note
    final Project project
    final User user
}
