package io.ehdev.timetracker.services.external.company

import io.ehdev.timetracker.core.company.CompanyImpl
import org.apache.commons.lang3.builder.EqualsBuilder
import org.apache.commons.lang3.builder.HashCodeBuilder
import org.apache.commons.lang3.builder.ReflectionToStringBuilder

class ExternalCompany {

    String uuid
    String name
    List<String> admin = []
    List<String> write = []
    List<String> read = []

    ExternalCompany() {

    }

    ExternalCompany(CompanyImpl company){
        uuid = company.getUuid()
        name = company.getName()
        company.permissions.each {
            if(it.adminAccess) {
                admin << it.refUser.uuid
            }

            if(it.writeAccess) {
                write << it.refUser.uuid
            }

            if(it.readAccess) {
                read << it.refUser.uuid
            }
        }
    }

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
