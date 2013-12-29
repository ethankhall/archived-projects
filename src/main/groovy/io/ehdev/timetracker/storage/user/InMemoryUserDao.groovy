package io.ehdev.timetracker.storage.user
import com.google.common.base.Optional
import io.ehdev.timetracker.core.user.UserImpl
import io.ehdev.timetracker.storage.InMemoryBaseDao

class InMemoryUserDao extends InMemoryBaseDao<UserImpl> implements UserDao{
    @Override
    Optional<UserImpl> getUserByUUID(String UUID) {
        def foundUser = storage.find { key, value ->
            value.getUuid() == UUID
        }
        return Optional.fromNullable(foundUser.getValue())
    }
}
