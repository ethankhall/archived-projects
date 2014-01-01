package io.ehdev.timetracker.services.external.company

import io.ehdev.timetracker.core.company.CompanyImpl
import io.ehdev.timetracker.core.user.UserImpl
import io.ehdev.timetracker.services.external.user.ExternalUser

class ExternalCompany {

    String uuid
    String name
    List<ExternalUser> admin
    List<ExternalUser> write
    List<ExternalUser> read

    ExternalCompany() {

    }

    ExternalCompany(CompanyImpl company){
        uuid = company.getUuid()
        name = company.getName()
        admin = company.adminAccess.collect { UserImpl it ->
            new ExternalUser(it)
        }
        read  = company.readAccess.collect { UserImpl it ->
            new ExternalUser(it)
        }
        write = company.writeAccess.collect { UserImpl it ->
            new ExternalUser(it)
        }
    }
}
