package io.ehdev.timetracker.security

import io.ehdev.timetracker.core.user.UserImpl
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.openid.OpenIDAuthenticationToken

interface SecureUserDetailsService extends UserDetailsService, AuthenticationUserDetailsService<OpenIDAuthenticationToken> {

    public UserImpl createNewUser(OpenIDAuthenticationToken token);

    public UserImpl getUser(OpenIDAuthenticationToken token);

}