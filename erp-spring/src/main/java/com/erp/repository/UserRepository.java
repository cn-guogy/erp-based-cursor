package com.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erp.entity.User;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByName(String userName);
    List<User> findByCompanyId(Integer companyId);
}
