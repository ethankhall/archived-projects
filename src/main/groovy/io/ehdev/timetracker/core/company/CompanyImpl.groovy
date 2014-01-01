package io.ehdev.timetracker.core.company

import io.ehdev.timetracker.core.PreformActionBaseImpl
import io.ehdev.timetracker.core.Storable
import io.ehdev.timetracker.core.permissions.Permissions
import io.ehdev.timetracker.core.user.User

class CompanyImpl extends PreformActionBaseImpl implements Company, Storable {

    Integer id
    String name
    Permissions permissions
    List<User> admin

}
