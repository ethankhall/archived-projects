package io.ehdev.timetracker.storage

import com.google.common.base.Optional
import io.ehdev.timetracker.core.user.User
import org.hibernate.Criteria
import org.hibernate.criterion.Example
import org.springframework.stereotype.Repository

import javax.transaction.Transactional

@Repository
class UserDao extends BaseDao<User>{

    @Transactional
    public Optional<User> getUserByUUID(String UUID){
        def results = query() { Criteria criteria ->
            Example example = Example.create(new User(uuid: UUID))
            criteria.add(example)
        }
        if(results.size() >= 1){
            return Optional.fromNullable(results[0])
        } else {
            return Optional.absent();
        }
    }

    @Override
    Class<User> getBaseType() {
        return User.class;
    }
}
