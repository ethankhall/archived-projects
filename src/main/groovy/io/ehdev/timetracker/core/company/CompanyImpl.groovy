package io.ehdev.timetracker.core.company
import io.ehdev.timetracker.core.PreformActionBaseImpl
import io.ehdev.timetracker.core.Storable
import io.ehdev.timetracker.core.permissions.Permissions

class CompanyImpl extends PreformActionBaseImpl implements Company, Storable {

    Integer id
    String name
    Permissions permissions

}
