package org.pizzacrud.database.repository;

import org.pizzacrud.database.entity.Pizza;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PizzaRepository extends CrudRepository<Pizza, Integer> {
    List<Pizza> findAll();
}
