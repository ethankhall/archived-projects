package io.ehdev.timetracker.core.project.permissions

import io.ehdev.timetracker.core.project.permissions.Permissions
import io.ehdev.timetracker.core.user.User
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

import static org.fest.assertions.Assertions.assertThat

class PermissionsTest {

    Permissions permissions;
    User writeUser;
    User readUser;

    @BeforeMethod
    public void setup(){
        writeUser = User.newInstance(id: "1")
        readUser = User.newInstance(id: "2")
        permissions = Permissions.newInstance(readAccess: [readUser], writeAccess: [writeUser])
    }

    @Test
    public void testReadPermissions() throws Exception {
        assertThat(permissions.canUserRead(readUser)).isTrue()
        assertThat(permissions.canUserRead(writeUser)).isTrue()
    }

    @Test
    public void testWritePermissions() throws Exception {
        assertThat(permissions.canUserWrite(writeUser)).isTrue()
        assertThat(permissions.canUserWrite(readUser)).isFalse()
    }
}
