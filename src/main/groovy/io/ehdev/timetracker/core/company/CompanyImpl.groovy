package io.ehdev.timetracker.core.company
import io.ehdev.timetracker.core.PreformActionBaseImpl
import io.ehdev.timetracker.core.Storable
import io.ehdev.timetracker.core.permissions.ExtendedPermissions

import javax.persistence.*

@Table
@Entity
class CompanyImpl extends PreformActionBaseImpl implements Company, Storable {

    @Id
    @GeneratedValue
    Integer id

    @Column
    String name

    @ManyToOne
    ExtendedPermissions permissions

}
