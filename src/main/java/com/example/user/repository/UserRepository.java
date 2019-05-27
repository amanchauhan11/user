package com.example.user.repository;

import com.example.user.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Integer> {

    @Query(value = "SELECT * FROM userinfo WHERE userinfo.uname = :customer", nativeQuery = true)
    List<UserInfo> getUserByUname(@Param("customer") String customer);

}
