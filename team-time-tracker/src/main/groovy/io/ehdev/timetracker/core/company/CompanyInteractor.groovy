package io.ehdev.timetracker.core.company

import io.ehdev.timetracker.core.permissions.InternalPermission
import io.ehdev.timetracker.core.permissions.UserCompanyPermissions
import io.ehdev.timetracker.core.user.UserImpl

class CompanyInteractor {

    CompanyImpl company
    UserImpl currentUser

    CompanyInteractor(UserImpl user, CompanyImpl company){
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

    public void setName(String name){
        company.preformWrite(currentUser) { CompanyImpl company ->
            if(name){
                company.setName(name)
            }
        }
    }

    public void addSetUserAsAdmin(UserImpl... userList){
        company.preformAdmin(currentUser) { CompanyImpl company ->
            userList.each { UserImpl user ->
                setPermissionForUser(user, InternalPermission.ADMIN)
            }
        }
    }

    public void addSetUserAsWrite(UserImpl... userList){
        company.preformAdmin(currentUser) { CompanyImpl company ->
            userList.each { UserImpl user ->
                setPermissionForUser(user, InternalPermission.WRITE)
            }
        }
    }

    public void addSetUserAsRead(UserImpl... userList){
        company.preformAdmin(currentUser) { CompanyImpl company ->
            userList.each { UserImpl user ->
                setPermissionForUser(user, InternalPermission.READ)
            }
        }
    }

    public UserCompanyPermissions findUserPermissions(UserImpl user) {
        return company.permissions.find {
            it.refUser.uuid == user.uuid
        }
    }

    private void setPermissionForUser(UserImpl user, InternalPermission permission){

        UserCompanyPermissions permissions = findUserPermissions(user)

        if(!permissions){
            permissions = createPermissionForUser(user, permissions)
        }

        permissions.adminAccess = permission.adminFlag
        permissions.writeAccess = permission.writeFlag
        permissions.readAccess = permission.readFlag
    }

    public UserCompanyPermissions createPermissionForUser(UserImpl user, UserCompanyPermissions permissions) {
        permissions = new UserCompanyPermissions(
                refUser: user,
                company: company)
        company.permissions << permissions
        permissions
    }

    public void clearPermissions() {
        company.preformAdmin(currentUser) { CompanyImpl company ->
            company.permissions.clear()
            company.permissions << new UserCompanyPermissions(refUser: currentUser, company: company, adminAccess: true)
        }
    }
}
