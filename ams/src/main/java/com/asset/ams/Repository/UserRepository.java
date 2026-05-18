package com.asset.ams.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.asset.ams.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User>{

    Optional<User> findByEmail(String email);

    List<User> findByDeletedFalse();

    Optional<User> findByuserName(String userName);



    // boolean existsByEmpName(String empName);

    // Optional<Employee> findByEmail(String email);

    // List<Employee> findAll();

}
