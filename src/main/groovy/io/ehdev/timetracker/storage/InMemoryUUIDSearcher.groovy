package io.ehdev.timetracker.storage
import groovy.util.logging.Slf4j
import io.ehdev.timetracker.core.Storable

@Slf4j
class InMemoryUUIDSearcher<T extends Storable> {


    InMemoryBaseDao<T> dao

    InMemoryUUIDSearcher(InMemoryBaseDao<T> dao){
        this.dao = dao
    }

    public T getByUuid(String uuid) {
        def found = dao.getStorage().find { key, value ->
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
