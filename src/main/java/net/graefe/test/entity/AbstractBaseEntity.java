package net.graefe.test.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

@MappedSuperclass
public abstract class AbstractBaseEntity implements Serializable
{
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    public Long getId()
    {
        return this.id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (null == obj)
            return false;
        if (!getClass().equals(obj.getClass()))
            return false;

        final Long id = getId();
        final Long otherId = ((AbstractBaseEntity) obj).getId();
        if (id == null || otherId == null) {
            return super.equals(obj);
        }

        return id.equals(otherId);
    }

    @Override
    public int hashCode()
    {
        final Long id = getId();
        return id != null ? Objects.hash(id, getClass()) : super.hashCode();
    }

}
