package com.example.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Action;
@Repository
public interface ActionRepository extends CrudRepository<Action, Long> {

}

