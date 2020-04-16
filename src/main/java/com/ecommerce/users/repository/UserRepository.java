package com.ecommerce.users.repository;

import com.ecommerce.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "CALL user_update(:id, :adminId, :username, :msisdn, :firstName,  :password, :lastName,:role, :status, :email);", nativeQuery = true)
    User user_update(Long id,
                     Long adminId,
                     String username,
                     String msisdn,
                     String firstName,
                     String password,
                     String lastName,
                     Character role,
                     char status,
                     String email);

    @Query(value = "CALL user_update(:id, :adminId, :username, :msisdn, :firstName,  :password, :lastName,:role, :status, :email);", nativeQuery = true)
    void delete_user(Long id,
                     Long adminId,
                     String username,
                     String msisdn,
                     String firstName,
                     String password,
                     String lastName,
                     Character role,
                     char status,
                     String email);

    @Query(value = "CALL users_fetch(:id, :uid, :username, :msisdn, :email, :role, :from, :limit);", nativeQuery = true)
    List<User> users_fetch(Long id,
                          String uid,
                          String username,
                          String msisdn,
                          String email,
                          Character role,
                          int from,
                          int limit);

    @Query(value = "CALL change_password(:id, :UpdaterUserId, :password);", nativeQuery = true)
    void change_password(long id, long UpdaterUserId, String password);

}
