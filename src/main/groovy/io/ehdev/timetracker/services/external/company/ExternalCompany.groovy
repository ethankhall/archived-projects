package io.ehdev.timetracker.services.external.company
import io.ehdev.timetracker.core.company.CompanyImpl
import io.ehdev.timetracker.services.external.user.ExternalUser

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
}
