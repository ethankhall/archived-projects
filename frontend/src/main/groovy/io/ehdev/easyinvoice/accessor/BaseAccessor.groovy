package io.ehdev.easyinvoice.accessor

interface BaseAccessor<T> {
    T get(def id)
    def save(T lineItem)
    def save(T... lineItem)
    def prune()
    def delete(String id)
}
