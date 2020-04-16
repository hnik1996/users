package com.ecommerce.users.service.user_service;

import com.ecommerce.users.controller.dto.UserDTO;
import com.ecommerce.users.model.User;

import java.util.List;

public interface UserService {

    List<User> getUsers(Long id, String uid, String username, String msisdn, String email, Character role, int start, int limitation);

    void userDelete(long id, long updaterId);

    void changePassword(long id, long updaterUserId, String password);

    UserDTO createUser(UserDTO userDTO);

    List<UserDTO> getProfiles(Long id, String uId, String username, String msisdn, String email, Character role, int start, int limitation);
}
