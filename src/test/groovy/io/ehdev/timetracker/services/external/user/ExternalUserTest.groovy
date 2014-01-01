package io.ehdev.timetracker.services.external.user

import io.ehdev.timetracker.core.user.UserImpl
import org.testng.annotations.Test

import static org.fest.assertions.Assertions.assertThat

class ExternalUserTest {

    @Test
    public void testCreateExternalUser() throws Exception {
        UserImpl user = new UserImpl(authToken: "123", email: 'user1@domain.com', name: 'john doe', uuid: "uuid")
        assertThat(ExternalUser.convertUser(user)).isEqualTo(new ExternalUser(email: 'user1@domain.com', name: 'john doe', uuid: 'uuid'))
    }
}
