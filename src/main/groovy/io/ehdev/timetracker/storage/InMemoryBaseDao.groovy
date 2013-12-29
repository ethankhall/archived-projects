package io.ehdev.timetracker.storage

import io.ehdev.timetracker.core.Storable

class InMemoryBaseDao<T extends Storable> implements Dao<T>{

    static final HashMap<Integer, T> storage = new HashMap<Integer, T>();

    @Override
    Integer save(T object) {
        object.setId((int)(Math.random() * 1000L))
        storage[object.getId()] = object
        object.getId()
    }

    @Override
    T getById(Integer id) {
        storage[id]
    }
}
