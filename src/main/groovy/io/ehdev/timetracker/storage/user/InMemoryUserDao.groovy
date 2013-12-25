package io.ehdev.timetracker.storage.user
import com.google.common.base.Optional
import io.ehdev.timetracker.core.user.User

class InMemoryUserDao implements UserDao{

    static final HashMap<Integer, User> storage

    @Override
    void save(User user) {
        storage[user.getId()] = user
    }

    @Override
    User getById(Integer id) {
        storage[id]
    }

    @Override
    Optional<User> getUserByUUID(String UUID) {
        def foundUser = storage.find { key, value ->
            value.getUuid() == UUID
        }
        return Optional.fromNullable(foundUser)
    }
}
