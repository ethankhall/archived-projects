package io.ehdev.timetracker.core.entry

import io.ehdev.timetracker.core.project.Project

abstract class BaseEntry implements LineItemEntry{
    final String descrption
    final String note
    final Project project
}
