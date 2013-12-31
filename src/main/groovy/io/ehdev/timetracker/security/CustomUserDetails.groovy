package io.ehdev.timetracker.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

class CustomUserDetails extends User {
    String email;
    String name;
    boolean newUser

    CustomUserDetails(String username, Collection<GrantedAuthority> authorities) {
        super(username, "unused", authorities);
    }
}
