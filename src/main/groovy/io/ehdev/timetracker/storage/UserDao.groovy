package io.ehdev.timetracker.storage
import io.ehdev.timetracker.core.user.User
import org.springframework.stereotype.Repository

@Repository
class UserDao extends BaseDao<User>{

    @Override
    Class<User> getBaseType() {
        return User.class;
    }
}
