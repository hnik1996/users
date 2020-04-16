package com.ecommerce.users.controller.v1;

import com.ecommerce.users.controller.dto.UserDTO;
import com.ecommerce.users.controller.exception.BadRequestException;
import com.ecommerce.users.controller.exception.SaveUserException;
import com.ecommerce.users.controller.exception.UserNotFoundException;
import com.ecommerce.users.model.User;
import com.ecommerce.users.service.user_service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class Controller {
    private final UserService userService;

    @Autowired
    public Controller(UserService userService) {
        this.userService = userService;
    }

    @ExceptionHandler(value = BadRequestException.class)
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public User getUser(@RequestParam(value = "id", required = false) Long id,
                        @RequestParam(value = "uid", required = false) String uid,
                        @RequestParam(value = "username", required = false) String username,
                        @RequestParam(value = "msisdn", required = false) String msisdn,
                        @RequestParam(value = "email", required = false) String email,
                        @RequestParam(value = "role", required = false) Character role) {
        if (id == null && uid == null && username == null && msisdn == null && email == null && role == null){
            throw new BadRequestException("all fields are null");
        }
        return userService.getUsers(id, uid, username, msisdn, email, role, 0, 1).get(0);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Object> deleteUser(@PathVariable("id") long id, @RequestBody UserDTO user) {
        try {
            userService.userDelete(id, user.getUpdaterUserId());
        }catch (Exception ignored){}
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ResponseBody
    public List<UserDTO> getUsers(@RequestParam(value = "id", required = false) Long id,
                               @RequestParam(value = "uid", required = false) String uid,
                               @RequestParam(value = "username", required = false) String username,
                               @RequestParam(value = "msisdn", required = false) String msisdn,
                               @RequestParam(value = "email", required = false) String email,
                               @RequestParam(value = "role", required = false) Character role,
                               @RequestParam(value = "start", defaultValue = "0") int start,
                               @RequestParam(value = "limitation", defaultValue = "100") int limitation) {
        return userService.getProfiles(id, uid, username, msisdn, email, role, start, limitation);
    }

    @RequestMapping(value = "/password/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public User updatePassword(@PathVariable("id") long id, @RequestBody User user) {
        userService.changePassword(id, user.getUpdaterUserId(), user.getPassword());
        return null;
    }

    @ExceptionHandler(value = SaveUserException.class)
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    public UserDTO saveUser(@RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    @ResponseBody
    public UserDTO getProfile(@RequestParam(value = "id", required = false) Long id,
                              @RequestParam(value = "uId", required = false) String uId,
                              @RequestParam(value = "userName", required = false) String username,
                              @RequestParam(value = "msisdn", required = false) String msisdn,
                              @RequestParam(value = "email", required = false) String email) {
        if (id == null && uId == null && username == null && msisdn == null && email == null) {
            return UserDTO.builder().build();
        }
        return userService.getProfiles(id, uId, username, msisdn, email, null, 0, 1).get(0);
    }

//    @RequestMapping(value = "/profile", method = RequestMethod.PUT)
//    @ResponseBody
//    public User updateProfile() {
//
//    }
}
