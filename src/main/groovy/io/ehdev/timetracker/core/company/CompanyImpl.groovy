package io.ehdev.timetracker.core.company

import groovy.transform.TupleConstructor
import io.ehdev.timetracker.core.PreformActionBaseImpl
import io.ehdev.timetracker.core.Storable
import io.ehdev.timetracker.core.permissions.UserCompanyPermissions
import org.apache.commons.lang3.builder.ReflectionToStringBuilder

import javax.persistence.*

@Entity
@Table(name = 'companies')
@TupleConstructor
class CompanyImpl extends PreformActionBaseImpl implements Company, Storable {

    @Id
    @GeneratedValue
    Integer id

    @Column
    String name

    @Column
    String uuid

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = 'company_id')
    List<UserCompanyPermissions> permissions

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof CompanyImpl)) return false

        CompanyImpl company = (CompanyImpl) o

        if (id != company.id) return false
        if (name != company.name) return false
        if (permissions != company.permissions) return false
        if (uuid != company.uuid) return false

        return true
    }

    int hashCode() {
        int result
        result = (id != null ? id.hashCode() : 0)
        result = 31 * result + (name != null ? name.hashCode() : 0)
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0)
        permissions.each {
            result = 31 * result + it.hashCode()
        }
        return result
    }
}
