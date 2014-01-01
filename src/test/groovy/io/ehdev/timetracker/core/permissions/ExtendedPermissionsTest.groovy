package io.ehdev.timetracker.core.permissions
import io.ehdev.timetracker.core.user.User
import io.ehdev.timetracker.core.user.UserImpl
import org.testng.annotations.Test

import static org.fest.assertions.Assertions.assertThat

class ExtendedPermissionsTest {

    private final static User writeUser = UserImpl.newInstance(id: "1")
    private final static User readUser = UserImpl.newInstance(id: "2")

    private ExtendedPermissions createPermissionObject(readUser, writeUser){
        return ExtendedPermissions.newInstance(readAccess: [readUser], writeAccess: [writeUser])
    }

    @Test
    public void testReadPermissions() throws Exception {
        ExtendedPermissions permissions = createPermissionObject(readUser, writeUser)
        assertThat(permissions.canUserRead(readUser)).isTrue()
        assertThat(permissions.canUserRead(writeUser)).isTrue()
    }

    @Test
    public void testWritePermissions() throws Exception {
        ExtendedPermissions permissions = createPermissionObject(readUser, writeUser)
        assertThat(permissions.canUserWrite(writeUser)).isTrue()
        assertThat(permissions.canUserWrite(readUser)).isFalse()
    }

    @Test
    public void testParentReadPermissions() throws Exception {
        ExtendedPermissions parent = createPermissionObject(readUser, writeUser)
        ExtendedPermissions permissions = createPermissionObject(null, null)
        permissions.parentPermissions = parent
        assertThat(permissions.canUserRead(readUser)).isTrue()
        assertThat(permissions.canUserRead(writeUser)).isTrue()
    }

    @Test
    public void testParentWritePermissions() throws Exception {
        ExtendedPermissions parent = createPermissionObject(readUser, writeUser)
        ExtendedPermissions permissions = createPermissionObject(null, null)
        permissions.parentPermissions = parent
        assertThat(permissions.canUserWrite(writeUser)).isTrue()
        assertThat(permissions.canUserWrite(readUser)).isFalse()
    }

    @Test
    public void testUserAdmin() throws Exception {
        ExtendedPermissions parent = createPermissionObject(readUser, writeUser)
        ExtendedPermissions permissions = createPermissionObject(null, null)
        User adminUser = UserImpl.newInstance(id: "3")
        ExtendedPermissions companyPermissions = new ExtendedPermissions(adminAccess: [adminUser])
        parent.parentPermissions = companyPermissions
        permissions.parentPermissions = parent
        assertThat(permissions.canUserAdmin(adminUser)).isTrue()
        assertThat(permissions.canUserAdmin(readUser)).isFalse()
        assertThat(permissions.canUserAdmin(writeUser)).isFalse()
    }
}
