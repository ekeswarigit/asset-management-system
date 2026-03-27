package com.asset.ams.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asset.ams.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

    Optional<Employee> findByEmail(String email);

    // boolean existsByEmpName(String empName);

    // Optional<Employee> findByEmail(String email);

    // List<Employee> findAll();

}
