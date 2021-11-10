package net.graefe.test.entity;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.Mapping;

@EntityView(Cat.class)
public interface CatView
{
    @IdMapping
    Long getId();

    String getName();

    @Mapping("favoriteFood")
    CatFood getFavoriteFood();
    
}
