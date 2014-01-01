package io.ehdev.timetracker.storage

import groovy.util.logging.Slf4j
import io.ehdev.timetracker.core.Storable

@Slf4j
class InMemoryBaseDao<T extends Storable> implements Dao<T>{

    static HashMap<Integer, T> storage = new HashMap<Integer, T>();

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

    @Override
    T getByUuid(String uuid) {
        def found = storage.find { key, value ->
            log.debug("Searching {}, {}", key, value)
            value.getUuid() == uuid
        }
        if(found != null){
            return found.value
        } else {
            return null
        }
    }
}
