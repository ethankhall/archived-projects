package io.ehdev.timetracker.core.project

import io.ehdev.timetracker.core.entry.LineItemEntry
import io.ehdev.timetracker.core.user.User

class ProjectInteractor {

    Project project
    User user

    public void changeName(String newName){
        project.writeData(user){ project ->
            project.setName(newName)
        }
    }

    public void addWriteUser(User newWriteUser){
        project.writeData(user){ project ->
            project.getPermissions().writeAccess << newWriteUser
        }
    }

    public BigDecimal getCurrentTotal() {
        return project.readData(user){ project ->
            def baseRate = project.getAmount()
            return baseRate.subtract(project.getDiscountAmount(baseRate))
        }
    }

    public List<LineItemEntry> getEntries(){
        return project.readData(user){ project ->
            return project.getLineItems()
        }
    }


}
