package net.graefe.test.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "cat")
public class Cat extends AbstractBaseEntity
{

    private String name;

    @ManyToOne
    @JoinColumn(referencedColumnName = "code")
    private CatFood favoriteFood;

    public Cat(Long id, String name, CatFood favoriteFood)
    {
        this.setId(id);
        this.name = name;
        this.favoriteFood = favoriteFood;
    }

    public Cat()
    {
        
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public CatFood getFavoriteFood()
    {
        return favoriteFood;
    }

    public void setFavoriteFood(CatFood favoriteFood)
    {
        this.favoriteFood = favoriteFood;
    }
}
