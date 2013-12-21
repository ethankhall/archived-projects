package io.ehdev.timetracker.core.project
import io.ehdev.timetracker.core.UserNotAuthorizedToWriteException
import io.ehdev.timetracker.core.project.permissions.Permissions
import io.ehdev.timetracker.core.user.User
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

import static org.fest.assertions.Assertions.assertThat

class ProjectInteractorTest {

    ProjectInteractor interactor
    private User writeUser
    private User readUser
    private Project project

    @BeforeMethod
    public void setup() {
        writeUser = new User()
        readUser = new User()
    }

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


}
