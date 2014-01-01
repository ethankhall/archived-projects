package io.ehdev.timetracker.storage.user
import com.google.common.base.Optional
import io.ehdev.timetracker.core.user.UserImpl
import io.ehdev.timetracker.storage.InMemoryBaseDao
import org.springframework.security.openid.OpenIDAuthenticationToken

class InMemoryUserDao extends InMemoryBaseDao<UserImpl> implements UserDao{
    @Override
    Optional<UserImpl> getUserByUUID(String UUID) {
        def foundUser = InMemoryBaseDao.storage.find { key, value ->
            value.getUuid() == UUID
        }
        return Optional.fromNullable(foundUser?.getValue())
    }

    @Override
    Optional<UserImpl> getUserFromToken(String token) {
        def foundUser = InMemoryBaseDao.storage.find { key, value ->
            value.getAuthToken() == token
        }
        return Optional.fromNullable(foundUser?.getValue())
    }

    @Override
    Optional<UserImpl> getUserFromToken(OpenIDAuthenticationToken token) {
        return getUserFromToken(token.getIdentityUrl())
    }
}
