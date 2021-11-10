package net.graefe.test.entity;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;

@EntityView(CatFood.class)
public interface CatFoodView
{
    @IdMapping
    Long getId();

    String getBrand();
}
