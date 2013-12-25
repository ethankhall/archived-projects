package io.ehdev.timetracker.storage.user
import com.google.common.base.Optional
import io.ehdev.timetracker.core.user.User
import io.ehdev.timetracker.storage.InMemoryBaseDao

class InMemoryUserDao extends InMemoryBaseDao<User> implements UserDao{
    @Override
    Optional<User> getUserByUUID(String UUID) {
        def foundUser = storage.find { key, value ->
            value.getUuid() == UUID
        }
        return Optional.fromNullable(foundUser)
    }
}
