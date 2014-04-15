package io.ehdev.timetracker.storage

import io.ehdev.timetracker.core.Storable

interface Dao<T extends Storable> {

    public Integer save(T object);

    public List<Integer> save(T... object);

    public T getById(Integer id);

    public void delete(T object);
}
