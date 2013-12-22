package io.ehdev.timetracker.core.project
import io.ehdev.timetracker.core.UserNotAuthorizedToWriteException
import io.ehdev.timetracker.core.entry.FixedTimeEntry
import io.ehdev.timetracker.core.project.permissions.Permissions
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
        project = new ProjectImpl(permissions: new Permissions(writeAccess: [writeUser], readAccess: [readUser]))
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

        def now = DateTime.now()
        project.lineItems.add(new FixedTimeEntry(startTime: now.minusHours(2), endTime: now.plusHours(2)))
        project.setRate(new FixedBidRate(rate: 1000))
        assertThat(interactor.getCurrentTotal()).isEqualTo(1000.00)
    }

    @Test
    public void testGettingEntries() throws Exception {
        setupInteractor(readUser)
        def now = DateTime.now()

        def entry = new FixedTimeEntry(startTime: now.minusHours(2), endTime: now.plusHours(2))
        project.lineItems.add(entry)
        assertThat(interactor.getEntries()).hasSize(1)
        assertThat(interactor.getEntries()).contains(entry)
    }


}
