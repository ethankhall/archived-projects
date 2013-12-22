package io.ehdev.timetracker.core.permissions
import io.ehdev.timetracker.core.user.User
import org.testng.annotations.Test

import static org.fest.assertions.Assertions.assertThat

class BasePermissionsTest {

    private final static User writeUser = User.newInstance(id: "1")
    private final static User readUser = User.newInstance(id: "2")

    private BasePermissions createPermissionObject(readUser, writeUser){
        return BasePermissions.newInstance(readAccess: [readUser], writeAccess: [writeUser])
    }

    @Test
    public void testReadPermissions() throws Exception {
        BasePermissions permissions = createPermissionObject(readUser, writeUser)
        assertThat(permissions.canUserRead(readUser)).isTrue()
        assertThat(permissions.canUserRead(writeUser)).isTrue()
    }

    @Test
    public void testWritePermissions() throws Exception {
        BasePermissions permissions = createPermissionObject(readUser, writeUser)
        assertThat(permissions.canUserWrite(writeUser)).isTrue()
        assertThat(permissions.canUserWrite(readUser)).isFalse()
    }

    @Test
    public void testParentReadPermissions() throws Exception {
        BasePermissions parent = createPermissionObject(readUser, writeUser)
        BasePermissions permissions = createPermissionObject(null, null)
        permissions.parentPermissions = parent
        assertThat(permissions.canUserRead(readUser)).isTrue()
        assertThat(permissions.canUserRead(writeUser)).isTrue()
    }

    @Test
    public void testParentWritePermissions() throws Exception {
        BasePermissions parent = createPermissionObject(readUser, writeUser)
        BasePermissions permissions = createPermissionObject(null, null)
        permissions.parentPermissions = parent
        assertThat(permissions.canUserWrite(writeUser)).isTrue()
        assertThat(permissions.canUserWrite(readUser)).isFalse()
    }

    @Test
    public void testUserAdmin() throws Exception {
        BasePermissions parent = createPermissionObject(readUser, writeUser)
        BasePermissions permissions = createPermissionObject(null, null)
        User adminUser = User.newInstance(id: "3")
        ExtendedPermissions companyPermissions = new ExtendedPermissions(adminAccess: [adminUser])
        parent.parentPermissions = companyPermissions
        permissions.parentPermissions = parent
        assertThat(permissions.canUserAdmin(adminUser)).isTrue()
        assertThat(permissions.canUserAdmin(readUser)).isFalse()
        assertThat(permissions.canUserAdmin(writeUser)).isFalse()
    }
}
