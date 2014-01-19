package io.ehdev.timetracker.core.permissions

import com.fasterxml.jackson.annotation.JsonIgnore
import io.ehdev.timetracker.core.project.ProjectImpl
import org.apache.commons.lang3.builder.EqualsBuilder
import org.apache.commons.lang3.builder.HashCodeBuilder
import org.apache.commons.lang3.builder.ReflectionToStringBuilder

import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table
class UserProjectPermissions extends ExtendedPermissions{

    @ManyToOne
    @JsonIgnore
    ProjectImpl project

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
