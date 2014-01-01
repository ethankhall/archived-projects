package io.ehdev.timetracker.core.company
import io.ehdev.timetracker.core.PreformActionBaseImpl
import io.ehdev.timetracker.core.Storable
import io.ehdev.timetracker.core.permissions.ExtendedPermissions
import org.apache.commons.lang3.builder.EqualsBuilder
import org.apache.commons.lang3.builder.HashCodeBuilder
import org.apache.commons.lang3.builder.ReflectionToStringBuilder

import javax.persistence.*

@Entity
@Table(name = 'companies')
class CompanyImpl extends PreformActionBaseImpl implements Company, Storable {

    @Id
    @GeneratedValue
    Integer id

    @Column
    String name

    @Column
    String uuid

    @OneToOne(cascade = CascadeType.ALL)
    @Delegate
    ExtendedPermissions permissions

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public boolean equals(Object object) {
        return EqualsBuilder.reflectionEquals(this, object)
    }

    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this)
    }
}
