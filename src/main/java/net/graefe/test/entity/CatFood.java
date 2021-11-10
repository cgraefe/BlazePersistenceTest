package net.graefe.test.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "cat_food")
public class CatFood extends AbstractBaseEntity
{
    @Column(name="code", nullable = false, unique = true)
    private Long code;

    private String brand;

    public CatFood(Long id, Long code, String brand)
    {
        this.setId(id);
        this.code = code;
        this.brand = brand;
    }

    public CatFood()
    {
        
    }

    public Long getCode()
    {
        return code;
    }

    public void setCode(Long code)
    {
        this.code = code;
    }

    public String getBrand()
    {
        return brand;
    }

    public void setBrand(String brand)
    {
        this.brand = brand;
    }
}
