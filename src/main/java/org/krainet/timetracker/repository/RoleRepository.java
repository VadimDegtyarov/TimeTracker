package org.krainet.timetracker.repository;

import org.krainet.timetracker.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Optional<Role> findByName(String name);
}
