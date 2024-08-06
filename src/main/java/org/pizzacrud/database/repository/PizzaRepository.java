package org.pizzacrud.database.repository;

import org.pizzacrud.database.entity.Pizza;
import org.springframework.data.repository.CrudRepository;

public interface PizzaRepository extends CrudRepository<Pizza, Integer> {
}
