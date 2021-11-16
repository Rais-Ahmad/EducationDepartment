package com.example.EducationDepartment.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EducationDepartment.Model.Permissions;

import java.util.List;
import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permissions, Long> {
	List<Permissions> findAllByIsActiveOrderByCreatedDateDesc(boolean status);

	Optional<Permissions> findByIdAndIsActive(long id, Boolean isActive);

}
