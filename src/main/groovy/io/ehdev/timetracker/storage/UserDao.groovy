package io.ehdev.timetracker.storage
import io.ehdev.timetracker.core.user.User
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import javax.transaction.Transactional

@Service
class UserDao {

    @Autowired
    private SessionFactory sessionFactory

    @Transactional
    public void saveUser(User user){
        sessionFactory.openSession().save(user)
    }

}
