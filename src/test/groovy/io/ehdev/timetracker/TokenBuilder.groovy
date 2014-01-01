package io.ehdev.timetracker

import io.ehdev.timetracker.core.user.UserImpl
import org.springframework.security.openid.OpenIDAttribute
import org.springframework.security.openid.OpenIDAuthenticationToken

import static com.google.common.collect.Lists.newArrayList
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

class TokenBuilder {

    public static OpenIDAuthenticationToken build(String id, OpenIDAttribute... openIDAttributes){
        def token = mock(OpenIDAuthenticationToken.class)
        when(token.getIdentityUrl()).thenReturn(id)
        when(token.getAttributes()).thenReturn(newArrayList(openIDAttributes))
        return token
    }

    public static OpenIDAuthenticationToken build(String id, String name, String email){
        return build(id, new OpenIDAttribute('fullname', 'something', [name]), new OpenIDAttribute('email', 'something', [email]))
    }

    public static OpenIDAuthenticationToken build(UserImpl user){
        return build(user.authToken, user.name, user.email)
    }
}
