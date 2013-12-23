package io.ehdev.timetracker.core.user

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table
class User {

    @Id
    @GeneratedValue
    String id

    @Column
    String uuid = UUID.randomUUID().toString()

    @Column
    String name

    @Column
    String email
}
