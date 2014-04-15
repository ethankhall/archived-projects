package io.ehdev.timetracker.core.permissions

import groovy.transform.TupleConstructor
import io.ehdev.timetracker.core.company.CompanyImpl
import org.apache.commons.lang3.builder.ReflectionToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = 'company_permissions')
@TupleConstructor class UserCompanyPermissions extends ExtendedPermissions {

    @ManyToOne(cascade = CascadeType.ALL)
    CompanyImpl company

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        UserCompanyPermissions that = (UserCompanyPermissions) o

        if (company.uuid != that.company.uuid) return false
        if (refUser.uuid != that.refUser.uuid) return false
        if (adminAccess != that.adminAccess) return false
        if (readAccess != that.readAccess) return false
        if (writeAccess != that.writeAccess) return false

        return true
    }

    int hashCode() {
        int result = super.hashCode()
        result = 31 * result + company.getUuid().hashCode()
        return result
    }
}
