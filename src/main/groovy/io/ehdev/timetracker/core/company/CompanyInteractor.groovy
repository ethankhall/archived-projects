package io.ehdev.timetracker.core.company

import io.ehdev.timetracker.core.permissions.UserCompanyPermissions
import io.ehdev.timetracker.core.user.User
import io.ehdev.timetracker.core.user.UserImpl

class CompanyInteractor {

    CompanyImpl company
    User currentUser

    CompanyInteractor(User user, CompanyImpl company){
        currentUser = user
        this.company = company
    }

    public static CompanyImpl createNewCompany(UserImpl user, String name){
        def company = new CompanyImpl(
                name: name,
                uuid: UUID.randomUUID().toString())
        company.permissions = [new UserCompanyPermissions(refUser: user, adminAccess: true, company: company)]
        return company
    }
}
