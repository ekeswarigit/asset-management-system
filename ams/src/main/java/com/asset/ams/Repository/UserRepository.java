package com.asset.ams.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.asset.ams.model.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User>{

    Optional<User> findByEmail(String email);

    List<User> findByDeletedFalse();

    Optional<User> findByuserName(String userName);

    @Query("SELECT u FROM User u WHERE " +
           "(:search IS NULL OR :search = '' OR " +
           "LOWER(u.userName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "CAST(u.userId AS string) LIKE CONCAT('%', :search, '%')) AND " +
           "(:role IS NULL OR :role = '' OR LOWER(u.role.roleName) = LOWER(:role))")
    Page<User> searchUsers(@Param("search") String search, @Param("role") String role, Pageable pageable);

}
