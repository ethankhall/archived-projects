package io.ehdev.timetracker.services.external.company
import io.ehdev.timetracker.core.company.CompanyImpl
import io.ehdev.timetracker.services.external.user.ExternalUser
import org.apache.commons.lang3.builder.EqualsBuilder
import org.apache.commons.lang3.builder.HashCodeBuilder
import org.apache.commons.lang3.builder.ReflectionToStringBuilder

class ExternalCompany {

    String uuid
    String name
    List<ExternalUser> admin = []
    List<ExternalUser> write = []
    List<ExternalUser> read = []

    ExternalCompany() {

    }

    ExternalCompany(CompanyImpl company){
        uuid = company.getUuid()
        name = company.getName()
        company.permissions.each {
            ExternalUser extUser = new ExternalUser(it.refUser)
            if(it.adminAccess) {
                admin << extUser
            }

            if(it.writeAccess) {
                write << extUser
            }

            if(it.readAccess) {
                read << extUser
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
