package io.ehdev.timetracker.storage

interface Dao<T> {

    public void save(T object);

    public T getById(Integer id);
}
