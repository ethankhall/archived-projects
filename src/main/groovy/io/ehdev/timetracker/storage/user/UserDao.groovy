package io.ehdev.timetracker.storage.user
import com.google.common.base.Optional
import io.ehdev.timetracker.core.user.UserImpl
import io.ehdev.timetracker.storage.Dao
import org.springframework.security.openid.OpenIDAuthenticationToken

interface UserDao extends Dao<UserImpl>{
    public Optional<UserImpl> getUserByUUID(String UUID);
    public Optional<UserImpl> getUserFromToken(String token);
    public Optional<UserImpl> getUserFromToken(OpenIDAuthenticationToken token);
}