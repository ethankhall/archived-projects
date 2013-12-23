package io.ehdev.timetracker.core.project
import io.ehdev.timetracker.core.user.UserNotAuthorizedToReadException
import io.ehdev.timetracker.core.user.UserNotAuthorizedToWriteException
import io.ehdev.timetracker.core.entry.LineItemEntry
import io.ehdev.timetracker.core.permissions.Permissions
import io.ehdev.timetracker.core.project.discount.Discount
import io.ehdev.timetracker.core.project.discount.DiscountFactory
import io.ehdev.timetracker.core.project.rate.Rate
import io.ehdev.timetracker.core.user.User

class ProjectImpl implements Project {

    @Delegate
    Rate rate
    @Delegate
    Discount discount = DiscountFactory.getNoDiscount()

    String name
    List<LineItemEntry> lineItems = []
    Permissions permissions;

    public List<LineItemEntry> getEntries(){
        return lineItems
    }

    BigDecimal getAmount(){
        return getAmount(lineItems)
    }

    public writeData(User user, Closure closure){
        if(permissions.canUserWrite(user)){
            return closure.call(this)
        } else {
            throw new UserNotAuthorizedToWriteException();
        }
    }

    public readData(User user, Closure closure){
        if(permissions.canUserRead(user)){
            return closure.call(this)
        } else {
            throw new UserNotAuthorizedToReadException();
        }
    }
}
