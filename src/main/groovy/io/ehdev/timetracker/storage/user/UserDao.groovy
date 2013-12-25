package io.ehdev.timetracker.storage.user
import com.google.common.base.Optional
import io.ehdev.timetracker.core.user.User
import io.ehdev.timetracker.storage.Dao

interface UserDao extends Dao<User>{
    public Optional<User> getUserByUUID(String UUID);
}