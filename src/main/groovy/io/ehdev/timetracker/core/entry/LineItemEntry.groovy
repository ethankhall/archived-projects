package io.ehdev.timetracker.core.entry

import io.ehdev.timetracker.core.project.ProjectImpl
import io.ehdev.timetracker.core.user.UserImpl
import org.joda.time.Duration

import javax.persistence.*

@Entity
@Inheritance
@Table
@DiscriminatorColumn(name="line_item_type", discriminatorType=DiscriminatorType.STRING)
abstract class LineItemEntry {

    @Id
    @GeneratedValue
    Integer id

    @Column
    String descrption

    @Column
    String note

    @ManyToOne
    ProjectImpl project

    @ManyToOne
    UserImpl user

    abstract public Duration getDuration();
}
