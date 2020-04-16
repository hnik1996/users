package com.ecommerce.users.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @Id
    @Column
    private long id;
    @Column
    private String uid;
    @Column
    private char status;
    @Column
    private String username;
    @Column
    private String msisdn;
    @Column
    private String email;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname")
    private String lastName;
    @Column
    private String password;
    @Column
    private Character role;
    @Column(name = "created_ts")
    private Date createdTs;
    @Column(name = "updated_ts")
    private Date updatedTs;
    @Column(name = "creator_user_id")
    private Long creatorUserId;
    @Column(name = "updater_user_id")
    private Long updaterUserId;
}

