package io.ehdev.timetracker.security

import com.google.common.base.Optional
import groovy.json.JsonBuilder
import groovy.util.logging.Slf4j
import io.ehdev.timetracker.core.user.User
import io.ehdev.timetracker.core.user.UserImpl
import io.ehdev.timetracker.storage.user.UserDao
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.openid.OpenIDAttribute
import org.springframework.security.openid.OpenIDAuthenticationToken
import org.springframework.stereotype.Repository

import javax.transaction.Transactional

@Repository
@Slf4j
@Transactional
class SecureUserDetailsService implements UserDetailsService, AuthenticationUserDetailsService<OpenIDAuthenticationToken> {

    @Autowired
    UserDao userDao

    /**
     * Implementation of {@code UserDetailsService}. We only need this to satisfy the {@code RememberMeServices}
     * requirements.
     */
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        def user = userDao.getUserFromToken(id)
        if (!user.isPresent()) {
            throw new UsernameNotFoundException(id);
        } else {
            return new SecureUserDetails(user.get())
        }
    }

    /**
     * Implementation of {@code AuthenticationUserDetailsService} which allows full access to the submitted
     * {@code Authentication} object. Used by the OpenIDAuthenticationProvider.
     */
    public UserDetails loadUserDetails(OpenIDAuthenticationToken token) {
        Optional<UserImpl> optionalSecureUser = userDao.getUserFromToken(token);

        if (optionalSecureUser.isPresent()) {
            return new SecureUserDetails(optionalSecureUser.get());
        }

        UserImpl user = createNewUser(token)
        SecureUserDetails secureUser = new SecureUserDetails(user);

        return secureUser;
    }

    UserImpl createNewUser(OpenIDAuthenticationToken token) {
        User user = new UserImpl()
        populateUserFromOpenId(user, token.getAttributes())
        user.setAuthToken(token.getIdentityUrl())
        user.setUuid(UUID.randomUUID().toString())
        userDao.save(user)
        log.debug("Creating new user: {}", new JsonBuilder(user).toPrettyString())
        return user
    }

    public UserImpl getUser(OpenIDAuthenticationToken token) {
        def user = userDao.getUserFromToken(token)
        if (user.isPresent()) {
            return user.get()
        } else {
            throw new RuntimeException("User not found")
        }
    }

    static void populateUserFromOpenId(UserImpl user, List<OpenIDAttribute> openIDAttributes) {
        String firstName = null, lastName = null, fullName = null;
        for (OpenIDAttribute attribute : openIDAttributes) {
            if (attribute.getName().equals("email")) {
                user.setEmail(attribute.getValues().get(0))
            }

            if (attribute.getName().equals("firstname")) {
                firstName = attribute.getValues().get(0);
            }

            if (attribute.getName().equals("lastname")) {
                lastName = attribute.getValues().get(0);
            }

            if (attribute.getName().equals("fullname")) {
                fullName = attribute.getValues().get(0);
            }
        }

        user.setName(generateName(firstName, lastName, fullName))
    }

    static String generateName(String firstName, String lastName, String fullName) {
        if (fullName != null) {
            return fullName
        } else {
            return StringUtils.trimToEmpty("${StringUtils.trimToEmpty(firstName)} ${StringUtils.trimToEmpty(lastName)}")
        }
    }
}
