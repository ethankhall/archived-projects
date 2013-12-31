package io.ehdev.timetracker.security
import io.ehdev.timetracker.storage.user.InMemoryUserDao
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.openid.OpenIDAttribute
import org.springframework.security.openid.OpenIDAuthenticationToken
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

import static org.fest.assertions.Assertions.assertThat
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when
import static org.testng.collections.Lists.newArrayList

class SecureUserDetailsServiceTest {

    SecureUserDetailsService service
    InMemoryUserDao userDao
    @BeforeMethod
    public void setup() {
        service = new SecureUserDetailsService()
        userDao = new InMemoryUserDao()
        service.setUserDao(userDao)
    }

    @Test
    public void testRequestingUserThatDoesNotExist() throws Exception {
        OpenIDAuthenticationToken token = createToken()
        UserDetails details = service.loadUserDetails(token)
        assertThat(details.getUsername()).isSameAs("123")
        assertThat(userDao.storage).hasSize(1)
    }

    public OpenIDAuthenticationToken createToken() {
        OpenIDAuthenticationToken token = mock(OpenIDAuthenticationToken.class)
        when(token.getIdentityUrl()).thenReturn("123")
        when(token.getAttributes()).thenReturn(
                newArrayList(new OpenIDAttribute('lastname', 'something', ['joe']),
                        new OpenIDAttribute('email', 'something', ['joe@doe.com'])))
        token
    }

    @Test
    public void testCreateUser() throws Exception {
        def user = service.createNewUser(createToken())
        assertThat(user.uuid).isNotNull()
        assertThat(user.name).isEqualTo('joe')
        assertThat(user.email).isEqualTo('joe@doe.com')
    }
}
