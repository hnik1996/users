package com.ecommerce.users.repository;

import com.ecommerce.users.model.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, String> {
    @Query(value = "CALL attributes_fetch();", nativeQuery = true)
    List<Attribute> attributes_fetch();
}
