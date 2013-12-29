package io.ehdev.timetracker.storage

import io.ehdev.timetracker.core.Storable

interface Dao<T extends Storable> {

    public String save(T object);

    public T getById(Integer id);
}
