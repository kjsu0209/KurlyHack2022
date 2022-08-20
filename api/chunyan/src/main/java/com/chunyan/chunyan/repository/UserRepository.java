package com.chunyan.chunyan.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.chunyan.chunyan.dao.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
}
