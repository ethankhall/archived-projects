package io.ehdev.timetracker.storage.permission

import io.ehdev.timetracker.core.company.CompanyImpl
import io.ehdev.timetracker.core.permissions.ExtendedPermissions
import io.ehdev.timetracker.core.permissions.UserCompanyPermissions
import io.ehdev.timetracker.core.user.UserImpl
import io.ehdev.timetracker.storage.BaseDao
import org.springframework.stereotype.Repository

import static com.google.common.collect.Lists.newArrayList

@Repository
class UserCompanyPermissionsDaoImpl extends BaseDao<UserCompanyPermissions> implements UserCompanyPermissionsDao {
    @Override
    Class<ExtendedPermissions> getBaseType() {
        return UserCompanyPermissions.class
    }

    @Override
    List<CompanyImpl> getCompaniesAvailableToUser(UserImpl user) {
        def query = getSession().createQuery("select ucp.company from UserCompanyPermissions ucp where ucp.refUser = :user")
        query.setParameter("user", user)
        return newArrayList(query.list())
    }
}
