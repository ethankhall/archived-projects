package io.ehdev.timetracker.core.project
import io.ehdev.timetracker.core.UserNotAuthorizedToWriteException
import io.ehdev.timetracker.core.entry.FixedTimeEntry
import io.ehdev.timetracker.core.project.discount.FixedRateDiscount
import io.ehdev.timetracker.core.permissions.BasePermissions
import io.ehdev.timetracker.core.project.rate.FixedBidRate
import io.ehdev.timetracker.core.user.User
import org.joda.time.DateTime
import org.testng.annotations.Test

import static org.fest.assertions.Assertions.assertThat

class ProjectInteractorTest {

    final private User writeUser = new User();
    final private User readUser = new User();

    ProjectInteractor interactor
    private ProjectImpl project

    private void setupInteractor(User user){
        project = new ProjectImpl(permissions: new BasePermissions(writeAccess: [writeUser], readAccess: [readUser]))
        interactor = new ProjectInteractor(user: user, project: project)
    }

    @Test
    public void testChangingNameWithWritePermissions() throws Exception {
        setupInteractor(writeUser)
        interactor.changeName("new name")
        assertThat(project.getName()).isEqualTo("new name")
    }

    @Test(expectedExceptions = UserNotAuthorizedToWriteException.class)
    public void testChangingNameWithReadPermissions() throws Exception {
        setupInteractor(readUser)
        interactor.changeName("new name")
    }

    @Test
    public void testAddNewWriteUser() throws Exception {
        setupInteractor(writeUser)
        interactor.addWriteUser(readUser)
        project.getPermissions().canUserWrite(readUser)
    }

    @Test
    public void testGetProjectAmount() throws Exception {
        setupInteractor(readUser)

        project.lineItems << createHourFixedTimeEntry(2)
        project.setRate(new FixedBidRate(rate: 1000))
        assertThat(interactor.getCurrentTotal()).isEqualTo(1000.00)
    }

    @Test
    public void testGettingEntries() throws Exception {
        setupInteractor(readUser)

        def entry = createHourFixedTimeEntry(2)
        project.lineItems << entry
        assertThat(interactor.getEntries()).hasSize(1)
        assertThat(interactor.getEntries()).contains(entry)
    }

    @Test
    public void testAddingLineItemThroughInteractor() throws Exception {
        setupInteractor(writeUser)

        def entry = createHourFixedTimeEntry(2)
        interactor.addLineEntry(entry)
        assertThat(interactor.getEntries()).hasSize(1)
        assertThat(interactor.getEntries()).contains(entry)
    }

    static public FixedTimeEntry createHourFixedTimeEntry(int offset) {
        def now = DateTime.now()
        return new FixedTimeEntry(startTime: now.minusHours(offset), endTime: now.plusHours(offset))
    }

    @Test
    public void testDiscountSetting() throws Exception {
        setupInteractor(writeUser)

        def discount = new FixedRateDiscount(10)
        interactor.setDiscount(discount)
        assertThat(interactor.getDiscount()).isEqualTo(discount)
    }

    @Test
    public void testRemovingEntry() throws Exception {
        setupInteractor(writeUser)
        def fourHourEntry = createHourFixedTimeEntry(2)
        def twoHourEntry = createHourFixedTimeEntry(1)
        def sixHourEntry = createHourFixedTimeEntry(3)
        interactor.addLineEntry(fourHourEntry)
        interactor.addLineEntry(twoHourEntry)
        interactor.addLineEntry(sixHourEntry)
        interactor.deleteEntry(twoHourEntry)
        assertThat(interactor.getEntries()).containsOnly(fourHourEntry, sixHourEntry)
    }

}
