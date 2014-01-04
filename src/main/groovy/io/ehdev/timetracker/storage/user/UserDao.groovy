package io.ehdev.timetracker.storage.user

import io.ehdev.timetracker.core.user.UserImpl
import io.ehdev.timetracker.storage.Dao
import org.springframework.security.openid.OpenIDAuthenticationToken

interface UserDao extends Dao<UserImpl>{
    public UserImpl getUserByUUID(String UUID);
    public UserImpl getUserFromToken(String token);
    public UserImpl getUserFromToken(OpenIDAuthenticationToken token);
}