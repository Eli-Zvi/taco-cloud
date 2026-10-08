package com.eli.tacocloud.repository;

import com.eli.tacocloud.model.TacoUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<TacoUser, Long> {

    public TacoUser findByUsername(String username);
}
