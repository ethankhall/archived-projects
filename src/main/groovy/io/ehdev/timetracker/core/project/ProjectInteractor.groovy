package io.ehdev.timetracker.core.project

import io.ehdev.timetracker.core.company.Company
import io.ehdev.timetracker.core.entry.LineItemEntry
import io.ehdev.timetracker.core.permissions.UserProjectPermissions
import io.ehdev.timetracker.core.project.discount.Discount
import io.ehdev.timetracker.core.project.rate.Rate
import io.ehdev.timetracker.core.user.User
import io.ehdev.timetracker.core.user.UserImpl

class ProjectInteractor {

    Project project
    UserImpl authUser

    public void changeName(String newName){
        project.preformWrite(authUser){ project ->
            project.setName(newName)
        }
    }

    public void addWriteUser(User newWriteUser){
        project.preformWrite(authUser){ ProjectImpl project ->
            project.getPermissions() << new UserProjectPermissions(refUser: newWriteUser, project: project, writeAccess: true)
        }
    }

    void addReadUser(UserImpl newReadUser) {
        project.preformWrite(authUser){ ProjectImpl project ->
            project.getPermissions() << new UserProjectPermissions(refUser: newReadUser, project: project, readAccess: true)
        }
    }

    public BigDecimal getCurrentTotal() {
        return project.preformRead(authUser){ project ->
            def baseRate = project.getAmount()
            return baseRate.subtract(project.getDiscountAmount(baseRate))
        }
    }

    public List<LineItemEntry> getEntries(){
        return project.preformRead(authUser){ project ->
            return project.getLineItems()
        }
    }

    public void addLineEntry(LineItemEntry lineItemEntry){
        project.preformWrite(authUser){ project ->
            project.lineItems << lineItemEntry
        }
    }

    public void setDiscount(Discount discount){
        project.preformWrite(authUser){ project ->
            project.discount = discount
        }
    }

    public Discount getDiscount(){
        return project.preformRead(authUser){ project ->
            project.getDiscount()
        }
    }

    public void deleteEntry(entry){
        project.preformWrite(authUser){ project ->
            project.lineItems.remove(entry)
        }
    }

    static ProjectImpl createNewProject(Company company, Rate rate, String name) {
        return new ProjectImpl(company: company, name: name, uuid: UUID.randomUUID().toString(), rate: rate)
    }

    static ProjectImpl createNewProject(User user, Rate rate, String name) {
        def project = new ProjectImpl(name: name, uuid: UUID.randomUUID().toString(), rate: rate)
        def permissions = new UserProjectPermissions(refUser: user, adminAccess: true, project: project)
        project.permissions << permissions
        return project
    }
}
