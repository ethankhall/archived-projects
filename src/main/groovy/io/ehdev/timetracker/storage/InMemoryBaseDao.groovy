package io.ehdev.timetracker.storage

class InMemoryBaseDao<T> implements Dao<T>{

    static final HashMap<Integer, T> storage

    @Override
    void save(T object) {
        storage[object.getId()] = object
    }

    @Override
    T getById(Integer id) {
        storage[id]
    }
}
