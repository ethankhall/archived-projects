package io.ehdev.timetracker.security
import io.ehdev.timetracker.core.user.UserImpl
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails

class SecureUserDetails implements UserDetails {

    @Delegate
    UserImpl systemUser

    SecureUserDetails(UserImpl user) {
        systemUser = user
    }

    @Override
    Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList("ROLE_USER");
    }

    String getPassword(){
        return ""
    }

    String getUsername(){
        systemUser.getAuthToken()
    }

    boolean isEnabled() {
        return true
    }

    @Override
    boolean isAccountNonExpired() {
        return false
    }

    @Override
    boolean isAccountNonLocked() {
        return false
    }

    @Override
    boolean isCredentialsNonExpired() {
        return false
    }
}
