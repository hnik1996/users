package com.ecommerce.users.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    private long id;
    private String uid;
    private char status;
    @Column(name = "username")
    private String userName;
    private String msisdn;
    private String email;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname")
    private String lastName;
    private String password;
    private char role;
    @Column(name = "created_ts")
    private Date createdTs;
    @Column(name = "updated_ts")
    private Date updatedTs;
    @Column(name = "creator_user_id")
    private long creatorUserId;
    @Column(name = "updater_user_id")
    private long updaterUserId;
}

