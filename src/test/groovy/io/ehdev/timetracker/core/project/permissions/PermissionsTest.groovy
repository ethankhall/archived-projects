package io.ehdev.timetracker.core.project.permissions
import io.ehdev.timetracker.core.user.User
import org.testng.annotations.Test

import static org.fest.assertions.Assertions.assertThat

class PermissionsTest {

    private final static User writeUser = User.newInstance(id: "1")
    private final static User readUser = User.newInstance(id: "2")

    private Permissions createPermissionObject(readUser, writeUser){
        return Permissions.newInstance(readAccess: [readUser], writeAccess: [writeUser])
    }

    @Test
    public void testReadPermissions() throws Exception {
        Permissions permissions = createPermissionObject(readUser, writeUser)
        assertThat(permissions.canUserRead(readUser)).isTrue()
        assertThat(permissions.canUserRead(writeUser)).isTrue()
    }

    @Test
    public void testWritePermissions() throws Exception {
        Permissions permissions = createPermissionObject(readUser, writeUser)
        assertThat(permissions.canUserWrite(writeUser)).isTrue()
        assertThat(permissions.canUserWrite(readUser)).isFalse()
    }

    @Test
    public void testParentReadPermissions() throws Exception {
        Permissions parent = createPermissionObject(readUser, writeUser)
        Permissions permissions = createPermissionObject(null, null)
        permissions.parentPermissions = parent
        assertThat(permissions.canUserRead(readUser)).isTrue()
        assertThat(permissions.canUserRead(writeUser)).isTrue()
    }

    @Test
    public void testParentWritePermissions() throws Exception {
        Permissions parent = createPermissionObject(readUser, writeUser)
        Permissions permissions = createPermissionObject(null, null)
        permissions.parentPermissions = parent
        assertThat(permissions.canUserWrite(writeUser)).isTrue()
        assertThat(permissions.canUserWrite(readUser)).isFalse()
    }
}
