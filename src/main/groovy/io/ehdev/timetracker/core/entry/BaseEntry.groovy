package io.ehdev.timetracker.core.entry
import io.ehdev.timetracker.core.project.Project
import io.ehdev.timetracker.core.user.UserImpl

import javax.persistence.*

@MappedSuperclass
abstract class BaseEntry implements LineItemEntry{

    @Id
    @GeneratedValue
    Integer id

    @Column
    String descrption

    @Column
    String note

    @Transient
    Project project

    @ManyToOne
    UserImpl user
}
