package io.ehdev.timetracker.core.company

import io.ehdev.timetracker.core.user.User

class CompanyInteractor {

    CompanyImpl company
    User currentUser

    CompanyInteractor(User user, CompanyImpl company){
        currentUser = user
        this.company = company
    }
}
