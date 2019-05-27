package com.example.user.repository;

import com.example.user.entity.AddressInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<AddressInfo, Integer> {

    @Query(value = "SELECT address FROM addressinfo WHERE uid = :user_id", nativeQuery = true)
    public List<String> getAddressByUser(@Param("user_id") Integer user_id);

}
