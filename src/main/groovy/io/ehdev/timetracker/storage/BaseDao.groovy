package io.ehdev.timetracker.storage
import io.ehdev.timetracker.core.Storable
import org.hibernate.HibernateException
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired

import javax.transaction.Transactional

abstract class BaseDao<T extends Storable>  implements Dao<T> {

    @Autowired
    SessionFactory sessionFactory

    public Session getSession() throws HibernateException {
        try {
            return sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            return sessionFactory.openSession();
        }
    }

    @Transactional
    List<T> query(Closure query){
        def criteria = getSession().createCriteria(getBaseType())
        query.call(criteria)
        return (List<T>) criteria.list()
    }

    @Transactional
    public Integer save(T object){
        def session = getSession()
        session.saveOrUpdate(object)
        session.saveOrUpdate(object)
        return object.getId()
    }

    @Override
    List<Integer> save(T... objects) {
        return objects.collect {
            save(it)
        }
    }

    public T getById(Integer id){
        return (T)getSession().get(getBaseType(), id)
    }

    public T getByUuid(String uuid){
        def query = getSession().createQuery("from ${getBaseType().getSimpleName()} as obj where obj.uuid = :uuid")
        query.setParameter("uuid", uuid)
        return (T)query.uniqueResult()
    }

    abstract Class<T> getBaseType();
}
