package io.ehdev.timetracker.core.entry
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import io.ehdev.timetracker.core.project.ProjectImpl
import io.ehdev.timetracker.core.user.UserImpl
import org.joda.time.Duration

import javax.persistence.*

@Entity
@Inheritance
@Table
@DiscriminatorColumn(name="line_item_type", discriminatorType=DiscriminatorType.STRING)

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="type")
@JsonSubTypes([
    @JsonSubTypes.Type(FixedTimeEntry.class),
])
abstract class LineItemEntry {

    @Id
    @GeneratedValue
    @JsonIgnore
    Integer id

    @Column
    String descrption

    @Column
    String note

    @ManyToOne
    @JsonIgnore
    ProjectImpl project

    @ManyToOne
    UserImpl user

    abstract public Duration getDuration();
    abstract void validateData()
}
