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
@Table(name = "phonebook")
@Data
public class PhoneBook {
    @Id
    private long id;
    private char status;
    private long userId;
    private String name;
    private String msisdn;
    @Column(name = "created_ts")
    private Date createdTs;
    @Column(name = "updated_ts")
    private Date updatedTs;
    @Column(name = "creator_user_id")
    private long creatorUserId;
    @Column(name = "updated_user_id")
    private long updatedUserId;

}
