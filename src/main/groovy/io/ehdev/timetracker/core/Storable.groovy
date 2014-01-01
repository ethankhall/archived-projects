package io.ehdev.timetracker.core

interface Storable {

    Integer getId();
    void setId(Integer id)

    String getUuid();
    void setUuid(String uuid)
}
