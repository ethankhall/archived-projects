package io.ehdev.easyinvoice.accessor

abstract class BaseAccessorImpl<T> implements BaseAccessor<T>{
    def storage = [ : ]

    T get(def id) {
        storage[id]
    }

    def save(T item) {
        storage[item.id] = item
    }

    def save(T... items) {
        items.each {
            save(it)
        }
    }

    def prune(){
        storage.clear()
    }
}
