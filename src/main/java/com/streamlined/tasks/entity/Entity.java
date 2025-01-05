package com.streamlined.tasks.entity;

public interface Entity<T> {

    T getPrimaryKey();
    
    boolean isIdenticalTo(Entity<T> entity);

}
