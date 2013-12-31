package io.ehdev.timetracker.core.user
import io.ehdev.timetracker.core.Storable
import org.apache.commons.lang3.builder.EqualsBuilder
import org.apache.commons.lang3.builder.HashCodeBuilder
import org.apache.commons.lang3.builder.ReflectionToStringBuilder

import javax.persistence.*

@Entity
@Table
class UserImpl implements User, Storable, Serializable {

    @Id
    @GeneratedValue
    Integer id

    @Column(unique = true)
    String uuid

    @Column
    String name

    @Column
    String email

    @Column
    String authToken

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public boolean equals(Object object) {
        return EqualsBuilder.reflectionEquals(this, object)
    }

    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this)
    }
}
