package com.streamlined.tasks.validator;

import com.streamlined.tasks.entity.Entity;

public interface Validator<T extends Entity<?>> {

    boolean isValid(T entity);

}
