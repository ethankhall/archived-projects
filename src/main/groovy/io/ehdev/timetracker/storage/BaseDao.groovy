package io.ehdev.timetracker.storage

import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

import javax.transaction.Transactional

@Repository
abstract class BaseDao<T> {

    @Autowired
    SessionFactory sessionFactory

    @Transactional
    public List<T> query(Closure query){
        def criteria = sessionFactory.openSession().createCriteria(getBaseType())
        query.call(criteria)
        return (List<T>) criteria.list()
    }

    @Transactional
    public void saveUser(T object){
        sessionFactory.openSession().save(object)
    }

    public T getObjectFromId(Integer id){
        return (T)sessionFactory.openSession().get(getBaseType(), id)
    }

    abstract Class<T> getBaseType();
}
