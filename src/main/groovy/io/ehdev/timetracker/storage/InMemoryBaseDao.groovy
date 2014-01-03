package io.ehdev.timetracker.storage

import groovy.util.logging.Slf4j
import io.ehdev.timetracker.core.Storable

@Slf4j
abstract class InMemoryBaseDao<T extends Storable> implements Dao<T>{

    abstract public Map<Integer, T> getStorage();

    @Override
    Integer save(T object) {
        object.setId((int)(Math.random() * 1000L))
        storage[object.getId()] = object
        object.getId()
    }

    @Override
    List<Integer> save(T... objects) {
        return objects.collect {
            save(it)
        }
    }

    @Override
    T getById(Integer id) {
        storage[id]
    }
}
