package com.ecommerce.users.repository;

import com.ecommerce.users.model.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetail, Long>{
    @Query(value = "CALL user_attribute_create(:userId, :creatorId, :attributeId, :attributeValue);", nativeQuery = true)
    void user_attribute_create(Long userId, Long creatorId, String attributeId, String attributeValue);

    @Query(value = "CALL user_attribute_update(:id, :updaterUserId, :status, :value, :valueBlob, :userId, :attributeId);", nativeQuery = true)
    void user_attribute_update(long id, long updaterUserId, char status, String value, String valueBlob, long userId, String attributeId);

    @Query(value = "CALL user_attributes_fetch(:id);", nativeQuery = true)
    List<UserDetail> user_attributes_fetch(Long id);
}
