package com.example.EducationDepartment.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EducationDepartment.Model.Roles;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Roles, Long> {
    List<Roles> findAllByIsActiveOrderByCreatedDateDesc(Boolean isActive);
    Optional<Roles> findByIdAndIsActive(long id, Boolean isActive);

}
