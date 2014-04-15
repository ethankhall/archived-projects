package io.ehdev.timetracker.storage.permission

import io.ehdev.timetracker.core.company.CompanyImpl
import io.ehdev.timetracker.core.permissions.UserCompanyPermissions
import io.ehdev.timetracker.core.user.UserImpl
import io.ehdev.timetracker.storage.Dao

public interface UserCompanyPermissionsDao extends Dao<UserCompanyPermissions> {

    List<CompanyImpl> getCompaniesAvailableToUser(UserImpl user)

}