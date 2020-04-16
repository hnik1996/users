package com.ecommerce.users.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private Long id;
    private String uid;
    private Character status;
    @JsonProperty("username")
    private String userName;
    private String msisdn;
    private String email;
    @JsonProperty("firstname")
    private String firstName;
    @JsonProperty("lastname")
    private String lastName;
    private String password;
    private Character role;
    @JsonProperty("created_ts")
    private Date createdTs;
    @JsonProperty("updated_ts")
    private Date updatedTs;
    @JsonProperty("creator_user_id")
    private Long creatorUserId;
    @JsonProperty("updater_user_id")
    private Long updaterUserId;
    private JsonNode attributes;
}
