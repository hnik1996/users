package com.ecommerce.users.service.user_service;

import com.ecommerce.users.controller.dto.UserDTO;
import com.ecommerce.users.controller.exception.SaveUserException;
import com.ecommerce.users.controller.exception.UserNotFoundException;
import com.ecommerce.users.model.Attribute;
import com.ecommerce.users.model.User;
import com.ecommerce.users.model.UserDetail;
import com.ecommerce.users.repository.UserRepository;
import com.ecommerce.users.service.attribute_service.AttributeService;
import com.ecommerce.users.service.user_detail_service.UserDetailService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AttributeService attributeService;
    private final UserDetailService userDetailService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AttributeService attributeService, UserDetailService userDetailService) {
        this.userRepository = userRepository;
        this.attributeService = attributeService;
        this.userDetailService = userDetailService;
    }

    @Override
    public List<User> getUsers(Long id, String uid, String username, String msisdn, String email, Character role, int start, int limitation) {
        return userRepository.users_fetch(id, uid, username, msisdn, email, role, start, limitation);
    }

    @Override
    public void userDelete(long id, long updaterId) {
            userRepository.delete_user(id, updaterId, null, null, null, null, null, null, 'D', null);
    }

    @Override
    public void changePassword(long id, long updaterUserId, String password) {
        userRepository.change_password(id, updaterUserId, password);
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        char role = userDTO.getRole();
        char status = userDTO.getStatus();
        String userName = userDTO.getUserName();
        String msisdn = userDTO.getMsisdn();
        String email = userDTO.getEmail();
        String firstName = userDTO.getFirstName();
        String lastName = userDTO.getLastName();
        String password = userDTO.getPassword();
        JsonNode attributesJson = userDTO.getAttributes();
        Long creatorUserId = userDTO.getCreatorUserId();
        User user;

        try {
            user = userRepository.user_update(null,
                    creatorUserId,
                    userName,
                    msisdn,
                    firstName,
                    password,
                    lastName,
                    role,
                    status,
                    email);
        } catch (Exception e) {
            log.error("cannot create user with message {} in time {}", e.getMessage(), new Date());
            throw new SaveUserException("cannot save user with message " + e.getMessage());
        }

        List<Attribute> attributes = getAttributes();

        attributes.forEach(attribute -> {
            String dataType = attribute.getDataType();
            String key = attribute.getAttr();
            String id = attribute.getId();

            if (attributesJson.has(key)) {
                JsonNode attributeJsonValue = attributesJson.get(key);
                if (dataType.contains("A") && !dataType.contains("B")) {
                    if (attributeJsonValue.isArray()) {
                        attributeJsonValue.forEach(value -> {
                            if (dataType.contains("B")) {
                                userDetailService.save(user.getId(), creatorUserId, id, value.asText().equals("true") ? "1" : "0");
                            } else {
                                userDetailService.save(user.getId(), creatorUserId, id, value.asText());
                            }
                        });
                    }
                } else {
                    if (dataType.contains("B")) {
                        userDetailService.save(user.getId(), creatorUserId, id, attributeJsonValue.asText().equals("true") ? "1" : "0");
                    } else {
                        userDetailService.save(user.getId(), creatorUserId, id, attributeJsonValue.asText());
                    }
                }
            }
        });

        return UserDTO.builder().id(user.getId()).uid(user.getUid()).build();
    }


    @Override
    public List<UserDTO> getProfiles(Long id, String uId, String username, String msisdn, String email, Character role, int start, int limit) {
        List<User> users = userRepository.users_fetch(id, uId, username, msisdn, email, role, start, limit);
        List<UserDTO> usersDTO = new ArrayList<>();

        if (users == null || users.isEmpty()) {
            throw new UserNotFoundException("user not found");
        }

        users.forEach(user -> {
            List<UserDetail> userDetails = userDetailService.getUserDetails(user.getId());

            List<Attribute> attributes = getAttributes();

            ObjectNode userAttributes = JsonNodeFactory.instance.objectNode();

            attributes.forEach(attribute -> {
                List<UserDetail> allUserDetailsByAttributeId = userDetails.stream().filter(userDetail -> userDetail.getAttribute_id().equals(attribute.getId())).collect(Collectors.toList());
                if (allUserDetailsByAttributeId.size() > 0) {
                    String dataType = attribute.getDataType();
                    ArrayNode responseValue = JsonNodeFactory.instance.arrayNode();
                    String attributeName = attribute.getAttr();
                    fillAttributes(userAttributes, allUserDetailsByAttributeId, dataType, responseValue, attributeName);
                }
            });

            usersDTO.add(UserDTO
                    .builder()
                    .id(user.getId())
                    .uid(user.getUid())
                    .status(user.getStatus())
                    .userName(user.getUsername())
                    .msisdn(user.getMsisdn())
                    .email(user.getEmail())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .password(user.getPassword())
                    .role(user.getRole())
                    .createdTs(user.getCreatedTs())
                    .updatedTs(user.getUpdatedTs())
                    .creatorUserId(user.getCreatorUserId())
                    .updaterUserId(user.getUpdaterUserId())
                    .attributes(userAttributes)
                    .build());
        });

        return usersDTO;
    }

    private void fillAttributes(ObjectNode userAttributes, List<UserDetail> allUserDetailsByAttributeId, String dataType, ArrayNode responseValue, String attributeName) {
        if (dataType.contains("A")) {
            List<String> values = allUserDetailsByAttributeId.stream().map(UserDetail::getValue).collect(Collectors.toList());
            List<String> valueBlobs = allUserDetailsByAttributeId.stream().map(UserDetail::getValueBlob).collect(Collectors.toList());
            switch (dataType) {
                case "AS":
                    values.forEach(value -> {
                        if (value != null)
                            userAttributes.put(attributeName, responseValue.add(value));
                    });
                    valueBlobs.forEach(value -> {
                        if (value != null)
                            userAttributes.put(attributeName, responseValue.add(value));
                    });
                    break;
                case "AB":
                    values.forEach(value -> userAttributes.put(attributeName, responseValue.add(value.equals("1"))));
                    break;
                case "AN":
                    values.forEach(value -> userAttributes.put(attributeName, responseValue.add(Long.valueOf(value))));
                    break;
            }
        } else {
            String value;
            switch (dataType) {
                case "N":
                    value = allUserDetailsByAttributeId.get(0).getValue();
                    userAttributes.put(attributeName, Long.valueOf(value));
                    break;
                case "S":
                    value = allUserDetailsByAttributeId.get(0).getValue();
                    if (value == null || value.isEmpty())
                        value = allUserDetailsByAttributeId.get(0).getValueBlob();
                    userAttributes.put(attributeName, String.valueOf(value));
                    break;
                case "B":
                    value = allUserDetailsByAttributeId.get(0).getValue();
                    userAttributes.put(attributeName, value.equals("1"));
                    break;
            }
        }
    }

    private List<Attribute> getAttributes() {
        return attributeService.getAll();
    }
}
